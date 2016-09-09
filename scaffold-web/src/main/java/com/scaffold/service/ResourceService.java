package com.scaffold.service;

import java.util.List;

import com.scaffold.model.Resource;
import com.scaffold.model.User;
import com.scaffold.util.PageInfo;
import com.scaffold.util.Tree;

/**
 * @description：资源管理
 * @author：sanboot
 * @date：2015/10/1 14:51
 */
public interface ResourceService {

    /**
     * 根据用户查询树形菜单列表
     *
     * @param currentUser
     * @return
     */
    List<Tree> findTree(User currentUser);

    /**
     * 查询所有资源
     *
     * @return
     */
    List<Resource> findResourceAll();
    
    
    /**
     * 查询资源列表
     *
     * @param pageInfo
     */
    void findResourceDataGrid(PageInfo pageInfo);

    /**
     * 添加资源
     *
     * @param resource
     */
    void addResource(Resource resource);

    /**
     * 查询二级数
     *
     * @return
     */
    List<Tree> findAllTree();

    /**
     * 查询三级数
     *
     * @return
     */
    List<Tree> findAllTrees();

    /**
     * 更新资源
     *
     * @param resource
     */
    void updateResource(Resource resource);

    /**
     * 根据id查询资源
     *
     * @param id
     * @return
     */
    Resource findResourceById(Long id);

    /**
     * 根据id删除资源
     *
     * @param id
     */
    void deleteResourceById(Long id);

}
