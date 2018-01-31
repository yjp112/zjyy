package com.supconit.common.web.controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.util.io.InputStreamSource;
import org.activiti.engine.impl.util.io.StreamSource;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supconit.honeycomb.mvc.utils.UploadUtils;
import com.supconit.honeycomb.mvc.utils.UploadUtils.UploadRenamePolicy;
import com.supconit.honeycomb.mvc.utils.UploadUtils.UploadedFile;

import hc.base.domains.AjaxMessage;
import jodd.io.StreamUtil;
import jodd.util.StringUtil;

@Controller
@RequestMapping("/bpm/model")
public class BpmModelController {
	@Value("${file.tmpsave}")
	private String tmpPath;

	@Value("${file.persistentsave}")
	private String savePath;
	private static final transient Logger logger = LoggerFactory.getLogger(BpmModelController.class);
	@Autowired
	private ProcessEngineFactoryBean processEngine;
	@Autowired
	private RepositoryService repositoryService;

	@RequestMapping(value = { "export" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public void doExport(@RequestParam("id") String id, HttpServletResponse response) throws IOException {
		Model model = this.repositoryService.getModel(id);
		ObjectNode modelNode = (ObjectNode) new ObjectMapper()
				.readTree(this.repositoryService.getModelEditorSource(model.getId()));
		BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(modelNode);
		byte[] bpmnXML = new BpmnXMLConverter().convertToXML(bpmnModel);
		ByteArrayInputStream bais = new ByteArrayInputStream(bpmnXML);
		String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
		response.setHeader("Content-Disposition", "attachment; filename=" + filename);
		StreamUtil.copy(bais, response.getOutputStream());
		response.flushBuffer();
		bais.close();
	}

	@ResponseBody
	@RequestMapping("import")
	public AjaxMessage doImport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		long startTime = System.currentTimeMillis();

		logger.debug("开始文件上传");
		logger.debug("文件临时存放目录：{}", tmpPath);
		System.out.println(tmpPath);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String result = "";
		try {
			MultiValueMap<String, UploadedFile> fileMap = UploadUtils.uploadFiles(request, tmpPath, Integer.MAX_VALUE,
					"UTF-8", new UploadRenamePolicy() {
						@Override
						public String rename(String s, String s1) {
							String postfix = s1.substring(s1.indexOf(".") + 1, s1.length());
							return UUID.randomUUID().toString() + "." + postfix;
						}
					}, null);

			List<UploadedFile> file = fileMap.get("file");
			DecimalFormat df2 = new DecimalFormat("0.00");

			if (file != null) {
				UploadedFile saveFile = file.get(0);
				result = "{ \"flag\": \"1\"," + "\"orignalname\" : \"" + saveFile.getSystemFileName() + "\","
						+ "\"filePath\" : \"" + tmpPath + saveFile.getFile().getName() + "\"," + "\"filename\" : \""
						+ saveFile.getFile().getName() + "\"," + "\"postfix\" : \""
						+ saveFile.getFile().getName().substring(saveFile.getFile().getName().indexOf(".") + 1,
								saveFile.getFile().getName().length())
						+ "\"," + "\"filesize\" : \"" + df2.format((double) saveFile.getFile().length() / 1024) + "\"}";
			}
			out.print(result);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			result = "{\"flag\":\"0\"}";
			out.print(result);
			out.close();
		}
		logger.debug("文件上传结束");
		long endTime = System.currentTimeMillis();
		logger.info("startTime=" + startTime);
		logger.info("endTime=" + endTime);
		logger.info("耗时(秒):" + (endTime - startTime) / 1000);
		return null;

	}

	@ResponseBody
	@RequestMapping(value = { "copy" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public AjaxMessage doCopy(@RequestParam(value = "id", required = false) String id)
			throws JsonProcessingException, IOException {
		if ((StringUtil.isBlank(id)) || (!StringUtils.isNumeric(id))) {
			return AjaxMessage.error("参数错误");
		}
		Model model = this.repositoryService.getModel(id);
		if (null == model)
			return AjaxMessage.error("该模型不存在");
		ObjectNode modelNode = (ObjectNode) new ObjectMapper()
				.readTree(this.repositoryService.getModelEditorSource(model.getId()));
		BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(modelNode);
		byte[] bpmnXML = new BpmnXMLConverter().convertToXML(bpmnModel);
		ByteArrayInputStream bais = new ByteArrayInputStream(bpmnXML);
		try {
			importModel(bais);
			return AjaxMessage.success();
		} catch (Exception e) {
			return AjaxMessage.error(e.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping("importModel")
	public AjaxMessage importModel(String[] filePaths) {
		for (String filePath : filePaths) {
			try {
				importModel(new FileInputStream(new File(filePath)));
			} catch (UnsupportedEncodingException | FileNotFoundException e) {
				logger.error("模型导入失败。xml[" + filePath + "]", e);
			}
		}
		return AjaxMessage.success();

	}

	private void importModel(InputStream xmlStream) throws UnsupportedEncodingException {
		String encoding = processEngine.getProcessEngineConfiguration().getXmlEncoding();
		StreamSource xmlSource = new InputStreamSource(xmlStream);
		BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xmlSource, false, false, encoding);
		Model modelData = repositoryService.newModel();
		modelData.setKey(bpmnModel.getMainProcess().getId());
		modelData.setName(bpmnModel.getMainProcess().getName());
		// modelData.setCategory(processDefinition.getDeploymentId());

		ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
		modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, bpmnModel.getMainProcess().getName());
		// modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
		modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, bpmnModel.getMainProcess().getDocumentation());
		modelData.setMetaInfo(modelObjectNode.toString());
		repositoryService.saveModel(modelData);
		repositoryService.addModelEditorSource(modelData.getId(),
				new BpmnJsonConverter().convertToJson(bpmnModel).toString().getBytes(encoding));
	}

	private String deploy(BpmnModel bpmnModel) throws UnsupportedEncodingException {
		Deployment deployment = repositoryService.createDeployment().name(bpmnModel.getMainProcess().getName())
				.addString(bpmnModel.getMainProcess().getId() + ".bpmn20.xml",
						new String(new BpmnXMLConverter().convertToXML(bpmnModel)))
				.deploy();
		return deployment.getId();
	}
}
