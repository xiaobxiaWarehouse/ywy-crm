package com.ywy.parameter;

import java.util.HashMap;
import java.util.Map;

import com.ywy.enumtype.ConditionType;

public class Condition {
	private Map<String, Map<String, Object>> conditions = new HashMap<String, Map<String, Object>>();
	
	private long splitValue = 0;

	public long getSplitValue() {
		return splitValue;
	}

	public void setSplitValue(long splitValue) {
		this.splitValue = splitValue;
	}
	/**
	 * 
	 * @param colName  列的名字
	 * @param value   列的值
	 * @param type    ConditionType 查询条件关系
	 */
	public void setCondition(String colName, Object value, ConditionType type) {
		if (!conditions.containsKey(type.getType())) {
			conditions.put(type.getType(), new HashMap<String, Object>());
		}
		conditions.get(type.getType()).put(colName, value);
	}
	
	public void setCondition(String colName, Object value) {
		ConditionType type = ConditionType.EQ;
		if (!conditions.containsKey(type.getType())) {
			conditions.put(type.getType(), new HashMap<String, Object>());
		}
		conditions.get(type.getType()).put(colName, value);
	}
	
	public Map<String, Map<String, Object>> getConditions() {
		return conditions;
	}
}
