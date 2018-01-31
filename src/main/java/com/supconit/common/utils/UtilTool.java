package com.supconit.common.utils;


import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;

import com.supconit.honeycomb.base.context.SpringContextHolder;

import hc.base.domains.AuditedEntity;

 
//import javax.servlet.http.HttpServletRequest;
 

public class UtilTool {
	private static SqlSession sqlSession = SpringContextHolder.getBean(SqlSession.class);
	/**
	 * * 判断输入的字符串是否包含GBK编码格式的字符。* 在此方法中，首先将字符串以ISO-8859-1编码格式提取并转化为byte[]数组；*
	 * 然后判断该数组中是否包含'?'（ASCII码值为63），如果有，则该字符串中包含GBK编码格式的字符。*
	 * 当然，为了排除原字符串中的'?'的干扰，应首先将'?'替换掉。*
	 * 
	 * @param string
	 *            String 字符串。*
	 * @throws UnsupportedEncodingException
	 *             如果系统不支持ISO-8859-1编码格式，将抛出此异常。*
	 * @return boolean 如果输入的字符串包含GBK编码格式的字符，则返回true；否则返回false。*
	 * @see Transform#toGBK*
	 * @see Transform#toISO_8859_1
	 */
	public static boolean isGBK(String string)
			throws java.io.UnsupportedEncodingException {
		byte[] bytes = string.replace('?', 'a').getBytes("ISO-8859-1");
		for (int i = 0; i < bytes.length; i++) {
			if (bytes[i] == 63) {
				return true;
			}
		}
		return false;
	}

	/**
	 * * 实现将字符串转换为GBK编码格式的字符串，并返回转换结果。*
	 * 如果原始字符串为ISO-8859-1编码格式的字符串，则返回转化为GBK编码格式的字符串；*
	 * 如果原始字符串本身即为GBK编码格式的字符串，则返回原字符串，即不进行编码格式的转换。*
	 * 判断字符串是否为GBK编码格式的标准是判断该字符串中是否包含GBK编码格式的字符。*
	 * 
	 * @param string
	 *            String 欲转换的字符串。*
	 * @throws UnsupportedEncodingException
	 *             如果系统不支持ISO-8859-1或GBK编码格式，将抛出此异常。*
	 * @return String 转换后的字符串。*
	 * @see Transform#isGBK*
	 * @see Transform#toISO_8859_1
	 */
	public static String toGBK(String string)
			throws java.io.UnsupportedEncodingException {
		if (string == null)
			return "";
		if (!isGBK(string)) {
			return new String(string.getBytes("ISO-8859-1"), "GBK");
		}
		return string;
	}

