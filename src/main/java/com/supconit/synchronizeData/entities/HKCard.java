package com.supconit.synchronizeData.entities;

public class HKCard {
		 private static final long	serialVersionUID	= 5215114907469638741L;
		 private Long  personId;
		 private String  cardNumber;
		
		public Long getPersonId() {
			 return personId;
		 }
		 public void setPersonId(Long personId) {
			 this.personId = personId;
		 }
		public String getCardNumber() {
			return cardNumber;
		}
		public void setCardNumber(String cardNumber) {
			this.cardNumber = cardNumber;
		}
		
}
