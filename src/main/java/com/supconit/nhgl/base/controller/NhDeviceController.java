package com.supconit.nhgl.base.controller;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.entities.Device;
import com.supconit.base.entities.DeviceCategory;
import com.supconit.base.entities.GeoArea;
import com.supconit.base.services.DeviceCategoryService;
import com.supconit.base.services.DeviceService;
import com.supconit.base.services.GeoAreaService;
import com.supconit.common.utils.Constant;
import com.supconit.common.utils.excel.ExcelImportHelper;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.hl.common.utils.excel.ExcelTemplate;
import com.supconit.hl.montrol.entity.DeviceAlarmLevel;
import com.supconit.hl.montrol.entity.DeviceTag;
import com.supconit.hl.montrol.service.DeviceAlarmLevelService;
import com.supconit.hl.montrol.service.DeviceTagService;
import com.supconit.hl.systemrule.entities.SystemRule;
import com.supconit.hl.systemrule.service.SystemRuleService;
import com.supconit.honeycomb.mvc.utils.UploadUtils;
import com.supconit.honeycomb.mvc.utils.UploadUtils.UploadRenamePolicy;
import com.supconit.honeycomb.mvc.utils.UploadUtils.UploadedFile;
import com.supconit.montrol.entity.MAlarmLevel;
import com.supconit.montrol.service.IAlarmLevelService;
import com.supconit.nhgl.analyse.electric.area.entities.NhArea;
import com.supconit.nhgl.base.entities.NhDevice;
import com.supconit.nhgl.base.service.NhDeviceService;
import com.supconit.nhgl.basic.ngArea.entities.NgArea;
import com.supconit.nhgl.basic.ngArea.service.NgAreaService;
import com.supconit.nhgl.basic.ngDept.entities.ExNhDepartment;
import com.supconit.nhgl.basic.ngDept.entities.NhDepartment;
import com.supconit.nhgl.basic.ngDept.service.NhDepartmentService;

@Controller
@RequestMapping("nhgl/nhDevice")
public class NhDeviceController extends BaseControllerSupport {
	private transient static final Logger log = LoggerFactory
			.getLogger(NhDeviceController.class);
	@Value("${electric_category}")
	private String dcategoryCode;
	@Value("${water_category}")
	private String scategoryCode;
	@Value("${gas_category}")
	private String qcategoryCode;
	@Value("${energy_category}")
	private String encategoryCode;
	@Value("${file.tmpsave}")
	private String tmpPath;

	@Autowired
	private NhDeviceService nhDeviceService;
	@Autowired
	private NgAreaService ngAreaService;
	@Autowired
	private IAlarmLevelService alarmLevelService;
	@Autowired
	private GeoAreaService geoAreaService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private DeviceCategoryService deviceCategoryService;
	@Autowired
	private SystemRuleService systemRuleService;
	@Autowired
	private DeviceTagService deviceTagService;
	@Autowired
	private DeviceAlarmLevelService deviceAlarmLevelService;
	@Autowired
	private NhDepartmentService nhDeptService;

	@RequestMapping(value = "devices", method = RequestMethod.GET)
	public String device(ModelMap model, String flag) {
		String category = "";
		String deviceName = "";
		Integer nhtype = 1;
		switch (Integer.valueOf(flag)) {
		case 1:
			category = dcategoryCode;
			nhtype = Constant.NH_TYPE_D;
			deviceName = "电表";
			break;
		case 2:
			category = scategoryCode;
			nhtype = Constant.NH_TYPE_S;
			deviceName = "水表";
			break;
		case 3:
			category = qcategoryCode;
			nhtype = Constant.NH_TYPE_Q;
			deviceName = "蒸汽表";
			break;
		case 4:
			category = encategoryCode;
			nhtype = Constant.NH_TYPE_EN;
			deviceName = "能量表";
			break;
		default:
			category = "";
			break;

		}
		List<NgArea> treeListArea = ngAreaService.findTree();
		model.put("treeList", treeListArea);
		model.put("deviceName", deviceName);
		model.put("category", category);
		model.put("nhtype", nhtype);
		return "nhgl/base/nhDevice/list";
	}

	@RequestMapping("devicePage")
	@ResponseBody
	public Pageable<NhDevice> devicePage(@ModelAttribute NhDevice condition,
			Pagination<NhDevice> pager, ModelMap model) {
		return nhDeviceService.findByCondition(pager, condition);
	}