	/**
	 * * 实现将字符串转换为ISO-8859-1编码格式的字符串，并返回转换结果。*
	 * 如果原始字符串为GBK编码格式的字符串，则返回转化为ISO-8859-1编码格式的字符串；*
	 * 如果原始字符串本身即为ISO-8859-1编码格式的字符串，则返回原字符串，即不进行编码格式的转换。*
	 * 判断字符串是否为ISO-8859-1编码格式的标准是判断该字符串中是否未包含GBK编码格式的字符。*
	 * 
	 * @param string
	 *            String 欲转换的字符串。*
	 * @throws UnsupportedEncodingException
	 *             如果系统不支持ISO-8859-1或GBK编码格式，将抛出此异常。*
	 * @return String 转换后的字符串。*
	 * @see Transform#isGBK*
	 * @see Transform#toGBK
	 */
	public static String toISO_8859_1(String string)
			throws java.io.UnsupportedEncodingException {
		if (string == null)
			return "";
		if (isGBK(string)) {
			return new String(string.getBytes("GBK"), "ISO-8859-1");
		}
		return string;
	}
	
	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(
							src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(
							src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
	
	/**
	 * 判读字符串是否有null或者空
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String str){
		return str == null || "".equals(str);
	}
	/**
	 * 判读字符串是否有null或者空
	 * @param string
	 * @return
	 */
	public static boolean isEmptyList(List lst){
		return lst == null || lst.size()==0;
	}
	/**
	 * 字符串null转为空
	 * @param string
	 * @return
	 */
	public static String nullToEmpty(String str){
		return str == null?"":str;
	}
	/**
	 * 根据sceneNumber和itemid、wordID命名规则(XFJZ-A-01-001,TX-A-B1-001)取得itemid、wordID的中间部分
	 * @param string
	 * @return
	 */
	public static String getMiddleOfItemid(String sceneNumber){
		String middleOfItemid = "";
		try {		
			if(sceneNumber.length()<3){//A楼
				middleOfItemid += "-A-";
				if(sceneNumber.startsWith("-")){
					middleOfItemid += sceneNumber.replace("-", "B");
				}else{
					middleOfItemid += sceneNumber.length()==1?"0"+sceneNumber:sceneNumber;
				}
				middleOfItemid +="-";
			}else if(sceneNumber.startsWith("-1") || sceneNumber.startsWith("1")){//B楼
				middleOfItemid += "-B-";
				if(sceneNumber.startsWith("-1")){
					middleOfItemid += sceneNumber.substring(2);
				}else{
					middleOfItemid += sceneNumber.substring(1);
				}
				middleOfItemid +="-";
			}
		} catch (Exception e) {
			middleOfItemid = "";
		}
		return middleOfItemid;
	}
	/**
	 * 拼接(用Fusioncharts生成flash图表时需传入的)xml
	 * @param string
	 * @return
	 */
	public static String composeFusionchartsXml(int flashType,List lstData,String title, String x,String y,String url,int urlParameterNo){

		StringBuffer xml =  new StringBuffer("");
		xml.append("<chart caption='"+title+"' xAxisName='"+x+"' yAxisName='"+y+"'>");
		if(lstData!=null || lstData.size()>0){		
			switch (flashType) {
			case 1://简单饼图
				for( int i=0;i<lstData.size();i++ ){
					Object[] objects = (Object[]) lstData.get(i) ;
					xml.append("<set label='"+objects[0]+"' value='"+objects[1] + "' ");
					if(!UtilTool.isEmpty(url)){
						//xml.append("link=\\\"JavaScript:goPage('"+url+objects[urlParameterNo]+"');\\\"");
						xml.append("link=\\\"JavaScript:window.location.href='"+url+objects[urlParameterNo]+"';\\\"");
						
					}
					xml.append("/>");
					//link=\"JavaScript:goPage('mouthArea.action?str=2012-02');\" 
				}			
				break;
			case 2://双柱柱形图
				//<categories> <category label="2006" /> <category label="2007" /> <category label="2008" />
				//<category label="2009" /> <category label="2010" /> <category label="2011" /> </categories>
				//<dataset seriesName="Model A12" color="A66EDD" > <set value="35" /> <set value="42" />
				//<set value="31" /> <set value="28" /> <set value="34" /> <set value="30" /> </dataset> <dataset 
				String lastKey = "";
				StringBuffer set1 =  new StringBuffer("");
				StringBuffer set2 =  new StringBuffer("");
				for( int i=0;i<lstData.size();i++ ){
					Object[] objects = (Object[]) lstData.get(i) ;
					if(i==0){
						xml.append("<categories>");
						set1.append("<dataset seriesName='"+objects[1]+"'/>");
						set2.append("<dataset seriesName='"+objects[2]+"'/>");
					}
					if(!lastKey.equals(String.valueOf(objects[0]))){
						xml.append("<category label='" + objects[0] + "'/>");
					}
					set1.append("<set value='"+objects[1] + "'/>");
					set2.append("<set value='"+objects[2] + "'/>");
					lastKey = String.valueOf(objects[0]);
				}
	
				break;
			default:
				break;
			}
		}
		xml.append("</chart>");
		return xml.toString();
		
	}
	/**
	 * double数据四舍五入
	 * @param val       计算对象
	 * @param precision 保留小数点位数
	 * @return
	 */
	public static Double roundDouble(double val, int precision) {  
		return new BigDecimal(String.valueOf(val)).setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	/**
	 * 判断日期是否合法
	 * @param str       日期
	 * @param format    日期格式
	 * @return
	 */
	public static Boolean isDate(String str, String format) {  
		try {
			SimpleDateFormat  sdf = new SimpleDateFormat(format);
			sdf.setLenient(false);
			sdf.parse(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

    /**
     *  判断Entity是否为NUll
     * @param auditExtend
     * @return
     */
    public static boolean isNull(AuditedEntity auditExtend){
        if(null == auditExtend || auditExtend.getId() == null || auditExtend.getId()==0){
            return true;
        }else {
            return false;
        }
    }
    
    /**
     * 查询时转化非法字符
     * 包括：%、_、/
     * @param str
     * @return
     */
    public static String transIllegalString(String str){
    	if(null == str || "".equals(str)) return "";
    	String dataBaseId = sqlSession.getConfiguration().getDatabaseId();
    	if(str.indexOf("/")>-1) str = str.replaceAll("/", "//"); 
		if(str.indexOf("%")>-1) str = str.replaceAll("%", "/%"); 
		if(str.indexOf("_")>-1) str = str.replaceAll("_", "/_");
		if(dataBaseId.equals("sqlserver")){
			if(str.indexOf("[")>-1) str = str.replaceAll("\\[", "/[");
			if(str.indexOf("]")>-1) str = str.replaceAll("\\]", "/]");
		}
//		if(str.indexOf("[")>-1){
//			str = str.replaceAll("\\[", "/[");
//		}
		return str;
    }
}
