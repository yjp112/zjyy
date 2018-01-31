package com.supconit.common.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.supconit.common.utils.excel.pojo.Testpojo;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * 
 */
public class ExcelImportHelper<T> {
	private transient static final Logger log = LoggerFactory
			.getLogger(ExcelImportHelper.class);

	Class<T> clazz;

	public ExcelImportHelper(Class<T> clazz) {
		this.clazz = clazz;
	}

	/**
	 * @param inputStreams
	 * @param sheetIdx sheet页索引编号，从0开始
	 * @param pattern
	 * @return
	 */
	public List<T> importExcel(InputStream inputStreams,int[] sheetIdxs	, String... pattern) {
		List<T> dist = new ArrayList<T>();
		try {
			/**
			 * 类反射得到调用方法
			 */
			// 得到目标目标类的所有的字段列表
			Field filed[] = clazz.getDeclaredFields();
			// 将所有标有Annotation的字段，也就是允许导入数据的字段,放入到一个map中
			Map<String, Method> fieldmap = new HashMap<String, Method>();
			// 循环读取所有字段
			for (int i = 0; i < filed.length; i++) {
				Field f = filed[i];
				// 得到单个字段上的Annotation
				ExcelAnnotation exa = f.getAnnotation(ExcelAnnotation.class);
				// 如果标识了Annotationd的话
				if (exa != null) {
					// 构造设置了Annotation的字段的Setter方法
					String fieldname = f.getName();
					String setMethodName = "set"
							+ fieldname.substring(0, 1).toUpperCase()
							+ fieldname.substring(1);
					// 构造调用的method，
					Method setMethod = clazz.getMethod(setMethodName,
							new Class[] { f.getType() });
					// 将这个method以Annotaion的名字为key来存入。
					fieldmap.put(exa.exportName().toUpperCase(), setMethod);
				}
			}
			/**
			 * excel的解析开始
			 */
			// 将传入的File构造为FileInputStream;
			// // 得到工作表
			Workbook book = Workbook.getWorkbook(inputStreams);
			for (int sheetIdx : sheetIdxs) {
				// // 得到第一页
				Sheet sheet = null;
				try {
					sheet = book.getSheet(sheetIdx);
				} catch (Exception e1) {
					log.error("工作薄数量不正确");
					break;
				}

				/**
				 * 标题解析
				 */
				// 得到第一行，也就是标题行
				Cell[] cellTitleIter = sheet.getRow(0);
				// 得到第一行的所有列
				// 将标题的文字内容放入到一个map中。
				Map<Integer, String> titlemap = new HashMap<Integer, String>();
				// 从标题第一列开始
				int i = 0;
				// 循环标题所有的列
				for (Cell cell : cellTitleIter) {
					titlemap.put(i, cell.getContents().toUpperCase());
					i = i + 1;
				}
				if (log.isInfoEnabled()) {
					log.info("===========col:title===================");
					log.info(titlemap.toString());
				}
				/**
				 * 解析内容行
				 */
				// 用来格式化日期的DateFormat
				SimpleDateFormat sf;
				if (pattern.length < 1) {
					sf = new SimpleDateFormat("yyyy-MM-dd");
				} else {
					sf = new SimpleDateFormat(pattern[0]);
				}
				// 标题下的行
				for (i = 1; i < sheet.getRows(); i++) {


					Cell[] dataCells = sheet.getRow(i);
					// 得到传入类的实例
					T tObject = clazz.newInstance();
					int k = 0;
					// 遍历一行的列
					for (Cell cell : dataCells) {

						// 这里得到此列的对应的标题
						String titleString = (String) titlemap.get(k);
						// 如果这一列的标题和类中的某一列的Annotation相同，那么则调用此类的的set方法，进行设值
						Method setMethod = null;
						if (fieldmap.containsKey(titleString)) {
							setMethod = (Method) fieldmap.get(titleString);
						} else if (fieldmap.containsKey("'" + titleString)) {
							setMethod = (Method) fieldmap
									.get("'" + titleString);
						} else if (fieldmap.containsKey("‘" + titleString)) {
							setMethod = (Method) fieldmap
									.get("‘" + titleString);
						} else if (fieldmap.containsKey("’" + titleString)) {
							setMethod = (Method) fieldmap
									.get("’" + titleString);
						}
						String cellContent = StringUtils.trimToEmpty(cell
								.getContents());
						if (setMethod != null
								&& StringUtils.isNotBlank(cellContent)) {
							try {
								// 得到setter方法的参数
								Type[] ts = setMethod.getGenericParameterTypes();
								// 只要一个参数
								String xclass = ts[0].toString();
								// 判断参数类型

								if (xclass.equals("class java.lang.String")) {
									setMethod.invoke(tObject, cellContent);
								} else if (xclass.equals("class java.util.Date")) {
									setMethod.invoke(tObject, sf.parse(cellContent));
								} else if (xclass.equals("class java.lang.Boolean")) {
									Boolean boolname = true;
									if (cellContent.equals("否")) {
										boolname = false;
									}
									setMethod.invoke(tObject, boolname);
								} else if (xclass.equals("class java.lang.Integer")) {
									setMethod.invoke(tObject, new Integer(cellContent));
								} else if (xclass.equals("class java.lang.Long")) {
									setMethod.invoke(tObject, new Long(cellContent));
								} else if (xclass.equals("class java.lang.Float")) {
									setMethod.invoke(tObject, new Float(cellContent));
								} else {
									setMethod.invoke(tObject, cellContent);
								}							
							} catch (Exception e) {
								log.error("第"+i+"行,第"+k+"列解析出错,内容为【"+cellContent+"】",e);
							}

						}
						// 下一列
						k = k + 1;
					}
					dist.add(tObject);
					
				}				
			}


		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return dist;
	}
	
	/**
	 * @param inputStreams
	 * @param sheetIdx sheet页索引编号，从0开始
	 * @param pattern
	 * @return
	 */
	public List<T> importExcel(Workbook book,int[] sheetIdxs	, String... pattern) {
		List<T> dist = new ArrayList<T>();
		try {
			/**
			 * 类反射得到调用方法
			 */
			// 得到目标目标类的所有的字段列表
			Field filed[] = clazz.getDeclaredFields();
			// 将所有标有Annotation的字段，也就是允许导入数据的字段,放入到一个map中
			Map<String, Method> fieldmap = new HashMap<String, Method>();
			// 循环读取所有字段
			for (int i = 0; i < filed.length; i++) {
				Field f = filed[i];
				// 得到单个字段上的Annotation
				ExcelAnnotation exa = f.getAnnotation(ExcelAnnotation.class);
				// 如果标识了Annotationd的话
				if (exa != null) {
					// 构造设置了Annotation的字段的Setter方法
					String fieldname = f.getName();
					String setMethodName = "set"
							+ fieldname.substring(0, 1).toUpperCase()
							+ fieldname.substring(1);
					// 构造调用的method，
					Method setMethod = clazz.getMethod(setMethodName,
							new Class[] { f.getType() });
					// 将这个method以Annotaion的名字为key来存入。
					fieldmap.put(exa.exportName().toUpperCase(), setMethod);
				}
			}
			/**
			 * excel的解析开始
			 */
			// 将传入的File构造为FileInputStream;
			// // 得到工作表
			for (int sheetIdx : sheetIdxs) {
				// // 得到第一页
				Sheet sheet = null;
				try {
					sheet = book.getSheet(sheetIdx);
				} catch (Exception e1) {
					log.error("工作薄数量不正确");
					break;
				}
				
				/**
				 * 标题解析
				 */
				// 得到第一行，也就是标题行
				Cell[] cellTitleIter = sheet.getRow(0);
				// 得到第一行的所有列
				// 将标题的文字内容放入到一个map中。
				Map<Integer, String> titlemap = new HashMap<Integer, String>();
				// 从标题第一列开始
				int i = 0;
				// 循环标题所有的列
				for (Cell cell : cellTitleIter) {
					titlemap.put(i, cell.getContents().toUpperCase());
					i = i + 1;
				}
				if (log.isInfoEnabled()) {
					log.info("===========col:title===================");
					log.info(titlemap.toString());
				}
				/**
				 * 解析内容行
				 */
				// 用来格式化日期的DateFormat
				SimpleDateFormat sf;
				if (pattern.length < 1) {
					sf = new SimpleDateFormat("yyyy-MM-dd");
				} else {
					sf = new SimpleDateFormat(pattern[0]);
				}
				// 标题下的行
				for (i = 1; i < sheet.getRows(); i++) {
					
					
					Cell[] dataCells = sheet.getRow(i);
					// 得到传入类的实例
					T tObject = clazz.newInstance();
					int k = 0;
					// 遍历一行的列
					for (Cell cell : dataCells) {
						
						// 这里得到此列的对应的标题
						String titleString = (String) titlemap.get(k);
						// 如果这一列的标题和类中的某一列的Annotation相同，那么则调用此类的的set方法，进行设值
						Method setMethod = null;
						if (fieldmap.containsKey(titleString)) {
							setMethod = (Method) fieldmap.get(titleString);
						} else if (fieldmap.containsKey("'" + titleString)) {
							setMethod = (Method) fieldmap
									.get("'" + titleString);
						} else if (fieldmap.containsKey("‘" + titleString)) {
							setMethod = (Method) fieldmap
									.get("‘" + titleString);
						} else if (fieldmap.containsKey("’" + titleString)) {
							setMethod = (Method) fieldmap
									.get("’" + titleString);
						}
						String cellContent = StringUtils.trimToEmpty(cell
								.getContents());
						if (setMethod != null
								&& StringUtils.isNotBlank(cellContent)) {
							try {
								// 得到setter方法的参数
								Type[] ts = setMethod.getGenericParameterTypes();
								// 只要一个参数
								String xclass = ts[0].toString();
								// 判断参数类型
								
								if (xclass.equals("class java.lang.String")) {
									setMethod.invoke(tObject, cellContent);
								} else if (xclass.equals("class java.util.Date")) {
									setMethod.invoke(tObject, sf.parse(cellContent));
								} else if (xclass.equals("class java.lang.Boolean")) {
									Boolean boolname = true;
									if (cellContent.equals("否")) {
										boolname = false;
									}
									setMethod.invoke(tObject, boolname);
								} else if (xclass.equals("class java.lang.Integer")) {
									setMethod.invoke(tObject, new Integer(cellContent));
								} else if (xclass.equals("class java.lang.Long")) {
									setMethod.invoke(tObject, new Long(cellContent));
								} else if (xclass.equals("class java.lang.Float")) {
									setMethod.invoke(tObject, new Float(cellContent));
								} else {
									setMethod.invoke(tObject, cellContent);
								}							
							} catch (Exception e) {
								log.error("第"+i+"行,第"+k+"列解析出错,内容为【"+cellContent+"】",e);
							}
							
						}
						// 下一列
						k = k + 1;
					}
					dist.add(tObject);
					
				}				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return dist;
	}
	public List<T> importExcel(File file, String... pattern) {
		try {
			return importExcel(new FileInputStream(file),new int[]{0}, pattern);
		} catch (FileNotFoundException e) {
			log.error("excel导入出错",e);
			return null;
		}
	}
	public List<T> importExcel(File file,int[] sheetIdxs, String... pattern) {
		try {
			return importExcel(new FileInputStream(file),sheetIdxs, pattern);
		} catch (FileNotFoundException e) {
			log.error("excel导入出错",e);
			return null;
		}
	}

	public static void main(String[] args) {
		ExcelImportHelper<Testpojo> test = new ExcelImportHelper(Testpojo.class);
		String path=Testpojo.class.getPackage().getName().replace(".", "/");
		URL url=ExcelImportHelper.class.getClassLoader().getResource(path);

		String fileName = null;
		try {
			fileName = URLDecoder.decode(url.getFile(),"UTF-8").substring(1);
		} catch (UnsupportedEncodingException e) {
		}
		System.out.println(fileName);
		File file = new File(fileName+"/testPojo.xls");
		Long befor = System.currentTimeMillis();
		Collection<Testpojo> result = test.importExcel(file);
		for (Testpojo sosPatient : result) {

			System.out.println("导入的信息为：" + sosPatient);
		}
		Long after = System.currentTimeMillis();
		System.out.println("此次操作共耗时：" + (after - befor) + "毫秒");

		System.out.println("共转化为List的行数为：" + result.size());
	}
}
