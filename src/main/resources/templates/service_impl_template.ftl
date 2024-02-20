package ${instance.packageName};

import ${instance.importEntityPackage}.${instance.importEntityName};
import ${instance.importMapperPackage}.${instance.importMapperName};
import ${instance.importQueryPackage}.${instance.importPageQueryName};
import ${instance.importQueryPackage}.${instance.importQueryName};
import ${instance.importServicePackage}.${instance.importServiceName};
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ${instance.className} implements ${instance.importServiceName} {
    @Autowired
    private ${instance.importMapperName} ${instance.importMapperName?uncap_first};

    @Override
    public ${instance.importEntityName} findById(Long id) {
        return ${instance.importMapperName?uncap_first}.selectById(id);
    }

    @Override
    public List<${instance.importEntityName}> findAll() {
        return ${instance.importMapperName?uncap_first}.selectList(null);
    }

    @Override
    public List<${instance.importEntityName}> findList(${instance.importQueryName?cap_first} ${instance.importQueryName?uncap_first}) {
        QueryWrapper<${instance.importEntityName}> queryWrapper = new QueryWrapper<>();
        <#list instance.fieldInfos as fieldInfo>
        <#if  fieldInfo.fieldType == 'String'>
        if (StringUtils.hasText(${instance.importQueryName?uncap_first}.get${fieldInfo.fieldName?cap_first}())){
        <#else>
        if (${instance.importQueryName?uncap_first}.get${fieldInfo.fieldName?cap_first}() != null){
        </#if>
            queryWrapper.eq("${fieldInfo.columnName}", ${instance.importQueryName?uncap_first}.get${fieldInfo.fieldName?cap_first}());
        }
        </#list>
        return ${instance.importMapperName?uncap_first}.selectList(queryWrapper);
    }

    @Override
    public Page<${instance.importEntityName}> findByPage(${instance.importPageQueryName?cap_first} ${instance.importPageQueryName?uncap_first}) {
        QueryWrapper<${instance.importEntityName}> queryWrapper = new QueryWrapper<>();
        Integer pageNum = ${instance.importPageQueryName?uncap_first}.getPageNum();
        Integer pageSize = ${instance.importPageQueryName?uncap_first}.getPageSize();
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        <#list instance.pageFieldInfos as pageFieldInfo>
            <#if pageFieldInfo.fieldName != 'pageSize' && pageFieldInfo.fieldName != 'pageNum'>
                <#if  pageFieldInfo.fieldType == 'String'>
        if (StringUtils.hasText(${instance.importPageQueryName?uncap_first}.get${pageFieldInfo.fieldName?cap_first}())){
                <#else>
        if (${instance.importPageQueryName?uncap_first}.get${pageFieldInfo.fieldName?cap_first}() != null){
                </#if>
           queryWrapper.eq("${pageFieldInfo.columnName}", ${instance.importPageQueryName?uncap_first}.get${pageFieldInfo.fieldName?cap_first}());
        }
            </#if>
        </#list>
        Page<${instance.importEntityName}> page = new Page<>(pageNum, pageSize);
        return ${instance.importMapperName?uncap_first}.selectPage(page, queryWrapper);
    }

    @Override
    public int save(${instance.importEntityName} ${instance.importEntityName?uncap_first}) {
        return ${instance.importMapperName?uncap_first}.insert(${instance.importEntityName?uncap_first});
    }

    @Override
    public int batchSave(List<${instance.importEntityName}> ${instance.importEntityName?uncap_first}List) {
        if (!CollectionUtils.isEmpty(${instance.importEntityName?uncap_first}List)) {
            ${instance.importEntityName?uncap_first}List.forEach(${instance.importEntityName?uncap_first} -> ${instance.importMapperName?uncap_first}.insert(${instance.importEntityName?uncap_first}));
        }
        return ${instance.importEntityName?uncap_first}List.size();
    }

    @Override
    public int deleteById(Long id) {
        return ${instance.importMapperName?uncap_first}.deleteById(id);
    }

    @Override
    public int updateById(${instance.importEntityName} ${instance.importEntityName?uncap_first}) {
        return ${instance.importMapperName?uncap_first}.updateById(${instance.importEntityName?uncap_first});
    }
}