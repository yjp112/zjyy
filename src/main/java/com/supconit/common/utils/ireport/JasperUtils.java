package com.supconit.common.utils.ireport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;


import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRQuery;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBaseReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.query.JRXPathQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@SuppressWarnings("unchecked")
public class JasperUtils {

    private static Logger logger = Logger.getLogger(JasperUtils.class);
    public static final String PRINT_TYPE = "print";
    public static final String PDF_TYPE = "pdf";
    public static final String EXCEL_TYPE = "excel";
    public static final String HTML_TYPE = "html";
    public static final String WORD_TYPE = "word";
    /**
     * 获取JasperPrint对象
     *
     * @param request
     * @param reportParas    模板需要用到的参数
     * @param jasperFilePath jasper文件路径
     * @param conn           数据库连接
     * @param reportUrl      报表项目路径
     * @param isExportExcel  是否是导出excel
     * @return
     * @throws Exception
     */
    public static JasperPrint getJasperPrint(HttpServletRequest request,
    		String businessId, String jasperFilePath, Connection conn, boolean isExportExcel)
            throws Exception {
        JasperPrint jasperPrint = null;
        try {

            // 根据jasper文件相对路径得到jasper文件绝对路径
            String jasperFileRealPath = JasperReportUtil.class.getClassLoader().getResource("jasperreport/"+jasperFilePath).getFile().replaceFirst("/", "");
            // 得到jasper文件
            File jasperFile = new File(jasperFileRealPath);
            // 创建JasperReport对象
            JasperReport jasperReport = null;
            if (isExportExcel) { // 如果是导出excel，则设置忽略分页，直接编译jrxml文件
                String jrxmlFileRealPath = jasperFileRealPath.substring(0,
                        jasperFileRealPath.lastIndexOf("."))
                        + ".jrxml";
                JasperDesign jasperDesign = JRXmlLoader.load(jrxmlFileRealPath);
                jasperDesign.setIgnorePagination(true); // 设置为忽略分页
                jasperReport = JasperCompileManager.compileReport(jasperDesign);
            } else {
                jasperReport = (JasperReport) JRLoader.loadObject(jasperFile);
            }
            // 取得jasper使用的是何种查询语言：SQL/XPath
            JRQuery jasperQuery = jasperReport.getMainDataset().getQuery();
            String jasperQueryLanguage = jasperQuery == null ? "EMPTY"
                    : jasperQuery.getLanguage();
            Map<String, Object> reportParas=new HashMap<>();
            reportParas.put("id", businessId);
            if ("SQL".equals(jasperQueryLanguage.toUpperCase())) { // 如果是JDBC数据源
                jasperPrint = JasperFillManager.fillReport(jasperReport,
                        reportParas, conn);
            } else if ("XPATH".equals(jasperQueryLanguage.toUpperCase())) { // 如果是XML数据源
                // xml数据源
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory
                        .newInstance();
                DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
                String xmlSource = ""; // xml数据源字符串
                if (JasperReportUtil.isPreviewVoucher(jasperFilePath)) { // 如果是传票
                    if (reportParas.get("voucherInfoXml") != null) {
                        xmlSource = (String) reportParas
                                .get("voucherInfoXml");
                    }
                } else { // 如果是面函
                    if (reportParas.get("docListInfoXml") != null) {
                        xmlSource = (String) reportParas
                                .get("docListInfoXml");
                    }
                }
                Document document = dbBuilder.parse(new InputSource(
                        new StringReader(xmlSource)));
                reportParas
                        .put(
                                JRXPathQueryExecuterFactory.PARAMETER_XML_DATA_DOCUMENT,
                                document);
                reportParas.put(JRXPathQueryExecuterFactory.XML_LOCALE,
                        Locale.CHINESE);
                reportParas.put(JRParameter.REPORT_LOCALE, Locale.CHINA);
                reportParas.put(JRParameter.REPORT_CONNECTION, conn);
                jasperPrint = JasperFillManager.fillReport(jasperReport,
                        reportParas);
            } else if ("EMPTY".equals(jasperQueryLanguage.toUpperCase())) { // 如果是空数据源
                jasperPrint = JasperFillManager.fillReport(jasperReport,
                        reportParas, new JREmptyDataSource());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return jasperPrint;
    }
    /**
     * 如果导出的是excel，则需要去掉周围的margin
     * 
     * @param jasperReport
     * @param type
     */
    public static void prepareReport(JasperReport jasperReport, String type) {
        logger
                .debug("The method======= prepareReport() start.......................");
        if (EXCEL_TYPE.equals(type))
            try {
                Field margin = JRBaseReport.class
                        .getDeclaredField("leftMargin");
                margin.setAccessible(true);
                margin.setInt(jasperReport, 0);
                margin = JRBaseReport.class.getDeclaredField("topMargin");
                margin.setAccessible(true);
                margin.setInt(jasperReport, 0);
                margin = JRBaseReport.class.getDeclaredField("bottomMargin");
                margin.setAccessible(true);
                margin.setInt(jasperReport, 0);
                Field pageHeight = JRBaseReport.class
                        .getDeclaredField("pageHeight");
                pageHeight.setAccessible(true);
                pageHeight.setInt(jasperReport, 2147483647);
            } catch (Exception exception) {

            }

    }

    /**
     * 导出Excel
     * 
     * @param jasperPrint
     * @param defaultFilename
     * @param request
     * @param response
     * @throws IOException
     * @throws JRException
     */
    public static void exportExcel(JasperPrint jasperPrint,
            String defaultFilename, HttpServletRequest request,
            HttpServletResponse response) throws IOException, JRException {
        logger
                .debug("执行导出excel   The method======= exportExcel() start.......................");
        response.setContentType("application/vnd.ms-excel"); // 设置头文件信息
        String defaultname = null;
        if (defaultFilename.trim() != null && defaultFilename != null) {
            defaultname = defaultFilename + ".xls";
        } else {
            defaultname = "export.xls";
        }
        String fileName = new String(defaultname.getBytes("gbk"), "utf-8");
        response.setHeader("Content-disposition", "attachment; filename="
                + fileName);
        ServletOutputStream ouputStream = response.getOutputStream();
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
        exporter.setParameter(
                JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
                Boolean.TRUE);
        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
                Boolean.FALSE);
        exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
                Boolean.FALSE);
        exporter.exportReport();
        ouputStream.flush();
        ouputStream.close();
    }

