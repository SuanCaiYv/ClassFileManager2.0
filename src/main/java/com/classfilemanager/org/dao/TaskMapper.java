package com.classfilemanager.org.dao;

import com.classfilemanager.org.pojo.Task;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author SuanCaiYv
 * @time 2020/2/22 下午4:36
 */
@Mapper
@Component
public interface TaskMapper
{
    /**
     * 增加一条数据
     * @param task NA
     */
    void insert(Task task);

    /**
     * 删除一条数据
     * @param task NA
     */
    void delete(Task task);

    /**
     * 删除一条数据
     * @param taskUuid NA
     */
    void delete(String taskUuid);

    /**
     * 更新一条数据
     * @param task NA
     */
    void update(Task task);

    /**
     * 选取一条数据
     * @param taskUuid NA
     * @return NA
     */
    Task selectOne(String taskUuid);

    /**
     * 选取发布时间早于给定参数的任务
     * @param lunchTime 发布时间
     * @return NA
     */
    List<Task> selectToDelete(long lunchTime);

    /**
     * 选取某一班委发布的全部任务
     * @param luncherId NA
     * @return NA
     */
    List<Task> selectById(String luncherId);
}
