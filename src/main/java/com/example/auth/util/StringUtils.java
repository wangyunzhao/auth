package com.example.auth.util;

import com.alibaba.fastjson.JSONObject;
import com.example.auth.exception.ServiceException;


import java.security.SecureRandom;
import java.util.*;

/**
 * Created by zhicheng.zhao on 2020/4/27.
 */
public class StringUtils {

    private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final Random RANDOM = new SecureRandom();

    /**
     * 判断对象是否为空
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof String && Objects.equals(String.valueOf(obj).trim(), "")) {
            return true;
        } else if (obj instanceof String && Objects.equals(String.valueOf(obj).trim(), "null")) {
            return true;
        } else if (obj instanceof Boolean && !((Boolean) obj)) {
            return true;
        } else if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        } else if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        } else if (obj instanceof Object[] && ((Object[]) obj).length == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Object obj) {
        return !StringUtils.isEmpty(obj);
    }

    public static String parseToString(Object object) {
        if (StringUtils.isEmpty(object)) {
            return "";
        } else {
            return String.valueOf(object);
        }
    }

    /**
     * 校验必填参数
     *
     * @param object
     * @param required
     */
    public static void validateParam(Object object, String... required) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(object));
        StringBuffer stringBuffer = new StringBuffer();

        for (String column : required) {
            Object value = jsonObject.get(column);
            if (StringUtils.isEmpty(value)) {
                stringBuffer.append(column + " ");
            }
        }

        if (StringUtils.isNotEmpty(stringBuffer.toString())) {
            throw new ServiceException("缺少必填参数：" + stringBuffer);
        }
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        String regex = "^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$";
        if (mobile.matches(regex)) {
            return true;
        }

        return false;
    }

    /**
     * 获取uuid，去掉-
     *
     * @return
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String generateStr(int i) {
        char[] nonceChars = new char[i];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }

    public static String parseArrayToString(String[] array) {
        int length = array.length;
        String result = "";

        for (int i = 0; i < length; i ++) {
            if (i != length - 1) {
                result += array[i] + ",";
            } else {
                result += array[i];
            }
        }

        return result;
    }

    public static String parseListToString(List<String> list) {
        int length = list.size();
        String result = "";

        for (int i = 0; i < length; i ++) {
            if (i != length - 1) {
                result += list.get(i) + ",";
            } else {
                result += list.get(i);
            }
        }

        return result;
    }

    public static List<Integer> parseStringToIntList(String str) {
        String[] strings = str.split(",");
        List<String> list = Arrays.asList(strings);
        List<Integer> listNew = new ArrayList();
        for (int j = 0; j < list.size(); j ++) {
            listNew.add(Integer.valueOf(list.get(j).trim()));
        }

        return listNew;
    }

    public static List<String> parseStringToList(String str) {
        String[] strings = str.split(",");
        List<String> list = Arrays.asList(strings);
        List<String> listNew = new ArrayList();
        for (int j = 0; j < list.size(); j ++) {
            listNew.add(list.get(j).trim());
        }

        return listNew;
    }

    public static void main(String[] args) {
        String str = "1,2";
        List list = parseStringToIntList(str);
        System.out.println("");
    }

}
