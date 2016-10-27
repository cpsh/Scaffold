package com.scaffold.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.scaffold.common.exception.BizException;
import com.scaffold.dao.RoleMapper;
import com.scaffold.dao.RoleResourceMapper;
import com.scaffold.dao.UserRoleMapper;
import com.scaffold.model.Role;
import com.scaffold.model.RoleResource;
import com.scaffold.service.RoleService;
import com.scaffold.util.PageInfo;
import com.scaffold.util.Tree;

@Service
public class RoleServiceImpl implements RoleService {

    private static Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleResourceMapper roleResourceMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

//    @Override
    public void findDataGrid(PageInfo pageInfo) {
        pageInfo.setRows(roleMapper.findRolePageCondition(pageInfo));
        pageInfo.setTotal(roleMapper.findRolePageCount(pageInfo));
    }
    
//    @Override
    public List<Role> findRoleAll(){
    	 List<Role> roles = roleMapper.findRoleAll();
    	 return roles;
    	
    }

//    @Override
    public List<Tree> findTree() {
        List<Tree> trees = Lists.newArrayList();
        List<Role> roles = roleMapper.findRoleAll();
        for (Role role : roles) {
            Tree tree = new Tree();
            tree.setId(role.getId());
            tree.setText(role.getName());

            trees.add(tree);
        }
        return trees;
    }

//    @Override
    public void addRole(Role role) {
        int insert = roleMapper.insert(role);
        if (insert != 1) {
            LOGGER.warn("插入失败，参数：{}", role.toString());
            throw new BizException("插入失败");
        }
    }

//    @Override
    public void deleteRoleById(Long id) {
        int update = roleMapper.deleteRoleById(id);
        if (update != 1) {
            LOGGER.warn("删除失败，id：{}", id);
            throw new BizException("删除失败");
        }
    }

//    @Override
    public Role findRoleById(Long id) {
        return roleMapper.findRoleById(id);
    }

//    @Override
    public void updateRole(Role role) {
        int update = roleMapper.updateRole(role);
        if (update != 1) {
            LOGGER.warn("更新失败，参数：{}", role.toString());
            throw new BizException("更新失败");
        }
    }

//    @Override
    public List<Long> findResourceIdListByRoleId(Long id) {
        return roleMapper.findResourceIdListByRoleId(id);
    }

//    @Override
    public void updateRoleResource(Long id, String resourceIds) {
        // 先删除后添加,有点爆力
        List<Long> roleResourceIdList = roleMapper.findRoleResourceIdListByRoleId(id);
        if (roleResourceIdList != null && (!roleResourceIdList.isEmpty())) {
            for (Long roleResourceId : roleResourceIdList) {
                roleResourceMapper.deleteById(roleResourceId);
            }
        }
        String[] resources = resourceIds.split(",");
        RoleResource roleResource = new RoleResource();
        for (String string : resources) {
            roleResource.setRoleId(id);
            roleResource.setResourceId(Long.parseLong(string));
            roleResourceMapper.insert(roleResource);
        }
    }

//    @Override
    public List<Long> findRoleIdListByUserId(Long userId) {
        return userRoleMapper.findRoleIdListByUserId(userId);
    }

//    @Override
    public List<Map<Long, String>> findRoleResourceListByRoleId(Long roleId) {
        return roleMapper.findRoleResourceListByRoleId(roleId);
    }

	@Override
	public void errorTransaction(Role role) {
		throw new BizException("手动抛出一条异常消息，测试事务");
		
	}

}
