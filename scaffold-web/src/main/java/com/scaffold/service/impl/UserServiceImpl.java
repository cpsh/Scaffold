package com.scaffold.service.impl;

import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scaffold.dao.UserMapper;
import com.scaffold.dao.UserRoleMapper;
import com.scaffold.model.User;
import com.scaffold.model.UserRole;
import com.scaffold.service.UserService;
import com.scaffold.util.PageInfo;
import com.scaffold.util.UserVo;

@Service
public class UserServiceImpl implements UserService {

    private static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

//    @Override
    public User findUserByLoginName(String username) {
        return userMapper.findUserByLoginName(username);
    }

//    @Override
    public User findUserById(Long id) {
        return userMapper.findUserById(id);
    }

//    @Override
    public void findDataGrid(PageInfo pageInfo) {
        pageInfo.setRows(userMapper.findUserPageCondition(pageInfo));
        pageInfo.setTotal(userMapper.findUserPageCount(pageInfo));
    }

//    @Override
    public void addUser(UserVo userVo) {
        User user = new User();
        try {
            PropertyUtils.copyProperties(user, userVo);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        userMapper.insert(user);

        Long id = user.getId();
        String[] roles = userVo.getRoleIds().split(",");
        UserRole userRole = new UserRole();

        for (String string : roles) {
            userRole.setUserId(id);
            userRole.setRoleId(Long.valueOf(string));
            userRoleMapper.insert(userRole);
        }
    }

//    @Override
    public void updateUserPwdById(Long userId, String pwd) {
        userMapper.updateUserPwdById(userId, pwd);
    }

//    @Override
    public UserVo findUserVoById(Long id) {
        return userMapper.findUserVoById(id);
    }

//    @Override
    public void updateUser(UserVo userVo) {
        User user = new User();
        try {
            PropertyUtils.copyProperties(user, userVo);
        } catch (Exception e) {
            LOGGER.error("类转换异常：{}", e);
            throw new RuntimeException("类型转换异常：{}", e);
        }
        userMapper.updateUser(user);
        Long id = userVo.getId();
        List<UserRole> userRoles = userRoleMapper.findUserRoleByUserId(id);
        if (userRoles != null && (!userRoles.isEmpty())) {
            for (UserRole userRole : userRoles) {
                userRoleMapper.deleteById(userRole.getId());
            }
        }

        String[] roles = userVo.getRoleIds().split(",");
        UserRole userRole = new UserRole();
        for (String string : roles) {
            userRole.setUserId(id);
            userRole.setRoleId(Long.valueOf(string));
            userRoleMapper.insert(userRole);
        }

    }

//    @Override
    public void deleteUserById(Long id) {
        userMapper.deleteById(id);
        List<UserRole> userRoles = userRoleMapper.findUserRoleByUserId(id);
        if (userRoles != null && (!userRoles.isEmpty())) {
            for (UserRole userRole : userRoles) {
                userRoleMapper.deleteById(userRole.getId());
            }
        }
    }

}
