package com.supconit.nhgl.cost.entities;

import java.math.BigDecimal;

import hc.base.domains.LongId;

/**
 * 年度用电量
 * 
 * @author dragon
 *
 */
public class YearEletricityCost extends LongId {
	private String deptCode;
	private String deptName;
	private Long deptId;
	private Long ppId;
	private Long lft;
	private Long rgt;
	private String currentYear;
	private String start;
	private String end;
	// 一月份用电量
	private BigDecimal electric_01 = new BigDecimal(0);
	// 一月份用电成本
	private BigDecimal electricCost_01 = new BigDecimal(0);
	// 某部门一月份用电量
	private BigDecimal totalElectric_01= new BigDecimal(0);
	// 某部门一月份用电成本
	private BigDecimal totalElectricCost_01= new BigDecimal(0);
	
	// 二月份用电量
	private BigDecimal electric_02= new BigDecimal(0);
	
	// 二月份用电成本
	private BigDecimal electricCost_02= new BigDecimal(0);
	// 某部门二月份用电量
	private BigDecimal totalElectric_02= new BigDecimal(0);
	// 某部门二月份用电成本
	private BigDecimal totalElectricCost_02= new BigDecimal(0);
	
	// 三月份用电量
	private BigDecimal electric_03= new BigDecimal(0);
	// 三月份用电成本
	private BigDecimal electricCost_03= new BigDecimal(0);
	// 某部门三月份用电量
	private BigDecimal totalElectric_03= new BigDecimal(0);
	// 某部门三月份用电成本
	private BigDecimal totalElectricCost_03= new BigDecimal(0);
	
	// 四月份用电量
	private BigDecimal electric_04= new BigDecimal(0);
	// 四月份用电成本
	private BigDecimal electricCost_04= new BigDecimal(0);
	// 某部门四月份用电量
	private BigDecimal totalElectric_04= new BigDecimal(0);
	// 某部门四月份用电成本
	private BigDecimal totalElectricCost_04= new BigDecimal(0);
	
	// 五月份用电量
	private BigDecimal electric_05= new BigDecimal(0);
	// 五月份用电成本
	private BigDecimal electricCost_05= new BigDecimal(0);
	// 某部门五月份用电量
	private BigDecimal totalElectric_05= new BigDecimal(0);
	// 某部门五月份用电成本
	private BigDecimal totalElectricCost_05= new BigDecimal(0);
	
	// 六月份用电量
	private BigDecimal electric_06= new BigDecimal(0);
	// 六月份用电成本
	private BigDecimal electricCost_06= new BigDecimal(0);
	// 某部门六月份用电量
	private BigDecimal totalElectric_06= new BigDecimal(0);
	// 某部门六月份用电成本
	private BigDecimal totalElectricCost_06= new BigDecimal(0);
	
	// 七月份用电量
	private BigDecimal electric_07= new BigDecimal(0);
	// 七月份用电成本
	private BigDecimal electricCost_07= new BigDecimal(0);
	// 某部门七月份用电量
	private BigDecimal totalElectric_07= new BigDecimal(0);;
	// 某部门七月份用电成本
	private BigDecimal totalElectricCost_07= new BigDecimal(0);
	
	// 8月份用电量
	private BigDecimal electric_08= new BigDecimal(0);
	// 8月份用电成本
	private BigDecimal electricCost_08= new BigDecimal(0);
	// 某部门8月份用电量
	private BigDecimal totalElectric_08= new BigDecimal(0);
	// 某部门8月份用电成本
	private BigDecimal totalElectricCost_08= new BigDecimal(0);
	
	// 9月份用电量
	private BigDecimal electric_09= new BigDecimal(0);
	// 9月份用电成本
	private BigDecimal electricCost_09= new BigDecimal(0);
	// 某部门9月份用电量
	private BigDecimal totalElectric_09= new BigDecimal(0);
	// 某部门9月份用电成本
	private BigDecimal totalElectricCost_09= new BigDecimal(0);
	
	// 10月份用电量
	private BigDecimal electric_10= new BigDecimal(0);
	// 10月份用电成本
	private BigDecimal electricCost_10= new BigDecimal(0);
	// 某部门10月份用电量
	private BigDecimal totalElectric_10= new BigDecimal(0);
	// 某部门10月份用电成本
	private BigDecimal totalElectricCost_10= new BigDecimal(0);
	
