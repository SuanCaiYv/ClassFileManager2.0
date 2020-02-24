package com.classfilemanager.org.pojo;

import org.springframework.stereotype.Component;

/**
 * @author SuanCaiYv
 * @time 2020/2/22 下午4:31
 */
@Component
public class Organization
{
    private String orgUuid;
    private String luncherId;
    private Long lunchTime;
    private String member;
    private String undoIds;
    private String doneIds;
    private String adminIds;

    @Override
    public String toString()
    {
        return "Organization{" +
                "orgUuid='" + orgUuid + '\'' +
                ", luncherId='" + luncherId + '\'' +
                ", lunchTime=" + lunchTime +
                ", member='" + member + '\'' +
                ", undoIds='" + undoIds + '\'' +
                ", doneIds='" + doneIds + '\'' +
                ", adminIds='" + adminIds + '\'' +
                '}';
    }

    public Organization(String orgUuid, String luncherId, Long lunchTime, String member, String undoIds, String doneIds, String adminIds)
    {
        this.orgUuid = orgUuid;
        this.luncherId = luncherId;
        this.lunchTime = lunchTime;
        this.member = member;
        this.undoIds = undoIds;
        this.doneIds = doneIds;
        this.adminIds = adminIds;
    }

    public String getAdminIds()
    {
        return adminIds;
    }

    public void setAdminIds(String adminIds)
    {
        this.adminIds = adminIds;
    }

    public String getDoneIds()
    {
        return doneIds;
    }

    public void setDoneIds(String doneIds)
    {
        this.doneIds = doneIds;
    }

    public String getUndoIds()
    {
        return undoIds;
    }

    public void setUndoIds(String undoIds)
    {
        this.undoIds = undoIds;
    }

    public String getMember()
    {
        return member;
    }

    public void setMember(String member)
    {
        this.member = member;
    }

    public Long getLunchTime()
    {
        return lunchTime;
    }

    public void setLunchTime(Long lunchTime)
    {
        this.lunchTime = lunchTime;
    }

    public String getLuncherId()
    {
        return luncherId;
    }

    public void setLuncherId(String luncherId)
    {
        this.luncherId = luncherId;
    }

    public Organization()
    {
    }

    public String getOrgUuid()
    {
        return orgUuid;
    }

    public void setOrgUuid(String orgUuid)
    {
        this.orgUuid = orgUuid;
    }
}
