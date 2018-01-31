
package com.supconit.spare.daos;

import com.supconit.base.entities.Device;
import com.supconit.common.daos.BaseDao;
import com.supconit.spare.entities.Spare;

import hc.base.domains.Pageable;

import java.util.List;


public interface SpareDao extends BaseDao<Spare, Long>{
	
	public Pageable<Spare> findByPage(Pageable<Spare> pager,Spare condition);

	int deleteByIds(Long[] ids);
    
    public  Spare findById(Long id); 
    
    /** 
     *@ćšćłĺç§°:countSpareIdUsed
     *@ä˝?   č?ä¸éłĺ?     *@ĺĺťşćĽć:2013ĺš?ć?9ć?     *@ćšćłćčż°:  čŽĄçŽ
     * @param spareId spareIdč˘ŤĺśäťčĄ¨ä˝żç¨çćŹĄć°ďźä¸?ŹĺŻäťĽç¨ćĽä˝ä¸şćŻĺŚĺŻäťĽĺ é¤čŻĽĺ¤äťśçäžćŽ
     * @return Long
     */
    public Long countSpareIdUsed(Long spareId);
    
    public Long countRecordBySpareCode(String spareCode);
    public Integer updateSelective(Spare spare);
    Long countByConditions(Device device);
    Pageable<Spare> findStockByCondition(Pageable<Spare> pager,Spare condition);

    /** 手机端使用 **/
    List<Spare> selectSpares(Spare spare);
    long countSpares(Spare spare);
}
