
package com.supconit.base.entities;

import java.util.Date;

import com.supconit.common.web.entities.AuditExtend;

public class Supplier extends AuditExtend{    
	
    	private static final long	serialVersionUID	= 1L;
        
        private String supplierCode;
        private String fullName;
        private String simpleName;
        private String shortCode;
        private String legalPerson;
        private String businessLicense;
        private Date licenseExpiryDate;
        private String companyType;
        private String website;
        private String address;
        private String contact;
        private String telNo;
        private String mobile;
        private String fax;
        private String email;
        private String zipCode;
        private String postAddress;
        private String bank;
        private String bankAccount;
        private String remark;
    
        public String getSupplierCode(){
            return supplierCode;
        }
        public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	    }        
        public String getFullName(){
            return fullName;
        }
        public void setFullName(String fullName) {
		this.fullName = fullName;
	    }        
        public String getSimpleName(){
            return simpleName;
        }
        public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	    }        
        public String getShortCode(){
            return shortCode;
        }
        public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	    }        
        public String getLegalPerson(){
            return legalPerson;
        }
        public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	    }        
        public String getBusinessLicense(){
            return businessLicense;
        }
        public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	    }        
        public Date getLicenseExpiryDate() {
			return licenseExpiryDate;
		}
		public void setLicenseExpiryDate(Date licenseExpiryDate) {
			this.licenseExpiryDate = licenseExpiryDate;
		}
		public String getCompanyType(){
            return companyType;
        }
        public void setCompanyType(String companyType) {
		this.companyType = companyType;
	    }        
        public String getWebsite(){
            return website;
        }
        public void setWebsite(String website) {
		this.website = website;
	    }        
        public String getAddress(){
            return address;
        }
        public void setAddress(String address) {
		this.address = address;
	    }        
        public String getContact(){
            return contact;
        }
        public void setContact(String contact) {
		this.contact = contact;
	    }        
        public String getTelNo(){
            return telNo;
        }
        public void setTelNo(String telNo) {
		this.telNo = telNo;
	    }        
        public String getMobile(){
            return mobile;
        }
        public void setMobile(String mobile) {
		this.mobile = mobile;
	    }        
        public String getFax(){
            return fax;
        }
        public void setFax(String fax) {
		this.fax = fax;
	    }        
        public String getEmail(){
            return email;
        }
        public void setEmail(String email) {
		this.email = email;
	    }        
        public String getZipCode(){
            return zipCode;
        }
        public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	    }        
        public String getPostAddress(){
            return postAddress;
        }
        public void setPostAddress(String postAddress) {
		this.postAddress = postAddress;
	    }        
        public String getBank(){
            return bank;
        }
        public void setBank(String bank) {
		this.bank = bank;
	    }        
        public String getBankAccount(){
            return bankAccount;
        }
        public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	    }        
        public String getRemark(){
            return remark;
        }
        public void setRemark(String remark) {
		this.remark = remark;
	    }        
}

