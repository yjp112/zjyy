package com.supconit.base.honeycomb.extend;

import java.util.Date;
import java.util.List;

import com.supconit.honeycomb.business.organization.entities.Person;

/**
 * 扩展Person,增加职位字段 
 * 
 * @author jarvis
 * 
 */
public class ExPerson extends Person {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -2175098736708921144L;


    private Long deptId;

    private String deptName; //部门名称

    private String personTell;//公司短号
    
    private Long personSex;// 1、男；2、女；
    
    private String personBirth; //出生日期
    
    private String personTelephone;//手机
    
    private String personOfficePhone;//办公室电话
    
    private String personEmail;//电子邮箱
    
    private Long status;//在职状态   1、在职；2、离职；3、退休
    
    protected List<ExDepartmentPersonInfo>	departmentPersonInfos2;

    private String  education;//学历
    
    private  String carNum;//身份证号码
    
    private  String address;//家庭住址
    
    private String  comeTime;//入职时间
    
    private String joinWorkTime;  //参加工作时间

	private String headPic;

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPersonTell() {
        return personTell;
    }

    public void setPersonTell(String personTell) {
        this.personTell = personTell;
    }

	public String getPersonTelephone() {
		return personTelephone;
	}

	public String getPersonOfficePhone() {
		return personOfficePhone;
	}

	public String getPersonEmail() {
		return personEmail;
	}

	public void setPersonTelephone(String personTelephone) {
		this.personTelephone = personTelephone;
	}

	public void setPersonOfficePhone(String personOfficePhone) {
		this.personOfficePhone = personOfficePhone;
	}

	public void setPersonEmail(String personEmail) {
		this.personEmail = personEmail;
	}

	public Long getPersonSex() {
		return personSex;
	}

	public void setPersonSex(Long personSex) {
		this.personSex = personSex;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public List<ExDepartmentPersonInfo> getDepartmentPersonInfos2() {
		return departmentPersonInfos2;
	}

	public void setDepartmentPersonInfos2(
			List<ExDepartmentPersonInfo> departmentPersonInfos2) {
		super.setDepartmentPersonInfos(departmentPersonInfos2);
		this.departmentPersonInfos2 = departmentPersonInfos2;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPersonBirth() {
		return personBirth;
	}

	public void setPersonBirth(String personBirth) {
		this.personBirth = personBirth;
	}

	public String getComeTime() {
		return comeTime;
	}

	public void setComeTime(String comeTime) {
		this.comeTime = comeTime;
	}

	public String getJoinWorkTime() {
		return joinWorkTime;
	}

	public void setJoinWorkTime(String joinWorkTime) {
		this.joinWorkTime = joinWorkTime;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	//	private Long				positionId;									//职位ID
//
//	/**
//	 * @return the positionId
//	 */
//	public Long getPositionId() {
//		return positionId;
//	}
//
//	/**
//	 * @param positionId
//	 *            the positionId to set
//	 */
//	public void setPositionId(Long positionId) {
//		this.positionId = positionId;
//	}

}
