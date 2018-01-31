package com.supconit.common.utils;

import java.math.BigDecimal;

public class NumberUtils extends org.apache.commons.lang.math.NumberUtils{
    public static BigDecimal nvl(BigDecimal decimal){
        return nvl(decimal,0);
    }  
    public static BigDecimal nvl(BigDecimal decimal,double defaultValue){
        if(decimal==null){
            return new BigDecimal(defaultValue);
        }else{
            return decimal;
        }
    }  
}
