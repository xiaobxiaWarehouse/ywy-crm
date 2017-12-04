package com.ywy.controller.company;

import com.ywy.annotation.UserInfo;
import com.ywy.consts.ErrorCode;
import com.ywy.consts.YWYConsts;
import com.ywy.controller.BaseController;
import com.ywy.entity.company.Company;
import com.ywy.entity.sys.CrmAccount;
import com.ywy.enumtype.DatasourceEnum;
import com.ywy.service.company.CompanyService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>公司客户列表</p>
 *
 * @author qijing110 <939474528@qq.com>
 * @date 2017-09-18
 */
@Controller
@RequestMapping(value = "/customer")
public class CompanyController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private CompanyService companyService;

    /**
     * 获取客户列表
     * @param request
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "/list/get", method = RequestMethod.POST)
    public  @ResponseBody JSONObject  list(HttpServletRequest request, @UserInfo CrmAccount account,
                                           @RequestParam(value = "pageNo") Integer pageNo,
                                           @RequestParam(value = "simpleSearch") Integer simpleSearch,
                                           @RequestParam(value = "industryName", required = false) String industryName,
                                           @RequestParam(value = "name", required = false) String name,
                                           @RequestParam(value = "mobile", required = false) String mobile,
                                           @RequestParam(value = "tag", required = false) String tag) {
        if (pageNo == null) {
            LOG.error("request parameter is null {} CompanyController list get error : {}", "pageNo", ErrorCode.NO_MATCH_DATA);
            throw new RuntimeException("参数不能为空");
        }
        if(simpleSearch == null || (simpleSearch != 0 && simpleSearch != 1)) {
            LOG.error("request parameter is not match {} CompanyController list get error : {}", "simpleSearch", ErrorCode.NO_MATCH_DATA);
            throw new RuntimeException("参数不能为空");
        }
        JSONObject jsonObject = null;
        try {
            Company company = new Company();
            company.setIndustry(industryName);
            company.setName(name);
            company.setMobile(mobile);
            company.setTagIds(tag);
            company.setCustomerId(account.getCustomerId());
            if(simpleSearch == 0) {
                jsonObject = companyService.getListByCondition(pageNo, company, account);
            } else if(simpleSearch == 1) {
                jsonObject = companyService.getListByConditionSimple(pageNo, company, account);
            }
        } catch (Exception e) {
            LOG.error("get company list error: [] and request line : []:[]", ErrorCode.NO_MATCH_DATA, request.getContextPath(), e);
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
    public  @ResponseBody JSONObject save(HttpServletRequest request, @UserInfo CrmAccount account, @RequestBody String body) {

        JSONObject jsonObject = null;
        try {
            Company company = ParamHandleUtil.build(body, Company.class);
            if(company == null) {
                LOG.error("request parameter is null {} CompanyController save error : {}", "company", ErrorCode.PARAM_ERROR);
                jsonObject = new JSONObject();
                jsonObject.put(YWYConsts.RC, ErrorCode.PARAM_ERROR);
                return wrapResult(jsonObject);
            }
            if(!StringUtil.isUrl(company.getDomain())) {
                LOG.error("request parameter is null {} CompanyController save error : {}", "domain", ErrorCode.PARAM_ERROR);
                jsonObject = new JSONObject();
                jsonObject.put(YWYConsts.RC, ErrorCode.DOMAIN_ERROR);
                return wrapResult(jsonObject);
            }
            company.setCustomerId(account.getCustomerId());
            company.setCreatorId(account.getId());
            company.setSource(DatasourceEnum.DATASOURCE_HANDWORDK.getVal());
            jsonObject = companyService.save(company);
        } catch (Exception e) {
            LOG.error("save company simple error: [] and request line : []:[]", ErrorCode.NO_MATCH_DATA, request.getContextPath(), e);
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
    public  @ResponseBody JSONObject deleteById(HttpServletRequest request, @RequestBody String body, @UserInfo CrmAccount account) {

        JSONObject jsonObject = null;
        try {
            Company company = ParamHandleUtil.build(body, Company.class);
            if(company == null || StringUtil.isEmpty(company.getUuid())) {
                jsonObject = new JSONObject();
                jsonObject.put(YWYConsts.RC, ErrorCode.PARAM_ERROR);
                return wrapResult(jsonObject);
            }
            company.setCustomerId(account.getCustomerId());
            jsonObject = companyService.delete(company);
        } catch (Exception e) {
            LOG.error("delete company error: [] and request line : []:[]", ErrorCode.NO_MATCH_DATA, request.getContextPath(), e);
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
    public  @ResponseBody JSONObject getById(HttpServletRequest request, @RequestBody String body, @UserInfo CrmAccount account) {

        JSONObject jsonObject = null;
        try {
            Company company = ParamHandleUtil.build(body, Company.class);
            if(company == null || StringUtil.isEmpty(company.getUuid())) {
                jsonObject = new JSONObject();
                jsonObject.put(YWYConsts.RC, ErrorCode.PARAM_ERROR);
                return wrapResult(jsonObject);
            }
            company.setCustomerId(account.getCustomerId());
            jsonObject = companyService.queryById(company);
        } catch (Exception e) {
            LOG.error("get company detail error: [] and request line : []:[]", ErrorCode.NO_MATCH_DATA, request.getContextPath(), e);
            return wrapErrorResult(ErrorCode.OPERATOR_ERROR);
        }
        return wrapResult(jsonObject);
    }

    /**
     * 客户转化
     * @param request
     * @param body
     * @return
     */
    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public  @ResponseBody JSONObject transfer(HttpServletRequest request, @RequestBody String body, @UserInfo CrmAccount account) {
        JSONObject jsonObject = null;
        try {
            Company company = ParamHandleUtil.build(body, Company.class);
            if(company == null || StringUtil.isEmpty(company.getClueId())) {
                jsonObject = new JSONObject();
                jsonObject.put(YWYConsts.RC, ErrorCode.PARAM_ERROR);
                return wrapResult(jsonObject);
            }

            if(!StringUtil.isUrl(company.getDomain())) {
                LOG.error("request parameter is null {} CompanyController transfer error : {}", "domain", ErrorCode.PARAM_ERROR);
                jsonObject = new JSONObject();
                jsonObject.put(YWYConsts.RC, ErrorCode.DOMAIN_ERROR);
                return wrapResult(jsonObject);
            }
            company.setCustomerId(account.getCustomerId());
            company.setCreatorId(account.getId());
            jsonObject = companyService.transfer(company);
        } catch (Exception e) {
            LOG.error("transfer clue to company error: [] and request line : []:[]", ErrorCode.NO_MATCH_DATA, request.getContextPath(), e);
            return wrapErrorResult(ErrorCode.OPERATOR_ERROR);
        }
        return wrapResult(jsonObject);
    }

    /**
     * 客户开启信息自动匹配
     * @param request
     * @param body
     * @return
     */
    @RequestMapping(value = "/autoMatch", method = RequestMethod.POST)
    public  @ResponseBody JSONObject autoMatch(HttpServletRequest request, @RequestBody String body, @UserInfo CrmAccount account) {

        JSONObject jsonObject = null;
        try {
            Company company = ParamHandleUtil.build(body, Company.class);
            if(company == null || StringUtil.isEmpty(company.getUuid())) {
                LOG.error("autoMatch company error: [] param info error:[]", ErrorCode.PARAM_ERROR, company.getUuid());
                jsonObject = new JSONObject();
                jsonObject.put(YWYConsts.RC, ErrorCode.PARAM_ERROR);
                return wrapResult(jsonObject);
            }
            company.setCustomerId(account.getCustomerId());
            jsonObject = companyService.autoMatch(company);
        } catch (Exception e) {
            LOG.error("autoMatch company error: [] and request line : []:[]", ErrorCode.NO_MATCH_DATA, request.getContextPath(), e);
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
            jsonObject = companyService.importByExcel(file, account);
        } catch (Exception e) {
            LOG.error("import company error: [] and request line : []:[]", ErrorCode.NO_MATCH_DATA, request.getContextPath(), e);
            return wrapErrorResult(ErrorCode.OPERATOR_ERROR);
        }
        return jsonObject;
    }

    /**
     * 报表导出
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public ResponseEntity<?> exportSmsStatics(HttpServletRequest request, @UserInfo CrmAccount account,
                                              @RequestParam(value = "industryName", required = false) String industryName,
                                              @RequestParam(value = "name", required = false) String name,
                                              @RequestParam(value = "mobile", required = false) String mobile,
                                              @RequestParam(value = "tag", required = false) String tag)
            throws IOException {
        try {
            Company company = new Company();
            company.setIndustry(industryName);
            company.setName(name);
            company.setMobile(mobile);
            company.setTagIds(tag);
            company.setCustomerId(account.getCustomerId());
            String path = companyService.exportList(1, company, account);
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
            LOG.error("COMPANY CSV EXPORT ERROR:" + e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.TOO_MANY_REQUESTS);
        }
    }


}
