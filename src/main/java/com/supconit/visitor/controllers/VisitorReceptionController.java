package com.supconit.visitor.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.base.domains.Organization;
import com.supconit.base.entities.Attachment;
import com.supconit.base.entities.EnumDetail;
import com.supconit.base.honeycomb.extend.ExPerson;
import com.supconit.base.services.AttachmentService;
import com.supconit.base.services.OrganizationService;
import com.supconit.common.utils.DateUtils;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.excel.ExcelExport;
import com.supconit.common.utils.OrganizationUtils;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.honeycomb.business.organization.entities.Department;
import com.supconit.honeycomb.business.organization.services.DepartmentService;
import com.supconit.honeycomb.business.organization.services.PersonService;
import com.supconit.visitor.entities.Visitor;
import com.supconit.visitor.entities.VisitorReservation;
import com.supconit.visitor.services.VisitorReservationService;

import hc.base.domains.AjaxMessage;
import hc.base.domains.Pageable;
import hc.base.domains.Pagination;

/**
 * 访客接待控制类
 * 
 * @author huanghaitao
 * @日期 2015/06
 */
@Controller
@RequestMapping("visitor/reception")
public class VisitorReceptionController extends BaseControllerSupport {
	@Autowired
	private VisitorReservationService reservationVisitorService;
	@Autowired
	private PersonService personService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private DepartmentService deptService;
	@Autowired
	private AttachmentService attachmentService;
	private static Pagination<VisitorReservation> cache;
	
	@Value("${file.tmpsave}")
	private String tmpPath;
	@Value("${image.server.url}")
	private String imageServerUrl;
	@Value("${image.temp.server.url}")
	private String imageTempServerUrl;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String go(ModelMap model) {

		List<Department> deptList = deptService.findAllWithoutVitualRoot();
		model.addAttribute("treeList", deptList);
		List<EnumDetail> visitorStatusList = DictUtils.getDictList(DictTypeEnum.VISIT_STATUS);
		model.put("visitorStatusList", visitorStatusList);
		return "visitor/reception/list";
	}

