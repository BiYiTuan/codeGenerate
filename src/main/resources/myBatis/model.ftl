package ${config.basePackage}.dal.entity;

<#list tableModel.columns as columnModel>
    <#if columnModel.javaTypeNameStr == "Date">
import java.util.Date;
        <#break />
    </#if>
</#list>
<#list tableModel.columns as columnModel>
    <#if columnModel.javaTypeNameStr == "Timestamp">
import java.sql.Timestamp;
        <#break />
    </#if>
</#list>
<#list tableModel.columns as columnModel>
    <#if columnModel.javaTypeNameStr == "BigDecimal">
import java.math.BigDecimal;
        <#break />
    </#if>
</#list>

/**
 *${tableModel.javaName} 数据对象
 * @Author By CodeGenerate. [Niejin]
 * @CreateAt ${date?string("yyyy-MM-dd HH:mm:ss")}
**/

public class ${tableModel.javaName} implements java.io.Serializable {

    private static final long serialVersionUID = ${serialVersionUID?c}L;

    <#assign  hasField = 0/>
    <#if tableModel.primaryKeyColumn?exists>
        <#assign hasField = 1/>
    //主键
    private ${tableModel.primaryKeyColumn.javaTypeNameStr} ${tableModel.primaryKeyColumn.javaFieldName};
    </#if>
    <#list tableModel.columns as column>
        <#if true>

    /*${column.comment}*/
    private ${column.javaTypeNameStr} ${column.javaFieldName};
        <#assign  hasField = 1/>
        </#if>
    </#list>

    public ${tableModel.javaName}(){}

    <#if tableModel.primaryKeyColumn?exists>
    public ${tableModel.javaName}(${tableModel.primaryKeyColumn.javaTypeNameStr} ${tableModel.primaryKeyColumn.javaFieldName}){
        this.${tableModel.primaryKeyColumn.javaFieldName} = ${tableModel.primaryKeyColumn.javaFieldName};
    }
    </#if>

    <#if tableModel.primaryKeyColumn?exists>
    public ${tableModel.primaryKeyColumn.javaTypeNameStr} get${tableModel.primaryKeyColumn.javaMethodName}(){
        return this.${tableModel.primaryKeyColumn.javaFieldName};
    }

    public void set${tableModel.primaryKeyColumn.javaMethodName}(${tableModel.primaryKeyColumn.javaTypeNameStr} ${tableModel.primaryKeyColumn.javaFieldName}){
        this.${tableModel.primaryKeyColumn.javaFieldName} = ${tableModel.primaryKeyColumn.javaFieldName};
    }
    </#if>

    <#list tableModel.columns as column>
        <#if true>
    public ${column.javaTypeNameStr} get${column.javaMethodName}(){
        return this.${column.javaFieldName};
    }

    public void set${column.javaMethodName}(${column.javaTypeNameStr} ${column.javaFieldName}) {
        this.${column.javaFieldName} = ${column.javaFieldName};
    }
        </#if>
    </#list>
}