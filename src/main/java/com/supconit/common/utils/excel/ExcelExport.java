package com.supconit.common.utils.excel;


import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;

import com.supconit.common.utils.excel.pojo.Testpojo;

public class ExcelExport<T> {
	//格式化日期
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 
	 * @param title 标题
	 * @param dataset 集合
	 * @param out  输出流
	 */
	public void exportExcel(String title, Collection<T> dataset,
			OutputStream out) {
		// 声明一个工作薄
		try {
			//首先检查数据看是否是正确的
			Iterator<T> its = dataset.iterator();
			if(dataset==null||!its.hasNext()||title==null||out==null)
			{
				throw new Exception("传入的数据不对！");
			}
			//取得实际泛型类
			T ts = (T) its.next();
			Class tCls = ts.getClass();
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 生成一个表格
			HSSFSheet sheet = workbook.createSheet(title);
			// 设置表格默认列宽度为15个字节
			sheet.setDefaultColumnWidth(15);
			// 生成一个样式
			HSSFCellStyle style = workbook.createCellStyle();
			// 设置标题样式
			style = ExcelStyle.setHeadStyle(workbook, style);

			// 得到所有字段
		
			Field filed[] = ts.getClass().getDeclaredFields();
			// 标题
			List<String> exportfieldtile = new ArrayList<String>();
			// 导出的字段的get方法
			List<Method> methodObj = new ArrayList<Method>();
			// 遍历整个filed
			for (int i = 0; i < filed.length; i++) {
				Field f = filed[i];
				ExcelAnnotation exa = f.getAnnotation(ExcelAnnotation.class);
				// 如果设置了annottion
				if (exa != null) {
					String exprot = exa.exportName();
					// 添加到标题
					exportfieldtile.add(exprot);
					// 添加到需要导出的字段的方法
					String fieldname = f.getName();
					String getMethodName = "get"
							+ fieldname.substring(0, 1).toUpperCase()
							+ fieldname.substring(1);
					
					Method getMethod = tCls.getMethod(getMethodName,
							new Class[] {});
					
					
					methodObj.add(getMethod);
				}
			}
			// 产生表格标题行
			HSSFRow row = sheet.createRow(0);
			for (int i = 0; i < exportfieldtile.size(); i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style);
				HSSFRichTextString text = new HSSFRichTextString(
						exportfieldtile.get(i));
				cell.setCellValue(text);
			}

	
			int index = 0;
			
			// 循环整个集合
			its = dataset.iterator();
			while (its.hasNext()) {
				//从第二行开始写，第一行是标题
				index++;
				row = sheet.createRow(index);
				T t = (T) its.next();
				for (int k = 0; k < methodObj.size(); k++) {
					HSSFCell cell = row.createCell(k);
					Method getMethod=methodObj.get(k);
					Object value = getMethod.invoke(t, new Object[] {});
					String textValue = getValue(value);
					cell.setCellValue(textValue);
				}

			}
			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void exportMeetingExcel(String title, Collection<T> dataset,String meetingSubject,String meetingCompereName,
			OutputStream out) {
		// 声明一个工作薄
		try {
			//首先检查数据看是否是正确的
			Iterator<T> its = dataset.iterator();
			if(dataset==null||!its.hasNext()||title==null||out==null)
			{
				throw new Exception("传入的数据不对！");
			}
			//取得实际泛型类
			T ts = (T) its.next();
			Class tCls = ts.getClass();
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 生成一个表格
			HSSFSheet sheet = workbook.createSheet(title);
			// 设置表格默认列宽度为15个字节
			sheet.setDefaultColumnWidth(15);
			// 生成一个样式
			HSSFCellStyle style = workbook.createCellStyle();
			// 设置标题样式
			style = ExcelStyle.setHeadStyle(workbook, style);
			
			// 得到所有字段
			
			Field filed[] = ts.getClass().getDeclaredFields();
			// 标题
			List<String> exportfieldtile = new ArrayList<String>();
			// 导出的字段的get方法
			List<Method> methodObj = new ArrayList<Method>();
			// 遍历整个filed
			for (int i = 0; i < filed.length; i++) {
				Field f = filed[i];
				ExcelAnnotation exa = f.getAnnotation(ExcelAnnotation.class);
				// 如果设置了annottion
				if (exa != null) {
					String exprot = exa.exportName();
					// 添加到标题
					exportfieldtile.add(exprot);
					// 添加到需要导出的字段的方法
					String fieldname = f.getName();
					String getMethodName = "get"
							+ fieldname.substring(0, 1).toUpperCase()
							+ fieldname.substring(1);
					
					Method getMethod = tCls.getMethod(getMethodName,
							new Class[] {});
					
					
					methodObj.add(getMethod);
				}
			}
			// 产生表格标题行
			HSSFRow row0 = sheet.createRow(0);
			HSSFCell cell00 = row0.createCell(0);
			HSSFCell cell01 = row0.createCell(1);
			cell00.setCellStyle(style);
			HSSFRichTextString text0 = new HSSFRichTextString(
					"会议主题");
			cell00.setCellValue(text0);
			cell01.setCellValue(meetingSubject);
			
			HSSFRow row1 = sheet.createRow(1);
			HSSFCell cell10 = row1.createCell(0);
			HSSFCell cell11 = row1.createCell(1);
			cell10.setCellStyle(style);
			HSSFRichTextString text1 = new HSSFRichTextString(
					"会议主持人");
			cell10.setCellValue(text1);
			cell11.setCellValue(meetingCompereName);
			
			HSSFRow row = sheet.createRow(2);
			for (int i = 0; i < exportfieldtile.size(); i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style);
				HSSFRichTextString text = new HSSFRichTextString(
						exportfieldtile.get(i));
				cell.setCellValue(text);
			}
			
			
			int index = 2;
			
			// 循环整个集合
			its = dataset.iterator();
			while (its.hasNext()) {
				//从第二行开始写，第一行是标题
				index++;
				row = sheet.createRow(index);
				T t = (T) its.next();
				for (int k = 0; k < methodObj.size(); k++) {
					HSSFCell cell = row.createCell(k);
					Method getMethod=methodObj.get(k);
					Object value = getMethod.invoke(t, new Object[] {});
					String textValue = getValue(value);
					cell.setCellValue(textValue);
				}
				
			}
			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String getValue(Object value) {
		String textValue = "";
		if (value == null)
			return textValue;

		if (value instanceof Boolean) {
			boolean bValue = (Boolean) value;
			textValue = "是";
			if (!bValue) {
				textValue = "否";
			}
		} else if (value instanceof Date) {
			Date date = (Date) value;
		
			textValue = sdf.format(date);
		} else
			textValue = value.toString();

		return textValue;
	}

	public static void main(String[] args) throws Exception {
		
		//构造一个模拟的List来测试，实际使用时，这个集合用从数据库中查出来
		List list = new ArrayList();
		for (int i = 0; i < 5000; i++) {
			Testpojo pojo = new Testpojo();
			pojo.setLoginname("登录名"+i);
			pojo.setUsername("用户名"+i);
			pojo.setMoney(new Long(1000+i));
			pojo.setCreatetime(new Date());
		    pojo.setAge(28);
			list.add(pojo);
		}
		//构造输出对象，可以从response输出，直接向用户提供下载
		OutputStream out = new FileOutputStream("D:\\testOne.xls");
		//开始时间
		Long l = System.currentTimeMillis();
		//注意
		ExcelExport<Testpojo> ex = new ExcelExport<Testpojo>();
		//
		ex.exportExcel("测试", list, out);
		out.close();
		//结束时间
		Long s = System.currentTimeMillis();
		System.out.println("总共耗时：" + (s - l));

	}
}



