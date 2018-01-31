package com.supconit.repair.daos.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.common.utils.UtilTool;
import com.supconit.repair.daos.RepairDeviceCategoryPersonDao;
import com.supconit.repair.entities.RepairDeviceCategoryPerson;

import hc.base.domains.Pageable;

@Repository
public class RepairDeviceCategoryPersonDaoImpl extends AbstractBaseDao<RepairDeviceCategoryPerson, Long> implements RepairDeviceCategoryPersonDao {

	
	private static final String	NAMESPACE	= RepairDeviceCategoryPerson.class.getName();

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}


	// 设备资产运行统计查询
	/*@Override
	public Pager<RepairDeviceCategoryPerson> findRunReportByCondition(Integer pageNo, Integer pageSize, RepairDeviceCategoryPerson condition) {
		return selectPagination("findRunReportByCondition", "countRunReportByCondition", condition, pageNo, pageSize);
	}
*/
	@Override
	public int deleteByIds(Long[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		return update("deleteByIds", map);
	}

	@Override
	public RepairDeviceCategoryPerson findById(Long id) {
		return selectOne("getById", id);
	}
	
	public List<RepairDeviceCategoryPerson> findByHpid(String hpid) {
		return selectList("getByHpid", hpid);
	}
	// 按设备模型编码或设备名称
	@Override
	public Pageable<RepairDeviceCategoryPerson> findByCodeName_g(Pageable<RepairDeviceCategoryPerson> pager, RepairDeviceCategoryPerson condition) {
		return findByPager(pager, "findByCodeName_g", "countByFindByCodeName_g", condition);
	}

	@Override
	public List<RepairDeviceCategoryPerson> findByCodeName_g(RepairDeviceCategoryPerson condition) {
		return selectList("findByCodeName_g", condition);
	}


	// 按设备类别ID查询数量-删除设备类别验证用
	public Long findCountByCategoryId(long id) {
		return getSqlSession().selectOne(getNamespace() + ".findCountByCategoryId", id);
	}
	
    @Override
    public List<RepairDeviceCategoryPerson> findByCategoryIdAndAreaId(Long categoryType,Long categoryId, Long areaId) {
        Map map = new HashMap();
        map.put("categoryType",categoryType);
        map.put("categoryId",categoryId);
        map.put("areaId",areaId);
        return selectList("findByCategoryIdAndAreaID",map);   
    }

	@Override
	public List findMjZnzm(String geoareaCode ,List<Long> lstHaveOpenStatus ,List<Long> lstHaveAlarmStatus, boolean bol){
		Map<String, Object> map = new HashMap<String, Object>();
		List lst = new ArrayList();
		map.put("geoareaCode", geoareaCode);
		map.put("lstHaveAlarmStatus", lstHaveAlarmStatus);		
		lst.add(selectList("findAlarmDevice_g", map));
		if (bol) {
			map.put("lstHaveOpenStatus", lstHaveOpenStatus);
			lst.add(selectList("findMjZnzm_g", map));
		}
		return lst;
	}


	@Override
	public List<RepairDeviceCategoryPerson> findStatusByCodes(RepairDeviceCategoryPerson repairDeviceCategoryPerson) {
		return selectList("findStatusByCodes", repairDeviceCategoryPerson);
	}

	@Override
	public List<RepairDeviceCategoryPerson> findGroupByCategoryId(List<Long> ids) {
		Map map = new HashMap();
		map.put("ids", ids);
		return selectList("findGroupByCategory", map);
	}

	@Override
	public List<RepairDeviceCategoryPerson> findGroupByLocationId(List<Long> ids) {
		Map map = new HashMap();
		map.put("ids", ids);
		return selectList("findGroupByLocation", map);
	}
    @Override
    public List<RepairDeviceCategoryPerson> findByIds(List<Long> ids){
        Map map = new HashMap();
        map.put("ids",ids);
        return selectList("findByIds",map);
    }

    @Override
    public List<RepairDeviceCategoryPerson> findByParam(RepairDeviceCategoryPerson repairDeviceCategoryPerson){
        return selectList("findByParam",repairDeviceCategoryPerson);
    }
    @Override
    public Long getByCategoryTotal(String categoryCode){
        return getSqlSession().selectOne("getByCategoryTotal",categoryCode);
    }
    
	// 按设备类型和地理区域--gis
	@Override
	public Pageable<RepairDeviceCategoryPerson> findByCategoryArea(Pageable<RepairDeviceCategoryPerson> pager, RepairDeviceCategoryPerson condition) {
		return findByPager(pager, "findByCategoryArea", "countByFindByCategoryArea", condition);
	}

	//========add by dingyg=============
	@Override
	public Pageable<RepairDeviceCategoryPerson> findByCondition(Pageable<RepairDeviceCategoryPerson> pager,
			RepairDeviceCategoryPerson condition) {
		return findByPager(pager,"findByCondition", "countByCondition", condition);
	}

	protected  Pageable<RepairDeviceCategoryPerson> findByPager(Pageable<RepairDeviceCategoryPerson> pager,
			String selectStatement, String countStatement, RepairDeviceCategoryPerson condition,
			Map<String, Object> otherParams) {
		return findByPager(pager, selectStatement, countStatement, condition);
	}

	@Override
	public void updateLocationName() {
		update("updateLocationName");
	}
	@Override
	public List<RepairDeviceCategoryPerson> findCate(List<Long> lstSystemRuleId) {
		List<RepairDeviceCategoryPerson> lst=new ArrayList<RepairDeviceCategoryPerson>();
		if(!UtilTool.isEmptyList(lstSystemRuleId)){
			Map map = new HashMap();
			map.put("lstSystemRuleId", lstSystemRuleId);
			lst=selectList("findCate", map);
		}
		return lst;
	}
	
	@Override
	public int 	deleteBypersonId(RepairDeviceCategoryPerson repairDeviceCategoryPerson) {
		return delete("deleteBypersonId", repairDeviceCategoryPerson);
	}
	
	@Override
	public void insert(List<Long> list,RepairDeviceCategoryPerson person) {
		Map map = new HashMap();
		map.put("list", list);
		map.put("person", person);
		insert("insertlist",map);
	}
	
	
	@Override
	public List<RepairDeviceCategoryPerson> findByPersonIdandCategoryId(RepairDeviceCategoryPerson repairDeviceCategoryPerson) {
		return selectList("findByPersonIdandCategoryId",repairDeviceCategoryPerson);}

	@Override
	public int deleteForDistinct(){
		return delete("deleteForDistinct");
    }
	
}
