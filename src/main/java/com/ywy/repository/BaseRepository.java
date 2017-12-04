package com.ywy.repository;

import com.ywy.annotation.ID;
import com.ywy.annotation.Ignore;
import com.ywy.annotation.Table;
import com.ywy.enumtype.ConditionType;
import com.ywy.parameter.Condition;
import com.ywy.rowmaper.CustomerMapper;
import com.ywy.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

@Component
public class BaseRepository<T> extends PageRespository {
	
	@Resource
	protected JdbcTemplate jdbcTemplate;

	private final static Logger logger = LoggerFactory.getLogger(BaseRepository.class);
	
	private String getTableName(String tableName, int mod, long id) {
		if (id % mod == 0) {
			return tableName + "_" + mod;
		} else {
			return tableName + "_" + id % mod;
		}
	}
	
	protected Map<String, Object> getUpdateParam(String[] fields, T t) {
		Map<String, Object> rst = new HashMap<String, Object>();
		Class<?> cls = t.getClass();
		Method method = null;
		try {
			for (String field : fields) {
				method = cls.getMethod("get" + field.substring(0, 1).toUpperCase() + field.substring(1));
				rst.put(field, method.invoke(t));
			}
		} catch (Exception e) {
			logger.error("fail to geneate update params.cls = " + cls.getName(), e);
			return null;
		}
		return rst;
	}
	
	protected Map<String, Object> getUpdateParam(String[] fields, Object[] values) {
		Map<String, Object> rst = new HashMap<String, Object>();
		if (fields.length != values.length) {
			return null;
		}
		for (int i = 0; i < fields.length; i++ ) {
			rst.put(fields[i], values[i]);
		}
		return rst;
	}
	
	protected List<T> queryObjByCondition(Condition condition, Class<T> cls) {
		Table table = cls.getAnnotation(Table.class);
		if (table != null) {
			StringBuilder sql = new StringBuilder();
			List<Object> values = new ArrayList<>();
			sql.append("select * from " + getTableNameFromCondition(condition, cls)).append(" ");
			if (condition != null) {
				sql.append(buildWhereCondition(condition, values));
			}
			return this.jdbcTemplate.query(sql.toString(),values.toArray(), new CustomerMapper<T>(cls));
		} else {
			logger.error("no table or params. table=" + cls.getName() + ",params = " + condition);
			return null;
		}
	}
	
	protected List<T> queryObjBySqlStr(String sql,List<Object> params,Class<T> cls){
		return this.jdbcTemplate.query(sql.toString(),params.toArray(), new CustomerMapper<T>(cls));
	}
	
	protected String getClassTable(Class<T> cls,long id){
		Table table = cls.getAnnotation(Table.class);
		if(table!=null){
			return this.getTableName(table.value(),Integer.parseInt(table.modValue()) , id);
		} else {
			logger.error("no table");
			return null;
		}
	}
	
	protected int deleteByCondition(Condition condition, Class<T> cls) {
		Table table = cls.getAnnotation(Table.class);
		if (table != null && condition != null) {
			StringBuilder sql = new StringBuilder();
			List<Object> values = new ArrayList<>();
			sql.append("delete from " + getTableNameFromCondition(condition, cls)).append(" ");
	 
			if (condition != null) {
				sql.append(buildWhereCondition(condition, values));
			}
			return this.jdbcTemplate.update(sql.toString(),values.toArray());
		} else {
			logger.error("no table or condition. table=" + cls.getName() + ",condition = " + condition);
			return -1;
		}
	}
	
	protected int countByCondition(Condition condition, Class<T> cls) {
		Table table = cls.getAnnotation(Table.class);
		if (table != null && condition != null) {
			StringBuilder sql = new StringBuilder();
			List<Object> values = new ArrayList<>();
			sql.append("select count(id) from " + getTableNameFromCondition(condition, cls)).append(" ");
			if (condition != null) {
				sql.append(buildWhereCondition(condition, values));
			}
			return super.getCountLimit(sql.toString(),values.toArray());
		} else {
			logger.error("no table or condition. table=" + cls.getName() + ",condition = " + condition);
			return -1;
		}
	}
	
