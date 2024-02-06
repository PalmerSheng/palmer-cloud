package com.palmer.framework.common.util.check;

import com.palmer.framework.common.exception.ErrorCode;
import com.palmer.framework.common.exception.ServiceException;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

public class CheckUtil {


    public static void isTrue(Boolean flag, ErrorCode errorCode) {
        if (flag) {
            throw new ServiceException(errorCode);
        }
    }

    public static void notNull(Object flag, ErrorCode errorCode) {
        if (flag == null) {
            throw new ServiceException(errorCode);
        }
    }

    public static void notBlank(String str, ErrorCode errorCode) {
        if (!StringUtils.hasText(str)) {
            throw new ServiceException(errorCode);
        }
    }


    public static void notEmpty(@Nullable Collection<?> collection, ErrorCode errorCode) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new ServiceException(errorCode);
        }
    }

    /**
     * 检查只有一个元素
     *
     * @param collection     集合
     * @param emptyErrorCode 空集合报错
     * @param multiErrorCode 多个元素报错
     */
    public static void onlyOne(@Nullable Collection<?> collection, ErrorCode emptyErrorCode, ErrorCode multiErrorCode) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new ServiceException(emptyErrorCode);
        }
        if (collection.size() > 1) {
            throw new ServiceException(multiErrorCode);
        }
    }


}
