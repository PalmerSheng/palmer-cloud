package com.palmer.module.system.convert.dict;

import com.palmer.framework.common.pojo.PageResult;
import com.palmer.module.system.controller.admin.dict.vo.type.DictTypeCreateReqVO;
import com.palmer.module.system.controller.admin.dict.vo.type.DictTypeRespVO;
import com.palmer.module.system.controller.admin.dict.vo.type.DictTypeSimpleRespVO;
import com.palmer.module.system.controller.admin.dict.vo.type.DictTypeUpdateReqVO;
import com.palmer.module.system.dal.dataobject.dict.DictTypeDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DictTypeConvert {

    DictTypeConvert INSTANCE = Mappers.getMapper(DictTypeConvert.class);


    DictTypeDO convert(DictTypeCreateReqVO bean);


    DictTypeDO convert(DictTypeUpdateReqVO reqVO);

    PageResult<DictTypeRespVO> convertPage(PageResult<DictTypeDO> dictTypePage);

    DictTypeRespVO convert(DictTypeDO dictType);

    List<DictTypeSimpleRespVO> convertList(List<DictTypeDO> list);
}
