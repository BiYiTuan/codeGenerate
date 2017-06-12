package ${config.basePackage}.dal.mapper;

import java.util.List;
import java.util.Map;

import ${config.basePackage}.dal.entity.${tableModel.javaName};

/**
 * ${tableModel.javaMethodName} mapper 接口
 * @Author by CodeGenerate [NieJin]
 * @CreateAt ${date?string("yyyy-MM-dd HH:mm:ss")}
**/
public interface ${tableModel.javaName}Mapper {

    boolean add(${tableModel.javaName} ${tableModel.javaMethodName});

    boolean delete(${tableModel.primaryKeyColumn.javaTypeNameStr} ${tableModel.primaryKeyColumn.javaFieldName});

    boolean update(${tableModel.javaName} ${tableModel.javaMethodName});

    ${tableModel.javaName} find(${tableModel.primaryKeyColumn.javaTypeNameStr} ${tableModel.primaryKeyColumn.javaFieldName});

    List<${tableModel.javaName}> list(Map<String,Object> params);

    Integer count(${tableModel.javaName} ${tableModel.javaMethodName});
}