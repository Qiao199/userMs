package com.user.userms.dao;

import com.user.userms.dao.po.User;

import java.util.List;

/**
 * 操作数据库用户表的DAO接口
 */
public interface UserDao {

    /**
     * 查询所有用户
     *
     * @return
     */
    public List<User> selectAll();

    /**
     * 插入一个用户到数据库中
     *
     * @param user 要插入的用户
     * @return 返回插入的数量，为0时插入失败
     */
    public long insert(User user);

    /**
     * 从数据库中删除一个用户
     * @param user
     */
    public void delete(User user);

}
