package com.ywy.service.clue;

import com.ywy.entity.sys.CrmAccount;

import net.sf.json.JSONObject;

/**
 * <p>一键获客</p>
 */
public interface ClueService {


    /**
     * <p>一键获客</p>
     * @param industryId
     * @param account
     * @return
     */
    JSONObject save(Long industryId,String searchTypeCode, CrmAccount account);

    /**
     * <p>新线索申请添加</p>
     * @param clueName
     * @param parentIndustryId
     * @return
     */
    JSONObject saveIndustryByclueName(String clueName, Integer parentIndustryId, CrmAccount account);
    
    
    JSONObject queryClues(long customerId);

	JSONObject queryAllocateClues(long customerId);

    JSONObject delete(long customerId, String clueId);
}
