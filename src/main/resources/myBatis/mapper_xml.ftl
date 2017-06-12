<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${config.basePackage}.dal.mapper.${tableModel.javaName}Mapper">

    <resultMap  id="${tableModel.javaName}ResultMap" type="${config.basePackage}.dal.entity.${tableModel.javaName}">
    <#if tableModel.primaryKeyColumn?exists>
        <id column="${tableModel.primaryKeyColumn.columnName}" property="${tableModel.primaryKeyColumn.javaFieldName}" jdbcType="${tableModel.primaryKeyColumn.myBatisTypeName}"/>
    </#if>
    <#list tableModel.columns as columnModel>
        <result column="${columnModel.columnName}" property="${columnModel.javaFieldName}"  jdbcType="${columnModel.myBatisTypeName}"/>
    </#list>
    </resultMap>

    <sql id="${tableModel.javaName}_Column_list">
        <#assign fieldSize = tableModel.columns?size>
        <#if tableModel.primaryKeyColumn?exists>
            ${tableModel.primaryKeyColumn.javaFieldName} ,
        </#if>
        <#list tableModel.columns as columnModel>
            <#if columnModel_index == fieldSize-1>
            ${columnModel.javaFieldName}
            <#else>
            ${columnModel.javaFieldName} ,
            </#if>
        </#list>
    </sql>

    <select id="find" parameterType="${tableModel.primaryKeyColumn.javaTypeNameStr}" resultType="${config.basePackage}.dal.entity.${tableModel.javaName}">
        SELECT
        <include refid="${tableModel.javaName}_Column_list"/>
        FROM
        ${tableModel.tableName} t
        WHERE
        ${tableModel.primaryKeyColumn.columnName} = ${"#{"+tableModel.primaryKeyColumn.javaFieldName+",jdbcType="+tableModel.primaryKeyColumn.myBatisTypeName+"}"}
    </select>

    <delete id="delete" parameterType="${tableModel.primaryKeyColumn.javaTypeNameStr}">
        DELETE FROM
        ${tableModel.tableName} t
        <where>
        ${tableModel.primaryKeyColumn.columnName} = ${"#{"+tableModel.primaryKeyColumn.javaFieldName+",jdbcType="+tableModel.primaryKeyColumn.myBatisTypeName+"}"}
        </where>
    </delete>

    <insert id="add" parameterType="${config.basePackage}.dal.entity.${tableModel.javaName}" useGeneratedKeys="true" keyProperty="${tableModel.primaryKeyColumn.javaFieldName}">
        INSERT INTO
        ${tableModel.tableName}
        <trim prefix="(" suffix=")" suffixOverrides="," >
            <#list tableModel.columns as column>
            <if test="${column.javaFieldName} != null">
               ${column.columnName},
            </if>
            </#list>
        </trim>
        VALUES
        <trim prefix="(" suffix=")" suffixOverrides="," >
            <#list tableModel.columns as column>
                <if test="${column.javaFieldName} != null">
                    ${"#{"+column.javaFieldName+",jdbcType="+column.myBatisTypeName+"},"}
                </if>
            </#list>
        </trim>
    </insert>

    <update id="update" parameterType="${config.basePackage}.dal.entity.${tableModel.javaName}">
        UPDATE
        ${tableModel.tableName}
        <set>
            <#list tableModel.columns as column>
                <if test="${column.javaFieldName} != null">
                ${column.columnName + "=#{" + column.javaFieldName + ",jdbcType=" + column.myBatisTypeName + "},"}
                </if>
            </#list>
        </set>
        WHERE
        ${tableModel.primaryKeyColumn.columnName} = ${"#{"+tableModel.primaryKeyColumn.javaFieldName+",jdbcType="+tableModel.primaryKeyColumn.myBatisTypeName+"}"}
    </update>

    <select id="count" resultType="java.lang.Integer" parameterType="${config.basePackage}.dal.entity.${tableModel.javaName}">
        SELECT count(1) FROM ${tableModel.tableName}
        <trim prefix="WHERE" suffixOverrides="AND|OR" >
            <#list tableModel.columns as column>
                <if test="${column.javaFieldName} != null">
                ${column.columnName} = ${"#{" + column.javaFieldName + ",jdbcType = " + column.myBatisTypeName + "} AND "}
                </if>
            </#list>
        </trim>
    </select>

    <select id="list" resultMap="${tableModel.javaName}ResultMap" parameterType="hashmap">
        SELECT
        <include refid="${tableModel.javaName}_Column_list"/>
        FROM
        ${tableModel.tableName} t
        <trim prefix="WHERE" suffixOverrides="AND|OR" >
        <#list tableModel.columns as column>
            <if test="${tableModel.javaMethodName}.${column.javaFieldName} != null">
            ${column.columnName} = ${"#{" + tableModel.javaMethodName+"."+column.javaFieldName + ",jdbcType = " + column.myBatisTypeName + "} AND "}
            </if>
        </#list>
        </trim>
        <if test="skip !=null and max != null">
            LIMIT ${r'#'}{skip,jdbcType=INT},${r'#'}{max,jdbcType=INT}
        </if>
        <if test="sort != null and sort != ''">
            ORDER BY ${r'#'}{sort,jdbcType=VARCAHR}
        </if>
    </select>

</mapper>