package com.classfilemanager.org.pojo;

import org.springframework.stereotype.Component;

/**
 * @author SuanCaiYv
 * @time 2020/2/22 下午4:26
 */
@Component
public class User
{
    private String id = "";
    private String name = "";
    private String password = "";
    private String nickName = "";
    private String email = "";
    private String qq = "";
    private String undoTask = "";
    private String doneTask = "";
    private String department = "";
    private Integer grade = 1970;
    private Integer classroom = 0;
    private Integer isCommittee = 0;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getQq()
    {
        return qq;
    }

    public void setQq(String qq)
    {
        this.qq = qq;
    }

    public String getUndoTask()
    {
        return undoTask;
    }

    public void setUndoTask(String undoTask)
    {
        this.undoTask = undoTask;
    }

    public String getDoneTask()
    {
        return doneTask;
    }

    public void setDoneTask(String doneTask)
    {
        this.doneTask = doneTask;
    }

    public String getDepartment()
    {
        return department;
    }

    public void setDepartment(String department)
    {
        this.department = department;
    }

    public Integer getGrade()
    {
        return grade;
    }

    public void setGrade(Integer grade)
    {
        this.grade = grade;
    }

    public Integer getClassroom()
    {
        return classroom;
    }

    public void setClassroom(Integer classroom)
    {
        this.classroom = classroom;
    }

    public Integer getIsCommittee()
    {
        return isCommittee;
    }

    public void setIsCommittee(Integer isCommittee)
    {
        this.isCommittee = isCommittee;
    }

    public User()
    {
    }
}