	// 11月份用电量
	private BigDecimal electric_11= new BigDecimal(0);
	// 11月份用电成本
	private BigDecimal electricCost_11= new BigDecimal(0);
	// 某部门11月份用电量
	private BigDecimal totalElectric_11= new BigDecimal(0);;
	// 某部门11月份用电成本
	private BigDecimal totalElectricCost_11= new BigDecimal(0);
	
	// 12月份用电量
	private BigDecimal electric_12= new BigDecimal(0);
	// 12月份用电成本
	private BigDecimal electricCost_12= new BigDecimal(0);
	// 某部门12月份用电量
	private BigDecimal totalElectric_12= new BigDecimal(0);
	// 某部门12月份用电成本
	private BigDecimal totalElectricCost_12= new BigDecimal(0);
	//标准煤 Kg
	private BigDecimal electric_bzm_01 = new BigDecimal(0);
	private BigDecimal electric_bzm_02 = new BigDecimal(0);
	private BigDecimal electric_bzm_03 = new BigDecimal(0);
	private BigDecimal electric_bzm_04 = new BigDecimal(0);
	private BigDecimal electric_bzm_05 = new BigDecimal(0);
	private BigDecimal electric_bzm_06 = new BigDecimal(0);
	private BigDecimal electric_bzm_07 = new BigDecimal(0);
	private BigDecimal electric_bzm_08 = new BigDecimal(0);
	private BigDecimal electric_bzm_09 = new BigDecimal(0);
	private BigDecimal electric_bzm_10 = new BigDecimal(0);
	private BigDecimal electric_bzm_11 = new BigDecimal(0);
	private BigDecimal electric_bzm_12 = new BigDecimal(0);
	private BigDecimal electric_bzm_12month = new BigDecimal(0);
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public Long getPpId() {
		return ppId;
	}
	public void setPpId(Long ppId) {
		this.ppId = ppId;
	}
	public BigDecimal getElectric_01() {
		return electric_01;
	}
	public void setElectric_01(BigDecimal electric_01) {
		this.electric_01 = electric_01;
	}
	public BigDecimal getElectricCost_01() {
		return electricCost_01;
	}
	public void setElectricCost_01(BigDecimal electricCost_01) {
		this.electricCost_01 = electricCost_01;
	}
	public BigDecimal getTotalElectric_01() {
		return totalElectric_01;
	}
	public void setTotalElectric_01(BigDecimal totalElectric_01) {
		this.totalElectric_01 = totalElectric_01;
	}
	public BigDecimal getTotalElectricCost_01() {
		return totalElectricCost_01;
	}
	public void setTotalElectricCost_01(BigDecimal totalElectricCost_01) {
		this.totalElectricCost_01 = totalElectricCost_01;
	}
	public BigDecimal getElectric_02() {
		return electric_02;
	}
	public void setElectric_02(BigDecimal electric_02) {
		this.electric_02 = electric_02;
	}
	public BigDecimal getElectricCost_02() {
		return electricCost_02;
	}
	public void setElectricCost_02(BigDecimal electricCost_02) {
		this.electricCost_02 = electricCost_02;
	}
	public BigDecimal getTotalElectric_02() {
		return totalElectric_02;
	}
	public void setTotalElectric_02(BigDecimal totalElectric_02) {
		this.totalElectric_02 = totalElectric_02;
	}
	public BigDecimal getTotalElectricCost_02() {
		return totalElectricCost_02;
	}
	public void setTotalElectricCost_02(BigDecimal totalElectricCost_02) {
		this.totalElectricCost_02 = totalElectricCost_02;
	}
	public BigDecimal getElectric_03() {
		return electric_03;
	}
	public void setElectric_03(BigDecimal electric_03) {
		this.electric_03 = electric_03;
	}
	public BigDecimal getElectricCost_03() {
		return electricCost_03;
	}
	public void setElectricCost_03(BigDecimal electricCost_03) {
		this.electricCost_03 = electricCost_03;
	}
	public BigDecimal getTotalElectric_03() {
		return totalElectric_03;
	}
	public void setTotalElectric_03(BigDecimal totalElectric_03) {
		this.totalElectric_03 = totalElectric_03;
	}
	public BigDecimal getTotalElectricCost_03() {
		return totalElectricCost_03;
	}
	public void setTotalElectricCost_03(BigDecimal totalElectricCost_03) {
		this.totalElectricCost_03 = totalElectricCost_03;
	}
	public BigDecimal getElectric_04() {
		return electric_04;
	}
	public void setElectric_04(BigDecimal electric_04) {
		this.electric_04 = electric_04;
	}
	public BigDecimal getElectricCost_04() {
		return electricCost_04;
	}
	public void setElectricCost_04(BigDecimal electricCost_04) {
		this.electricCost_04 = electricCost_04;
	}
	public BigDecimal getTotalElectric_04() {
		return totalElectric_04;
	}
	public void setTotalElectric_04(BigDecimal totalElectric_04) {
		this.totalElectric_04 = totalElectric_04;
	}
	public BigDecimal getTotalElectricCost_04() {
		return totalElectricCost_04;
	}
	public void setTotalElectricCost_04(BigDecimal totalElectricCost_04) {
		this.totalElectricCost_04 = totalElectricCost_04;
	}
	public BigDecimal getElectric_05() {
		return electric_05;
	}
	public void setElectric_05(BigDecimal electric_05) {
		this.electric_05 = electric_05;
	}
	public BigDecimal getElectricCost_05() {
		return electricCost_05;
	}
	public void setElectricCost_05(BigDecimal electricCost_05) {
		this.electricCost_05 = electricCost_05;
	}
	public BigDecimal getTotalElectric_05() {
		return totalElectric_05;
	}
	public void setTotalElectric_05(BigDecimal totalElectric_05) {
		this.totalElectric_05 = totalElectric_05;
	}
	public BigDecimal getTotalElectricCost_05() {
		return totalElectricCost_05;
	}
	public void setTotalElectricCost_05(BigDecimal totalElectricCost_05) {
		this.totalElectricCost_05 = totalElectricCost_05;
	}
	public BigDecimal getElectric_06() {
		return electric_06;
	}
	public void setElectric_06(BigDecimal electric_06) {
		this.electric_06 = electric_06;
	}
	public BigDecimal getElectricCost_06() {
		return electricCost_06;
	}
	public void setElectricCost_06(BigDecimal electricCost_06) {
		this.electricCost_06 = electricCost_06;
	}
	public BigDecimal getTotalElectric_06() {
		return totalElectric_06;
	}
	public void setTotalElectric_06(BigDecimal totalElectric_06) {
		this.totalElectric_06 = totalElectric_06;
	}
	public BigDecimal getTotalElectricCost_06() {
		return totalElectricCost_06;
	}
	public void setTotalElectricCost_06(BigDecimal totalElectricCost_06) {
		this.totalElectricCost_06 = totalElectricCost_06;
	}
	public BigDecimal getElectric_07() {
		return electric_07;
	}
	public void setElectric_07(BigDecimal electric_07) {
		this.electric_07 = electric_07;
	}
	public BigDecimal getElectricCost_07() {
		return electricCost_07;
	}
	public void setElectricCost_07(BigDecimal electricCost_07) {
		this.electricCost_07 = electricCost_07;
	}
	public BigDecimal getTotalElectric_07() {
		return totalElectric_07;
	}
	public void setTotalElectric_07(BigDecimal totalElectric_07) {
		this.totalElectric_07 = totalElectric_07;
	}
	public BigDecimal getTotalElectricCost_07() {
		return totalElectricCost_07;
	}
	public void setTotalElectricCost_07(BigDecimal totalElectricCost_07) {
		this.totalElectricCost_07 = totalElectricCost_07;
	}
	public BigDecimal getElectric_08() {
		return electric_08;
	}
	public void setElectric_08(BigDecimal electric_08) {
		this.electric_08 = electric_08;
	}
	public BigDecimal getElectricCost_08() {
		return electricCost_08;
	}
	public void setElectricCost_08(BigDecimal electricCost_08) {
		this.electricCost_08 = electricCost_08;
	}
	public BigDecimal getTotalElectric_08() {
		return totalElectric_08;
	}
	public void setTotalElectric_08(BigDecimal totalElectric_08) {
		this.totalElectric_08 = totalElectric_08;
	}
	public BigDecimal getTotalElectricCost_08() {
		return totalElectricCost_08;
	}
	public void setTotalElectricCost_08(BigDecimal totalElectricCost_08) {
		this.totalElectricCost_08 = totalElectricCost_08;
	}
	public BigDecimal getElectric_09() {
		return electric_09;
	}
	public void setElectric_09(BigDecimal electric_09) {
		this.electric_09 = electric_09;
	}
	public BigDecimal getElectricCost_09() {
		return electricCost_09;
	}
	public void setElectricCost_09(BigDecimal electricCost_09) {
		this.electricCost_09 = electricCost_09;
	}
	public BigDecimal getTotalElectric_09() {
		return totalElectric_09;
	}
	public void setTotalElectric_09(BigDecimal totalElectric_09) {
		this.totalElectric_09 = totalElectric_09;
	}
	public BigDecimal getTotalElectricCost_09() {
		return totalElectricCost_09;
	}
	public void setTotalElectricCost_09(BigDecimal totalElectricCost_09) {
		this.totalElectricCost_09 = totalElectricCost_09;
	}
	public BigDecimal getElectric_10() {
		return electric_10;
	}
	public void setElectric_10(BigDecimal electric_10) {
		this.electric_10 = electric_10;
	}
	public BigDecimal getElectricCost_10() {
		return electricCost_10;
	}
	public void setElectricCost_10(BigDecimal electricCost_10) {
		this.electricCost_10 = electricCost_10;
	}
	public BigDecimal getTotalElectric_10() {
		return totalElectric_10;
	}
	public void setTotalElectric_10(BigDecimal totalElectric_10) {
		this.totalElectric_10 = totalElectric_10;
	}
	public BigDecimal getTotalElectricCost_10() {
		return totalElectricCost_10;
	}
	public void setTotalElectricCost_10(BigDecimal totalElectricCost_10) {
		this.totalElectricCost_10 = totalElectricCost_10;
	}
	public BigDecimal getElectric_11() {
		return electric_11;
	}
	public void setElectric_11(BigDecimal electric_11) {
		this.electric_11 = electric_11;
	}
	public BigDecimal getElectricCost_11() {
		return electricCost_11;
	}
	public void setElectricCost_11(BigDecimal electricCost_11) {
		this.electricCost_11 = electricCost_11;
	}
	public BigDecimal getTotalElectric_11() {
		return totalElectric_11;
	}
	public void setTotalElectric_11(BigDecimal totalElectric_11) {
		this.totalElectric_11 = totalElectric_11;
	}
	public BigDecimal getTotalElectricCost_11() {
		return totalElectricCost_11;
	}
	public void setTotalElectricCost_11(BigDecimal totalElectricCost_11) {
		this.totalElectricCost_11 = totalElectricCost_11;
	}
	public BigDecimal getElectric_12() {
		return electric_12;
	}
	public void setElectric_12(BigDecimal electric_12) {
		this.electric_12 = electric_12;
	}
	public BigDecimal getElectricCost_12() {
		return electricCost_12;
	}
	public void setElectricCost_12(BigDecimal electricCost_12) {
		this.electricCost_12 = electricCost_12;
	}
	public BigDecimal getTotalElectric_12() {
		return totalElectric_12;
	}
	public void setTotalElectric_12(BigDecimal totalElectric_12) {
		this.totalElectric_12 = totalElectric_12;
	}
	public BigDecimal getTotalElectricCost_12() {
		return totalElectricCost_12;
	}
	public void setTotalElectricCost_12(BigDecimal totalElectricCost_12) {
		this.totalElectricCost_12 = totalElectricCost_12;
	}
	public Long getLft() {
		return lft;
	}
	public void setLft(Long lft) {
		this.lft = lft;
	}
	public Long getRgt() {
		return rgt;
	}
	public void setRgt(Long rgt) {
		this.rgt = rgt;
	}
	public String getCurrentYear() {
		return currentYear;
	}
	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public BigDecimal getElectric_bzm_01() {
		return electric_bzm_01;
	}
	public void setElectric_bzm_01(BigDecimal electric_bzm_01) {
		this.electric_bzm_01 = electric_bzm_01;
	}
	public BigDecimal getElectric_bzm_02() {
		return electric_bzm_02;
	}
	public void setElectric_bzm_02(BigDecimal electric_bzm_02) {
		this.electric_bzm_02 = electric_bzm_02;
	}
	public BigDecimal getElectric_bzm_03() {
		return electric_bzm_03;
	}
	public void setElectric_bzm_03(BigDecimal electric_bzm_03) {
		this.electric_bzm_03 = electric_bzm_03;
	}
	public BigDecimal getElectric_bzm_04() {
		return electric_bzm_04;
	}
	public void setElectric_bzm_04(BigDecimal electric_bzm_04) {
		this.electric_bzm_04 = electric_bzm_04;
	}
	public BigDecimal getElectric_bzm_05() {
		return electric_bzm_05;
	}
	public void setElectric_bzm_05(BigDecimal electric_bzm_05) {
		this.electric_bzm_05 = electric_bzm_05;
	}
	public BigDecimal getElectric_bzm_06() {
		return electric_bzm_06;
	}
	public void setElectric_bzm_06(BigDecimal electric_bzm_06) {
		this.electric_bzm_06 = electric_bzm_06;
	}
	public BigDecimal getElectric_bzm_07() {
		return electric_bzm_07;
	}
	public void setElectric_bzm_07(BigDecimal electric_bzm_07) {
		this.electric_bzm_07 = electric_bzm_07;
	}
	public BigDecimal getElectric_bzm_08() {
		return electric_bzm_08;
	}
	public void setElectric_bzm_08(BigDecimal electric_bzm_08) {
		this.electric_bzm_08 = electric_bzm_08;
	}
	