	protected int updateByCondition(Condition condition, Map<String, Object> params, Class<T> cls) {
		Table table = cls.getAnnotation(Table.class);
		if (table != null && params != null) {
			StringBuilder sql = new StringBuilder();
			List<Object> values = new ArrayList<>();
			sql.append("update " + getTableNameFromCondition(condition, cls)).append(" set ");
			
			sql.append(buildSetSql(params, values));
			
			if (params != null) {
				sql.append(buildWhereCondition(condition, values));
			}
			return this.jdbcTemplate.update(sql.toString(),values.toArray());
		} else {
			logger.error("no table or params. table=" + cls.getName() + ",params = " + params);
			return -1;
		}
	}
	
	protected int updateByLimitCondition(Condition condition, Map<String, Object> params, Class<T> cls,long limitNum) {
		Table table = cls.getAnnotation(Table.class);
		if (table != null && params != null) {
			StringBuilder sql = new StringBuilder();
			List<Object> values = new ArrayList<>();
			sql.append("update " + getTableNameFromCondition(condition, cls)).append(" set ");
			
			sql.append(buildSetSql(params, values));
			
			if (params != null) {
				sql.append(buildWhereCondition(condition, values));
			}
			
			if(limitNum>0){
				sql.append(" LIMIT "+limitNum);
			}
			
			return this.jdbcTemplate.update(sql.toString(),values.toArray());
		} else {
			logger.error("no table or params. table=" + cls.getName() + ",params = " + params);
			return -1;
		}
	}
	
