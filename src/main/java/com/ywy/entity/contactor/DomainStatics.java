package com.ywy.entity.contactor;

import com.ywy.annotation.ID;
import com.ywy.annotation.Table;

/**
 * com.ywy.entity.contactor
 * Created by yaoyan on 17/11/9.
 * 17/11/9 下午8:18
 */
@Table(value = "crm_domain_statics")
public class DomainStatics {
    @ID
    private String id;
    /** 总请求数量 */
    private Long requestTotal;
    /** 匹配数量 */
    private Long matchedTotal;
    /** 去重请求总数量 */
    private Long requestTotalNoDup;
    /** 总的域名数量 */
    private Long domainTotal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getRequestTotal() {
        return requestTotal;
    }

    public void setRequestTotal(Long requestTotal) {
        this.requestTotal = requestTotal;
    }

    public Long getMatchedTotal() {
        return matchedTotal;
    }

    public void setMatchedTotal(Long matchedTotal) {
        this.matchedTotal = matchedTotal;
    }

    public Long getRequestTotalNoDup() {
        return requestTotalNoDup;
    }

    public void setRequestTotalNoDup(Long requestTotalNoDup) {
        this.requestTotalNoDup = requestTotalNoDup;
    }

    public Long getDomainTotal() {
        return domainTotal;
    }

    public void setDomainTotal(Long domainTotal) {
        this.domainTotal = domainTotal;
    }
}
