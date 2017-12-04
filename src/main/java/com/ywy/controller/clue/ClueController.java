package com.ywy.controller.clue;

import com.ywy.annotation.UserInfo;
import com.ywy.consts.ErrorCode;
import com.ywy.controller.BaseController;
import com.ywy.entity.clue.CrmClue;
import com.ywy.entity.sys.CrmAccount;
import com.ywy.service.clue.ClueService;
import com.ywy.util.ParamHandleUtil;
import com.ywy.util.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/clue")
public class ClueController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ClueController.class);

    @Autowired
    private ClueService clueService;

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public @ResponseBody JSONObject save(@RequestParam("industryId") Long industryId,@RequestParam("searchTypeCode") String searchTypeCode, @UserInfo CrmAccount account) {
        return wrapResult(clueService.save(industryId,searchTypeCode,account));
    }

    @RequestMapping(value = "/apply/new", method = RequestMethod.POST)
    public @ResponseBody
    JSONObject saveIndustry(@RequestParam("clueName") String clueName, @RequestParam("parentIndustryId") Integer parentIndustryId ,@UserInfo CrmAccount account) {
        return wrapResult(clueService.saveIndustryByclueName(clueName, parentIndustryId, account));
    }
    
    @RequestMapping(value = "/apply/hisClue", method = RequestMethod.POST)
    public @ResponseBody
    JSONObject queryClues(@UserInfo CrmAccount account) {
        return wrapResult(clueService.queryClues(account.getCustomerId()));
    }
    
    @RequestMapping(value = "/allocate/hisClue", method = RequestMethod.POST)
    public @ResponseBody
    JSONObject queryAllocateClues(@UserInfo CrmAccount account) {
        return wrapResult(clueService.queryAllocateClues(account.getCustomerId()));
    }

    /**
     * 删除线索
     * @param body
     * @param account
     * @return
     */
    @RequestMapping(value = "/delete/byId", method = RequestMethod.POST)
    public @ResponseBody
    JSONObject queryClues(HttpServletRequest request, @RequestBody String body, @UserInfo CrmAccount account) {
        JSONObject jsonObject;
        try {
            CrmClue crmClue = ParamHandleUtil.build(body, CrmClue.class);
            if(crmClue == null || StringUtil.isEmpty(crmClue.getClueId())) {
                return wrapErrorResult(ErrorCode.PARAM_ERROR);
            }
            jsonObject = wrapResult(clueService.delete(account.getCustomerId(), crmClue.getClueId()));
        } catch (Exception e) {
            LOG.error("delete clue error: [] and request line : []:[]", ErrorCode.NO_MATCH_DATA, request.getContextPath(), e);
            return wrapErrorResult(ErrorCode.OPERATOR_ERROR);
        }
        return wrapResult(jsonObject);

    }
}
