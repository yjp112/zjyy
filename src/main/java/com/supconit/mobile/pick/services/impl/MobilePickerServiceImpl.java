package com.supconit.mobile.pick.services.impl;

import com.supconit.base.daos.DutyGroupPersonDao;
import com.supconit.base.entities.DutyGroupPerson;
import com.supconit.base.entities.EnumDetail;
import com.supconit.mobile.pick.entities.MobilePicker;
import com.supconit.mobile.pick.services.MobilePickerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangwei on 2016-4-15.
 */
@Service
public class MobilePickerServiceImpl implements MobilePickerService{

    private transient static final Logger logger	= LoggerFactory.getLogger(MobilePickerServiceImpl.class);

    @Autowired
    private DutyGroupPersonDao dutyGroupPersonDao;

    @Override
    public MobilePicker searchDutyGroupPersons(DutyGroupPerson dutyGroupPerson) {
        List<DutyGroupPerson> dutyGroupPersons=dutyGroupPersonDao.searchDutyGroupPersons(dutyGroupPerson);
        MobilePicker mobilePicker=new MobilePicker();
        StringBuffer personIdJson=new StringBuffer("[");
        StringBuffer personNameJson=new StringBuffer("[");
        if (dutyGroupPersons.size()>0)
        {
            for(DutyGroupPerson d:dutyGroupPersons)
            {
                personIdJson.append(d.getPersonId()+",");
                personNameJson.append("'"+d.getPersonName()+"',");
            }
            personIdJson.deleteCharAt(personIdJson.length()-1);
            personNameJson.deleteCharAt(personNameJson.length()-1);
            personIdJson.append("]");
            personNameJson.append("]");
        }
        else
        {
            personIdJson.append("]");
            personNameJson.append("]");
        }
        mobilePicker.setPersonIdJson(personIdJson.toString());
        mobilePicker.setPersonNameJson(personNameJson.toString());
        return mobilePicker;
    }

    @Override
    public MobilePicker searchMobilePicker(List<EnumDetail> enumDetails)
    {
        MobilePicker mobilePicker=new MobilePicker();
        StringBuffer valueBuff=new StringBuffer("['',");
        StringBuffer displayValueBuff=new StringBuffer("['',");
        if (enumDetails.size()>0)
        {
            for(EnumDetail e:enumDetails)
            {
                valueBuff.append("'"+e.getEnumValue()+"',");
                displayValueBuff.append("'"+e.getEnumText()+"',");
            }
            valueBuff.deleteCharAt(valueBuff.length()-1);
            displayValueBuff.deleteCharAt(displayValueBuff.length()-1);
            valueBuff.append("]");
            displayValueBuff.append("]");
        }
        else
        {
            valueBuff.deleteCharAt(valueBuff.length()-1);
            displayValueBuff.deleteCharAt(displayValueBuff.length()-1);
            valueBuff.append("]");
            displayValueBuff.append("]");
        }
        mobilePicker.setValue(valueBuff.toString());
        mobilePicker.setDisplayValue(displayValueBuff.toString());
        return mobilePicker;
    }
}
