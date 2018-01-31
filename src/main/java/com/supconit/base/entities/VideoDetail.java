package com.supconit.base.entities;

import com.supconit.common.web.entities.AuditExtend;

public class VideoDetail extends AuditExtend{    
	
    	private static final long	serialVersionUID	= 1L;
        
        private Long deviceId;
        private String videoCode;//摄像头编号，用于观看和控制
        private String viewIp;
        private Long viewPort;
        private Long channelNo;//存档视频摄像头通道号
        private String controlIp;
        private Long controlPort;
        private String username;
        private String password;
    
        private String hpid; // 
        private String deviceName; //设备名称
        
        public String getHpid() {
			return hpid;
		}
		public void setHpid(String hpid) {
			this.hpid = hpid;
		}
		public String getDeviceName() {
			return deviceName;
		}
		public void setDeviceName(String deviceName) {
			this.deviceName = deviceName;
		}
		public Long getDeviceId(){
            return deviceId;
        }
        public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	    }        
        public String getVideoCode(){
            return videoCode;
        }
        public void setVideoCode(String videoCode) {
		this.videoCode = videoCode;
	    }        
        public String getViewIp(){
            return viewIp;
        }
        public void setViewIp(String viewIp) {
		this.viewIp = viewIp;
	    }        
        public Long getViewPort(){
            return viewPort;
        }
        public void setViewPort(Long viewPort) {
		this.viewPort = viewPort;
	    }        
        public Long getChannelNo(){
            return channelNo;
        }
        public void setChannelNo(Long channelNo) {
		this.channelNo = channelNo;
	    }        
        public String getControlIp(){
            return controlIp;
        }
        public void setControlIp(String controlIp) {
		this.controlIp = controlIp;
	    }        
        public Long getControlPort(){
            return controlPort;
        }
        public void setControlPort(Long controlPort) {
		this.controlPort = controlPort;
	    }        
        public String getUsername(){
            return username;
        }
        public void setUsername(String username) {
		this.username = username;
	    }        
        public String getPassword(){
            return password;
        }
        public void setPassword(String password) {
		this.password = password;
	    }
		@Override
		public String toString() {
			return "VideoDetail [deviceId=" + deviceId + ", videoCode="
					+ videoCode + ", viewIp=" + viewIp + ", viewPort="
					+ viewPort + ", channelNo=" + channelNo + ", controlIp="
					+ controlIp + ", controlPort=" + controlPort
					+ ", username=" + username + ", password=" + password + "]";
		}   
        
}

