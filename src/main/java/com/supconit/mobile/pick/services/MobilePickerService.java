package com.supconit.mobile.pick.services;

import com.supconit.base.entities.DutyGroupPerson;
import com.supconit.base.entities.EnumDetail;
import com.supconit.mobile.pick.entities.MobilePicker;

import java.util.List;

public interface MobilePickerService{

	MobilePicker searchDutyGroupPersons(DutyGroupPerson dutyGroupPerson);

	MobilePicker searchMobilePicker(List<EnumDetail> enumDetails);
}