    /**
     * 导出pdf，注意此处中文问题， 这里应该详细说：主要在ireport里变下就行了。看图
     * 1）在ireport的classpath中加入iTextAsian.jar 2）在ireport画jrxml时，看ireport最左边有个属性栏。
     * 下边的设置就在点字段的属性后出现。 pdf font name ：STSong-Light ，pdf encoding ：UniGB-UCS2-H
     */
    public static void exportPdf(JasperPrint jasperPrint,
            String defaultFilename, HttpServletRequest request,
            HttpServletResponse response) throws IOException, JRException {
        response.setContentType("application/pdf");
        String defaultname = null;
        if ( defaultFilename != null&&defaultFilename.trim() != null) {
            defaultname = defaultFilename + ".pdf";
        } else {
            defaultname = "export.pdf";
        }
        String fileName = new String(defaultname.getBytes("GBK"), "utf-8");
        response.setHeader("Content-disposition", "attachment; filename="
                + fileName);
        ServletOutputStream ouputStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, ouputStream);
        ouputStream.flush();
        ouputStream.close();
    }

    /**
     * 导出html
     * 
     * @param pageIndexStr
     */

    public static void exportHtml(JasperPrint jasperPrint,
            String defaultFilename, HttpServletRequest request,
            HttpServletResponse response, String pageIndexStr)
            throws IOException, JRException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String fileName=System.getProperty("java.io.tmpdir")+System.currentTimeMillis()+".html";
        JasperExportManager.exportReportToHtmlFile(jasperPrint, fileName);
        ServletOutputStream ouputStream = response.getOutputStream();
        ouputStream.write(FileUtils.readFileToByteArray(new File(fileName)));
        new File(fileName).deleteOnExit();
        ouputStream.flush();
        ouputStream.close();
    }

    /**
     * description:通过传进来的pageIndexStr 得到当前页码 Date 2013-1-18 上午10:49:38
     * 
     * @param @param jasperPrint
     * @param @param request
     * @param @param pageIndexStr
     * @param @return
     * @return Integer
     */
    private static Integer getPageIndex(JasperPrint jasperPrint,
            HttpServletRequest request, String pageIndexStr) {
        if (pageIndexStr == null || StringUtils.isBlank(pageIndexStr)) { // 如果pageIndexStr为空或空字符串则返回null
            return null;
        }
        Integer pageIndex = 0;
        int lastPageIndex = 0;
        if (jasperPrint.getPages() != null) { // 得到最后一页的 页码
            lastPageIndex = jasperPrint.getPages().size() - 1;
        }
        if ("lastPage".equals(pageIndexStr)) { // 如果字符串＝＝lastPage
            // 则反lastPageIndex的值赋给pageIndex
            // 并返回pageIndex
            pageIndex = lastPageIndex;
            return pageIndex;
        }
        try {
            pageIndex = Integer.parseInt(pageIndexStr);
            if (pageIndex > 0) { // 从ireport
                // 传来的PageIndex是从1开始，而JRExporterParameter.PAGE_INDEX是从0开始的
                pageIndex = pageIndex - 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageIndex > lastPageIndex) {
            pageIndex = lastPageIndex;
        }
        return pageIndex;
    }

    /**
     * 导出word
     */

    public static void exportWord(JasperPrint jasperPrint,
            String defaultFilename, HttpServletRequest request,
            HttpServletResponse response) throws JRException, IOException {
        response.setContentType("application/msword;charset=utf-8");
        String defaultname = null;
        if (defaultFilename.trim() != null && defaultFilename != null) {
            defaultname = defaultFilename + ".doc";
        } else {
            defaultname = "export.doc";
        }
        String fileName = new String(defaultname.getBytes("GBK"), "utf-8");
        response.setHeader("Content-disposition", "attachment; filename="
                + fileName);
        JRExporter exporter = new JRRtfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response
                .getOutputStream());
        exporter.exportReport();
    }

    /**
     * 按照类型导出不同格式文件
     * 
     * @param datas
     *            数据
     * @param type
     *            文件类型
     * @param is
     *            jasper文件的来源
     * @param request
     * @param response
     * @param defaultFilename默认的导出文件的名称
     */

    private static void export(Collection datas, String type,
            String defaultFilename, InputStream is, HttpServletRequest request,
            HttpServletResponse response, String pageIndexStr) {
        logger
                .debug("导出判断     The method======= export() start.......................");
        try {
            logger.info("is==" + is);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(is);
            prepareReport(jasperReport, type);
            JRDataSource ds = new JRBeanCollectionDataSource(datas, false);
            Map parameters = new HashMap();
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport, parameters, ds);
            if (EXCEL_TYPE.equals(type)) {
                exportExcel(jasperPrint, defaultFilename, request, response);
            } else if (PDF_TYPE.equals(type)) {
                exportPdf(jasperPrint, defaultFilename, request, response);
            } else if (HTML_TYPE.equals(type)) {
                exportHtml(jasperPrint, defaultFilename, request, response,
                        pageIndexStr);
            } else if (WORD_TYPE.equals(type)) {
                exportWord(jasperPrint, defaultFilename, request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出入口 （导出所有的数据 ）
     * 
     * @param exportType
     *            导出文件的类型
     * @param jaspername
     *            jasper文件的名字 如： xx.jasper
     * @param lists
     *            导出的数据
     * @param request
     * @param response
     * @param defaultFilename默认的导出文件的名称
     */
    public static void exportmain(String exportType, String jaspername,
            List lists, String defaultFilename,HttpServletRequest request,
            HttpServletResponse response) {
        exportmain(exportType, jaspername, lists, defaultFilename, null,request,response);
    }

    /**
     * 导出入口 (分页显示 导出传入的pageIndex 页的数据)
     * 
     * @param exportType
     *            导出文件的类型
     * @param jaspername
     *            jasper文件的名字 如： xx.jasper
     * @param lists
     *            导出的数据
     * @param request
     * @param response
     * @param defaultFilename默认的导出文件的名称
     * @param pageIndex
     *            需要导出数据的页码 当pageIndex = null时导出所有数据
     */

    @SuppressWarnings("deprecation")
    public static void exportmain(String exportType, String jaspername,
            List lists, String defaultFilename, String pageIndexStr,HttpServletRequest request,
            HttpServletResponse response) {
        logger
                .debug("进入导出    The method======= exportmain() start.......................");
        String jasperFileRealPath = JasperReportUtil.class.getClassLoader().getResource("jasperreport/"+jaspername).getFile().replaceFirst("/", "");
        File file = new File(jasperFileRealPath);
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        export(lists, exportType, defaultFilename, is, request, response,
                pageIndexStr);
    }
}