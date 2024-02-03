package com.palmer.framework.common.util.object;


import com.palmer.framework.common.pojo.PageParam;

/**
 * {@link PageParam} 工具类
 *
 * @author 芋道源码
 */
public class PageUtils {

    public static int getStart(PageParam pageParam) {
        return (pageParam.getPageNo() - 1) * pageParam.getPageSize();
    }

}