	@RequestMapping(value = "preImp")
	public String preImp(ModelMap model) {
		return "nhgl/base/nhDevice/nhDevice_imp";
	}

	@RequestMapping(value = "impNhDevice", method = RequestMethod.POST)
	public void impNhDevice(HttpServletRequest request,
			HttpServletResponse response) {
		List<String> lstErrMsg = new ArrayList<String>();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		UploadedFile saveFile = null;
		try {
			MultiValueMap<String, UploadedFile> fileMap = UploadUtils
					.uploadFiles(request, tmpPath, Integer.MAX_VALUE, "UTF-8",
							new UploadRenamePolicy() {
								@Override
								public String rename(String s, String s1) {
									String postfix = s1.substring(
											s1.indexOf(".") + 1, s1.length());
									return UUID.randomUUID().toString() + "."
											+ postfix;
								}
							}, null);

			List<UploadedFile> file = fileMap.get("file");
			if (file != null) {
				saveFile = file.get(0);
			}
		} catch (Exception e) {
			log.error("上传出错");
			lstErrMsg.add("上传出错<br>");
		}
		String msg = "";
		try {
			if (saveFile != null && saveFile.getFile() != null) {
				File excel = saveFile.getFile();
				InputStream in = new FileInputStream(excel);
				nhDeviceService.importNhDevice(in, lstErrMsg);
				for (String s : lstErrMsg) {
					msg += s + "<br>";
				}
				if (StringUtils.isNotEmpty(msg)) {
					String prefix = "error,导入失败,原因:<br>";
					if (msg.indexOf(prefix) == -1)
						msg = prefix + msg;
				} else {
					msg = "导入成功!";
				}
			}
		} catch (Exception e) {
			msg = "error,导入失败,原因:<br>" + e.getMessage();
			log.error("导入能耗设备档案出错.");
		} finally {
			response.setContentType("text/html;charset=UTF-8");
			try {
				response.getWriter().write(msg);
				response.getWriter().flush();
			} catch (IOException e) {
				msg = "error,导入失败,原因:<br>" + e.getMessage();
				log.error("导入能耗设备档案出错");
			}
		}
	}

