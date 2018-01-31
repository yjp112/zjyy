package com.supconit.parking.entity;
/*
 * 值班日志
 */
public class ParkingLot  {

	private static final long	serialVersionUID	= 1L;
	
	private String hpid;      //对应phid
	
	private String LotNo;  //车位编号
	
	private int lotStatus;   //是否有车标示（LOTSTATUS，0：无车，1：有车）
	
	private int devStatus;  //是否故障标示（DEVSTATUS，0：工作正常，1：设备断线）
	
	private int batStatus;   //电量报警标示（BATSTATUS，0：电量充足，1：电量低于10%告警）
	
	public String getHpid() {
		return hpid;
	}
	public void setHpid(String hpid) {
		this.hpid = hpid;
	}
	public String getLotNo() {
		return LotNo;
	}
	public void setLotNo(String lotNo) {
		LotNo = lotNo;
	}
	public int getLotStatus() {
		return lotStatus;
	}
	public void setLotStatus(int lotStatus) {
		this.lotStatus = lotStatus;
	}
	public int getDevStatus() {
		return devStatus;
	}
	public void setDevStatus(int devStatus) {
		this.devStatus = devStatus;
	}
	public int getBatStatus() {
		return batStatus;
	}
	public void setBatStatus(int batStatus) {
		this.batStatus = batStatus;
	}
}
