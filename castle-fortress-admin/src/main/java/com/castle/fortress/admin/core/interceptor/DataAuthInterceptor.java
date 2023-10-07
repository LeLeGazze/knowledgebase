package com.castle.fortress.admin.core.interceptor;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.enums.DataPermissionPostEnum;
import com.castle.fortress.common.utils.ReflectUtil;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * mybatis 数据权限拦截器
 * 运营管理端用户 且 非管理员 需要筛选数据权限
 * 实体类需要继承 DataAuthBaseEntity
 * @author Castle
 */
@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class}
)})
public class DataAuthInterceptor extends AbstractSqlParserHandler implements Interceptor {
    /**
     * 只拦截一个参数的查询
     * 参数对象为继承权限基类的子类  或者是 具有dataAuthFlag 的Map
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        this.sqlParser(metaObject);

        // 先判断是不是SELECT操作
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        }

        //id为执行的mapper方法的全路径名
        String id = mappedStatement.getId();
        ParameterMap parameterMap = mappedStatement.getParameterMap();
        BoundSql boundSql = statementHandler.getBoundSql();
        //获取原始sql
        String sql = boundSql.getSql();
        Object parameterObject =boundSql.getParameterObject();
        if(parameterObject == null){
            return invocation.proceed();
        }
        //数据权限校验标识
        boolean dataAuthFlag=false;
        //参数
        if(parameterObject instanceof Map){
            Map<String,Object> paramMap = (Map<String,Object>)parameterObject;
            for(int i = 1;i<= paramMap.size()/2;i++){
                Object param = paramMap.get("param"+i);
                if(isLimitAuth(param)){
                    dataAuthFlag = true;
                    break;
                }
            }
        }else{
            if(isLimitAuth(parameterObject)){
                dataAuthFlag = true;
            }
        }
        if(dataAuthFlag){
            SysUser user= WebUtil.currentUser();
            //未获取当前登录用户 或者 用户是管理员 不限制数据权限
            if(user==null || user.getIsAdmin() || user.getIsSuperAdmin()){
                return invocation.proceed();
            }
            //查询当前用户的部门权限
            List<Long> deptIdList = user.getAuthDept();
            //查询当前用户的职位权限
            if(user.getPostId()!=null){
                if(user.getPostDataAuth() == null || DataPermissionPostEnum.NO_LIMIT.getCode().equals(user.getPostDataAuth())){
                    sql= unlimited(sql,deptIdList);
                }else if(DataPermissionPostEnum.SELF_SUB.getCode().equals(user.getPostDataAuth())){
                    List<Long> subPostList = user.getSubPost();
                    sql= selfAndFollow(sql,user,deptIdList,subPostList);
                }else if(DataPermissionPostEnum.SELF.getCode().equals(user.getPostDataAuth())){
                    sql = selfLimit(sql,user,deptIdList);
                }
                //职位不限
            }else{
                sql= unlimited(sql,deptIdList);
            }
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


    public static void main(String[] args) throws ClassNotFoundException, JSQLParserException {
        String sql = "select * from castle_config_params t left outer join castle_bsld b  on t.a = b.c where t.param_code = '001'";
        Select stmt = (Select) CCJSqlParserUtil.parse(sql);
        PlainSelect plainSelect =(PlainSelect)stmt.getSelectBody();
        Table table = (Table)plainSelect.getFromItem();
        String tableAlias = "";
        if(table.getAlias()!=null){
            tableAlias = table.getAlias()+".";
        }
        //原查询条件
        Expression whereExpression = plainSelect.getWhere();
        System.out.println(whereExpression.toString());
        //新添加的查询条件
        Expression jointWhere = CCJSqlParserUtil.parseCondExpression(whereExpression.toString() + "and "+tableAlias+"create_dept in (1343804872651849729,1343805894971506690)");
        plainSelect.setWhere(jointWhere);

        System.out.println("after " + stmt.toString());
    }

    /**
     * 职位限制 无限制,或者用户无职位
     * @param sql 原始sql
     * @param deptList 角色对应的部门
     * @return
     */
    private String unlimited(String sql,List<Long> deptList){
        Select stmt = null;
        String joinSql = sql;
        if(deptList == null || deptList.isEmpty()){
            return joinSql;
        }
        try {
            stmt = (Select) CCJSqlParserUtil.parse(sql);
            PlainSelect plainSelect =(PlainSelect)stmt.getSelectBody();
            //获取表的别名
            Table table = (Table)plainSelect.getFromItem();
            String tableAlias = "";
            if(table.getAlias()!=null){
                tableAlias = table.getAlias()+".";
            }
            //原查询条件
            Expression whereExpression = plainSelect.getWhere();

            StringBuilder deptSB= new StringBuilder();
            for(Long deptId:deptList){
                deptSB.append(deptId+",");
            }
            String deptS=deptSB.toString().substring(0,deptSB.toString().length()-1);

            //新添加的查询条件
            Expression jointWhere = CCJSqlParserUtil.parseCondExpression(whereExpression.toString() + "and ("+tableAlias+"create_dept in ("+deptS+") or "+tableAlias+"create_dept is null)");
            plainSelect.setWhere(jointWhere);

            joinSql = stmt.toString() ;
        } catch (JSQLParserException e) {
            return sql;
        }
        return joinSql;
    }

