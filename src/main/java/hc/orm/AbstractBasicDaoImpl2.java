package hc.orm;

import hc.base.domains.LogicDelete;
import hc.base.domains.PK;
import hc.base.domains.Pageable;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public abstract class AbstractBasicDaoImpl2<T extends PK<ID>, ID extends Serializable>
		extends AbstractDaoSupport implements BasicDao<T, ID> {
	protected static final String STATEMENT_GET_BY_ID = "getById";
	protected static final String STATEMENT_DELETE_BY_ID = "deleteById";
	protected static final String STATEMENT_DISABLED = "disabledById";
	protected static final String STATEMENT_INSERT = "insert";
	protected static final String STATEMENT_UPDATE = "update";

	public T getById(ID id) {
		return (T) selectOne("getById", id);
	}

	public int delete(T entity) {
		if ((entity instanceof LogicDelete)) {
			return update("disabledById", entity.getId());
		}
		return delete("deleteById", entity.getId());
	}

	public int insert(T entity) {
		return insert("insert", entity);
	}

	public int update(T entity) {
		return update("update", entity);
	}

	protected <X> Pageable<X> findByPager(Pageable<X> pager,
			String selectStatement, String countStatement, X condition) {
		return findByPager(pager, selectStatement, countStatement, condition,
				null);
	}

	protected <X> Pageable<X> findByPager(Pageable<X> pager,
			String selectStatement, String countStatement, X condition,
			Map<String, Object> otherParams) {
		Map<String, Object> params = new HashMap();

		params.put("offset", Integer.valueOf(pager.getOffset()));
		params.put("pageSize", Integer.valueOf(pager.getPageSize()));
		params.put("offsetEnd", Integer.valueOf(pager.getOffsetEnd()));
		if(null != condition){
			transSpecialCharacter(condition);
		}
		params.put("condition", condition);
		if (!CollectionUtils.isEmpty(otherParams)) {
			params.putAll(otherParams);
		}
		List<X> result = selectList(selectStatement, params);
		pager.addAll(result);
		long total = calculateTotal(pager, result);
		if (total < 0L) {
			total = ((Long) selectOne(countStatement, params)).longValue();
		}
		pager.setTotal(total);
		return pager;
	}

	protected <X> long calculateTotal(Pageable<X> pager, List<X> result) {
		if (pager.hasPrevPage()) {
			if (CollectionUtils.isEmpty(result))
				return -1L;
			if (result.size() == pager.getPageSize())
				return -1L;
			return (pager.getPageNo() - 1) * pager.getPageSize()
					+ result.size();
		}
		if (result.size() < pager.getPageSize())
			return result.size();
		return -1L;
	}
	
	protected <X> X transSpecialCharacter(X obj) {
		String dataBaseId = sqlSession.getConfiguration().getDatabaseId();
		Field[] field = obj.getClass().getDeclaredFields();
		for (int j = 0; j < field.length; j++) {
			String type = field[j].getGenericType().toString(); 
			if (type.equals("class java.lang.String")) {
				String name = field[j].getName(); 
				name = name.substring(0, 1).toUpperCase() + name.substring(1); 
				try {
					Method m = obj.getClass().getMethod("get" + name);
					String value = (String) m.invoke(obj);
					if (value != null) {
						field[j].setAccessible(true);
						if(value.indexOf("/")>-1) value = value.replaceAll("/", "//");
						if(value.indexOf("%")>-1) value = value.replaceAll("%", "/%");
						if(value.indexOf("_")>-1) value = value.replaceAll("_", "/_");
						if(dataBaseId.equals("sqlserver")){
							if(value.indexOf("[")>-1) value = value.replaceAll("\\[", "/[");
							if(value.indexOf("]")>-1) value = value.replaceAll("\\]", "/]");
						}
						field[j].set(obj, value);
					}
				} catch (NoSuchMethodException e) {
					continue;
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			
		}
		return obj;
	}
	
}

