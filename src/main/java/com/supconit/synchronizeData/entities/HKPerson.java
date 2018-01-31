package com.supconit.synchronizeData.entities;

public class HKPerson {
		 private static final long	serialVersionUID	= 5225114907469638741L;
		 private Long  personId;
		 private String  personCode;
		 private String  personName;
		 public String getPersonCode() {
			return personCode;
		}
		public void setPersonCode(String personCode) {
			this.personCode = personCode;
		}
		public Long getPersonId() {
			 return personId;
		 }
		 public void setPersonId(Long personId) {
			 this.personId = personId;
		 }
		 public String getPersonName() {
			 return personName;
		 }
		 public void setPersonName(String personName) {
			 this.personName = personName;
		 }
}
