package com.ywy.enumtype;

public enum ConditionType {
	EQ("="),
	NEQ("!="),
	LIKE("like"),
	GT(">="),
	LT("<="),
	LL("<"),
	GG(">"),
	IN("in"),
	ISNULL("ISNULL"),
	OR("or");
	public String getType() {
		return type;
	}
	private String type;
	
	ConditionType(String type) {
		this.type = type;
	}
}
