
package com.supconit.base.entities;

import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;
import com.supconit.common.web.entities.AuditExtend;

public class Contract extends AuditExtend{    
	
    	private static final long	serialVersionUID	= 1L;
        
        private String contractCode;
        private String contractName;
        private String contractA;
        private String contractB;
        private Long contractStatus;
        private String beginDate;
        private String endDate;
        private String contractDesc;
        private String contractStatusName;
        
        public String getContractCode(){
            return contractCode;
        }
        public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	    }        
        public String getContractName(){
            return contractName;
        }
        public void setContractName(String contractName) {
		this.contractName = contractName;
	    }        
        public String getContractA(){
            return contractA;
        }
        public void setContractA(String contractA) {
		this.contractA = contractA;
	    }        
        public String getContractB(){
            return contractB;
        }
        public void setContractB(String contractB) {
		this.contractB = contractB;
	    }        
        public Long getContractStatus(){
            return contractStatus;
        }
        public void setContractStatus(Long contractStatus) {
		this.contractStatus = contractStatus;
	    }        
        public String getBeginDate(){
            return beginDate;
        }
        public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	    }        
        public String getEndDate(){
            return endDate;
        }
        public void setEndDate(String endDate) {
		this.endDate = endDate;
	    }        
        public String getContractDesc(){
            return contractDesc;
        }
        public void setContractDesc(String contractDesc) {
		this.contractDesc = contractDesc;
	    }
		public String getContractStatusName() {
			return DictUtils.getDictLabel(DictTypeEnum.CONTRACT_STATUS, this.contractStatus.toString());
			}
		public void setContractStatusName(String contractStatusName) {
			this.contractStatusName = contractStatusName;
		}
        
}

