package com.supconit.common.services.impl;

import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.sax.SAXResult;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import com.supconit.common.daos.MenuOperateDao;
import com.supconit.common.domains.MenuOperate;
import com.supconit.common.services.AbstractBaseTreeServiceImpl;
import com.supconit.common.services.MenuExportService;
import com.supconit.honeycomb.business.authorization.xml.entities.MenuXml;
import com.supconit.honeycomb.business.authorization.xml.entities.MenuXmlList;
import com.supconit.honeycomb.business.authorization.xml.entities.OperateXml;
import com.supconit.honeycomb.business.authorization.xml.entities.OperateXmlList;

@Service
public class MenuExportServiceImpl implements MenuExportService {
	private transient static final Logger log = LoggerFactory.getLogger(AbstractBaseTreeServiceImpl.class);
	@Resource
	private MenuOperateDao menuOperateDao;
	private String encoding="UTF-8";

	@Override
	public String exportAllMenuForXML() throws Exception {
		List<MenuOperate> list = menuOperateDao.selectAllMenuOperate();
		Map<String, MenuXml> menuMap = new LinkedHashMap<String, MenuXml>();
		for (MenuOperate m : list) {
			String mcode = m.getMcode();
			MenuXml menuXml = null;
			if (menuMap.containsKey(mcode)) {
				menuXml = menuMap.get(mcode);
			} else {
				menuXml = new MenuXml();
				menuMap.put(mcode, menuXml);
			}
			menuXml.setCode(mcode);
			menuXml.setParentCode(m.getMparentcode());
			menuXml.setName(m.getMname());
			menuXml.setLinkUrl(m.getMlinkUrl());
			menuXml.setShowIcon(m.getMshowIcon());
			menuXml.setSortValue(m.getMsortValue());
			menuXml.setDisplay(m.getMdisplay());
			menuXml.setDescription(m.getMdescription());
			menuXml.setResources(m.getMresources());
			OperateXmlList operates = null;
			if (menuXml.getOperates() != null) {
				operates = menuXml.getOperates();
			} else {
				operates = new OperateXmlList();
				menuXml.setOperates(operates);
			}
			OperateXml operate = new OperateXml();
			if(StringUtils.isBlank(m.getOcode())){
				continue;
			}
			operates.add(operate);
			operate.setCode(m.getOcode());
			operate.setName(m.getOname());
			operate.setIcon(m.getOicon());
			operate.setOnclick(m.getOonclick());
			operate.setSortValue(m.getOsortValue());
			operate.setListButton(m.getOlistButton());
			operate.setDescription(m.getOdescription());
			operate.setResources(m.getOresources());

		}
		MenuXmlList menus = new MenuXmlList();
		for (Map.Entry<String, MenuXml> menu : menuMap.entrySet()) {
			menus.add(menu.getValue());
		}
		
	     JAXBContext context = JAXBContext.newInstance(new Class[] { menus.getClass() });
	     Marshaller marshaller = context.createMarshaller();
	     marshaller.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
	     marshaller.setProperty("jaxb.encoding", encoding);
	     
	     StringWriter writer = new StringWriter();
	     marshaller.marshal(menus, new SAXResult( getXMLSerializer(writer,new String[] { "^onclick"}).asContentHandler() ));
		return writer.toString();
	}

	private static XMLSerializer getXMLSerializer(StringWriter writer,String[] cDataElements) {
		// configure an OutputFormat to handle CDATA
		OutputFormat of = new OutputFormat();

		// specify which of your elements you want to be handled as CDATA.
		// The use of the '^' between the namespaceURI and the localname
		// seems to be an implementation detail of the xerces code.
		// When processing xml that doesn't use namespaces, simply omit the
		// namespace prefix as shown in the third CDataElement below.
		of.setCDataElements(cDataElements); //

		// set any other options you'd like
		of.setPreserveSpace(true);
		of.setIndenting(true);
		of.setLineSeparator(SystemUtils.LINE_SEPARATOR);
		// create the serializer
		XMLSerializer serializer = new XMLSerializer(of);

		serializer.setOutputCharStream(writer);

		return serializer;
	}

}