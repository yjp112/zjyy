
package com.supconit.base.entities;

import hc.base.domains.AuditedEntity;

public class EnumDetail extends AuditedEntity{    
	
    	private static final long	serialVersionUID	= 1L;
        
        private Long typeId;
        private String typeName;
        private String enumText;
        private String enumValue;
        private Long sortIndex;
        private Long int1;
        private Long int2;
        private Long int3;
        private String str1;
        private String str2;
        private String str3;
    
        public Long getTypeId(){
            return typeId;
        }
        public void setTypeId(Long typeId) {
		this.typeId = typeId;
	    }        
        public String getTypeName(){
            return typeName;
        }
        public void setTypeName(String typeName) {
		this.typeName = typeName;
	    }        
        public String getEnumText(){
            return enumText;
        }
        public void setEnumText(String enumText) {
		this.enumText = enumText;
	    }        
        public String getEnumValue(){
            return enumValue;
        }
        public void setEnumValue(String enumValue) {
		this.enumValue = enumValue;
	    }        
        public Long getSortIndex(){
            return sortIndex;
        }
        public void setSortIndex(Long sortIndex) {
		this.sortIndex = sortIndex;
	    }        
        public Long getInt1(){
            return int1;
        }
        public void setInt1(Long int1) {
		this.int1 = int1;
	    }        
        public Long getInt2(){
            return int2;
        }
        public void setInt2(Long int2) {
		this.int2 = int2;
	    }        
        public Long getInt3(){
            return int3;
        }
        public void setInt3(Long int3) {
		this.int3 = int3;
	    }        
        public String getStr1(){
            return str1;
        }
        public void setStr1(String str1) {
		this.str1 = str1;
	    }        
        public String getStr2(){
            return str2;
        }
        public void setStr2(String str2) {
		this.str2 = str2;
	    }        
        public String getStr3(){
            return str3;
        }
        public void setStr3(String str3) {
		this.str3 = str3;
	    }        
}

