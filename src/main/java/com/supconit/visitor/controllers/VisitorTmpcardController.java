package com.supconit.visitor.controllers;

import com.supconit.base.domains.Organization;
import com.supconit.base.entities.Attachment;
import com.supconit.base.entities.EnumDetail;
import com.supconit.base.honeycomb.extend.ExPerson;
import com.supconit.base.services.AttachmentService;
import com.supconit.base.services.OrganizationService;
import com.supconit.common.utils.DateUtils;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.utils.OrganizationUtils;
import com.supconit.common.utils.excel.ExcelExport;
import com.supconit.common.web.controllers.BaseControllerSupport;
import com.supconit.common.web.entities.ScoMessage;
import com.supconit.honeycomb.business.organization.entities.Department;
import com.supconit.honeycomb.business.organization.services.DepartmentService;
import com.supconit.honeycomb.business.organization.services.PersonService;
import com.supconit.visitor.entities.TmpCard;
import com.supconit.visitor.entities.Visitor;
import com.supconit.visitor.entities.VisitorReservation;
import com.supconit.visitor.services.TmpCardService;
import com.supconit.visitor.services.VisitorReservationService;
import hc.base.domains.AjaxMessage;
import hc.base.domains.Pageable;
import hc.base.domains.Pagination;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

/**
 * 临时卡办理
 * by zx
 */
@Controller
@RequestMapping("visitor/tmpcard")
public class VisitorTmpcardController extends BaseControllerSupport {
	@Autowired
	private VisitorReservationService reservationVisitorService;
    @Autowired
    private TmpCardService tmpCardService;
	@Autowired
	private PersonService personService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private DepartmentService deptService;
	@Autowired
	private AttachmentService attachmentService;
	private static Pagination<TmpCard> cache;
	
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
		return "visitor/tmpcard/list";
	}

	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public Pageable<TmpCard> list(Pagination<TmpCard> pager,
			@ModelAttribute TmpCard tmpCard) {
        tmpCardService.findByPages(pager, tmpCard);
		cache = pager;
		return pager;
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
     public String edit(@RequestParam(required = false) Boolean viewOnly,@RequestParam(required = false) Long id,ModelMap model,@RequestParam(required = false,defaultValue = "1") String type,
                        @RequestParam(required = false) Boolean view) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String nows = sdf.format(new Date());
        model.put("type", type);
        model.put("nows", nows);
        model.put("viewOnly", viewOnly);
        model.put("view", view);
        model.put("imagePath", imageServerUrl);
        model.put("imageTempPath", imageTempServerUrl);
        if ("1".equals(type)) {
            return "visitor/tmpcard/tmpcard_edit";
        } else {//暂时不用
            return "visitor/tmpcard/tmpcard_edit";
        }
    }

	@ResponseBody
	@RequestMapping(value = "come", method = RequestMethod.POST)
	public ScoMessage comeSave(@ModelAttribute TmpCard tmpCard) {
        tmpCardService.save(tmpCard);
		return ScoMessage.success("visitor/tmpcard/list", ScoMessage.SAVE_SUCCESS_MSG);
	}

}
