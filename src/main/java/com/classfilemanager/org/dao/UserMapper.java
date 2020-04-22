package com.classfilemanager.org.dao;

import com.classfilemanager.org.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author SuanCaiYv
 * @time 2020/2/22 下午4:35
 */
@Mapper
@Component
public interface UserMapper
{
    /**
     * 插入数据
     * @param user NA
     */
    void insert(User user);

    /**
     * 获取一条数据
     * @param id 用户ID
     * @return NA
     */
    User selectOne(String id);

    /**
     * 选取同一班级的所有人
     * @param user NA
     * @return NA
     */
    List<User> selectSameClassroom(User user);

    /**
     * 更新一条数据
     * @param user NA
     */
    void update(User user);

    /**
     * 删除一条数据
     * @param id 用户id
     */
    void delete(String id);

    /**
     * 删除一条数据
     * @param user 用户
     */
    void delete(User user);
}
