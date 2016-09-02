package com.scaffold.dao;

import com.scaffold.model.Resource;

public interface ResourceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Resource record);

    int insertSelective(Resource record);

    Resource selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);
}