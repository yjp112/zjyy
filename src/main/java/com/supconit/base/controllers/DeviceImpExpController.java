package com.supconit.base.controllers;

import com.supconit.base.entities.Device;
import com.supconit.base.entities.DeviceCategory;
import com.supconit.base.entities.GeoArea;
import com.supconit.base.services.*;
import com.supconit.common.utils.UtilTool;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.honeycomb.business.authorization.constants.SettingConstants;

/*import com.supconit.ywgl.utils.ListUtils;
import com.supconit.ywgl.utils.excel.ExcelReader;
import com.supconit.ywgl.utils.excel.ExcelTemplate;*/
import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.*;

@Controller
@RequestMapping("device/imp_exp")
public class DeviceImpExpController extends BaseControllerSupport {

	@Autowired
	private DeviceService deviceService;
	@Autowired
	private DevicePiontsService devicePiontsService;
	@Autowired
	private DevicePropertyService devicePropertyService;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private DeviceCategoryService deviceCategoryService;
	@Autowired
	private GeoAreaService geoAreaService;

    @Autowired
    private DeviceImpExpService deviceImpExpService;


	@Autowired
	private DeviceStartStopRecordsService deviceStartStopRecordsService;

	@Value("${image.server.url}")
	private String imageServerUrl;
	@Value("${image.temp.server.url}")
	private String imageTempServerUrl;

	/**
	 * @param
	 * @方法描述:list
	 */
	@RequestMapping(value = "exp")
	public String expDeviceList(ModelMap model) {
		List<GeoArea> treeListLou = geoAreaService.findTree();
		model.put("treeListLou", treeListLou);
		List<DeviceCategory> treeListType = deviceCategoryService.findAll();
		model.put("treeListType", treeListType);
		return "base/device/device_dev_list";
	}
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public ScoMessage delete( @RequestParam("ids") Long[] ids) {
		deviceService.deleteByIds(ids);	
		return ScoMessage.success("device/imp_exp/exp",ScoMessage.DELETE_SUCCESS_MSG);
	}
	
	/*
	 * show datagrid
	 */
	@ResponseBody
	@RequestMapping("expDevicePage")
	public Pageable<Device> expDevicePage( Pagination<Device> pager,
			@ModelAttribute Device device, ModelMap model) {
        return deviceService.findByCondition(pager, device);
	}

/*
	*/
/**
	 * @param
	 * @方法描述:导出设备档案excel
	 *//*

	@ResponseBody
	@RequestMapping(value = "expDevice", method = RequestMethod.GET)
	public void expDevice(Device device, HttpServletResponse response) {
		try {
			URL url = this.getClass().getResource("/deviceExpTemplate.xls");
			String urlStr = url.getFile();
			ExcelTemplate temp = new ExcelTemplate();
			ExcelTemplate excel = temp.readTemplatePath(urlStr);

			List<Device> List = deviceService.findByExpDevice(device);

			if (!UtilTool.isEmptyList(List)) {
				for (int i = 0; i < List.size(); i++) {
					Device d = List.get(i);
					excel.creatNewRow();
					// excel.createNewCol(i + 1);
					excel.createNewCol(d.getHpid());
					excel.createNewCol(d.getDeviceName());
					excel.createNewCol(d.getCategoryName());
					excel.createNewCol(d.getDeviceSpec());
					excel.createNewCol(d.getDeviceCode());
					excel.createNewCol(d.getBarcode());
					excel.createNewCol(d.getAssetsCode());
					excel.createNewCol(d.getManagePersonIds());
					excel.createNewCol(d.getManagePersonName());
					excel.createNewCol(d.getUpdator());
					excel.createNewCol(d.getSmallType());
				}
			}
			Map<String, String> datas = new HashMap<String, String>();
			// datas.put("title", + "device");
			excel.replaceFind(datas);
			// ===========================
			String filename = "device.xls";
			response.reset();
			response.addHeader("Content-Disposition", "attachment;filename="
					+ java.net.URLEncoder.encode(filename, "UTF-8"));
			response.setContentType("application/octet-stream");
			excel.writeToStream(response.getOutputStream());
			// ===========================
			// excel.writeToFile("C://联系人.xls");
			/*
			 * response.reset();
			 * response.setContentType("application/vnd.ms-excel"); OutputStream
			 * os = response.getOutputStream();// 取得输出流 excel.writeToStream(os);
			 *//*

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * @param
	 * @方法描述:导入
	 */
	@RequestMapping(value = "imp")
	public String impDeviceList(ModelMap model) {
		return "base/device/device_dev_imp";
	}


