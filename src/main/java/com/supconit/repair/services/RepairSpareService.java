package com.supconit.repair.services;

import com.supconit.common.services.BaseBusinessService;
import com.supconit.repair.entities.RepairSpare;

import java.util.List;

/**
 * @文件名: RepairSpareService
 * @创建日期: 13-8-1
 * @版权: Copyright(c)2013
 * @开发人员:
 * @版本:
 * @描述:
 */
public interface RepairSpareService extends BaseBusinessService<RepairSpare, Long> {

    List<RepairSpare> findByOrderCode(String orderCode);

    void batchAdd(List<RepairSpare> spareList,String orderCode);

    void deleteByOrderCode(String orderCode);
}
