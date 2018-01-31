package com.supconit.nhgl.base.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import jxl.read.biff.BiffException;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.nhgl.base.entities.NhDevice;

import hc.base.domains.Pageable;
import hc.base.domains.Pagination;


public interface NhDeviceService extends BaseBusinessService<NhDevice,Long>{

	Pageable<NhDevice> findByCondition(Pagination<NhDevice> pager, NhDevice condition);
	List<String> importNhDevice(InputStream inputStreams,List<String> lstErrMsg) throws BiffException, IOException, SQLException;
}
