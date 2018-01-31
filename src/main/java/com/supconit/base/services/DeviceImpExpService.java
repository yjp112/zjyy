
package com.supconit.base.services;

import com.supconit.base.entities.Device;
import jxl.read.biff.BiffException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;


public interface DeviceImpExpService {

	public List<String> importDevice(InputStream inputStreams) throws BiffException, IOException, SQLException;
	public List<Device> exportDevice();
}