    /**
     * @param
     * @方法描述:导入
     */
    @RequestMapping(value = "impall")
    public String impDeviceAll(ModelMap model) {
        return "base/device/device_all_imp";
    }
/*

	@ResponseBody
	@RequestMapping("impDevice")
	public void impDevice(@RequestParam MultipartFile file,
			HttpServletResponse response) {

		String msg = "";
		List<String> lstErrMsg = new ArrayList<String>();
		try {
			Long befor = System.currentTimeMillis();
			if (file != null) {
				Map<String, List> map = new ExcelReader()
						.readExcelContentDevice(file.getInputStream());
				lstErrMsg = map.get("lstErrMsg");
				List<String> lstHpid = map.get("lstHpid");
				List<Device> lstDevice = map.get("lstDevice");
				Map<String, Long> mapCategory = new HashMap<String, Long>();
				Map<String, Long> mapAreaId = new HashMap<String, Long>();
				Map<String, String> mapAreaFullName = new HashMap<String, String>();
				if (UtilTool.isEmptyList(lstErrMsg)) {
					List<DeviceCategory> lstCategory = deviceCategoryService
							.findAll();
					List<GeoArea> lstGeoArea = geoAreaService.findTree();
					Date createDate = new Date();
					for (Device d : lstDevice) {
						// -----类别验证-----
						boolean bolExists = false;
						for (DeviceCategory dc : lstCategory) {
							if (dc.getCategoryName()
									.equals(d.getCategoryName())) {
								d.setCategoryId(dc.getId());
								bolExists = true;
								break;
							}
						}
						if (!bolExists) {
							lstErrMsg.add("此设备类别(" + d.getCategoryName()
									+ ")在设备类别表里不存在。");
							break;
						}

						// -----区域验证-----
						bolExists = false;
						for (GeoArea g : lstGeoArea) {
							if (d.getHpid().length() > 5
									&& g.getAreaCode().equals(
											d.getHpid().substring(0, 5))) {
								d.setLocationId(g.getId());
								d.setLocationName(g.getFullLevelName());
								bolExists = true;
								break;
							}
						}
						if (!bolExists) {
							lstErrMsg.add("此hpid(" + d.getHpid()
									+ ")所表示的楼层区域在地理区域表里不存在。");
							break;
						}

						// not null 字段补充
						if (UtilTool.isEmpty(d.getDeviceCode())) {
							d.setDeviceCode(d.getHpid() + "#");
						}
						d.setUseDepartmentId(1L);// USE_DEPARTMENT_ID BIGINT NOT
													// NULL,
						d.setManageDepartmentId(1L);// MANAGE_DEPARTMENT_ID
													// BIGINT NOT NULL,
						d.setTimeAfterMaintain(0L);// TIME_AFTER_MAINTAIN INT
													// NOT NULL,
						d.setTotalRunningTime(0D);// TOTAL_RUNNING_TIME NUMERIC
													// (19, 1) NOT NULL,
						d.setSpecialStatus(0L);// SPECIAL_STATUS INT NOT NULL,
						d.setStatus(0L);// STATUS INT NOT NULL,
						d.setCreateId(1L);// CREATE_ID BIGINT NOT NULL,
						d.setCreateDate(createDate);// CREATE_DATE DATETIME NOT
													// NULL,
					}

					// -----hpid重复验证-----
					if (UtilTool.isEmptyList(lstErrMsg)) {
						List<List<String>> partitionList = ListUtils.partition(
                                lstHpid, 200);
						System.out.println("resultList.size():"
								+ partitionList.size());
						for (List<String> listSub : partitionList) {
							Device d = new Device();
							d.setLstDeviceCode(new ArrayList<String>());
							d.getLstDeviceCode().addAll(listSub);
							List<Device> lstExists = deviceService
									.findByParam(d);
							for (Device d_ : lstExists) {
								lstErrMsg.add("此hpid(" + d_.getHpid()
										+ ")在设备表里已经存在，不能重复插入。");
							}
						}
					}
				}

				if (UtilTool.isEmptyList(lstErrMsg)) {// 验证通过
					System.out.println("插入数据库开始");
					deviceService.insertForImp(lstDevice);
					System.out.println("插入数据库结束");
					Long after = System.currentTimeMillis();
					msg = "导入成功,此次导入" + lstDevice.size() + "条数据";
					System.out.println("导入结束，此次导入" + lstDevice.size()
							+ "条数据,共耗时：" + (after - befor) + "毫秒");
				} else {
					msg = "error,导入失败,原因:<br>";
					for (String s : lstErrMsg) {
						msg += s + "<br>";
					}
				}
			}

		} catch (Exception e) {
			msg = "error,导入失败,原因:<br>" + e.getMessage();
			System.out.println("导入device出错:");
			e.printStackTrace();

		} finally {
			response.setContentType("text/html;charset=UTF-8");
			try {
				response.getWriter().write(msg);
				response.getWriter().flush();
			} catch (IOException e) {
				msg = "error,导入失败,原因:<br>" + e.getMessage();
				System.out.println("导入device出错:");
				e.printStackTrace();
			}
		}
	}
*/



    @ResponseBody
    @RequestMapping("impDeviceAll")
    public void impDeviceAll(HttpServletRequest request,
                          HttpServletResponse response) {
        PrintWriter print = null;
        try {
            print = response.getWriter();
        } catch (IOException e1) {
            e1.printStackTrace();
            print.print("0");
        }
        int maxPostSize = 52428800;//50m大小
        int a = request.getContentLength();
        if(a>maxPostSize){ print.print("1");return;}

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
        servletFileUpload.setSizeMax(maxPostSize);

        List<FileItem> fileItems = null;
        try {
            fileItems = servletFileUpload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
            print.print("0");
        }
        if(CollectionUtils.isEmpty(fileItems)) print.print("0");
        FileItem item = (FileItem)fileItems.get(0);







        String msg = "";
        try {
            List<String> errors =deviceImpExpService.importDevice(item.getInputStream());

        } catch (Exception e) {
            msg = "error,导入失败,原因:<br>" + e.getMessage();
            System.out.println("导入device出错:");
            e.printStackTrace();

        } finally {
            response.setContentType("text/html;charset=UTF-8");
            try {
                response.getWriter().write(msg);
                response.getWriter().flush();
            } catch (IOException e) {
                msg = "error,导入失败,原因:<br>" + e.getMessage();
                System.out.println("导入device出错:");
                e.printStackTrace();
            }
        }
    }

}
