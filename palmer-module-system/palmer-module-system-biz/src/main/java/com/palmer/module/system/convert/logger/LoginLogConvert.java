package com.palmer.module.system.convert.logger;

import com.palmer.module.system.api.logger.dto.LoginLogCreateReqDTO;
import com.palmer.module.system.dal.dataobject.logger.LoginLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoginLogConvert {

    LoginLogConvert INSTANCE = Mappers.getMapper(LoginLogConvert.class);


    LoginLogDO convert(LoginLogCreateReqDTO reqDTO);
}