	@ResponseBody
	@RequestMapping(value = "expDevice", method = RequestMethod.GET)
	public void expSystemRule(HttpServletResponse response) {
		try {
			URL url = getClass().getResource("/templates/newNhDeviceExpTemplate.xls");
			String urlStr = url.getFile();
			ExcelTemplate temp = new ExcelTemplate();
			ExcelTemplate excelTemplate = temp.readTemplatePath(urlStr);

			List<MAlarmLevel> alarmLevels = alarmLevelService.findList();
			expAlarmLevel(excelTemplate, alarmLevels);

			excelTemplate.initTemplateMulti(1);
			List<GeoArea> areas = geoAreaService.findAll();
			if (areas != null) {
				for (GeoArea g : areas) {
					Long pid = g.getParentId();
					if (pid != null) {
						GeoArea pg = (GeoArea) geoAreaService.getById(pid);
						if ((pg != null) && (pg.getAreaCode() != null)) {
							g.setParentName(pg.getAreaCode());
						}
					}
				}
			}
			expGeoArea(excelTemplate, areas);

			excelTemplate.initTemplateMulti(2);
			List<DeviceCategory> cates = deviceCategoryService.findAll();
			if (cates != null) {
				for (DeviceCategory dc : cates) {
					Long pid = dc.getParentId();
					if (pid != null) {
						if (pid.longValue() == 0L) {
							dc.setParentName("");
						} else {
							DeviceCategory dcate = (DeviceCategory) deviceCategoryService
									.getById(pid);
							if ((dcate != null)
									&& (dcate.getCategoryCode() != null)) {
								dc.setParentName(dcate.getCategoryCode());
							}
						}
					}
				}
			}

			expDeviceCategory(excelTemplate, cates);

			excelTemplate.initTemplateMulti(3);
			List<Device> devices = deviceService.findAll();
			Device root = null;
			if (devices != null) {
				for (Device dd : devices) {
					if (dd.getId().longValue() == 0L) {
						root = dd;
					}
					Long pid = dd.getParentId();
					if (pid != null) {
						Device pd = (Device) deviceService.getById(pid);
						if ((pd != null) && (pd.getHpid() != null)) {
							dd.setParentName(pd.getHpid());
						}
					}

					Long sytemRuleId = dd.getgSystemRuleId();
					if (sytemRuleId != null) {
						SystemRule rule = (SystemRule) systemRuleService
								.getById(sytemRuleId);
						if ((rule != null) && (rule.getCode() != null)) {
							dd.setgSystemRuleName(rule.getCode());
						}
					}

					Long catagoryId = dd.getCategoryId();
					if (catagoryId != null) {
						DeviceCategory category = (DeviceCategory) deviceCategoryService
								.getById(catagoryId);
						if ((category != null)
								&& (category.getCategoryCode() != null)) {
							dd.setCategoryCode(category.getCategoryCode());
						}
					}

					Long locationId = dd.getLocationId();
					if (locationId != null) {
						GeoArea area = (GeoArea) geoAreaService
								.getById(locationId);
						if ((area != null) && (area.getAreaCode() != null)) {
							dd.setLocationCode(area.getAreaCode());
						}
					}
				}
			}
			devices.remove(root);
			expDevice(excelTemplate, devices);

			excelTemplate.initTemplateMulti(4);
			List<DeviceTag> tags = deviceTagService.findAll();
			expDeviceTag(excelTemplate, tags);

			excelTemplate.initTemplateMulti(5);
			List<SystemRule> rules = systemRuleService.findAll();
			if (rules != null) {
				for (SystemRule rule : rules) {
					Long pid = rule.getParentId();
					if (pid != null) {
						SystemRule rr = (SystemRule) systemRuleService
								.getById(pid);
						if ((rr != null) && (rr.getName() != null)) {
							rule.setParentName(rr.getName());
						}
					}
				}
			}
			expSystemRule(excelTemplate, rules);

			excelTemplate.initTemplateMulti(6);
			List<ExNhDepartment> nhDeptlist = nhDeptService.findAll();
			Map<Long, ExNhDepartment> parentMap;
		    if (!nhDeptlist.isEmpty())
		    {
		      Set<Long> set = new HashSet();
		      for (ExNhDepartment nhDepartment : nhDeptlist) {
		        set.add(nhDepartment.getPid());
		      }
		      List<ExNhDepartment> parents = nhDeptService.findByIds(set);
		      if (!CollectionUtils.isEmpty(parents))
		      {
		        parentMap = new HashMap();
		        for (ExNhDepartment d : parents) {
		          parentMap.put(d.getId(), d);
		        }
		        for (NhDepartment d : nhDeptlist) {
		          if (null != d.getPid()) {
		            d.setParent((NhDepartment)parentMap.get(d.getPid()));
		          }
		        }
		      }
		    }
			expNhDept(excelTemplate, nhDeptlist);
			
			excelTemplate.initTemplateMulti(7);
			List<NgArea> nhAreaList = ngAreaService.findAll();
			expNgArea(excelTemplate, nhAreaList);

			String filename = "newNhDeviceExpTemplate"+System.currentTimeMillis()+".xls";
			response.reset();
			response.addHeader("Content-Disposition", "attachment;filename="
					+ URLEncoder.encode(filename, "UTF-8"));

			response.setContentType("application/octet-stream");
			excelTemplate.writeToStream(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void expSystemRule(ExcelTemplate excelTemplate, List<SystemRule> result) {
		if (result != null) {
			for (SystemRule sr : result) {
				excelTemplate.creatNewRow();
				excelTemplate.createNewCol(sr.getCode());
				excelTemplate.createNewCol(sr.getName());
				excelTemplate.createNewCol(sr.getParentName());
			}
		}
		Map<String, String> datas = new HashMap();
		excelTemplate.replaceFind(datas);
	}

	private void expDeviceTag(ExcelTemplate excelTemplate, List<DeviceTag> result) {
		if (result != null) {
			for (DeviceTag dt : result) {
				excelTemplate.creatNewRow();
				excelTemplate.createNewCol(dt.getMonitorID());
				excelTemplate.createNewCol(dt.getTagName());
				excelTemplate.createNewCol(dt.getTagDesc());
				excelTemplate.createNewCol(dt.getTagUnit());
				excelTemplate.createNewCol(dt.getTextRules());
				String tagTypeId = dt.getTagType();
				if ((tagTypeId != null) && (!"".equals(tagTypeId))) {
					tagTypeId = tagTypeId.trim();
					if (tagTypeId.equals("13")) {
						excelTemplate.createNewCol("\u5F00\u5173\u91CF");
					} else if (tagTypeId.equals("14")) {
						excelTemplate.createNewCol("\u6A21\u62DF\u91CF");
					} else if (tagTypeId.equals("15")) {
						excelTemplate.createNewCol("\u5B57\u7B26\u4E32");
					} else {
						excelTemplate.createNewCol("");
					}
				} else {
					excelTemplate.createNewCol("");
				}

				Long isWritable = dt.getIsWrite();
				if (isWritable != null) {
					if (isWritable.longValue() == 1L) {
						excelTemplate.createNewCol("\u662F");
					} else if (isWritable.longValue() == 0L) {
						excelTemplate.createNewCol("\u5426");
					} else {
						excelTemplate.createNewCol("");
					}
				} else {
					excelTemplate.createNewCol("");
				}

				Long type = dt.getAlarmPoint();
				if (type != null) {
					if (type.longValue() == 1L) {
						excelTemplate.createNewCol("\u62A5\u8B66\u70B9");
					} else if (type.longValue() == 0L) {
						excelTemplate.createNewCol("\u8FD0\u884C\u70B9");
					} else if (type.longValue() == 3L) {
						excelTemplate.createNewCol("\u6545\u969C\u70B9");
					} else if (type.longValue() == 2L) {
						excelTemplate.createNewCol("\u8D77\u505C\u70B9");
					} else {
						excelTemplate.createNewCol("");
					}
				} else {
					excelTemplate.createNewCol("");
				}
				DeviceAlarmLevel con = new DeviceAlarmLevel();
				con.setTagId(dt.getId());
				List<DeviceAlarmLevel> levels = deviceAlarmLevelService
						.findList(con);
				String on = null;
				String off = null;
				String hh = null;
				String h = null;
				String ll = null;
				String l = null;
				String prin = null;
				String nrin = null;
				Long alarmLevelId = Long.valueOf(0L);
				MAlarmLevel al = null;
				for (DeviceAlarmLevel level : levels) {
					alarmLevelId = level.getAlarmLevelId();
					al = (MAlarmLevel) alarmLevelService.getById(alarmLevelId);
					if (al != null) {
						if (level.getAlarmType().equals("ON")) {
							on = al.getAlarmLevel();
						}
						if (level.getAlarmType().equals("OFF")) {
							off = al.getAlarmLevel();
						}
						if (level.getAlarmType().equals("HH")) {
							hh = al.getAlarmLevel();
						}
						if (level.getAlarmType().equals("H")) {
							h = al.getAlarmLevel();
						}
						if (level.getAlarmType().equals("LL")) {
							ll = al.getAlarmLevel();
						}
						if (level.getAlarmType().equals("L")) {
							l = al.getAlarmLevel();
						}
						if (level.getAlarmType().equals("PRIN")) {
							prin = al.getAlarmLevel();
						}
						if (level.getAlarmType().equals("NRIN")) {
							nrin = al.getAlarmLevel();
						}
					}
				}

				excelTemplate.createNewCol(on);
				excelTemplate.createNewCol(off);
				excelTemplate.createNewCol(hh);
				excelTemplate.createNewCol(h);
				excelTemplate.createNewCol(ll);
				excelTemplate.createNewCol(l);
				excelTemplate.createNewCol(prin);
				excelTemplate.createNewCol(nrin);
			}
		}

		Map<String, String> datas = new HashMap();
		excelTemplate.replaceFind(datas);
	}

	private void expAlarmLevel(ExcelTemplate excelTemplate, List<MAlarmLevel> result) {
		if (result != null) {
			for (MAlarmLevel ml : result) {
				excelTemplate.creatNewRow();
				excelTemplate.createNewCol(ml.getAlarmLevel());
				excelTemplate.createNewCol(ml.getRemark());
				excelTemplate.createNewCol(Long.valueOf(ml.getAlarmLNum()));
			}
		}

		Map<String, String> datas = new HashMap();
		excelTemplate.replaceFind(datas);
	}

	private void expDeviceCategory(ExcelTemplate excelTemplate, List<DeviceCategory> result) {
		if (result != null) {
			for (DeviceCategory dc : result) {
				excelTemplate.creatNewRow();
				excelTemplate.createNewCol(dc.getCategoryName());
				if (dc.getParentName() != null) {
					excelTemplate.createNewCol(dc.getParentName());
				} else {
					excelTemplate.createNewCol("");
				}
				excelTemplate.createNewCol(dc.getCategoryCode());
				excelTemplate.createNewCol(dc.getSortIndex());
				excelTemplate.createNewCol(dc.getRemark());
			}
		}

		Map<String, String> datas = new HashMap();
		excelTemplate.replaceFind(datas);
	}

	private void expDevice(ExcelTemplate excelTemplate, List<Device> result) {
		if (result != null) {
			for (Device dd : result) {
				excelTemplate.creatNewRow();
				excelTemplate.createNewCol(dd.getHpid());
				excelTemplate.createNewCol(dd.getDeviceName());
				if (dd.getParentName() != null) {
					excelTemplate.createNewCol(dd.getParentName());
				} else {
					excelTemplate.createNewCol("");
				}
				excelTemplate.createNewCol(dd.getCategoryCode());
				excelTemplate.createNewCol(dd.getLocationCode());
				if (dd.getThreeDimdisplay() != null) {
					if (dd.getThreeDimdisplay().longValue() == 1L) {
						excelTemplate.createNewCol("\u662F");
					} else {
						excelTemplate.createNewCol("\u5426");
					}
				} else {
					excelTemplate.createNewCol("");
				}

				if (dd.getMapDisplay() != null) {
					if (dd.getMapDisplay().longValue() == 1L) {
						excelTemplate.createNewCol("\u662F");
					} else {
						excelTemplate.createNewCol("\u5426");
					}
				} else {
					excelTemplate.createNewCol("");
				}

				excelTemplate.createNewCol(dd.getgSystemRuleName());
				excelTemplate.createNewCol(dd.getDeviceCode());
				excelTemplate.createNewCol(dd.getDiscipinesCode());
				excelTemplate.createNewCol(dd.getEnergyCode());
				excelTemplate.createNewCol(dd.getDeviceSpec());
				excelTemplate.createNewCol(dd.getAssetsCode());
				excelTemplate.createNewCol(dd.getBarcode());
				excelTemplate.createNewCol(dd.getManagePersonIds());
				excelTemplate.createNewCol(dd.getManagePersonName());
				excelTemplate.createNewCol(dd.getManageDepartmentId());
				excelTemplate.createNewCol(dd.getUseDepartmentId());
				excelTemplate.createNewCol(dd.getSortIdx());
			}
		}
		Map<String, String> datas = new HashMap();
		excelTemplate.replaceFind(datas);
	}

	private void expGeoArea(ExcelTemplate excelTemplate, List<GeoArea> result) {
		if (result != null) {
			for (GeoArea ga : result) {
				excelTemplate.creatNewRow();
				excelTemplate.createNewCol(ga.getAreaName());
				excelTemplate.createNewCol(ga.getParentName());
				excelTemplate.createNewCol(ga.getAreaCode());
				excelTemplate.createNewCol(ga.getSort());
				excelTemplate.createNewCol(ga.getRemark());
			}
		}

		Map<String, String> datas = new HashMap();
		excelTemplate.replaceFind(datas);
	}

	private void expNhDept(ExcelTemplate excelTemplate, List<ExNhDepartment> result) {
		if (result != null) {
			for (ExNhDepartment dept : result) {
				excelTemplate.creatNewRow();
				excelTemplate.createNewCol(dept.getName());
				excelTemplate.createNewCol(dept.getParent()==null? "":dept.getParent().getCode());
				excelTemplate.createNewCol(dept.getCode());
				excelTemplate.createNewCol(dept.getPersons());
				excelTemplate.createNewCol(dept.getArea());
			}
		}

		Map<String, String> datas = new HashMap();
		excelTemplate.replaceFind(datas);
	}
	
	private void expNgArea(ExcelTemplate excelTemplate, List<NgArea> result) {
		if (result != null) {
			for (NgArea area : result) {
				excelTemplate.creatNewRow();
				excelTemplate.createNewCol(area.getAreaName());
				excelTemplate.createNewCol(area.getParentName());
				excelTemplate.createNewCol(area.getAreaCode());
				excelTemplate.createNewCol(area.getSort());
				excelTemplate.createNewCol(area.getPersons());
				excelTemplate.createNewCol(area.getArea());
				excelTemplate.createNewCol(area.getRemark());
			}
		}
		
		Map<String, String> datas = new HashMap();
		excelTemplate.replaceFind(datas);
	}

}
