package com.supconit.common.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.supconit.common.daos.SimpleJdbc;
import com.supconit.common.domains.TreeNode;
import com.supconit.common.exceptions.BusinessDoneException;
import com.supconit.honeycomb.base.context.SpringContextHolder;

@SuppressWarnings("rawtypes")
public abstract class  AbstractBaseTreeServiceImpl<T extends TreeNode>implements BaseTreeService<T> {
	private transient static final Logger log = LoggerFactory
			.getLogger(AbstractBaseTreeServiceImpl.class);
	private SimpleJdbc	jdbcProcessor=SpringContextHolder.getBean(SimpleJdbc.class);
	private JdbcTemplate	jdbcProcessor2=new JdbcTemplate((DataSource) SpringContextHolder.getBean("defaultDS"));
	private  final String TABLE_NAME=getTableName();
	private  final String SELECT_PRE="SELECT "+getNameColumn()+" name,"+getIdColumn()+","+getPIDColumn()+",lft,rgt,lvl,sort_idx FROM "+getTableName();

	public abstract String getTableName();
	public abstract String getIdColumn();
	public abstract String getPIDColumn();
	public abstract String getNameColumn();
	@Override
	public T insertNode(T node,Long parentId,IInsertCommand insertCmd) {
		TreeNode parent=null;
		if (parentId != null) {
			parent=find(parentId);
			if(parent==null){
				throw new BusinessDoneException("父节点不存在");
			}
			insertCmd.insert(node);
			node.setLft(parent.getRgt());
			node.setRgt(parent.getRgt()+1);
			String lftUpdate ="update "+TABLE_NAME+" set lft=lft+2 where lft>"+parent.getRgt();
			String rgtUpdate="update "+TABLE_NAME+" set rgt=rgt+2 where rgt>="+parent.getRgt();
			String lftRgtUpdate="update "+TABLE_NAME+" set lft="+node.getLft()+",rgt="+node.getRgt()+" where id="+node.getId();
			jdbcProcessor.batchUpdate(new String[]{lftUpdate,rgtUpdate,lftRgtUpdate});
		}else{
			insertCmd.insert(node);
			node.setLft(1L);
			node.setRgt(2L);
			jdbcProcessor.update("update "+TABLE_NAME+" set lft=1,rgt=2 where id=?", node.getId());
		}
		return node;
	}

	@Override
	public void delete(Long nodeId) {
		TreeNode node=find(nodeId);
		if(node==null){
			throw new BusinessDoneException("要删除的节点不存在");
		}
		String delete="DELETE FROM "+TABLE_NAME+" WHERE lft BETWEEN "+node.getLft()+" AND "+node.getRgt();
		String updateLft ="update "+TABLE_NAME+" set lft=lft-"+node.getWidth()+" where lft>"+node.getRgt();
		String updateRgt="update "+TABLE_NAME+" set rgt=rgt-"+node.getWidth()+" where rgt>"+node.getRgt();
		jdbcProcessor.batchUpdate(new String[]{delete,updateLft,updateRgt});
	}


