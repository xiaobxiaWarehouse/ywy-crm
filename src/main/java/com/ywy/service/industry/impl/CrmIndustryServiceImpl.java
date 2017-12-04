package com.ywy.service.industry.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ywy.consts.ErrorCode;
import com.ywy.consts.YWYConsts;
import com.ywy.entity.industry.CrmIndustry;
import com.ywy.entity.industry.CrmIndustryTree;
import com.ywy.enumtype.ConditionType;
import com.ywy.parameter.Condition;
import com.ywy.repository.industry.CrmIndustryRepository;
import com.ywy.repository.industry.CrmIndustryTreeRepository;
import com.ywy.service.impl.CommonService;
import com.ywy.service.industry.CrmIndustryService;

import net.sf.json.JSONObject;

@Service
public class CrmIndustryServiceImpl extends CommonService implements CrmIndustryService {

	@Resource
	private CrmIndustryRepository crmIndustryDao;
	
	@Resource
	private CrmIndustryTreeRepository CrmIndustryTreeDao;

	private JSONObject newCrmIndustry(CrmIndustry industry) {
		JSONObject rst = generateRst();
		Condition condition = new Condition();
		condition.setCondition("nodeName", industry.getNodeName());
		CrmIndustry tmpIndustry  = crmIndustryDao.queryIndustry(condition);
		if (tmpIndustry != null) {
			rst.put(YWYConsts.RC, ErrorCode.DUPLICATE_INDUSTRY);
			return rst;
		}
		rst.put("industyId", crmIndustryDao.newIndustry(industry, null));
		rst.put("level", industry.getLevel());
		return rst;
	}

	private JSONObject updateMsgIndustry(CrmIndustry industry) {
		JSONObject rst = generateRst();
		Condition condition = new Condition();
		condition.setCondition("id", industry.getId());
		CrmIndustry tmpIndustry  = crmIndustryDao.queryIndustry(condition);
		if (tmpIndustry == null) {
			rst.put(YWYConsts.RC, ErrorCode.NO_MATCH_DATA);
			return rst;
		}
		condition.getConditions().clear();
		condition.setCondition("id", industry.getId(), ConditionType.NEQ);
		condition.setCondition("nodeName", industry.getNodeName());
		tmpIndustry  = crmIndustryDao.queryIndustry(condition);
		if (tmpIndustry != null) {
			rst.put(YWYConsts.RC, ErrorCode.DUPLICATE_INDUSTRY);
			return rst;
		}
		
		condition.getConditions().clear();
		condition.setCondition("id", industry.getId());
		
		crmIndustryDao.updateIndustry(condition, industry, new String[]{"nodeName","level","parentId",
				"creatorId","path"});
		return rst;
	}

	@Override
	public JSONObject saveCrmIndustry(CrmIndustry industry) {
		if (industry.getId() > 0) {
			return updateMsgIndustry(industry);
		} else {
			return newCrmIndustry(industry);
		}
	}
	
	
	
	
	@Override
	public JSONObject getIdustryList(){
		JSONObject rst = generateRst();
		Condition condition = new Condition();
		List<CrmIndustryTree> mgIs=CrmIndustryTreeDao.queryIndustrysTree(condition);
		List<CrmIndustryTree> meneList=new ArrayList<CrmIndustryTree>();
		if(mgIs.isEmpty()){
			rst.put("dataList", meneList);
			return rst;
		}
		for(CrmIndustryTree mi:mgIs){
			if(mi.getLevel()==1){
				meneList.add(mi);
			}
		}
		 // 为一级菜单设置子菜单，getChild是递归调用的
	    for (CrmIndustryTree menu : meneList) {
	        menu.setChildren(getChild(menu.getId(), mgIs));
	    }
	    rst.put("dataList", meneList);
	    return rst;
	}
	
	private List<CrmIndustryTree> getChild(Long id,List<CrmIndustryTree> menus){
		 // 子菜单
	    List<CrmIndustryTree> childList = new ArrayList<>();
	    for (CrmIndustryTree menu : menus) {
	        // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getParentId()==id) {
                childList.add(menu);
            }
	    }
	    // 把子菜单的子菜单再循环一遍
	    for (CrmIndustryTree menu : childList) {// 没有url子菜单还有子菜单
	        if (menu.getChildFlag()==1) {
	            // 递归
	            menu.setChildren(getChild(menu.getId(), menus));
	        }
	    } // 递归退出条件
	    if (childList.size() == 0) {
	        return null;
	    }
	    return childList;
	}


	
	
	
}
