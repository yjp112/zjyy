package com.supconit.common.services;

import com.supconit.common.domains.TreeNode;

/**
 * 约定: 根节点的level为0 根节点没有父亲为null 根节点的lft值为1
 */
@SuppressWarnings("rawtypes")
public interface BaseTreeService<T extends TreeNode> {
	/**
	 * 插入节点,要负责维护插入后的nested关系
	 */
	public T insertNode(T node,Long parentId,IInsertCommand insertCmd);

	/**
	 * 删除节点,并负责维护节点删除操作后的nest关系
	 */
	public void delete(Long nodeId);

	/**
	 * 移动节点到指定节点下,并负责维护节点移动操作后的nest关系
	 */
	public void changeParent(Long nodeId, Long targetParentId);

	/**
	 * 在控制台 文本输出以此节点为根的树结构,用于调试
	 */
	public void printTree(Long id);


	/**
	 * 重建以id为根的数的nested set树型关系 lefterNumber为根节点的左侧加权值
	 */
	public long reBuildTree(Long id, Long leftNmuber);


}