	@Override
	public void changeParent(Long nodeId, Long targetParentId) {
		TreeNode node =find(nodeId);
		TreeNode parentNode =find(targetParentId);
		if(node==null||parentNode==null){
			throw new BusinessDoneException("源节点或者目标节点不存在");
		}

		if(nodeId==targetParentId){
			throw new BusinessDoneException("源节点和目标节点相同，无须移动。");
		}
		Long count=jdbcProcessor.get(Long.class, "select * from "+getTableName()+" where lft>=? and rgt<=?  and id=?",node.getLft(),node.getRgt(),parentNode.getId());
		if(count!=null&&count>0){
			throw new BusinessDoneException("源节点是目标节点的父节点，不能完成移动操作。");	
		}
		long span=parentNode.getRgt()-node.getLft();
		//把lvl标记为-1，表示该节点已被删除
		jdbcProcessor.update("UPDATE "+TABLE_NAME+" SET lvl=-1 WHERE lft between ? and ?"
				,node.getLft(),node.getRgt());
		//调整目标及目标的父亲节点的右值
		int count1=jdbcProcessor.update("UPDATE "+TABLE_NAME+" SET lft = lft + ? WHERE lft >? and (lvl!=-1 or lvl is null)"
				,node.getWidth(),parentNode.getRgt()); 
		int count2=jdbcProcessor.update("UPDATE "+TABLE_NAME+" SET rgt = rgt + ? WHERE rgt >=? and (lvl!=-1 or lvl is null)"
				,node.getWidth(),parentNode.getRgt()); 
		//把源节点移动到目标节点下
		int count3=jdbcProcessor.update("UPDATE "+TABLE_NAME+" SET lft = lft+?,rgt=rgt+?,lvl=null WHERE lft between ? and ? and lvl=-1"
				,span,span,node.getLft(),node.getRgt());
		log.info(count1+"");
		log.info(count2+"");
		log.info(count3+"");
		//把源节点后面的节点往前移
		jdbcProcessor.update("UPDATE "+TABLE_NAME+" SET rgt=rgt-? WHERE rgt>?"
				,node.getWidth(),node.getRgt());  
		jdbcProcessor.update("UPDATE "+TABLE_NAME+" SET lft=lft-? WHERE lft>?"
				,node.getWidth(),node.getRgt());  

	}
	public void move2(Long nodeId, Long targetId) {
		/*修改节点(在修改lft和rgt之前，当前节点的父节点id已经改变)
		  a. 查出当前节点的左右节点（nodelft、nodergt），并nodergt-nodelft+1 = span，获取父节点的左节点parentlft
		  b. 将所有大于parentlft的lft(左节点)、rgt(右节点)的值+span
		  c. 查找当前节点的左右节点（nodelft、nodergt），并parentlft-nodelft+1 = offset
		  d. 将所有lft(左节点) between nodelft and nodergt的值+offset
		  e. 将所有大于nodergt的lft(左节点)、rgt(右节点)的值-span*/
		/*SELECT @myLeft := lft, @myRight := rgt, @myWidth := rgt - lft + 1 FROM category WHERE  category_id = 'category_id';  
	    SELECT @parentLeft := lft, @parentRight := rgt FROM category WHERE  category_id = 'parentId';  
	    UPDATE category SET rgt = rgt + @myWidth WHERE rgt > @parentLeft;  
	    UPDATE category SET lft = lft + @myWidth WHERE lft > @parentLeft;  
	    SELECT @myLeft := lft, @myRight := rgt, @myWidth := rgt - lft + 1 FROM category WHERE  category_id = 'category_id';  
	    UPDATE category set lft=lft+@parentLeft-@myLeft+1, rgt=rgt+@parentLeft-@myLeft+1 WHERE lft between @myLeft and @myRight;  
	    UPDATE category set  rgt=rgt-@myWidth WHERE rgt>@myRight;  
	    UPDATE category set  lft=lft-@myWidth WHERE lft>@myRight;  */
		//如果parentId为空及意味着将子节点升为根节点，在有些情况下可能表中没有根节点，一般情况下上面操作已经比较完善，这时可以这样操作
		/*SELECT @myLeft := lft, @myRight := rgt, @myWidth := rgt - lft + 1 FROM category WHERE  category_id = '7';  
	    select @maxRgt:=max(rgt)-@myLeft+1 from category;  
	    UPDATE category set  lft=lft+@maxRgt, rgt=rgt+@maxRgt WHERE lft between @myLeft and @myRight;  
	    UPDATE category set  rgt=rgt-@myWidth WHERE rgt>@myRight;  
	    UPDATE category set  lft=lft-@myWidth WHERE lft>@myRight;  */
		TreeNode node =find(nodeId);
		TreeNode parentNode =find(targetId);
		if(node==null||parentNode==null){
			throw new RuntimeException("源节点或者目标节点不存在");
		}
		jdbcProcessor.update("UPDATE "+TABLE_NAME+" SET rgt = rgt + ? WHERE rgt > ?",node.getWidth(),parentNode.getLft());  
		jdbcProcessor.update("UPDATE "+TABLE_NAME+" SET lft = lft + ? WHERE lft > ?",node.getWidth(),parentNode.getLft());  
		node =find(nodeId);
		//查找当前节点的左右节点（nodelft、nodergt），并parentlft-nodelft+1 = offset
		long offset=parentNode.getLft()-node.getLft()+1;
		//将所有lft(左节点) between nodelft and nodergt的值+offset
		jdbcProcessor.update("UPDATE "+TABLE_NAME+" SET lft = lft+?,rgt=rgt+? WHERE lft between ? and ?"
				,offset,offset,node.getLft(),node.getRgt());  
		//将所有大于nodergt的lft(左节点)、rgt(右节点)的值-span
		jdbcProcessor.update("UPDATE "+TABLE_NAME+" SET rgt=rgt-? WHERE rgt>?"
				,node.getWidth(),node.getRgt());  
		jdbcProcessor.update("UPDATE "+TABLE_NAME+" SET lft=lft-? WHERE lft>?"
				,node.getWidth(),node.getRgt());  
	}
	
