package com.karlchu.medicalorder.core.utils;


import com.karlchu.medicalorder.core.UserPrincipal;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by hieu on 4/11/2016.
 */
public class DataBinder {
    public final static ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public static UserPrincipal readUserPrincipal(String json) {
        UserPrincipal res = null;
        try {
            res = mapper.readValue(json, UserPrincipal.class);
        }catch (Exception e) {
            ELog.e(e.getMessage(), e);
        }
        return res;
    }

    public static String toJsonString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            ELog.e(e.getMessage(), e);
        }
        return "";
    }
}
