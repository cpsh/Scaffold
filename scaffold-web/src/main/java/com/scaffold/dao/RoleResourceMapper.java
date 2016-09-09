package com.scaffold.dao;

import java.util.List;

import com.scaffold.model.RoleResource;

public interface RoleResourceMapper {
    /**
     * 添加角色资源关联
     *
     * @param roleResource
     * @return
     */
    int insert(RoleResource roleResource);

    /**
     * 根据角色id查询角色资源关联列表
     *
     * @param id
     * @return
     */
    List<RoleResource> findRoleResourceIdListByRoleId(Long id);

    /**
     * 删除角色资源关联关系
     *
     * @param roleResourceId
     * @return
     */
    int deleteById(Long roleResourceId);
}