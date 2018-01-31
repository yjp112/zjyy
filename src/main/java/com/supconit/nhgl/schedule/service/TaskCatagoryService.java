/**
 * 
 */
package com.supconit.nhgl.schedule.service;

import java.util.List;

import com.supconit.nhgl.schedule.entites.TaskCatagory;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-17 00:48:22 
 * @since 
 * 
 */
public interface TaskCatagoryService extends hc.orm.BasicOrmService<TaskCatagory, Long> {

	/**
	 * 分页按条件查询。
	 * @param pager
	 * @param condition
	 * @return
	 */
	Pageable<TaskCatagory> find(Pageable<TaskCatagory> pager, TaskCatagory condition);
	List<TaskCatagory> getTaskCategory();
	int deleteByIds(Long[] ids);
	Pageable<TaskCatagory> findTaskByPager(Pageable<TaskCatagory> pager,
			TaskCatagory condition);
}
