package com.supconit.common.utils.ireport;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.query.JRXPathQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;


public class JasperReportUtil {
    private JasperPrint jasperPrint;
    private int currentPage;
    private int totalPagesNumber;
    private String reportName;

    public JasperPrint getJasperPrint() {
        return jasperPrint;
    }

    public void setJasperPrint(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPagesNumber() {
        return totalPagesNumber;
    }

    public void setTotalPagesNumber(int totalPagesNumber) {
        this.totalPagesNumber = totalPagesNumber;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public void exportHTML(HttpServletRequest request,
                           HttpServletResponse response, String path, String id) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            String  a =request.getContextPath();
            JRHtmlExporter exporter = new JRHtmlExporter();
            exporter.setParameter(JRHtmlExporterParameter.PAGE_INDEX,
                    currentPage - 1);
            exporter
                    .setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            // 设置页面头部
            exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER,
                    getHtmlHeaderStr(path, id,a));
            exporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT,"pt");
            exporter.setParameter(JRHtmlExporterParameter.FRAMES_AS_NESTED_TABLES,false);
            // 设置图片处理的Servlet
            exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,
                    "image?image=");
            exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response
                    .getWriter());
            exporter.setParameter(
                    JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, false);
            exporter.exportReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出为WORD格式的报表
     *
     * @param request
     * @param response
     */
    public void exportWORD(HttpServletRequest request,
                           HttpServletResponse response) {
        try {
            // [BegFree_2012_Swift报文导出Word格式混乱 ]_B wangjun 2013-12-4
            JRExporter exporter = new JRRtfExporter();
            // JRBaseStyle style = new JRBaseStyle();
            // style.setHorizontalAlignment(HorizontalAlignEnum.LEFT);
            response.setContentType("application/msword;charset=ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment; filename="
                    + reportName + ".doc");

            exporter
                    .setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response
                    .getOutputStream());

            exporter.exportReport();
            // [BegFree_2012_Swift报文导出Word格式混乱 ]_B wangjun 2013-12-4
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出为EXCEL格式的报表
     *
     * @param request
     * @param response
     */
    public void exportEXCEL(HttpServletRequest request,
                            HttpServletResponse response) {
        try {
            ByteArrayOutputStream oStream = new ByteArrayOutputStream();
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE,
                    Boolean.TRUE);
            exporter
                    .setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, oStream);
            exporter.setParameter(
                    JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
                    Boolean.TRUE);
            exporter.setParameter(
                    JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
                    Boolean.FALSE);
            exporter.exportReport();
            byte[] bytes = oStream.toByteArray();
            if (bytes != null && bytes.length > 0) {
                response.reset();
                response.setContentType("application/vnd.ms-excel;");
                response.setHeader("Content-Disposition",
                        "attachment;filename=" + reportName + ".xls");
                response.setContentLength(bytes.length);
                ServletOutputStream ouputStream = response.getOutputStream();
                ouputStream.write(bytes, 0, bytes.length);
                ouputStream.flush();
                ouputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出为PDF格式的报表
     *
     * @param request
     * @param response
     */
    public void exportPDF(HttpServletRequest request,
                          HttpServletResponse response) {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename="
                    + reportName + ".pdf");

            JRPdfExporter exporter = new JRPdfExporter();
            exporter
                    .setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response
                    .getOutputStream());
            exporter.exportReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量导出PDF
     *
     * @param request
     * @param response
     * @param jasperPrintList
     */
    public static void exportPatchPDF(HttpServletRequest request,
                                      HttpServletResponse response, ArrayList<JasperPrint> jasperPrintList) {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename="
                    + new String("report.pdf".getBytes(), "ISO-8859-1"));
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,
                    jasperPrintList);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response
                    .getOutputStream());
            exporter.exportReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getHtmlHeaderStr(String path, String id,String head) {
        StringBuffer headStr = new StringBuffer("");
        headStr.append("<html>\r\n");
        headStr.append("    <head>\r\n");
        headStr
               .append("        <script type=\"text/javascript\" src=\""+head+"/main_static/custom/js/ReportCommonFun.js\" charset=\"utf-8\"></script>\r\n");
        headStr                .append("        <script type=\"text/javascript\" src=\""+head+"/main_static/custom/js/jasperreport.js\" charset=\"utf-8\"></script>\r\n");
        headStr.append("        <script type=\"text/javascript\">\r\n");
        headStr.append("            window.onload = function() {\r\n");
        headStr.append("                var currentPage = " + currentPage
                + ";\r\n");
       // headStr
        //        .append("                document.getElementById('currentPage').value = "
       //                 + currentPage + ";\r\n");
   /*     headStr.append("                    if (currentPage == 1) {" + "\r\n");
        headStr
                .append("                        document.getElementById('first').disabled = 'true';\r\n");
        headStr
                .append("                        document.getElementById('previous').disabled = 'true';\r\n");
        headStr.append("                    }\r\n");
        headStr.append("                    if (currentPage == "
                + totalPagesNumber + ") {\r\n");
        headStr
                .append("                        document.getElementById('next').disabled = 'true';\r\n");
        headStr
                .append("                        document.getElementById('last').disabled = 'true';\r\n");
        headStr.append("                    }\r\n");*/

        headStr.append("            }; " + "\r\n");
        headStr
                .append("            function jasperPrint(prtRef, rePrtAuth, reviewAuth, flag, userid, orgid) {"
                        + "\r\n");
        headStr
                .append("                if (beforeReportPrint(prtRef, rePrtAuth, reviewAuth)) {"
                        + "\r\n");
        headStr.append("                    var jasperParametetId = \""
                + id + "\";" + "\r\n");
        headStr.append("                    printPDF2(jasperParametetId,\""+head+"\");"
                + "\r\n");
        headStr
                .append("                    updateReportPrintCounts(prtRef,flag,userid,orgid);"
                        + "\r\n");
        headStr.append("                }" + "\r\n");
        headStr.append("            }" + "\r\n");

        headStr.append("            function jasperPrint2() {" + "\r\n");
        headStr.append("                  var jasperParametetId = \""
                + id + "\";" + "\r\n");
  
        headStr
                .append("                printPDF2(jasperParametetId,\""+head+"\");"
                        + "\r\n");
        headStr.append("            }" + "\r\n");

        headStr.append("            function goToPage(obj) {\r\n");
        headStr
                .append("              var currentPage =  document.getElementById(\"currentPage\").value;\r\n");
        headStr.append("                var regExp =  /^[1-9][0-9]*$/;\r\n");
        headStr
                .append("                if(!currentPage.match(regExp)) {currentPage = "
                        + currentPage + ";}\r\n");
        headStr.append("                if(currentPage > " + totalPagesNumber
                + ") {currentPage = " + currentPage + ";}\r\n");
        headStr
                .append("                currentPage = parseInt(currentPage);\r\n");
        headStr
                .append("                if(obj.id == 'first') {currentPage = 1;}\r\n");
        headStr
                .append("                if(obj.id == 'previous') {currentPage = "
                        + (currentPage - 1) + ";}\r\n");
        headStr.append("                if(obj.id == 'next') {currentPage = "
                + (currentPage + 1) + ";}\r\n");
        headStr.append("                if(obj.id == 'last') {currentPage = "
                + totalPagesNumber + ";}\r\n");
        headStr
                .append("                document.getElementById(\"currentPage\").value = currentPage;\r\n");
        headStr
                .append("                document.getElementById(\"exportType\").value = 'html';\r\n");
        headStr.append("                jasperForm.submit();\r\n");
        headStr.append("            }\r\n");

        headStr.append("            function exportReport(obj) {\r\n");
        headStr
                .append("                document.getElementById(\"exportType\").value = obj.id;\r\n");
        headStr.append("                jasperForm.submit();\r\n");
        headStr.append("            }\r\n");

        headStr.append("        </script>\r\n");
        headStr.append("    </head>\r\n");
        headStr.append("    <body     id=\"report_body\">\n");
        headStr
                .append("        <form name=\"jasperForm\" action=\""+head+"/repair/task/ireport\" method=\"post\">\r\n");
       // headStr
        //        .append("            <input id=\"first\" type=\"button\" value=\"首页\" onclick=\"goToPage(this)\" />\r\n");
      //  headStr
       //         .append("            <input id=\"previous\" type=\"button\" value=\"上一页\" onclick=\"goToPage(this)\" />\r\n");
     //   headStr
       //         .append("            <input id=\"currentPage\" type=\"text\" name=\"currentPage\" size=\"5\"/>\r\n");

       // headStr
      //          .append("            <input type=\"button\" value=\"go\" onclick=\"goToPage(this)\" />\r\n");
    //    headStr.append("            共" + totalPagesNumber + "页\r\n");
      //  headStr
     //           .append("            <input id=\"next\" type=\"button\" value=\"下一页\" onclick=\"goToPage(this)\" />\r\n");
     //   headStr
       //         .append("            <input id=\"last\" type=\"button\" value=\"末页\" onclick=\"goToPage(this)\" />\r\n");


        // 报表管理中的打印无须控制，直接打印
        headStr
                .append("            <input id='print' type='button' value='打印' onclick=\"jasperPrint2();\" />\r\n");


      /*  headStr
                .append("            <input id=\"word\" type='button' value='导出word' onclick=\"exportReport(word)\" />\r\n");
        headStr
                .append("            <input id=\"excel\" type='button' value='导出excel' onclick=\"exportReport(excel)\" />\r\n");
        headStr
                .append("            <input id=\"pdf\" type='button' value='导出pdf' onclick=\"exportReport(pdf)\" />\r\n");*/

        headStr
                .append("            <input id=\"exportType\" name=\"exportType\" type=\"hidden\" />\r\n");
        headStr
                .append("            <input name=\"id\" type=\"hidden\" value=\""+id+"\" />\r\n");
        headStr
                .append("            <input type=\"hidden\" name =\"path\" value="
                        + path + " />\r\n");


        headStr.append("        </form>\r\n");

        System.err.println(headStr.toString());
        return headStr.toString();
    }

    public static boolean isPreviewVoucher(String path) {
        if (path.indexOf("previewVoucher") != -1) {
            return true;
        }
        return false;
    }

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
            reportParas.put("ID", businessId);
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
    
}
