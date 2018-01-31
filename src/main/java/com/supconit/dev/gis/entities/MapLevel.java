
package com.supconit.dev.gis.entities;

import com.supconit.common.web.entities.AuditExtend;

public class MapLevel extends AuditExtend {
	
    	private static final long	serialVersionUID	= 1L;
        
		private Long initBuild;
		private Long initFloor;
		private String scales;
    	private double minlon;
    	private String sminlon;//查看修改页面去掉'.0'用
		private double maxlon;
    	private String smaxlon;
    	private double minlat;
    	private String sminlat;
    	private double maxlat;
    	private String smaxlat;
    	private double centerx;
    	private String scenterx;
    	private double centery;
    	private String scentery;
    	private Integer initlevel;
    	private String baseLayer;
    	private String buildName;
    	private String floorName;
    	private String systemName;
    	private String systemCode;
    	private int type;
		private String fullLevelName;
    	
		public String getSystemName() {
			return systemName;
		}
		public void setSystemName(String systemName) {
			this.systemName = systemName;
		}
		public String getSystemCode() {
			return systemCode;
		}
		public void setSystemCode(String systemCode) {
			this.systemCode = systemCode;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public String getScales() {
			return scales;
		}
		public void setScales(String scales) {
			this.scales = scales;
		}
		public double getMinlon() {
			return minlon;
		}
		public void setMinlon(double minlon) {
			this.minlon = minlon;
		}
		public double getMaxlon() {
			return maxlon;
		}
		public void setMaxlon(double maxlon) {
			this.maxlon = maxlon;
		}
		public double getMinlat() {
			return minlat;
		}
		public void setMinlat(double minlat) {
			this.minlat = minlat;
		}
		public double getMaxlat() {
			return maxlat;
		}
		public void setMaxlat(double maxlat) {
			this.maxlat = maxlat;
		}
		public double getCenterx() {
			return centerx;
		}
		public void setCenterx(double centerx) {
			this.centerx = centerx;
		}
		public Long getInitBuild() {
			return initBuild;
		}
		public void setInitBuild(Long initBuild) {
			this.initBuild = initBuild;
		}
		public Long getInitFloor() {
			return initFloor;
		}
		public void setInitFloor(Long initFloor) {
			this.initFloor = initFloor;
		}
		public double getCentery() {
			return centery;
		}
		public void setCentery(double centery) {
			this.centery = centery;
		}
		public Integer getInitlevel() {
			return initlevel;
		}
		public void setInitlevel(Integer initlevel) {
			this.initlevel = initlevel;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		public String getSminlon() {
			return sminlon;
		}
		public void setSminlon(String sminlon) {
			this.sminlon = sminlon;
		}
		public String getSmaxlon() {
			return smaxlon;
		}
		public void setSmaxlon(String smaxlon) {
			this.smaxlon = smaxlon;
		}
		public String getSminlat() {
			return sminlat;
		}
		public void setSminlat(String sminlat) {
			this.sminlat = sminlat;
		}
		public String getSmaxlat() {
			return smaxlat;
		}
		public void setSmaxlat(String smaxlat) {
			this.smaxlat = smaxlat;
		}
		public String getScenterx() {
			return scenterx;
		}
		public void setScenterx(String scenterx) {
			this.scenterx = scenterx;
		}
		public String getScentery() {
			return scentery;
		}
		public void setScentery(String scentery) {
			this.scentery = scentery;
		}
		public String getBaseLayer() {
			return baseLayer;
		}
		public void setBaseLayer(String baseLayer) {
			this.baseLayer = baseLayer;
		}
		public String getBuildName() {
			return buildName;
		}
		public void setBuildName(String buildName) {
			this.buildName = buildName;
		}
		public String getFloorName() {
			return floorName;
		}
		public void setFullLevelName(String fullLevelName) {
			this.fullLevelName = fullLevelName;
		}
		public String getFullLevelName() {
			return fullLevelName;
		}
		public void setFloorName(String floorName) {
			this.floorName = floorName;
		}
}