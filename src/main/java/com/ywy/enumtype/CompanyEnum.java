package com.ywy.enumtype;

/**
 * 客户枚举类型
 * cn.upg.credit.common.po.enums
 * Created by yaoyan on 17/6/25.
 * 17/6/25 下午5:25
 */
public enum CompanyEnum {

    STATUS_ENABLED(1,"客户状态：有效"),
    STATUS_DISABLED(2,"客户状态：无效"),

    MATCHSTATUS_INIT(0,"匹配状态：初始"),
    MATCHSTATUS_PROC(1,"匹配状态：匹配中"),
    MATCHSTATUS_SUCC(2,"匹配状态：匹配成功"),
    MATCHSTATUS_FALI(10,"匹配状态：匹配失败"),

    AUTO_MATCH_FLAG_CLOSE(0,"自动匹配标识：关闭"),
    AUTO_MATCH_FLAG_OPEN(1,"自动匹配标识：开启");

    private long val ;
    private String desc ;

    CompanyEnum(long val, String desc) {
        this.val = val;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getVal() {
        return val;
    }

    public void setVal(long val) {
        this.val = val;
    }
}
