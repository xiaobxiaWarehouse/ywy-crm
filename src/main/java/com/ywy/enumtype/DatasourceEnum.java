package com.ywy.enumtype;

/**
 * 线索导入
 * cn.upg.credit.common.po.enums
 * Created by yaoyan on 17/6/25.
 * 17/6/25 下午5:25
 */
public enum DatasourceEnum {

    DATASOURCE_HANDWORDK("HANDWORK","手工导入"),
    DATASOURCE_EXCEL("EXCEL","EXCEL导入"),
    DATASOURCE_OTB("OTB","线索导入");

    private String val ;
    private String desc ;

    DatasourceEnum(String val, String desc) {
        this.val = val;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
