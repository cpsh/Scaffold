package com.scaffold.dao;

import java.util.List;

import com.scaffold.model.UserRole;

public interface UserRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

    List<UserRole> findUserRoleByUserId(Long id);

    void deleteById(Long id);

    List<Long> findRoleIdListByUserId(Long id);
}