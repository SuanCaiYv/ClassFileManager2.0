package com.classfilemanager.org.dao;

import com.classfilemanager.org.pojo.Organization;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author SuanCaiYv
 * @time 2020/2/22 下午4:35
 */
@Mapper
@Component
public interface OrganizationMapper
{
    /**
     * 增加一条记录
     * @param org NA
     */
    void insert(Organization org);

    /**
     * 删除一条记录
     * @param org NA
     */
    void delete(OrganizationMapper org);

    /**
     * 删除一条记录
     * @param orgUuid NA
     */
    void delete(String orgUuid);

    /**
     * 更新一条记录
     * @param organization NA
     */
    void update(Organization organization);

    /**
     * 获取一条记录
     * @param orgUuid NA
     * @return NA
     */
    Organization selectOne(String orgUuid);
}
