package com.supconit.common.utils.excel.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.supconit.common.utils.excel.ExcelAnnotation;

/**
 * 
 */
public class Testpojo {
	@ExcelAnnotation(exportName = "用户名")
	String username;
	@ExcelAnnotation(exportName = "登录名")
	String loginname;
	@ExcelAnnotation(exportName = "年龄")
	Integer age;
	@ExcelAnnotation(exportName = "收入")
	Long money;
	@ExcelAnnotation(exportName = "时间")
	Date createtime;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Long getMoney() {
		return money;
	}

	public void setMoney(Long money) {
		this.money = money;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Override
	public String toString() {
		return "Testpojo [username=" + username + ", loginname=" + loginname
				+ ", age=" + age + ", money=" + money + ", createtime="
				+ new SimpleDateFormat("yyyy-MM-dd").format(createtime) + "]";
	}

}
