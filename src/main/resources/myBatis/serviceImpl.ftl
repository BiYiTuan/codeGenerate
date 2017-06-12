package ${config.basePackage}.service.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${config.basePackage}.dal.entity.${tableModel.javaName};
import ${config.basePackage}.dal.mapper.${tableModel.javaName}Mapper;
import ${config.basePackage}.facade.dto.${tableModel.javaName}Service;

/**
* ${tableModel.javaMethodName} service 实现
* @Author by CodeGenerate [NieJin]
* @CreateAt ${date?string("yyyy-MM-dd HH:mm:ss")}
**/
@Service
public class ${tableModel.javaName}ServiceImpl implements ${tableModel.javaName}Service {

    @Autowired
    private ${tableModel.javaName}Mapper ${tableModel.javaMethodName}Mapper;

    @Override
    public boolean add(${tableModel.javaName} ${tableModel.javaMethodName}){
        return ${tableModel.javaMethodName}Mapper.add(${tableModel.javaMethodName});
    }

    @Override
    public boolean delete(${tableModel.primaryKeyColumn.javaTypeNameStr} ${tableModel.primaryKeyColumn.javaFieldName}){
        return ${tableModel.javaMethodName}Mapper.delete(${tableModel.primaryKeyColumn.javaFieldName});
    }

    @Override
    public boolean update(${tableModel.javaName} ${tableModel.javaMethodName}){
        return ${tableModel.javaMethodName}Mapper.update(${tableModel.javaMethodName});
    }

    @Override
    public ${tableModel.javaName} find(${tableModel.primaryKeyColumn.javaTypeNameStr} ${tableModel.primaryKeyColumn.javaFieldName}){
        return ${tableModel.javaMethodName}Mapper.find(${tableModel.primaryKeyColumn.javaFieldName});
    }

    @Override
    public List<${tableModel.javaName}> list(${tableModel.javaName} ${tableModel.javaMethodName},Integer skip,Integer max,String sort){
        Map<String,Object> params = new HashMap<String,Object>();
        if(${tableModel.javaMethodName} != null){
            params.put("${tableModel.javaMethodName}",${tableModel.javaMethodName});
        }
        if(skip != null){
            params.put("skip",skip);
        }
        if(max != null){
            params.put("max",max);
        }
        if(StringUtils.isNotBlank(sort)){
            params.put("sort",sort);
        }
        return ${tableModel.javaMethodName}Mapper.list(params);
    }

    @Override
    public Integer count(${tableModel.javaName} ${tableModel.javaMethodName}){
        return ${tableModel.javaMethodName}Mapper.count(${tableModel.javaMethodName});
    }
}

