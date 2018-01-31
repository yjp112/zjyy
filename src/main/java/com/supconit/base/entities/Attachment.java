
package com.supconit.base.entities;

import com.supconit.common.web.entities.AuditExtend;

public class Attachment extends AuditExtend{    
	
    	private static final long	serialVersionUID	= 1L;
        
        private Long fId;
        private String modelType;
        private String fileType;
        private String fileName;
        private Long fileSize;
        private String storePath;
        private String remark;
    
        public Long getFId(){
            return fId;
        }
        public void setFId(Long fId) {
		this.fId = fId;
	    }        
        public String getModelType(){
            return modelType;
        }
        public void setModelType(String modelType) {
		this.modelType = modelType;
	    }        
        public String getFileType(){
            return fileType;
        }
        public void setFileType(String fileType) {
		this.fileType = fileType;
	    }        
        public String getFileName(){
            return fileName;
        }
        public void setFileName(String fileName) {
		this.fileName = fileName;
	    }        
        public Long getFileSize(){
            return fileSize;
        }
        public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	    }        
        public String getStorePath(){
            return storePath;
        }
        public void setStorePath(String storePath) {
		this.storePath = storePath;
	    }        
        public String getRemark(){
            return remark;
        }
        public void setRemark(String remark) {
		this.remark = remark;
	    }        
}

