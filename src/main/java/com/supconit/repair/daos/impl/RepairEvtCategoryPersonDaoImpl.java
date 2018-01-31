package com.supconit.repair.daos.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.supconit.common.daos.AbstractBaseDao;
import com.supconit.common.utils.UtilTool;
import com.supconit.repair.daos.RepairEvtCategoryPersonDao;
import com.supconit.repair.entities.RepairEvtCategoryPerson;

import hc.base.domains.Pageable;

@Repository
public class RepairEvtCategoryPersonDaoImpl extends AbstractBaseDao<RepairEvtCategoryPerson, Long> implements RepairEvtCategoryPersonDao {

	
	private static final String	NAMESPACE	= RepairEvtCategoryPerson.class.getName();

	@Override
	protected String getNamespace() {
		return NAMESPACE;
	}


	// 设备资产运行统计查询
	/*@Override
	public Pager<RepairEvtCategoryPerson> findRunReportByCondition(Integer pageNo, Integer pageSize, RepairEvtCategoryPerson condition) {
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
	public RepairEvtCategoryPerson findById(Long id) {
		return selectOne("getById", id);
	}
	
	public List<RepairEvtCategoryPerson> findByHpid(String hpid) {
		return selectList("getByHpid", hpid);
	}
	// 按设备模型编码或设备名称
	@Override
	public Pageable<RepairEvtCategoryPerson> findByCodeName_g(Pageable<RepairEvtCategoryPerson> pager, RepairEvtCategoryPerson condition) {
		return findByPager(pager, "findByCodeName_g", "countByFindByCodeName_g", condition);
	}

	@Override
	public List<RepairEvtCategoryPerson> findByCodeName_g(RepairEvtCategoryPerson condition) {
		return selectList("findByCodeName_g", condition);
	}


	// 按设备类别ID查询数量-删除设备类别验证用
	public Long findCountByCategoryId(long id) {
		return getSqlSession().selectOne(getNamespace() + ".findCountByCategoryId", id);
	}
	
    @Override
    public List<RepairEvtCategoryPerson> findByCategoryIdAndAreaId(Long categoryType,Long categoryId, Long areaId) {
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
	public List<RepairEvtCategoryPerson> findStatusByCodes(RepairEvtCategoryPerson repairEvtCategoryPerson) {
		return selectList("findStatusByCodes", repairEvtCategoryPerson);
	}

	@Override
	public List<RepairEvtCategoryPerson> findGroupByCategoryId(List<Long> ids) {
		Map map = new HashMap();
		map.put("ids", ids);
		return selectList("findGroupByCategory", map);
	}

	@Override
	public List<RepairEvtCategoryPerson> findGroupByLocationId(List<Long> ids) {
		Map map = new HashMap();
		map.put("ids", ids);
		return selectList("findGroupByLocation", map);
	}
    @Override
    public List<RepairEvtCategoryPerson> findByIds(List<Long> ids){
        Map map = new HashMap();
        map.put("ids",ids);
        return selectList("findByIds",map);
    }

    @Override
    public List<RepairEvtCategoryPerson> findByParam(RepairEvtCategoryPerson repairEvtCategoryPerson){
        return selectList("findByParam",repairEvtCategoryPerson);
    }
    @Override
    public Long getByCategoryTotal(String categoryCode){
        return getSqlSession().selectOne("getByCategoryTotal",categoryCode);
    }
    
	// 按设备类型和地理区域--gis
	@Override
	public Pageable<RepairEvtCategoryPerson> findByCategoryArea(Pageable<RepairEvtCategoryPerson> pager, RepairEvtCategoryPerson condition) {
		return findByPager(pager, "findByCategoryArea", "countByFindByCategoryArea", condition);
	}

	//========add by dingyg=============
	@Override
	public Pageable<RepairEvtCategoryPerson> findByCondition(Pageable<RepairEvtCategoryPerson> pager,
			RepairEvtCategoryPerson condition) {
		return findByPager(pager,"findByCondition", "countByCondition", condition);
	}

	protected  Pageable<RepairEvtCategoryPerson> findByPager(Pageable<RepairEvtCategoryPerson> pager,
			String selectStatement, String countStatement, RepairEvtCategoryPerson condition,
			Map<String, Object> otherParams) {
		return findByPager(pager, selectStatement, countStatement, condition);
	}

	@Override
	public void updateLocationName() {
		update("updateLocationName");
	}
	@Override
	public List<RepairEvtCategoryPerson> findCate(List<Long> lstSystemRuleId) {
		List<RepairEvtCategoryPerson> lst=new ArrayList<RepairEvtCategoryPerson>();
		if(!UtilTool.isEmptyList(lstSystemRuleId)){
			Map map = new HashMap();
			map.put("lstSystemRuleId", lstSystemRuleId);
			lst=selectList("findCate", map);
		}
		return lst;
	}
	
	@Override
	public int 	deleteBypersonId(RepairEvtCategoryPerson repairEvtCategoryPerson) {
		return delete("deleteBypersonId", repairEvtCategoryPerson);
	}
	
	@Override
	public void insert(List<Long> list,RepairEvtCategoryPerson person) {
		Map map = new HashMap();
		map.put("list", list);
		map.put("person", person);
		insert("insertlist",map);
	}
	
	
	@Override
	public List<RepairEvtCategoryPerson> findByPersonIdandCategoryId(RepairEvtCategoryPerson repairEvtCategoryPerson) {
		return selectList("findByPersonIdandCategoryId",repairEvtCategoryPerson);}

	@Override
	public int deleteForDistinct(){
		return delete("deleteForDistinct");
    }


	@Override
	public Long countByCategoryId(Long categoryId) {
		Map map = new HashMap();
		map.put("categoryId", categoryId);
		return selectOne("countByCategoryId", map);
	}
	
}
