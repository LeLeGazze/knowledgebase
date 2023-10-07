package com.castle.fortress.admin.core.interceptor;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler;
import com.castle.fortress.admin.core.entity.BaseEntity;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.utils.CommonUtil;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * mybatis 修改拦截器
 * 当对象继承BaseEntity修改人、修改时间
 * 未继承时如果对象字段中有 修改人、修改时间则自动注入
 * @author Castle
 */
@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class}
)})
public class UpdateInterceptor extends AbstractSqlParserHandler implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        this.sqlParser(metaObject);

        // 先判断是不是UPDATE操作
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (!SqlCommandType.UPDATE.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        }
        SysUser user= WebUtil.currentUser();
        if(user==null){
            return invocation.proceed();
        }
        //id为执行的mapper方法的全路径名
        String id = mappedStatement.getId();
        ParameterMap parameterMap = mappedStatement.getParameterMap();
        BoundSql boundSql = statementHandler.getBoundSql();
        //获取原始sql
        String sql = boundSql.getSql();
        //判断是否是DDL
        String lowSql=sql.toLowerCase(Locale.ROOT);
        if(lowSql.indexOf("update")!=0){
            return invocation.proceed();
        }
        Update stmt = (Update) CCJSqlParserUtil.parse(sql);
        List<Column> columnList =stmt.getColumns();
        List<Expression> expressionList=stmt.getExpressions();
        Class paramClass = parameterMap.getType();
        //系统方法
        if(paramClass !=null){
            if(BaseEntity.class.isAssignableFrom(paramClass)){
                if(user!=null && !exist(columnList,"update_user")){
                    //重新设置mapper接口的参数或者对sql进行改造
                    Column updateUser= new Column();
                    updateUser.setColumnName("update_user");
                    columnList.add(updateUser);
                    Expression updateUserExpression = CCJSqlParserUtil.parseExpression(user.getId()+"");
                    expressionList.add(updateUserExpression);
                }
                if(!exist(columnList,"update_time")){
                    Column updateTime= new Column();
                    updateTime.setColumnName("update_time");
                    columnList.add(updateTime);
                    Expression updateTimeExpression = CCJSqlParserUtil.parseExpression("'"+DateUtil.now()+"'");
                    expressionList.add(updateTimeExpression);
                }
                //未继承父类
            }else{
                //如果有创建者 字段
                try {
                    Field field = paramClass.getDeclaredField("updateUser");
                    if(user!=null && !exist(columnList,"update_user")){
                        Column updateUser= new Column();
                        updateUser.setColumnName("update_user");
                        columnList.add(updateUser);
                        Expression updateUserExpression = CCJSqlParserUtil.parseExpression(user.getId()+"");
                        expressionList.add(updateUserExpression);
                    }
                } catch (NoSuchFieldException e) {
                }
                //如果有创建时间 字段
                try {
                    Field field = paramClass.getDeclaredField("updateTime");
                    if(!exist(columnList,"update_time")){
                        Column updateTime= new Column();
                        updateTime.setColumnName("update_time");
                        columnList.add(updateTime);
                        Expression updateTimeExpression = CCJSqlParserUtil.parseExpression("'"+DateUtil.now()+"'");
                        expressionList.add(updateTimeExpression);
                    }
                } catch (NoSuchFieldException e) {
                }
            }
            stmt.setExpressions(expressionList);
            stmt.setColumns(columnList);
//            System.out.println("update sql after:" + stmt.toString());
            metaObject.setValue("delegate.boundSql.sql", stmt.toString());
        }


        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return target instanceof StatementHandler ? Plugin.wrap(target, this) : target;
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private Boolean exist(List<Column> columnList, String paramName){
        if(CommonUtil.verifyParamNull(columnList,paramName)){
            return false;
        }
        boolean flag = false;
        for(Column m:columnList){
            if(m.getColumnName().equals(paramName)){
                flag = true;
                return flag;
            }
        }
        return flag;
    }
}
