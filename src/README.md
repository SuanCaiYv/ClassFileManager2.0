# Java web Servlet（一）入门

## 1、servlet简介

servlet在英文中的解释（翻译来自有道词典）：

​				servlet		n. （尤指 Java 语言中在服务器上运行的）小型应用程序；小服务程序

​		在我理解中，servlet就是一个小的代码块，可以对**前端**（浏览器）传来的 **request** 和 **response** 作出反应操作，也就是执行你在servlet类中编写的方法。

​		那么怎么使用呢？

​		主要有两个步骤：

1. 编写一个实现类，实现Servlet接口
2. 将开发好的类部署到web服务器中（目前来说（抛开框架）：也就是在web.xml文件中注册Servlet）

具体怎么做？

不急！

请继续往下看

## 2、做一个简单的servlet实验

### 2.1、导包

首先，想要使用servlet的相关api，我们需要导包，我这使用的是maven进行导包，依赖如下：

```xml
<dependencies>
        <!-- https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
            <scope>provided</scope>
        </dependency>
        <!-- https://mvnrepository.com/artifact/javax.servlet.jsp/javax.servlet.jsp-api -->
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>javax.servlet.jsp-api</artifactId>
            <version>2.3.3</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
```

有新版本的可以考虑使用最新版本。

### 2.2编写servlet实现类

​		真正要实现的Servlet接口已经在HttpServlet类中进行了最基础的一个实现，由于一些api封装的问题，我们不直接实现servlet接口，而是通过继承HttpServlet类，并重写父类方法做到对Servlet接口的实现。

1. 写一个普通类，并实现 HttpServlet接口。

```java
public class HelloServlet extends HttpServlet {
 
}
```

2. 重写Servlet方法（使用idea的话可以通过快捷键 ” ctrl + o “ 快速调出 ）

   父类有许多方法，我们只需要重写两个方法，分别是doGet()和doPost()

   如下：

```java
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
```

​		正如我们所知道的，网络上的**通信请求**方式主要分为两种：**get方法、post方法**。

​		而代码中**重写的两个方法**分别对应了这**两种请求方式**。

3. 我们第一个简单的Servlet程序就简单一点，只使用doGet方法好了，对于doPost方法，我们直接调用doGet方法。

   然后我们在doGet方法中写下如下的代码：

```java
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Hello Servlet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
```

​	到此为止，我们的第一个Servlet程序就成功了一半啦！

### 2.3配置xml文件

ps：只有在web.xml中配置了<servlet>才算成功创建了一个servlet

ps：只有在web.xml中配置了<servlet>才算成功创建了一个servlet

ps：只有在web.xml中配置了<servlet>才算成功创建了一个servlet

重要的话说三遍！

下面是一个空的web.xml，创建完javaweb项目就会有的（如果是用maven的Javaweb模板创建的Javaweb项目，得到的web.xml是旧版的，建议复制下面的文本进行替换）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

</web-app>
```

其中与servlet有关的配置文件为：

```xml
<servlet>
    <servlet-name>Hello</servlet-name>
    <servlet-class>org.example.servlet.HelloServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>Hello</servlet-name>
    <url-pattern>/hello</url-pattern>
</servlet-mapping>
```

分析：

* servlet：创建一个servlet

  1. servlet-name：给servlet取一个名字（随意取）

  2. servlet-class：指定该servlet实现类所在的位置，从项目包开始，逐层精确到类名

* servlet-mapping：<font color=syan>映射路径</font>

  1. servlet-name：想要进行映射路径的servlet的名字，要与上文注册的servlet中的servlet-name名字相符

  2. url-pattern：使用这个servlet对前端发送的信息进行操作的路径

  **注意：**“/”不能漏！！

  **注意：**“/”不能漏！！

  **注意：**“/”不能漏！！

  3. 特点：
     1. 一个Servlet可以映射多个路径
     2. 一个Servlet可以指定通用映射路径
     3. 制定一些前缀或者后缀等

接下来，将servlet配置代码写入web.xml，得到下面的一个web.xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <servlet>
        <servlet-name>Hello</servlet-name>
        <servlet-class>org.example.servlet.HelloServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>Hello</servlet-name>
        <url-pattern>/hello</url-pattern>
    </servlet-mapping>
    
</web-app>
```

这样一来，我们的第一个servlet小程序就写好了

配置好tomcat运行一下试试，在形成的网页的网址栏的最后加入我们上面写上的url-pattern，也就是”/hello“。

这么一来，我们完成了我们的第一个Servlet小程序。