	@Override
	public void printTree(Long id) {
		TreeNode root = find(id);
		List children = getTreeByNestNubmer(root.getLft(),root.getRgt());
		Stack<Long> stack = new Stack<Long>();
		int nameLength=40;
		int length=8;
		StringBuffer buf=new StringBuffer(StringUtils.rightPad("节点名称(ID)",nameLength," ")
				+StringUtils.rightPad("父",length," ")
				+StringUtils.rightPad("左",length," ")
				+StringUtils.rightPad("右",length," ")
				+StringUtils.rightPad("层",length," ")
				+StringUtils.rightPad("排序号",length," ")+"\r\n");
		for (Iterator iterator = children.iterator(); iterator.hasNext();) {
			TreeNode node = (TreeNode) iterator.next();
			if (!stack.isEmpty()) {
				while (stack.lastElement() < node.getRgt()) {
					stack.pop();
				}
			}

			buf.append(StringUtils.rightPad(StringUtils.repeat("    ", stack.size()) + (node.getName()+"("+node.getId()+")"),nameLength," ") 
					+ StringUtils.rightPad(node.getPid()+"",length," ") + "\t"
					+ StringUtils.rightPad(node.getLft()+"",length," ") + "\t"
					+ StringUtils.rightPad(node.getRgt()+"",length," ") + "\t" 
					+ StringUtils.rightPad(getLvl((Long)node.getId())+"",length," ")+"\t"
					+ StringUtils.rightPad(node.getSortIdx()+"",length," ")+"\r\n");

			stack.push(node.getRgt());
		}
		log.error("\r\n"+buf.toString());
	}


	@Override
	public long reBuildTree(Long id, Long leftNmuber) {
		Long rightNumber = leftNmuber + 1;
		List<? extends Serializable> children = jdbcProcessor.find(id.getClass(), "SELECT ID FROM "+TABLE_NAME+" WHERE "+getPIDColumn()+" = ? ORDER BY SORT_IDX ASC", id);
		if(children==null){
			return 0;
		}
		Iterator itr = children.iterator();
		while (itr.hasNext()) {
			rightNumber = reBuildTree((Long) itr.next(), rightNumber);
		}
		/*TreeNode curNode = this.find(id);
		curNode.setLft(leftNmuber);
		curNode.setRgt(rightNumber);*/
		jdbcProcessor.update("UPDATE "+TABLE_NAME+" SET lft=?,rgt=? where ID=?", leftNmuber,rightNumber,id);
		return rightNumber + 1;
	}

	@SuppressWarnings({ "unchecked" })
	private TreeNode find(Long id) {
		List<Map> list=jdbcProcessor.find(Map.class, SELECT_PRE+" WHERE  id=?", new Object[]{id});
		if(list.size()>0){
			Map<String, Object> map=list.get(0);
			TreeNode node=new TreeNode();
			node.setName((String)map.get("NAME"));
			node.setId(id);
			node.setPid(map.get(getPIDColumn())==null? 0:((BigDecimal)map.get(getPIDColumn())).longValue());
			node.setLft(map.get("LFT")==null?0:((BigDecimal)map.get("LFT")).longValue());
			node.setRgt(map.get("RGT")==null?0:((BigDecimal)map.get("RGT")).longValue());
			node.setLvl(map.get("LVL")==null?0:((BigDecimal)map.get("LVL")).intValue());
			node.setSortIdx(map.get("SORT_IDX")==null?0:((BigDecimal)map.get("SORT_IDX")).intValue());
			TreeNode pNode=new TreeNode();
			pNode.setId(map.get(getPIDColumn())==null? 0:((BigDecimal)map.get(getPIDColumn())).longValue());
			node.setParent(pNode);
			return node;
		}
		return null;
	}
	@SuppressWarnings({ "unchecked" })
	private List<TreeNode> getTreeByNestNubmer(Long lft, Long rgt) {
		List<Map> list=jdbcProcessor.find(Map.class, SELECT_PRE+" WHERE  lft>=? AND lft<=? ORDER BY lft ASC", new Object[]{lft,rgt});
		List<TreeNode> result=new ArrayList<TreeNode>();
		for (Map map : list) {
			TreeNode node=new TreeNode();
			node.setName((String)map.get("NAME"));
			node.setId((Long)map.get(getIdColumn()));
			node.setPid((Long)map.get(getPIDColumn()));
			node.setLft(map.get("LFT")==null?0:((BigDecimal)map.get("LFT")).longValue());
			node.setRgt(map.get("RGT")==null?0:((BigDecimal)map.get("RGT")).longValue());
			node.setLvl(map.get("LVL")==null?0:((BigDecimal)map.get("LVL")).intValue());
			node.setSortIdx(map.get("SORT_IDX")==null?0:((BigDecimal)map.get("SORT_IDX")).intValue());
			TreeNode pNode=new TreeNode();
			pNode.setId((Long)map.get(getPIDColumn()));
			node.setParent(pNode);
			result.add(node);
		}
		return result;
	}	
	
	private Integer getLvl(Long id){
		List<Integer> lvls=jdbcProcessor.find(Integer.class, "select count(1) lvl from "+TABLE_NAME+" c,"+TABLE_NAME+" p where c.id=? and p.lft<=c.lft and p.rgt>=c.rgt", id);
		if(lvls==null||lvls.size()==0){
			return null;
		}
		return lvls.get(0);
	}
}