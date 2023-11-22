package com.palmer.module.system.convert.auth;

import com.palmer.framework.common.pojo.PageResult;
import com.palmer.module.system.controller.admin.oauth2.client.OAuth2ClientCreateReqVO;
import com.palmer.module.system.controller.admin.oauth2.client.OAuth2ClientRespVO;
import com.palmer.module.system.controller.admin.oauth2.client.OAuth2ClientUpdateReqVO;
import com.palmer.module.system.dal.dataobject.oauth2.OAuth2ClientDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * OAuth2 客户端 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface OAuth2ClientConvert {

    OAuth2ClientConvert INSTANCE = Mappers.getMapper(OAuth2ClientConvert.class);

    OAuth2ClientDO convert(OAuth2ClientCreateReqVO bean);

    OAuth2ClientDO convert(OAuth2ClientUpdateReqVO bean);

    OAuth2ClientRespVO convert(OAuth2ClientDO bean);

    List<OAuth2ClientRespVO> convertList(List<OAuth2ClientDO> list);

    PageResult<OAuth2ClientRespVO> convertPage(PageResult<OAuth2ClientDO> page);

}
