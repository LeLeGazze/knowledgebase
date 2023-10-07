package com.castle.fortress.admin.core.interceptor;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler;
import com.castle.fortress.admin.core.entity.BaseEntity;
import com.castle.fortress.admin.core.entity.DataAuthBaseEntity;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.utils.CommonUtil;
import com.castle.fortress.common.utils.ReflectUtil;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * mybatis 保存拦截器
 * 当对象继承BaseEntity自动插入创建人、创建时间、创建人部门(未指定时为当前用户的所在部门)、创建人职位(未指定时为当前用户的所在职位)、是否删除为未删除
 * 对象未继承时根据对象字段判断 如果包含创建人、创建时间 自动插入
 * @author Castle
 */
@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class}
)})
public class SaveInterceptor extends AbstractSqlParserHandler implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        this.sqlParser(metaObject);

        // 先判断是不是INSERT操作
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (!SqlCommandType.INSERT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        }
        //id为执行的mapper方法的全路径名
        String id = mappedStatement.getId();
        ParameterMap parameterMap = mappedStatement.getParameterMap();
        BoundSql boundSql = statementHandler.getBoundSql();
        //获取原始sql
        String sql = boundSql.getSql();
        List<String> sqlParams=new ArrayList<>();
        List<ParameterMapping> parameterMappingsList = boundSql.getParameterMappings();
        Object parameterObject =boundSql.getParameterObject();
        Class paramClass = parameterMap.getType();
        SysUser user= WebUtil.currentUser();
        if(paramClass!=null){
            //判断对象是否继承了父类
            if(DataAuthBaseEntity.class.isAssignableFrom(paramClass)) {
                if (user != null) {
                    if(!exist(parameterMappingsList,"createUser")){
                        //重新设置mapper接口的参数或者对sql进行改造
                        ParameterMapping createUser = new ParameterMapping.Builder(mappedStatement.getConfiguration(), "createUser", Long.class).build();
                        parameterMappingsList.add(createUser);
                        ReflectUtil.setMethod(paramClass, parameterObject, "setCreateUser", new Class[]{Long.class}, new Object[]{user.getId()});
                        sqlParams.add("create_user");
                    }

                    if (user.getDeptId() != null && !exist(parameterMappingsList,"createDept")) {

                        ParameterMapping createDept = new ParameterMapping.Builder(mappedStatement.getConfiguration(), "createDept", Long.class).build();
                        parameterMappingsList.add(createDept);
                        ReflectUtil.setMethod(paramClass, parameterObject, "setCreateDept", new Class[]{Long.class}, new Object[]{user.getDeptId()});
                        sqlParams.add("create_dept");
                    }

                    if (user.getPostId() != null && !exist(parameterMappingsList,"createPost")) {
                        ParameterMapping createPost = new ParameterMapping.Builder(mappedStatement.getConfiguration(), "createPost", Long.class).build();
                        parameterMappingsList.add(createPost);
                        ReflectUtil.setMethod(paramClass, parameterObject, "setCreatePost", new Class[]{Long.class}, new Object[]{user.getPostId()});
                        sqlParams.add("create_post");
                    }
                }
                if(!exist(parameterMappingsList,"isDeleted")){
                    ParameterMapping isDeleted = new ParameterMapping.Builder(mappedStatement.getConfiguration(), "isDeleted", Integer.class).build();
                    parameterMappingsList.add(isDeleted);
                    ReflectUtil.setMethod(paramClass, parameterObject, "setIsDeleted", new Class[]{Integer.class}, new Object[]{YesNoEnum.NO.getCode()});
                    sqlParams.add("is_deleted");
                }

                if(!exist(parameterMappingsList,"createTime")){
                    ParameterMapping createTime = new ParameterMapping.Builder(mappedStatement.getConfiguration(), "createTime", Date.class).build();
                    parameterMappingsList.add(createTime);
                    ReflectUtil.setMethod(paramClass, parameterObject, "setCreateTime", new Class[]{Date.class}, new Object[]{new Date()});
                    sqlParams.add("create_time");
                }
            }else if(BaseEntity.class.isAssignableFrom(paramClass)){
                if (user != null && !exist(parameterMappingsList,"createUser")) {
                    //重新设置mapper接口的参数或者对sql进行改造
                    ParameterMapping createUser = new ParameterMapping.Builder(mappedStatement.getConfiguration(), "createUser", Long.class).build();
                    parameterMappingsList.add(createUser);
                    ReflectUtil.setMethod(paramClass, parameterObject, "setCreateUser", new Class[]{Long.class}, new Object[]{user.getId()});
                    sqlParams.add("create_user");
                }
                if(!exist(parameterMappingsList,"isDeleted")){
                    ParameterMapping isDeleted = new ParameterMapping.Builder(mappedStatement.getConfiguration(), "isDeleted", Integer.class).build();
                    parameterMappingsList.add(isDeleted);
                    ReflectUtil.setMethod(paramClass, parameterObject, "setIsDeleted", new Class[]{Integer.class}, new Object[]{YesNoEnum.NO.getCode()});
                    sqlParams.add("is_deleted");
                }

                if(!exist(parameterMappingsList,"createTime")){
                    ParameterMapping createTime = new ParameterMapping.Builder(mappedStatement.getConfiguration(), "createTime", Date.class).build();
                    parameterMappingsList.add(createTime);
                    ReflectUtil.setMethod(paramClass, parameterObject, "setCreateTime", new Class[]{Date.class}, new Object[]{new Date()});
                    sqlParams.add("create_time");
                }
                //未继承父类
            }else{
                //如果有创建者 字段
                try {
                    Field field = paramClass.getDeclaredField("createUser");
                    if(user!=null && !exist(parameterMappingsList,"createUser")){
                        ParameterMapping createUser=new ParameterMapping.Builder(mappedStatement.getConfiguration(),"createUser",Long.class).build() ;
                        parameterMappingsList.add(createUser);
                        ReflectUtil.setMethod(paramClass,parameterObject,"setCreateUser", new Class[]{Long.class}, new Object[]{user.getId()});
                        sqlParams.add("create_user");
                    }
                } catch (NoSuchFieldException e) {
                }

                //如果有逻辑删 字段
                try {
                    Field field = paramClass.getDeclaredField("isDeleted");
                    if(!exist(parameterMappingsList,"isDeleted")){
                        ParameterMapping isDeleted=new ParameterMapping.Builder(mappedStatement.getConfiguration(),"isDeleted",Integer.class).build() ;
                        parameterMappingsList.add(isDeleted);
                        ReflectUtil.setMethod(paramClass,parameterObject,"setIsDeleted", new Class[]{Integer.class}, new Object[]{YesNoEnum.NO.getCode()});
                        sqlParams.add("is_deleted");
                    }
                } catch (NoSuchFieldException e) {
                }

                //如果有创建时间 字段
                try {
                    Field field = paramClass.getDeclaredField("createTime");
                    if(!exist(parameterMappingsList,"createTime")){
                        ParameterMapping createTime=new ParameterMapping.Builder(mappedStatement.getConfiguration(),"createTime", Date.class).build() ;
                        parameterMappingsList.add(createTime);
                        ReflectUtil.setMethod(paramClass,parameterObject,"setCreateTime", new Class[]{Date.class}, new Object[]{new Date()});
                        sqlParams.add("create_time");
                    }
                } catch (NoSuchFieldException e) {
                }
            }
        }
        if(!sqlParams.isEmpty()){
            sql = insertJointSqlParams(sql,sqlParams);
        }
        metaObject.setValue("delegate.boundSql.sql", sql);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return target instanceof StatementHandler ? Plugin.wrap(target, this) : target;
    }

    @Override
    public void setProperties(Properties properties) {
    }

    /**
     * insert方法拼接sql参数
     * @param sql
     * @param paramsName
     * @return
     */
    private String insertJointSqlParams(String sql,List<String> paramsName){
        StringBuilder sbParams=new StringBuilder();
        StringBuilder sbMk=new StringBuilder();
        for(String s:paramsName){
            sbParams.append(","+s);
            sbMk.append(",?");
        }
        Integer index = sql.indexOf(")  VALUES  (");
        Integer end =sql.lastIndexOf(")");
        sql= sql.substring(0,index)+  sbParams.toString() + sql.substring(index,end)+ sbMk.toString()+" )";
        return sql;
    }

    private Boolean exist(List<ParameterMapping> parameterMappingsList,String paramName){
        if(CommonUtil.verifyParamNull(parameterMappingsList,paramName)){
            return false;
        }
        boolean flag = false;
        for(ParameterMapping m:parameterMappingsList){
            if(m.getMode().equals(ParameterMode.IN) && m.getProperty().equals(paramName)){
                flag = true;
                return flag;
            }
        }
        return flag;
    }


}
