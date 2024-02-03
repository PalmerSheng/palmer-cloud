package com.palmer.module.system.dal.mysql.permission;


import com.palmer.framework.mybatis.mybatis.core.mapper.BaseMapperX;
import com.palmer.module.system.dal.dataobject.permission.RoleDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper extends BaseMapperX<RoleDO> {

//    default PageResult<RoleDO> selectPage(RolePageReqVO reqVO) {
//        return selectPage(reqVO, new LambdaQueryWrapperX<RoleDO>()
//                .likeIfPresent(RoleDO::getName, reqVO.getName())
//                .likeIfPresent(RoleDO::getCode, reqVO.getCode())
//                .eqIfPresent(RoleDO::getStatus, reqVO.getStatus())
//                .betweenIfPresent(BaseDO::getCreateTime, reqVO.getCreateTime())
//                .orderByDesc(RoleDO::getId));
//    }
//
//    default List<RoleDO> selectList(RoleExportReqVO reqVO) {
//        return selectList(new LambdaQueryWrapperX<RoleDO>()
//                .likeIfPresent(RoleDO::getName, reqVO.getName())
//                .likeIfPresent(RoleDO::getCode, reqVO.getCode())
//                .eqIfPresent(RoleDO::getStatus, reqVO.getStatus())
//                .betweenIfPresent(BaseDO::getCreateTime, reqVO.getCreateTime()));
//    }
//
//    default RoleDO selectByName(String name) {
//        return selectOne(RoleDO::getName, name);
//    }
//
//    default RoleDO selectByCode(String code) {
//        return selectOne(RoleDO::getCode, code);
//    }
//
//    default List<RoleDO> selectListByStatus(@Nullable Collection<Integer> statuses) {
//        return selectList(RoleDO::getStatus, statuses);
//    }

}
