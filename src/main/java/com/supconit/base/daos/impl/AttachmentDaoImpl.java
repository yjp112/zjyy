
package com.supconit.base.daos.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.supconit.base.daos.AttachmentDao;
import com.supconit.base.entities.Attachment;
import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.common.utils.DictUtils;
import com.supconit.common.utils.DictUtils.DictTypeEnum;

import hc.base.domains.Pageable;
import jodd.io.FileUtil;



@Repository
public class AttachmentDaoImpl extends AbstractBaseDao<Attachment, Long> implements AttachmentDao {
	
	/**
	 * 日志服务。
	 */
	private transient static final Logger	logger	= LoggerFactory.getLogger(AttachmentDaoImpl.class);

    private static final String	NAMESPACE	= Attachment.class.getName();
	@Value("${file.tmpsave}")
	private String tmpPath;

	@Value("${file.persistentsave}")
	private String savePath;
	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}
    
	@Override
	public Pageable<Attachment> findByCondition(Pageable<Attachment> pager, Attachment condition) {
		return findByPager(pager,"findByCondition", "countByCondition", condition);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}
    @Override
	public Attachment findById(Long id) {
		return selectOne("findById",id);
	}
    @Override
	public List<Attachment> getAttachmentByFid(Long fId,String modelType) {
    	Attachment att = new Attachment();
    	att.setFId(fId);
    	att.setModelType(modelType);
		return selectList("findByFid",att);
	}
    @Override
    public void saveAttachements(Long objId,String objType, String[] fileorignal,
			String[] filename, String[] delfiles,String fileLength){
			if("".equals(fileLength)){//配置上传文件大小
				fileLength = DictUtils.getDictValue(DictUtils.getDictList(DictTypeEnum.FILELENGTH), "默认");
			}
			if (delfiles != null) {
				for (String item : delfiles) {
					Attachment att = this.getById(Long
							.parseLong(item));
					// 删除磁盘文件
					String filePath = savePath + "/" + att.getStorePath();
					try {
						FileUtil.delete(filePath);
					} catch (Exception e) {
						System.out.println("删除磁盘文件失败");
					}
					
					// 删除数据库记录
					this.deleteById(att.getId());
				}
			}

			// 新文件移动并保存
			if (fileorignal != null) {
				int fileNum = fileorignal.length;
				for (int i = 0; i < fileNum; i++) {

					Attachment att = new Attachment();
					
					File saveFile = new File(tmpPath + "/" + filename[i]);
					if (checkFileSize(saveFile, Integer.valueOf(fileLength) * 1024 * 1024L)) { 
						//throw new Exception("新增附件失败,附件过大");
						logger.error("新增附件失败,附件过大"+filename[i]);
						throw new BusinessDoneException("新增附件失败,附件过大");  
					}
					att.setFileSize((long)Math.ceil(saveFile.length()/1024));//单位KB
					// 移动文件
					try {
						System.out.println(filename[i]);
						FileUtil.move(tmpPath + "/" + filename[i], savePath + "/"+ filename[i]);
					} catch (Exception e) {
						logger.error("新增附件失败,移动文件失败"+filename[i]+e.getMessage());
						throw new BusinessDoneException("新增附件失败,移动文件失败"+e.getMessage());  
					}				
					
					att.setFileName(fileorignal[i]);
					att.setStorePath(filename[i]);
					att.setFId(objId);
					att.setModelType(objType);
					att.setFileType(fileorignal[i].substring(fileorignal[i].lastIndexOf(".")+1, fileorignal[i].length()));
					
					// 增加数据库记录
					this.insert(att);
				}
			}

	}

	// 判断文件是否过大
	public boolean checkFileSize(File file, Long maxSize) {
		boolean tooBig = false;
		if (file.length() > maxSize) {
			tooBig = true;
		}
		return tooBig;
	}
	
	@Override
	public int deleteByObj(Long fId,String modelType) {
		Attachment at =  new Attachment();
		at.setFId(fId);
		at.setModelType(modelType);
		return update("deleteByObj", at);
	}
	
}