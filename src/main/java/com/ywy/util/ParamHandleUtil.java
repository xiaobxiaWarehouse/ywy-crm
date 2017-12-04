package com.ywy.util;

import com.ywy.entity.company.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class ParamHandleUtil{
    private static final Logger LOG = LoggerFactory.getLogger(ParamHandleUtil.class);

	public static <T> T build(String param, Class<T> t) {
	    try {
            Map map = new HashMap();
            if(StringUtil.isEmpty(param)) {
                return null;
            }
            String decodeParam = URLDecoder.decode(param, "UTF-8");
            LOG.info("[" + t.getName() + "]GET PARAM INFO:" + decodeParam);
            String[] params = decodeParam.split("&");
            for (int i = 0; i < params.length; i++) {
                String[] innerParam = params[i].split("=");
                if(innerParam.length == 2 && !StringUtil.isEmpty(innerParam[1])) {
                    try {
                        String value = innerParam[1];
                        if(!StringUtil.isEmpty(value) && value.indexOf("'") >= 0) {
                            value = value.replaceAll("'", "\\\\'");
                        }
                        map.put(innerParam[0], value);
                    } catch (Exception e) {
                        LOG.error(e.getMessage(), e);
                    }
                }
            }
            return (T) mapToObject(map, t);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
	}

    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        org.apache.commons.beanutils.BeanUtils.populate(obj, map);

        return obj;
    }

}
