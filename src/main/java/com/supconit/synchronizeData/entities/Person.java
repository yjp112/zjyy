package com.supconit.synchronizeData.entities;

public class Person {
		 private static final long	serialVersionUID	= 5225454907469638741L;
		 private Long  personId;
		 private String  personCode;
		 private String  personName;
		 private String  cardNumber;
		 private Long  hkPersonId;
		public Long getHkPersonId() {
			return hkPersonId;
		}
		public void setHkPersonId(Long hkPersonId) {
			this.hkPersonId = hkPersonId;
		}
		public String getCardNumber() {
			return cardNumber;
		}
		public void setCardNumber(String cardNumber) {
			this.cardNumber = cardNumber;
		}
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