    /**
     * 职位限制 本人及以下
     * @param sql 原始sql
     * @param user 当前登录用户
     * @param deptList 角色对应的部门
     * @param subPostList 下属职位,不包含本级职位
     * @return
     */
    private String selfAndFollow(String sql,SysUser user, List<Long> deptList, List<Long> subPostList){
        Select stmt = null;
        String joinSql = sql;

        try {
            stmt = (Select) CCJSqlParserUtil.parse(sql);
            PlainSelect plainSelect =(PlainSelect)stmt.getSelectBody();
            //获取表的别名
            Table table = (Table)plainSelect.getFromItem();
            String tableAlias = "";
            if(table.getAlias()!=null){
                tableAlias = table.getAlias()+".";
            }
            //原查询条件
            Expression whereExpression = plainSelect.getWhere();
            String deptS = null;
            String postS = null;

            //部门权限
            if(deptList != null && !deptList.isEmpty()){
                //剔除当前用户的部门id
                StringBuilder deptSB= new StringBuilder();
                for(Long deptId:deptList){
                    if(!deptId.equals(user.getDeptId())){
                        deptSB.append(deptId+",");
                    }
                }
                if(StrUtil.isNotEmpty(deptSB.toString())){
                    deptS="("+tableAlias+"create_dept in ("+deptSB.toString().substring(0,deptSB.toString().length()-1)+") or "+tableAlias+"create_dept is null)";
                }
            }

            //职位权限
            if(subPostList != null && !subPostList.isEmpty()){
                StringBuilder postSb= new StringBuilder();
                for(Long postId:subPostList){
                    postSb.append(postId+",");
                }
                postS="("+tableAlias+"create_dept = "+ user.getDeptId() +" and "+tableAlias+"create_post in ("+postSb.toString().substring(0,postSb.toString().length()-1)+"))";
            }
            //个人
            String userS = "("+tableAlias+"create_user = "+ user.getId()+")";
            StringBuilder finalWhereSb= new StringBuilder("(");
            finalWhereSb.append(userS);
            if(deptS != null){
                finalWhereSb.append("or"+ deptS);
            }
            if(postS != null){
                finalWhereSb.append("or" +postS);
            }
            finalWhereSb.append(")");

            //新添加的查询条件
            Expression jointWhere = CCJSqlParserUtil.parseCondExpression(whereExpression.toString() + "and "+finalWhereSb.toString());
            plainSelect.setWhere(jointWhere);

            joinSql = stmt.toString() ;
        } catch (JSQLParserException e) {
            return sql;
        }
        return joinSql;
    }

    /**
     * 职位限制 本人
     * @param sql 原始sql
     * @param user 当前登录用户
     * @param deptList 角色对应的部门
     * @return
     */
    private String selfLimit(String sql,SysUser user, List<Long> deptList){
        Select stmt = null;
        String joinSql = sql;

        try {
            stmt = (Select) CCJSqlParserUtil.parse(sql);
            PlainSelect plainSelect =(PlainSelect)stmt.getSelectBody();
            //获取表的别名
            Table table = (Table)plainSelect.getFromItem();
            String tableAlias = "";
            if(table.getAlias()!=null){
                tableAlias = table.getAlias()+".";
            }
            //原查询条件
            Expression whereExpression = plainSelect.getWhere();

            //部门权限
            String deptS = null;
            if(deptList != null && !deptList.isEmpty()){
                //剔除当前用户的部门id
                StringBuilder deptSB= new StringBuilder();
                for(Long deptId:deptList){
                    if(!deptId.equals(user.getDeptId())){
                        deptSB.append(deptId+",");
                    }
                }
                if(StrUtil.isNotEmpty(deptSB.toString())){
                    deptS="("+tableAlias+"create_dept in ("+deptSB.toString().substring(0,deptSB.toString().length()-1)+") or "+tableAlias+"create_dept is null)";
                }
            }
            //个人
            String userS = "("+tableAlias+"create_user = "+ user.getId()+")";
            StringBuilder finalWhereSb= new StringBuilder("(");
            finalWhereSb.append(userS);
            if(deptS != null){
                finalWhereSb.append("or"+ deptS);
            }
            finalWhereSb.append(")");

            //新添加的查询条件
            Expression jointWhere = CCJSqlParserUtil.parseCondExpression(whereExpression.toString() + "and "+ finalWhereSb.toString());
            plainSelect.setWhere(jointWhere);

            joinSql = stmt.toString() ;
        } catch (JSQLParserException e) {
            return sql;
        }
        return joinSql;
    }

    /**
     * 是否需要校验数据权限
     * @param param
     * @return
     */
    private boolean isLimitAuth(Object param){
        boolean dataAuthFlag = false;
        if(param == null){
            return dataAuthFlag;
        }
        if(Map.class.isAssignableFrom(param.getClass()) && (((Map)param).get("dataAuthFlag") !=null && Boolean.parseBoolean(((Map)param).get("dataAuthFlag").toString()))){
            dataAuthFlag = true;
        //实体类包含数据权限标志字段并开启
        }else{
            Object paramFlag = ReflectUtil.getMethod(param.getClass(),param,"getDataAuthFlag");
            if(paramFlag !=null && Boolean.parseBoolean(paramFlag.toString())){
                dataAuthFlag = true;
            }
        }
        return dataAuthFlag;
    }


}
