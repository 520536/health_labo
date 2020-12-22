## app大纲

health_common(pojo)->health_backend(controller)/health_mobile(controller)->health_interface(service)->health_service_provide(serviceimpl->dao->dao.xml(sql))

controller

````java
package com.labo.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.labo.constant.MessageConstant;
import com.labo.entity.PageResult;
import com.labo.entity.QueryPageBean;
import com.labo.entity.Result;
import com.labo.pojo.CheckItem;
import com.labo.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;    
@RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){}
````



service

````java
package com.labo.service;
import com.labo.entity.PageResult;
import com.labo.entity.QueryPageBean;
import com.labo.pojo.CheckItem;
import java.util.List;

public interface CheckItemService {
    public void add(CheckItem checkItem);
````



impl

````java
package com.labo.service;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.labo.constant.MessageConstant;
import com.labo.dao.CheckItemDao;
import com.labo.entity.PageResult;
import com.labo.entity.QueryPageBean;
import com.labo.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service(interfaceClass = CheckItemService.class)//实现接口
@Transactional//事务
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;//新增检查项
    public void add(CheckItem checkItem) {}
````

dao

````java
package com.labo.dao;
import com.github.pagehelper.Page;
import com.labo.pojo.CheckItem;

public interface CheckItemDao{public void add(CheckItem checkItem);}
````

dao.xml

````xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.labo.dao.CheckItemDao">
    <insert id="add" parameterType="com.labo.pojo.CheckItem">
        insert into t_checkitem(code,name,sex,age,price,type,remark,attention)
                      values
        (#{code},#{name},#{sex},#{age},#{price},#{type},#{remark},#{attention})
    </insert>
 </mapper>   
````



## health_common

### constant (常量)

### entity

### pojo

### utils(工具类)



## health_backend(admin)

### CheckItemController

````java
package com.labo.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.labo.constant.MessageConstant;
import com.labo.entity.PageResult;
import com.labo.entity.QueryPageBean;
import com.labo.entity.Result;
import com.labo.pojo.CheckItem;
import com.labo.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * 检查项管理
 */

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;
    //新增
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){}
    //分页查询
     @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){}
     //根据id删除检查项
    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    public Result delete(Integer id){}
        //根据id查询检查项
    @RequestMapping("/findById")
    public Result findById(Integer id){}
        //编辑
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem){}
        //查询所有检查项
    @RequestMapping("/findAll")
    public Result findAll(){}
    
    //根据检查组id查询关联的检查项id
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer checkgroupId){}
}   
````



### OrderSettingController



### ReportController

````java
package com.labo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.labo.constant.MessageConstant;
import com.labo.entity.Result;
import com.labo.service.MemberService;
import com.labo.service.ReportService;
import com.labo.service.SetmealService;
import com.labo.utils.DateUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * 统计报表
 */

@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    private MemberService memberService;
    @Reference
    private SetmealService setmealService;
    //获取会员数量统计数据
    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){}
    //获取套餐占比统计数据
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport(){}
    
    @Reference
    private ReportService reportService;

    //获取运营统计数据
    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData(){}
    
    //导出运营数据到Excel并提供客户端下载
    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response){}
    //导出运营数据到PDF文件并提供客户端下载
    @RequestMapping("/exportBusinessReport4PDF")
    public Result exportBusinessReport4PDF(HttpServletRequest request, HttpServletResponse response){}
````



### SetmealController

````java
package com.labo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.labo.constant.MessageConstant;
import com.labo.constant.RedisConstant;
import com.labo.entity.PageResult;
import com.labo.entity.QueryPageBean;
import com.labo.entity.Result;
import com.labo.pojo.Setmeal;
import com.labo.service.SetmealService;
import com.labo.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

/**
 * 体检套餐管理
 */

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;
    @Autowired
    private JedisPool jedisPool;

    //图片上传
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){}
     //新增体检套餐
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){}
      //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){}
````



### UserController

````java
package com.labo.controller;

import com.labo.constant.MessageConstant;
import com.labo.entity.Result;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/user")
public class UserController {
    //获取当前登录（认证）用户的用户名
    @RequestMapping("/getLoginUsername")
    public Result getLoginUsername(){
````



### CheckGroupController

````java
package com.labo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.labo.constant.MessageConstant;
import com.labo.entity.PageResult;
import com.labo.entity.QueryPageBean;
import com.labo.entity.Result;
import com.labo.pojo.CheckGroup;
import com.labo.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 检查组管理
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;
    //新增
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){}
        //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean pageBean){}
        //根据id查询检查组
    @RequestMapping("/findById")
    public Result findById(Integer id){}
        //编辑
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){}
        //查询所有检查组
    @RequestMapping("/findAll")
    public Result findAll(){}
````

### webapp admin页面



## health_mobile(user)

### MemberController

````java
package com.labo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.labo.constant.MessageConstant;
import com.labo.constant.RedisMessageConstant;
import com.labo.entity.Result;
import com.labo.pojo.Member;
import com.labo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * 会员管理
 */
@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private MemberService memberService;

    //手机验证码登录
    @RequestMapping("/login")
    public Result login(HttpServletResponse response,@RequestBody Map map){
````



### OrderController

````java
package com.labo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.labo.constant.MessageConstant;
import com.labo.constant.RedisMessageConstant;
import com.labo.entity.Result;
import com.labo.pojo.Order;
import com.labo.service.OrderService;
import com.labo.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * 体检预约
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;
    //体检预约
    @RequestMapping("/submitOrder")
    public Result submitOrder(@RequestBody Map map){}
    //根据预约id查询预约信息
    @RequestMapping("/findById")
    public Result findById(Integer id){}
}
````



### SetmealController

````java
package com.labo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.labo.constant.MessageConstant;
import com.labo.entity.Result;
import com.labo.pojo.Setmeal;
import com.labo.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 体检套餐
 */

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;
    //获取所有套餐信息
    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){}
    //根据套餐id查询套餐详情，包含（套餐基本信息、套餐关联的检查组、检查组关联的检查项）
    @RequestMapping("/findById")
    public Result findById(Integer id){}
}
````



### ValidateCodeController

````java
package com.labo.controller;

import com.labo.constant.MessageConstant;
import com.labo.constant.RedisMessageConstant;
import com.labo.entity.Result;
import com.labo.utils.SMSUtils;
import com.labo.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validatecode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    //体检预约发送验证码
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){}
        //手机号快速登录发送验证码
    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){}
````





### webapp 用户页面



## health_interface

### service 



## health_service_provide

### 接口实现-

### dao文件-

### dao.xml



## health_jobs(quartz)





## 配置

### health_jobs(quartz)

log4j.properties

spring-jobs.xml

spring-redis.xml

### health_backend 配置

spring-security.xml

spring-redis.xml(jedis)

spring-mvc.xml

1. zookeeper dubbo
2. bean
3. import

jasperreports

log4j.properties

### health_service_provider

spring-dao.xml

spring-redis.xml

spring-service.xml

spring-tx.xml

SqlMapConfig.xml