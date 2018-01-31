package com.supconit.duty.services.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supconit.common.services.AbstractBaseBusinessService;
import com.supconit.duty.daos.DutyLogDao;
import com.supconit.duty.daos.DutyLogDetailDao;
import com.supconit.duty.entities.DutyLog;
import com.supconit.duty.entities.DutyLogDetail;
import com.supconit.duty.services.DutyLogService;

import hc.base.domains.Pageable;
@Service
public class DutyLogServiceImp extends AbstractBaseBusinessService<DutyLog, Long> implements DutyLogService{
	
	@Autowired
	private DutyLogDao		dutyLogDao;	
	@Autowired
	private DutyLogDetailDao		dutyLogDetailDao;	
	
	@Override
	@Transactional(readOnly = true)
	public DutyLog getById(Long id) {
		if (null == id || id <= 0)
			return null;
		DutyLog dutyLog = dutyLogDao.getById(id);
		return dutyLog;
	}

	@Override
	@Transactional(readOnly = true)
	public void insert(DutyLog dutyLog) {
		dutyLogDao.insert(dutyLog);
		if(dutyLog.getDutyType()!=3&&dutyLog.getDutyType()!=4){ //3 表示总值班 无需关联值班详情表
			if(dutyLog.getDutyLogDetailList()!=null){
				for (DutyLogDetail dutyLogDetail : dutyLog.getDutyLogDetailList()) { 
					dutyLogDetail.setDutyLogId(dutyLog.getId()); 
					dutyLogDetailDao.insert(dutyLogDetail);
				}
			}
		}
		
		
		
	}

	@Override
	public void update(DutyLog dutyLog) {
		//先更新值班日志
		dutyLogDao.update(dutyLog);
		if(dutyLog.getDutyType()!=3&&dutyLog.getDutyType()!=4){ //3 表示总值班 无需关联值班详情表
			//根据值班日志的id更新详情表
			//将对应的详情数据先删除掉在添加
			deleteDetail(dutyLog);
			insertDetail(dutyLog);
		}
	}

	/*private int chackDetail(DutyLog dutyLog) {
		 int checked=0; 
		 List<DutyLogDetail> dutyLogDetail =dutyLogDetailDao.getByIds(dutyLog.getId());
		 if(dutyLogDetail.size()==dutyLog.getDutyLogDetailList().size()){//只做修改
			 checked=1;
		 }else if(dutyLogDetail.size()>dutyLog.getDutyLogDetailList().size()){
			 checked=1;
		 }
		return checked;
	}*/
	
	/*
	 * 将对应的值班详情数据插入导数据库
	 */
	private void insertDetail(DutyLog dutyLog) {
		if(dutyLog.getDutyLogDetailList()!=null){
			for (DutyLogDetail dutyLogDetail : dutyLog.getDutyLogDetailList()) { 
				dutyLogDetail.setDutyLogId(dutyLog.getId()); 
				dutyLogDetailDao.insert(dutyLogDetail);
			}
		}
	}

	/*
	 * 将对应的值班详情数据先删除掉
	 */
	private void deleteDetail(DutyLog dutyLog) {
		List<DutyLogDetail> DetailList=dutyLogDetailDao.findAll(dutyLog.getId()); 
		for (DutyLogDetail dutyLogDetail : DetailList) { 
			dutyLogDetailDao.deleteById(dutyLogDetail.getId());
		}
	}



	@Override
	@Transactional(readOnly = true)
	public Pageable<DutyLog> findByCondition(Pageable<DutyLog> pager,DutyLog condition) {	
		Pageable<DutyLog> dutyLogPage =dutyLogDao.findByCondition(pager , condition);
		if(dutyLogPage!=null){
			for (int i = 0; i < dutyLogPage.size(); i++) {
				DutyLog dulog=dutyLogPage.get(i);
				if(dulog.getOrderType()==1){        //1 表示早班   2 中班       3 晚班
					dulog.setOrderName("早班");
				}else if(dulog.getOrderType()==2){
					dulog.setOrderName("中班");
				}else if(dulog.getOrderType()==3){
					dulog.setOrderName("晚班");
				}
				dutyLogPage.set(i, dulog);
			}
		}
		return dutyLogPage;
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteByIds(Long[] ids, Long type) {
		dutyLogDao.deleteById(ids[0]);//删除值班日志
		/*删除值班日志对应的值班详情
		*/
		if(type!=3&&type!=4){  
			List<DutyLogDetail> DetailList=dutyLogDetailDao.findAll(ids[0]); 
			for (DutyLogDetail dutyLogDetail : DetailList) { 
				dutyLogDetailDao.deleteById(dutyLogDetail.getId());
			}
		}
	}




}