	protected long insertObjectAndGetAutoIncreaseId(final T obj, final List<String> definedFields) {
		final List<Field> fields = new ArrayList<Field>();
		final String sql = getInsertSql(obj, fields, definedFields);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				int index = 1;
				for (Field field : fields) {
					try {
						ps.setObject(index, obj.getClass().getMethod("get" + upperFirstChar(field.getName())).invoke(obj));
						index++;
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
				}
				return ps;
			}
		}, keyHolder);

		return keyHolder.getKey().longValue();
	}
	
	protected void insertObject(final T obj, final List<String> definedFields) {
		final List<Field> fields = new ArrayList<Field>();
		final String sql = getInsertSql(obj, fields, definedFields);

		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				int index = 1;
				for (Field field : fields) {
					try {
						ps.setObject(index, obj.getClass().getMethod("get" + upperFirstChar(field.getName())).invoke(obj));
						index++;
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
				}
				return ps;
			}
		});
	}

	protected String getTableNameFromObj(Object obj) {
		Table table = obj.getClass().getAnnotation(Table.class);
		if (table != null) {
			//分表逻辑
			if (StringUtils.isNotEmpty(table.modValue()) && StringUtils.isNotEmpty(table.splitFieldName())) {
				try {
					long value = Long.parseLong(obj.getClass().getMethod("get" + upperFirstChar(table.splitFieldName())).invoke(obj).toString());
					return getTableName(table.value(), Integer.parseInt(table.modValue()), value);
				} catch (Exception e) {
					logger.error("fail to generate split rule field === " + table.splitFieldName());
					return null;
				}
			} else if (StringUtils.isNotEmpty(table.modType()) && StringUtils.isNotEmpty(table.splitFieldName())) {
				try {
					Object value = obj.getClass().getMethod("get" + upperFirstChar(table.splitFieldName())).invoke(obj);
					return table.value() + (value != null ? ("_" + value.toString().substring(0, 1)) : "");
				} catch (Exception e) {
					logger.error("fail to generate split rule field === " + table.splitFieldName());
					return null;
				}
			} else {
				return table.value();
			}
		} else {
			return obj.getClass().toString().substring(obj.getClass().toString().lastIndexOf(".") + 1);
		}
		
	}
	
	private String getTableNameFromCondition(Condition condition, Class<?> cls) {
		Table table = cls.getAnnotation(Table.class);
		if (table != null) {
			//分表逻辑
			if (condition.getSplitValue() > 0) {
				if (StringUtils.isNotEmpty(table.modValue()) && StringUtils.isNotEmpty(table.splitFieldName())) {
					try {
						return getTableName(table.value(), Integer.parseInt(table.modValue()), condition.getSplitValue());
					} catch (Exception e) {
						logger.error("fail to generate split rule field === " + table.splitFieldName());
						return null;
					}
				} else {
					return table.value();
				}
			} else {
				return table.value();
			}
		} else {
			return cls.toString().substring(cls.toString().lastIndexOf(".") + 1);
		}
		
	}
	
	
	
	private String getInsertSql(Object obj, List<Field> fieldList, List<String> definedFields) {
		String tableName = getTableNameFromObj(obj);
		
		StringBuffer sql = new StringBuffer();
		StringBuffer prepareSql = new StringBuffer();
		sql.append("insert into " + tableName + " (");
		prepareSql.append(" values (");
		boolean hasFields = false;
		Field[] fields = obj.getClass().getDeclaredFields();
		Field field = null;
		for (int i= 0 ; i < fields.length; i++) {
			field = fields[i];
			if (definedFields == null || 
					definedFields.contains(field.getName())) {  //只插入需要插入的字段，其他字段使用默认值
 				if (field.getAnnotation(ID.class) == null && field.getAnnotation(Ignore.class) == null) { // 主键自增的不插入
					sql.append(field.getName()).append(",");
					hasFields = true;
					prepareSql.append("?,");
					fieldList.add(field);
				}
			}
		}
		if (hasFields) { // 去掉最后一个,
			sql.deleteCharAt(sql.length() - 1).append(")");
			prepareSql.deleteCharAt(prepareSql.length() - 1);
		}

		sql.append(prepareSql).append(")");
		return sql.toString();
	}
	
	private String buildSetSql(Map<String, Object> params, List<Object> values) {
		StringBuilder sql = new StringBuilder();
		for (Entry<String, Object> param : params.entrySet()) {
			sql.append(param.getKey()).append("=?,");
			values.add(param.getValue());
		}
		return sql.toString().substring(0, sql.length()-1);
	}
	
	public String buildWhereCondition(Condition condition, List<Object> values) {
		StringBuilder sql = new StringBuilder();
		sql.append(" where ");
		Iterator<String> conKeys = condition.getConditions().keySet().iterator();
		String key = null;
		Map<String, Object> params = null;
		String andOr = "and";
		while (conKeys.hasNext()) {
			key = conKeys.next();
			params = condition.getConditions().get(key);
			if (key.equals(ConditionType.OR.getType())) {
				sql.append("(");
				andOr = "or";
			} else {
				andOr = "and";
			}
			if (key.equals(ConditionType.IN.getType())) {
				for (Entry<String, Object> param : params.entrySet()) {
					sql.append(param.getKey()).append(" " + key + " (");
					if (param.getValue() instanceof int[]) {
						int[] valList = (int[])param.getValue();
						for (int val : valList) {
							sql.append("?,");
							values.add(val);
						}
					}
					sql.append("-99999) and ");
				}
			}else if(key.equals(ConditionType.ISNULL.getType())){
					sql.append(" " +ConditionType.ISNULL.getType()+"(assignTo");
					sql.append(")" + " and ");	
			}else{
				for (Entry<String, Object> param : params.entrySet()) {
					sql.append(param.getKey()).append(" " + key + " ? " + andOr + " ");
					if (key.equals(ConditionType.LIKE.getType())) {
						values.add("%" + param.getValue() + "%");
					} else {
						values.add(param.getValue());
					}
				}
			}
			if (andOr.equals("or")) {
				sql.append(" 1=1)");
			}
		}
		sql.append("1=1");
		return sql.toString();
	}
	
	private String upperFirstChar(String str) {
		return str.substring(0,1).toUpperCase() + str.substring(1);
	}
}
