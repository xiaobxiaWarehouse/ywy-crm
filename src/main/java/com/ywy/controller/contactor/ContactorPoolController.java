package com.ywy.controller.contactor;

import com.ywy.annotation.NoAuth;
import com.ywy.consts.ErrorCode;
import com.ywy.consts.YWYConsts;
import com.ywy.controller.BaseController;
import com.ywy.entity.contactor.ContactorPool;
import com.ywy.service.contactor.ContactorPoolService;
import com.ywy.util.ParamHandleUtil;
import com.ywy.util.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>联系人总库</p>
 *
 * @author yaoya
 * <939474528@qq.com>
 * @date 2017-09-18
 */
@Controller
@RequestMapping(value = "/pool")
public class ContactorPoolController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(ContactorPoolController.class);

    @Autowired
    private ContactorPoolService contactorPoolService;

    /**
     * 同步
     * @param request
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "/contactor/query", method = RequestMethod.POST)
    @NoAuth
    public  @ResponseBody JSONObject  list(HttpServletRequest request,
                                           @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                           @RequestParam(value = "domain", required = false) String domain) {

        if (pageNo == null) {
            pageNo = 1;
        }
        JSONObject jsonObject;
        try {
            if(!StringUtil.isUrl(domain)) {
                jsonObject = new JSONObject();
                jsonObject.put(YWYConsts.RC, ErrorCode.DOMAIN_ERROR);
                return wrapResult(jsonObject);
            }
            ContactorPool contactorPool = new ContactorPool();
            contactorPool.setDomain(domain.trim());
            if(pageNo == null) pageNo = 1;
            jsonObject = contactorPoolService.getListByCondition(pageNo, contactorPool);
        } catch (Exception e) {
            LOG.error("get contactor pool list error: [] and request line : []", ErrorCode.NO_MATCH_DATA, request.getContextPath());
            return wrapErrorResult(ErrorCode.OPERATOR_ERROR);
        }
        return wrapResult(jsonObject);
    }

    @RequestMapping(value = "/contactor/statics", method = RequestMethod.POST)
    @NoAuth
    public  @ResponseBody JSONObject  statics(HttpServletRequest request) {

        JSONObject jsonObject;
        try {
            jsonObject = contactorPoolService.statics();
        } catch (Exception e) {
            LOG.error("get contactor pool statics error: [] and request line : []", ErrorCode.NO_MATCH_DATA, request.getContextPath());
            return wrapErrorResult(ErrorCode.OPERATOR_ERROR);
        }
        return wrapResult(jsonObject);
    }

    /**
     * 联系人同步
     * @param request
     * @return
     */
    @RequestMapping(value = "/contactor/sync", method = RequestMethod.POST)
    @NoAuth
    public  @ResponseBody JSONObject save(HttpServletRequest request, @RequestBody ContactorPool contactorPool) {

        JSONObject jsonObject = null;
        try {
            LOG.info("GET CONTACTOR POOL INFO:" + contactorPool);
            if(contactorPool == null || !StringUtil.isUrl(contactorPool.getDomain())) {
                LOG.error("contactor pool domain error:[]", contactorPool.getDomain());
                jsonObject = new JSONObject();
                jsonObject.put(YWYConsts.RC, ErrorCode.PARAM_ERROR);
                return wrapResult(jsonObject);
            }

            jsonObject = contactorPoolService.save(contactorPool);
        } catch (Exception e) {
            LOG.error("get contactor pool list error: [] and request line : []:[]", ErrorCode.NO_MATCH_DATA, request.getContextPath(), e);
            return wrapErrorResult(ErrorCode.OPERATOR_ERROR);
        }
        return wrapResult(jsonObject);
    }

    /**
     * 客户删除
     * @param request
     * @param body
     * @return
     */
    @RequestMapping(value = "/contactor/delete/byId", method = RequestMethod.POST)
    @NoAuth
    public  @ResponseBody JSONObject deleteById(HttpServletRequest request, @RequestBody String body) {

        JSONObject jsonObject = null;
        try {
            ContactorPool contactorPool = ParamHandleUtil.build(body, ContactorPool.class);
            if(contactorPool == null || StringUtil.isEmpty(contactorPool.getDomain())) {
                jsonObject = new JSONObject();
                jsonObject.put(YWYConsts.RC, ErrorCode.PARAM_ERROR);
                return wrapResult(jsonObject);
            }
            jsonObject = contactorPoolService.delete(contactorPool);
        } catch (Exception e) {
            LOG.error("delete contactor pool error: [] and request line : []", ErrorCode.NO_MATCH_DATA, request.getContextPath());
            return wrapErrorResult(ErrorCode.OPERATOR_ERROR);
        }
        return wrapResult(jsonObject);
    }

    /**
     * 联系人详情获取
     * @param request
     * @param body
     * @return
     */
    @RequestMapping(value = "/contactor/detail/get", method = RequestMethod.POST)
    @NoAuth
    public  @ResponseBody JSONObject getById(HttpServletRequest request, @RequestBody String body) {

        JSONObject jsonObject = null;
        try {
            ContactorPool contactorPool = ParamHandleUtil.build(body, ContactorPool.class);
            if(contactorPool == null || contactorPool.getId() == null || StringUtil.isEmpty(contactorPool.getDomain())) {
                jsonObject = new JSONObject();
                jsonObject.put(YWYConsts.RC, ErrorCode.PARAM_ERROR);
                return wrapResult(jsonObject);
            }
            jsonObject = contactorPoolService.queryById(contactorPool);
        } catch (Exception e) {
            LOG.error("get contactor pool detail error: [] and request line : []", ErrorCode.NO_MATCH_DATA, request.getContextPath());
            return wrapErrorResult(ErrorCode.OPERATOR_ERROR);
        }
        return wrapResult(jsonObject);
    }


}
