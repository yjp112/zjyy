/**
 * 
 */
package com.supconit.nhgl.schedule.dao;

import java.util.List;

import com.supconit.nhgl.schedule.entites.TaskCatagory;

import hc.base.domains.Pageable;

/**
 * @author 
 * @create 2014-06-17 00:48:22
 * @since 
 * 
 */
 
public interface TaskCategoryDao extends hc.orm.BasicDao<TaskCatagory, Long> {

	/**
	 * @param pager
	 * @param condition
	 * @return
	 */
	Pageable<TaskCatagory> findByPager(Pageable<TaskCatagory> pager, TaskCatagory condition);
	List<TaskCatagory> getTaskCategory();
	int deleteByIds(Long[] ids);
	Pageable<TaskCatagory> findTaskByPager(Pageable<TaskCatagory> pager,
			TaskCatagory condition);
}
