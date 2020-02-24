package com.classfilemanager.org.pojo;

import org.springframework.stereotype.Component;

/**
 * @author SuanCaiYv
 * @time 2020/2/22 下午4:26
 */
@Component
public class User
{
    private String id;
    private String name;
    private String password;
    private String nickName;
    private String email;
    private String qq;
    private String department;
    private Integer grade;
    private String organization;

    @Override
    public String toString()
    {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", qq='" + qq + '\'' +
                ", department='" + department + '\'' +
                ", grade=" + grade +
                ", organization='" + organization + '\'' +
                '}';
    }

    public String getOrganization()
    {
        return organization;
    }

    public void setOrganization(String organization)
    {
        this.organization = organization;
    }

    public Integer getGrade()
    {
        return grade;
    }

    public void setGrade(Integer grade)
    {
        this.grade = grade;
    }

    public String getDepartment()
    {
        return department;
    }

    public void setDepartment(String department)
    {
        this.department = department;
    }

    public String getQq()
    {
        return qq;
    }

    public void setQq(String qq)
    {
        this.qq = qq;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public User(String id, String name, String password, String nickName, String email, String qq, String department, Integer grade, String organization)
    {
        this.id = id;
        this.name = name;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.qq = qq;
        this.department = department;
        this.grade = grade;
        this.organization = organization;
    }

    public User()
    {
    }
}