	public BigDecimal getElectric_bzm_09() {
		return electric_bzm_09;
	}
	public void setElectric_bzm_09(BigDecimal electric_bzm_09) {
		this.electric_bzm_09 = electric_bzm_09;
	}
	public BigDecimal getElectric_bzm_10() {
		return electric_bzm_10;
	}
	public void setElectric_bzm_10(BigDecimal electric_bzm_10) {
		this.electric_bzm_10 = electric_bzm_10;
	}
	public BigDecimal getElectric_bzm_11() {
		return electric_bzm_11;
	}
	public void setElectric_bzm_11(BigDecimal electric_bzm_11) {
		this.electric_bzm_11 = electric_bzm_11;
	}
	public BigDecimal getElectric_bzm_12() {
		return electric_bzm_12;
	}
	public void setElectric_bzm_12(BigDecimal electric_bzm_12) {
		this.electric_bzm_12 = electric_bzm_12;
	}
	public BigDecimal getElectric_bzm_12month() {
		return electric_bzm_12month;
	}
	public void setElectric_bzm_12month(BigDecimal electric_bzm_12month) {
		this.electric_bzm_12month = electric_bzm_12month;
	}
	public void updateElectricBzm(BigDecimal electric_bzm){
		this.electric_bzm_01 = getElectric_01().multiply(electric_bzm);
		this.electric_bzm_02 = getElectric_02().multiply(electric_bzm);
		this.electric_bzm_03 = getElectric_03().multiply(electric_bzm);
		this.electric_bzm_04 = getElectric_04().multiply(electric_bzm);
		this.electric_bzm_05 = getElectric_05().multiply(electric_bzm);
		this.electric_bzm_06 = getElectric_06().multiply(electric_bzm);
		this.electric_bzm_07 = getElectric_07().multiply(electric_bzm);
		this.electric_bzm_08 = getElectric_08().multiply(electric_bzm);
		this.electric_bzm_09 = getElectric_09().multiply(electric_bzm);
		this.electric_bzm_10 = getElectric_10().multiply(electric_bzm);
		this.electric_bzm_11 = getElectric_11().multiply(electric_bzm);
		this.electric_bzm_12 = getElectric_12().multiply(electric_bzm);
		this.electric_bzm_12month = getTotalElectric_12().multiply(electric_bzm);
	}
}