	@RequestMapping(value = "listAll", method = RequestMethod.GET)
	public String goAll(ModelMap model) {
		List<Department> deptList = deptService.findAllWithoutVitualRoot();
		model.addAttribute("treeList", deptList);
		List<EnumDetail> visitorStatusList = DictUtils.getDictList(DictTypeEnum.VISIT_STATUS);
		model.put("visitorStatusList", visitorStatusList);
		return "visitor/reception/list_all";
	}

	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public Pageable<VisitorReservation> list(Pagination<VisitorReservation> pager,
			@ModelAttribute VisitorReservation reservationVisitor) {
		boolean b = hasAdminRole();
		boolean c = hasVisitorRole();
		if (!b) {
			reservationVisitor.setReceptorID(getCurrentPersonId());
		}
		if (c) {
			reservationVisitor.setReceptorID(null);
		}
		if (null != reservationVisitor.getDeptNameId() && reservationVisitor.getDeptNameId().longValue() == DEFAULT_DEPARTMENTID) {
			reservationVisitor.setDeptNameId(null);
		}
		if (reservationVisitor.isFlag() == true || "1".equals(reservationVisitor.getVisitStatus())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String s = sdf.format(new Date());
			try {
				reservationVisitor.setVisitTime(sdf.parse(s));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		reservationVisitorService.findByPages(pager, reservationVisitor);
		cache = pager;
		return pager;
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) Boolean viewOnly, ModelMap model,
			@RequestParam(required = false) Long id, @RequestParam(required = false) String type,
			@RequestParam(required = false) Boolean view) {
		Date now = new Date();
		VisitorReservation reservationVisitor = null;
		List<Attachment> listImage = new ArrayList<Attachment>();
		ExPerson receptor = null;
		ExPerson grantPerson = null;
		ExPerson revokePerson = null;
		model.put("imagePath", imageServerUrl);
		model.put("imageTempPath", imageTempServerUrl);
		// 修改
		viewOnly = false;
		if (null != id) {
			reservationVisitor = reservationVisitorService.findById(id);
			listImage = attachmentService.getAttachmentByFid(id, "visitor");
			// 查询接待人员
			receptor = personService.getById(reservationVisitor.getReceptorID());
			List<Organization> orList = organizationService
					.getFullDeptNameByPersonId(reservationVisitor.getReceptorID());
			String deptFullName = OrganizationUtils.joinFullDeptName(orList);
			receptor.setDeptName(deptFullName);
			// 查询发证人
			if (null == reservationVisitor.getGrantPersonID()) {
				reservationVisitor.setGrantPersonID(getCurrentPersonId());
			}
			grantPerson = personService.getById(reservationVisitor.getGrantPersonID());
			orList = organizationService.getFullDeptNameByPersonId(reservationVisitor.getGrantPersonID());
			deptFullName = OrganizationUtils.joinFullDeptName(orList);
			grantPerson.setDeptName(deptFullName);
			// 查询收证人
			if (null == reservationVisitor.getRevokePersonID()) {
				reservationVisitor.setRevokePersonID(getCurrentPersonId());
			}
			revokePerson = personService.getById(reservationVisitor.getRevokePersonID());
			orList = organizationService.getFullDeptNameByPersonId(reservationVisitor.getRevokePersonID());
			deptFullName = OrganizationUtils.joinFullDeptName(orList);
			revokePerson.setDeptName(deptFullName);

			if (reservationVisitor.getActualVisitTime() == null && reservationVisitor.getGrantTime() == null
					&& "1".equals(type)) {
				reservationVisitor.setGrantPersonID(getCurrentPersonId());
				reservationVisitor.setActualVisitTime(now);
				reservationVisitor.setGrantTime(now);
			}
			if (reservationVisitor.getActualLeaveTime() == null && reservationVisitor.getRevokeTime() == null
					&& "2".equals(type) && view == false) {
				reservationVisitor.setRevokePersonID(getCurrentPersonId());
				reservationVisitor.setActualLeaveTime(now);
				reservationVisitor.setRevokeTime(now);
			}
			viewOnly = true;
		}
		List<EnumDetail> visitorTypeList = DictUtils.getDictList(DictTypeEnum.VISIT_REASON);
		List<EnumDetail> visitorZJLXList = DictUtils.getDictList(DictTypeEnum.ZJLX);
		List<EnumDetail> visitorRETURNENList = DictUtils.getDictList(DictTypeEnum.VISIT_RETURN_CARD);
		model.put("visitorTypeList", visitorTypeList);
		model.put("visitorZJLXList", visitorZJLXList);
		model.put("visitorRETURNENList", visitorRETURNENList);
		model.put("reservationVisitor", reservationVisitor);
		model.put("listImage", listImage);
		model.put("receptor", receptor);
		model.put("grantPerson", grantPerson);
		model.put("revokePerson", revokePerson);
		model.put("type", type);
		model.put("viewOnly", viewOnly);
		model.put("view", view);
		if ("1".equals(type)) {
			return "visitor/reception/reception_edit";
		} else if ("2".equals(type)) {
			return "visitor/reception/leave_edit";
		} else {
			if(reservationVisitor.getActualVisitTime()==null){
				model.put("grantPerson", null);
			}
			if(reservationVisitor.getActualLeaveTime()==null){
				model.put("revokePerson", null);
			}
			return "visitor/reception/visitor_view";
		}

	}

	@ResponseBody
	@RequestMapping(value = "come", method = RequestMethod.POST)
	public ScoMessage comeSave(VisitorReservation reservationVisitor, String[] fileorignal, String[] filename,String type) {
		if (reservationVisitor.getId() != null) {
			int index = 0;
			Visitor tmp;
			List<Visitor> itemList = reservationVisitor.getVisitorList();
			Iterator<Visitor> iter = itemList.iterator();
			while (iter.hasNext()) {
				tmp = iter.next();
				if (StringUtils.isEmpty(tmp.getVisitorCardNo()) && StringUtils.isEmpty(tmp.getVisitorName())) {
					iter.remove();
					continue;
				} else {
					if (StringUtils.isNotEmpty(tmp.getVisitorCardNo())) {
						index++;
					}
				}
			}
			if(index == 0){
	        	return ScoMessage.error("请至少登记一个访客证号。");
	        }else{
				reservationVisitor.setVisitStatus("1");
			}

			reservationVisitor.setActualVisitors(index);
			reservationVisitor.setFlag(true);
			copyUpdateInfo(reservationVisitor);
			reservationVisitorService.update(reservationVisitor,fileorignal, filename, null,"");
		}
		return ScoMessage.success("visitor/reception/list", ScoMessage.SAVE_SUCCESS_MSG);
	}

	@ResponseBody
	@RequestMapping(value = "leave", method = RequestMethod.POST)
	public ScoMessage leaveSave(VisitorReservation reservationVisitor,String[] fileorignal, String[] filename,String type) {
        if (reservationVisitor.getId() != null) {
			int index = 0;
			Visitor tmp;
			List<Visitor> itemList = reservationVisitor.getVisitorList();
			Iterator<Visitor> iter = itemList.iterator();
			while (iter.hasNext()) {
				tmp = iter.next();
				if (StringUtils.isEmpty(tmp.getVisitorCardNo()) && StringUtils.isEmpty(tmp.getVisitorName())) {
					iter.remove();
				}
				if (StringUtils.isNotEmpty(tmp.getVisitorReturnCard()) && "1".equals(tmp.getVisitorReturnCard())) {
					index++;
				}
			}
			if(index == 0){
				return ScoMessage.error("请确认至少一个访客归还访客证。");
			}
			if(reservationVisitor.getActualVisitors().intValue()==index){
				reservationVisitor.setVisitStatus("2");
			}else{
				reservationVisitor.setVisitStatus("1");
			}
			reservationVisitor.setFlag(false);
			copyUpdateInfo(reservationVisitor);
			reservationVisitorService.update(reservationVisitor,fileorignal, filename, null,"");
		}
		return ScoMessage.success("visitor/reception/list", ScoMessage.SAVE_SUCCESS_MSG);
	}

	@RequestMapping(value = "webcam", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage webcam(HttpServletRequest request, HttpServletResponse response) {
		try {
			ServletInputStream sis = request.getInputStream();
		//	String filePath = new SimpleDateFormat("yyyyMMdd").format(new Date());
			String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
			BufferedImage tag = ImageIO.read(sis);
			tag.flush();
			boolean writerExists = ImageIO.write(tag, "jpg", new File(tmpPath + fileName));
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			PrintWriter out = response.getWriter();
			if (writerExists) {
				out.print(fileName);
			} else {
				out.print("error");
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//数据以xls导出
    @RequestMapping("excelExport")
    public void excelExport(HttpServletRequest request,HttpServletResponse response) {
        String title = "访客信息"+DateUtils.format(new Date(),"yyyyMMddHHmmss");
        OutputStream out = null;
        try {
        	response.setHeader("Content-Disposition", "attachment; filename="+ new String((title+".xls").getBytes("GB2312"), "iso8859-1"));
            response.setContentType("application/msexcel;charset=UTF-8");
            out = response.getOutputStream();            
            ExcelExport<VisitorReservation> ex = new ExcelExport<VisitorReservation>();
            List<VisitorReservation> list = new ArrayList<VisitorReservation>();
            Iterator<VisitorReservation> it = cache.iterator();
            while(it.hasNext()){
            	list.add(it.next());
            }
            ex.exportExcel(title,list,out);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
            out.close();
            }catch(Exception e){
            	
            }
        }
    }

}
