package ${instance.packageName};
import ${instance.importEntityPackage}.${instance.importEntityName};
import ${instance.importQueryPackage}.${instance.importPageQueryName?cap_first};
import ${instance.importQueryPackage}.${instance.importQueryName?cap_first};
import java.util.List;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface ${instance.className}{
    ${instance.importEntityName} findById(Long id);

    List<${instance.importEntityName}> findAll();

    List<${instance.importEntityName}> findList(${instance.importQueryName?cap_first} ${instance.importQueryName?uncap_first});

    Page<${instance.importEntityName}> findByPage(${instance.importPageQueryName?cap_first} ${instance.importPageQueryName?uncap_first});

    int save(${instance.importEntityName} ${instance.importEntityName?uncap_first});

    int batchSave(List<${instance.importEntityName}> ${instance.importEntityName?uncap_first}List);

    int deleteById(Long id);

    int updateById(${instance.importEntityName} ${instance.importEntityName?uncap_first});
}