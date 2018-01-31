package com.supconit.common.utils.socketio.server.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.supconit.common.utils.ExpressionParserUtils;
import com.supconit.common.utils.ListUtils;
import com.supconit.common.utils.socketio.server.IRealtimedataReader;
import com.supconit.common.utils.socketio.server.IRequestResponseData;
import com.supconit.hl.gis.entities.MReadBlock;
import com.supconit.hl.gis.services.IBlockService;
import com.supconit.honeycomb.base.context.SpringContextHolder;

public class BitReader implements IRealtimedataReader {
	private static Logger log = LoggerFactory.getLogger(BitReader.class);
	private IBlockService blockService;

	private static class InnerInstanceHolder {
		private static final BitReader INSTANCE = new BitReader();
	}

	private BitReader() {
		super();
	}

	public static BitReader getInstance() {
		return InnerInstanceHolder.INSTANCE;
	}

	@Override
	public IRequestResponseData dataRead(IRequestResponseData data) {
		if (data == null || !(data instanceof BlockRequestResponse)) {
			return data;
		}
		BlockRequestResponse request = (BlockRequestResponse) data;
		if (request.getBlocks() == null || request.getBlocks().length <= 0) {
			return data;
		}
		readSyn(request.getBlocks());
		return request;
	}

	/**
	 * 同步执行
	 * 
	 * @param hpidReadblocks
	 *            点位列表
	 * @return 读取到的点位数据
	 */
	public List<HpidReadBlock> readSyn(HpidReadBlock... hpidReadblocks) {
		readBits(hpidReadblocks);
		return Arrays.asList(hpidReadblocks);
	}

	// 读多个点位的值
	private void readBits(HpidReadBlock... hpidReadblocks) {
		if (hpidReadblocks == null || hpidReadblocks.length <= 0) {
			return;
		}
		List<String> bits = new ArrayList<String>();
		List<HpidReadBlock> blocksEL=new ArrayList<HpidReadBlock>();//该部分点位数据是通过其他点位计算得到
		for (HpidReadBlock hpidReadBlock : hpidReadblocks) {
			if(StringUtils.isBlank(hpidReadBlock.getSpringEL())){
				bits.add(hpidReadBlock.getBlock());
			}else{
				blocksEL.add(hpidReadBlock);
			}

//			//测试数据
//			/*hpidReadBlock.setValue(RandomUtils.getIntRandom(100, 200) + "");
//			hpidReadBlock.setUpdateTime(new Date());*/
		}
		// 读点位值
		long start = System.currentTimeMillis();

		if (blockService == null) {
			blockService = SpringContextHolder.getBean(IBlockService.class);
		}
		log.debug("------读取点位值start-----");
		int maxSize = 200; // 读点位值
		List<MReadBlock> lstReadBlocks = new ArrayList<MReadBlock>();
		for (List<String> eachList : ListUtils.partition(bits, maxSize)) {
			List<MReadBlock> result = blockService.readBlocks(eachList);
			if (result == null || result.size() <= 0) {
				log.error("读取点位值返回空，点位个数[" + eachList.size() + "],点位："
						+ eachList.toString());
				continue;
			}
			lstReadBlocks.addAll(result);
		}
		if (lstReadBlocks == null || lstReadBlocks.size() <= 0) {
			log.error("读取点位值返回空，点位个数[" + bits.size() + "],点位："
					+ bits.toString());
			return;
		}
		Map<String,String> variableMap=new HashMap<String, String>();
		Date updateTime=new Date();
		for (MReadBlock r : lstReadBlocks) {
			for (HpidReadBlock hpidReadBlock : hpidReadblocks) {
				if (hpidReadBlock.getBlock().equals(r.getBlock())) {
					hpidReadBlock.setValue(r.getValue());	
					if(!hpidReadBlock.isPointValidate()){
						log.warn("点位["+hpidReadBlock.getBlock()+"]获取的值["+r.getValue()+"]非法！！！");
						hpidReadBlock.setValue("");	
					}
					hpidReadBlock.setUpdateTime(updateTime);
					variableMap.put(hpidReadBlock.getBlock(), hpidReadBlock.getValue());
					break;
				}
			}
		}

		for (HpidReadBlock readBlock : blocksEL) {
			try {
				ExpressionParserUtils.getSprintElExpressionValue(readBlock.getSpringEL(), variableMap);
			} catch (Exception e) {
				log.error("点位["+readBlock.getBlock()+"]计算出错,SpringEl表达式为["+readBlock.getSpringEL()+"]");
			}
		}
		log.debug("----读取点位值end,耗时:" + (System.currentTimeMillis() - start)
				+ "毫秒.----");
	}
}
