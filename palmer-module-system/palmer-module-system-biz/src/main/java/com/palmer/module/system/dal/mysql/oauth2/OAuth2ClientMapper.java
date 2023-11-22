package com.palmer.module.system.dal.mysql.oauth2;


import com.palmer.framework.common.pojo.PageResult;
import com.palmer.framework.mybatis.mybatis.core.mapper.BaseMapperX;
import com.palmer.framework.mybatis.mybatis.core.query.LambdaQueryWrapperX;
import com.palmer.module.system.controller.admin.oauth2.client.OAuth2ClientPageReqVO;
import com.palmer.module.system.dal.dataobject.oauth2.OAuth2ClientDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * OAuth2 客户端 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface OAuth2ClientMapper extends BaseMapperX<OAuth2ClientDO> {

    default PageResult<OAuth2ClientDO> selectPage(OAuth2ClientPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OAuth2ClientDO>()
                .likeIfPresent(OAuth2ClientDO::getName, reqVO.getName())
                .eqIfPresent(OAuth2ClientDO::getStatus, reqVO.getStatus())
                .orderByDesc(OAuth2ClientDO::getId));
    }

    default OAuth2ClientDO selectByClientId(String clientId) {
        return selectOne(OAuth2ClientDO::getClientId, clientId);
    }

}
