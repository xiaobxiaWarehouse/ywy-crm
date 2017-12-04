package com.ywy.controller.contactor;

import com.ywy.annotation.UserInfo;
import com.ywy.consts.ErrorCode;
import com.ywy.consts.YWYConsts;
import com.ywy.controller.BaseController;
import com.ywy.entity.contactor.Contactor;
import com.ywy.entity.sys.CrmAccount;
import com.ywy.enumtype.DatasourceEnum;
import com.ywy.service.contactor.ContactorService;
import com.ywy.util.ParamHandleUtil;
import com.ywy.util.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>联系人信息</p>
 *
 * @author qijing110 <939474528@qq.com>
 * @date 2017-09-18
 */
@Controller
@RequestMapping(value = "/contactor")
public class ContactorController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(ContactorController.class);

    @Autowired
    private ContactorService contactorService;

    /**
     * 获取联系人列表
     * @param request
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "/list/get", method = RequestMethod.POST)
    public  @ResponseBody JSONObject  list(HttpServletRequest request, @UserInfo CrmAccount account,
                                           @RequestParam(value = "pageNo") Integer pageNo,
                                           @RequestParam(value = "name", required = false) String name,
                                           @RequestParam(value = "mobile", required = false) String mobile,
                                           @RequestParam(value = "tag", required = false) String tag) {

        if (pageNo == null) {
            LOG.error("request parameter is null {} CompanyController list get error : {}", "pageNo", ErrorCode.NO_MATCH_DATA);
            throw new RuntimeException("参数不能为空");
        }
        JSONObject jsonObject = null;
        try {
            Contactor contactor = new Contactor();
            contactor.setCustomerId(account.getCustomerId());
            contactor.setName(name);
            contactor.setMobile(mobile);
            contactor.setTagIds(tag);
            jsonObject = contactorService.getListByCondition(pageNo, contactor, account);
        } catch (Exception e) {
            LOG.error("get contactor list simple error: [] and request line : []:[]", ErrorCode.NO_MATCH_DATA, request.getContextPath(), e);
            return wrapErrorResult(ErrorCode.OPERATOR_ERROR);
        }
        return wrapResult(jsonObject);
    }

    /**
     * 获取联系人列表
     * @param request
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "/list/baseinfo/get", method = RequestMethod.POST)
    public  @ResponseBody JSONObject  listSimple(HttpServletRequest request, @UserInfo CrmAccount account,
                                           @RequestParam(value = "pageNo") Integer pageNo,
                                           @RequestParam(value = "name", required = false) String name,
                                           @RequestParam(value = "mobile", required = false) String mobile,
                                           @RequestParam(value = "tag", required = false) String tag) {

        if (pageNo == null) {
            LOG.error("request parameter is null {} CompanyController list get error : {}", "pageNo", ErrorCode.NO_MATCH_DATA);
            throw new RuntimeException("参数不能为空");
        }
        JSONObject jsonObject = null;
        try {
            Contactor contactor = new Contactor();
            contactor.setCustomerId(account.getCustomerId());
            contactor.setName(name);
            contactor.setMobile(mobile);
            contactor.setTagIds(tag);
            jsonObject = contactorService.getListByConditionSimple(pageNo, contactor, account);
        } catch (Exception e) {
            LOG.error("get contactor list simple error: [] and request line : []:[]", ErrorCode.NO_MATCH_DATA, request.getContextPath(), e);
            return wrapErrorResult(ErrorCode.OPERATOR_ERROR);
        }
        return wrapResult(jsonObject);
    }

    /**
     * 客户信息保存
     * @param request
     * @param body
     * @return
     */
    @RequestMapping(value = "/baseinfo/save", method = RequestMethod.POST)
    public  @ResponseBody JSONObject save(HttpServletRequest request, @RequestBody String body, @UserInfo CrmAccount crmAccount) {

        JSONObject jsonObject = null;
        try {
            Contactor contactor = ParamHandleUtil.build(body, Contactor.class);
            if(contactor == null) {
                jsonObject = new JSONObject();
                jsonObject.put(YWYConsts.RC, ErrorCode.PARAM_ERROR);
                return wrapResult(jsonObject);
            }
            contactor.setCustomerId(crmAccount.getCustomerId());
            contactor.setCreateorId(crmAccount.getId());
            contactor.setSource(DatasourceEnum.DATASOURCE_HANDWORDK.getVal());
            jsonObject = contactorService.save(contactor);
        } catch (Exception e) {
            LOG.error("save contactor error: [] and request line : []:[]", ErrorCode.NO_MATCH_DATA, request.getContextPath(), e);
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
    @RequestMapping(value = "/delete/byId", method = RequestMethod.POST)
    public  @ResponseBody JSONObject deleteById(HttpServletRequest request, @RequestBody String body, @UserInfo CrmAccount crmAccount) {

        JSONObject jsonObject = null;
        try {
            Contactor contactor = ParamHandleUtil.build(body, Contactor.class);
            if(contactor == null || StringUtil.isEmpty(contactor.getUuid())) {
                jsonObject = new JSONObject();
                jsonObject.put(YWYConsts.RC, ErrorCode.PARAM_ERROR);
                return wrapResult(jsonObject);
            }
            contactor.setCustomerId(crmAccount.getCustomerId());
            jsonObject = contactorService.delete(contactor);
        } catch (Exception e) {
            LOG.error("delete contactor error: [] and request line : []:[]", ErrorCode.NO_MATCH_DATA, request.getContextPath(), e);
            return wrapErrorResult(ErrorCode.OPERATOR_ERROR);
        }
        return wrapResult(jsonObject);
    }

    /**
     * 客户详情获取
     * @param request
     * @param body
     * @return
     */
    @RequestMapping(value = "/get/byId", method = RequestMethod.POST)
    public  @ResponseBody JSONObject getById(HttpServletRequest request, @RequestBody String body, @UserInfo CrmAccount crmAccount) {

        JSONObject jsonObject = null;
        try {
            Contactor contactor = ParamHandleUtil.build(body, Contactor.class);
            if(contactor == null || StringUtil.isEmpty(contactor.getUuid())) {
                jsonObject = new JSONObject();
                jsonObject.put(YWYConsts.RC, ErrorCode.PARAM_ERROR);
                return wrapResult(jsonObject);
            }
            contactor.setCustomerId(crmAccount.getCustomerId());
            jsonObject = contactorService.queryById(contactor);
        } catch (Exception e) {
            LOG.error("get contactor detail error: [] and request line : []:[]", ErrorCode.NO_MATCH_DATA, request.getContextPath(), e);
            return wrapErrorResult(ErrorCode.OPERATOR_ERROR);
        }
        return wrapResult(jsonObject);
    }

    /**
     * excel导入
     * @param request
     * @param file
     * @return
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public  @ResponseBody JSONObject importByExcel(HttpServletRequest request, @RequestParam("file") MultipartFile file, @UserInfo CrmAccount account) {

        JSONObject jsonObject = null;
        try {
            jsonObject = contactorService.importByExcel(file, account);
        } catch (Exception e) {
            LOG.error("import contactor error: [] and request line : []:[]", ErrorCode.NO_MATCH_DATA, request.getContextPath(), e);
            return wrapErrorResult(ErrorCode.OPERATOR_ERROR);
        }
        return jsonObject;
    }

    /**
     * 呼叫统计报表导出
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public ResponseEntity<?> exportSmsStatics(HttpServletRequest request, @UserInfo CrmAccount account,
                                              @RequestParam(value = "name", required = false) String name,
                                              @RequestParam(value = "mobile", required = false) String mobile,
                                              @RequestParam(value = "tag", required = false) String tag)
            throws IOException {
        try {
            Contactor contactor = new Contactor();
            contactor.setCustomerId(account.getCustomerId());
            contactor.setName(name);
            contactor.setMobile(mobile);
            contactor.setTagIds(tag);
            String path = contactorService.exportList(1, contactor, account);
            FileSystemResource file = new FileSystemResource(path);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("charset", "utf-8");
            headers.add("Content-Type", "application/x-msdownload");
            headers.add("Content-Length", String.valueOf(file.contentLength()));

            return new ResponseEntity<>(new InputStreamResource(file.getInputStream()), headers, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("CONTACTOR CSV EXPORT ERROR:" + e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.TOO_MANY_REQUESTS);
        }
    }

}
