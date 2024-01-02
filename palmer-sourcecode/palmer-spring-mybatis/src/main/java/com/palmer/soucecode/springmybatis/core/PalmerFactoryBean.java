package com.palmer.soucecode.springmybatis.core;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author palmer
 * @date 2023-12-13
 */
public class PalmerFactoryBean implements FactoryBean {

   private  Class clazz;


    SqlSession sqlSession;

    public void setSqlSession(SqlSessionFactory sqlSessionFactory) {
        sqlSessionFactory.getConfiguration().addMapper(clazz);
        this.sqlSession = sqlSessionFactory.openSession();
    }

    public PalmerFactoryBean(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object getObject() throws Exception {
        return sqlSession.getMapper(clazz);
    }

    @Override
    public Class<?> getObjectType() {
        return clazz;
    }

}
