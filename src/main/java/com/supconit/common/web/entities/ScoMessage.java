package com.supconit.common.web.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ScoMessage implements Serializable {

    /**
     * 字段名称 serialVersionUID 字段类型 long 字段描述
     */
    private static final long serialVersionUID = -4388675421281323884L;
    public static final String DEFAULT_FAIL_MSG = "操作失败";
    public static final String INNER_ERROR_MSG = "内部错误";
    public static final String DEFAULT_SUCCESS_MSG = "操作成功";
    public static final String DELETE_SUCCESS_MSG = "删除成功";
    public static final String SAVE_SUCCESS_MSG = "保存成功";
    public static final String SUCCESS_STATUS = "success";
    public static final String FAIL_STATUS = "fail";
    public static final String ERROR_STATUS = "error";
    public static final String NEXT_REFRESH = ".";
    public static final String NEXT_CLOSE  = "x";
    private String status;
    private Data data=new Data();
    private String message;
    
    public ScoMessage() {
    }


    public static ScoMessage success(String msg) {
        return new ScoMessage(SUCCESS_STATUS,null,NEXT_CLOSE,msg);
    }
    public static ScoMessage success(String next,String msg) {
    	/*Menu m=MenuContextHolder.getMenu();
    	if(StringUtils.isNotBlank(next)&&m!=null){
    		if(next.indexOf('?')!=-1){
    			next=next+"&menuCode="+m.getCode();
    		}else{
    			next=next+"?menuCode="+m.getCode();
    		}
    	}*/
    	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); 
    	String menuCode=request.getParameter("menuCode");
    	if(StringUtils.isNotBlank(next)&&StringUtils.isNotBlank(menuCode)){
    		if(next.indexOf('?')!=-1){
    			next=next+"&menuCode="+menuCode;
    		}else{
    			next=next+"?menuCode="+menuCode;
    		}
    	}
        return new ScoMessage(SUCCESS_STATUS,null,next,msg);
    }
    public static ScoMessage fail(String msg) {
        return new ScoMessage(FAIL_STATUS,DEFAULT_FAIL_MSG);
    }

    public static ScoMessage error(String msg) {
        if(StringUtils.isBlank(msg)){
            msg=INNER_ERROR_MSG;
        }
        return new ScoMessage(ERROR_STATUS,msg);
    }

    public ScoMessage(String status,String message) {
        this.status = status;
        this.message=message;
    }
    private ScoMessage(String status,String message,String next,String dataMessage) {
        this.status = status;
        this.message=message;
        if(StringUtils.isNotBlank(dataMessage)){
            data.setMessage(dataMessage);
        }
        if(StringUtils.isNotBlank(next)){
            data.setNext(next);
        }else{
            data.setNext(NEXT_CLOSE);
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public void addError(String key, String msg) {
        data.addError(key, msg);
    }
    public class Data implements Serializable {
        /**
         * 字段名称 serialVersionUID
         * 字段类型 long
         * 字段描述 
         */
        private static final long serialVersionUID = -3547672751703668772L;
        //public static final String next_redirect = "";
        private String next;
        private String message;
        private Map<String, String> errors = new HashMap<String, String>();
        public Data(String next, String message) {
            super();
            this.next = next;
            this.message = message;
        }

        public Data() {
            super();
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Map<String, String> getErrors() {
            return errors;
        }

        public void addError(String key, String msg) {
            errors.put(key, msg);
        }
    }
}
