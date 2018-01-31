package com.supconit.common.services;

import com.supconit.common.domains.TreeNode;

@SuppressWarnings("rawtypes")
public interface IInsertCommand<T extends TreeNode>{

	public T insert(T obj);
}
