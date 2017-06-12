package ${config.basePackage}.service;

import java.util.List;

import ${config.basePackage}.dal.entity.${tableModel.javaName};

/**
 * ${tableModel.javaMethodName} service 接口
 * @Author by CodeGenerate [NieJin]
 * @CreateAt ${date?string("yyyy-MM-dd HH:mm:ss")}
**/
public interface ${tableModel.javaName}Service {

    boolean add(${tableModel.javaName} ${tableModel.javaMethodName});

    boolean delete(${tableModel.primaryKeyColumn.javaTypeNameStr} ${tableModel.primaryKeyColumn.javaFieldName});

    boolean update(${tableModel.javaName} ${tableModel.javaMethodName});

    ${tableModel.javaName} find(${tableModel.primaryKeyColumn.javaTypeNameStr} ${tableModel.primaryKeyColumn.javaFieldName});

    List<${tableModel.javaName}> list(${tableModel.javaName} ${tableModel.javaMethodName},Integer skip,Integer max,String sort);

    Integer count(${tableModel.javaName} ${tableModel.javaMethodName});
}

