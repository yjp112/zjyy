package com.supconit.mobile.bpm.controllers;

import com.supconit.employee.todo.entities.BpmApprovalRecord;
import com.supconit.employee.todo.services.BpmApprovalRecordService;
import com.supconit.honeycomb.business.authorization.entities.User;
import com.supconit.honeycomb.business.authorization.services.UserService;
import com.supconit.honeycomb.business.organization.entities.Person;
import com.supconit.honeycomb.business.organization.services.PersonService;
import hc.mvc.annotations.FormBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping({"/mobile/bpm/approval"})
public class ApprovalController {

    private static final transient Logger logger = LoggerFactory.getLogger(ApprovalController.class);

    @Autowired
    private BpmApprovalRecordService bpmApprovalRecordService;
    @Autowired
    private UserService userService;
    @Autowired
    private PersonService personService;

    @ModelAttribute("prepareBpmApprovalRecord")
    public BpmApprovalRecord prepareBpmApprovalRecord(){return new BpmApprovalRecord();}

    public ApprovalController() {
    }

    @ResponseBody
    @RequestMapping(value = "fetch", method = RequestMethod.GET)
    public List<BpmApprovalRecord> fetch(@FormBean(value = "condition",modelCode = "prepareBpmApprovalRecord")BpmApprovalRecord bpmApprovalRecord) {
        List<BpmApprovalRecord> bpmApprovalRecords=this.bpmApprovalRecordService.selectApprovalRecords(bpmApprovalRecord);
        if(bpmApprovalRecords!=null&&bpmApprovalRecords.size()>0)
        {
            for(BpmApprovalRecord b:bpmApprovalRecords)
            {
                String userName=b.getUsername();
                String personName="未知";
                if (userName!=null)
                {
                    User user = this.userService.getByUsername(userName);
                    if(user != null && user.getPersonId() != null) {
                        Person person = this.personService.getById(user.getPersonId().longValue());
                        if(person != null) {
                            personName=person.getName();
                        }
                    }
                }
                b.setPersonName(personName);
            }
        }
        return bpmApprovalRecords;
    }
}
