package com.supconit.nhgl.utils;

import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.supconit.honeycomb.base.context.SpringContextHolder;
import com.supconit.honeycomb.cache.Cache;
import com.supconit.nhgl.base.entities.NhglOrganization;
import com.supconit.nhgl.base.service.NhglOrganizationService;

/**
 * @文 件 名：OrganizationUtils.java
 * @创建日期：2015年7月2日
 * @版 权：Copyrigth(c)2015
 * @公司名称：浙江浙大中控信息技术有限公司
 * @开发人员：丁阳光
 * @版 本:
 * @描 述：人员组织关系工具类
 */
public class OrganizationUtils {
	private static Cache cache = SpringContextHolder.getBean(Cache.class);
	private static String group = NhglOrganization.class.getName();
	private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	private static NhglOrganizationService service = SpringContextHolder.getBean(NhglOrganizationService.class);
	public static	String getFullDeptNameByDeptId(long deptId){
		rwl.readLock().lock();
		try {
			String result = cache.get(group, String.valueOf(deptId));
			if (result == null) {
				rwl.readLock().unlock();
				rwl.writeLock().lock();
				try {
					if (result == null) {
						result = service.getFullDeptNameByDeptId(deptId);
						cache.put(group, String.valueOf(deptId), result);
					}
					
				}catch(Exception e){
					e.printStackTrace();
				}finally {
					rwl.writeLock().unlock(); // Unlock write, still hold read
				}
				rwl.readLock().lock();
			}
			return result;
		} finally {
			rwl.readLock().unlock();
		}
	}
	public static	List<NhglOrganization> getFullDeptNameByPersonId(long personId){
		rwl.readLock().lock();
		try {
			List<NhglOrganization> result = cache.get(group, String.valueOf(personId));
			if (result == null) {
				rwl.readLock().unlock();
				rwl.writeLock().lock();
				try {
					if (result == null) {
						result = service.getFullDeptNameByPersonId(personId);
						cache.put(group, String.valueOf(personId), result);
					}
					
				}catch(Exception e){
					e.printStackTrace();
				}finally {
					rwl.writeLock().unlock(); // Unlock write, still hold read
				}
				rwl.readLock().lock();
			}
			return result;
		} finally {
			rwl.readLock().unlock();
		}
	}
	
	public static String joinFullDeptName(List<NhglOrganization> organizations){
		if(organizations==null||organizations.size()==0){
			return "";
		}
		String result=organizations.get(0).getFullDeptName();
		for (int i = 1; i < organizations.size(); i++) {
			result=result+";"+organizations.get(i).getFullDeptName();
		}
		return result;
	}
}
