package com.classfilemanager.org.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author SuanCaiYv
 * @time 2020/3/1 下午11:21
 */
@Mapper
@Component
public interface VerCodeMapper
{
    void insert(String id, String code);
    void delete(String id);
    String select(String id);
}
