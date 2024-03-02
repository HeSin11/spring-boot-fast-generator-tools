package ${instance.packageName};


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${instance.resultPackage}.${instance.resultClassName};
import ${instance.importEntityPackage}.${instance.importEntityName};
import ${instance.importQueryPackage}.${instance.importPageQueryName};
import ${instance.importQueryPackage}.${instance.importQueryName};
import ${instance.importServicePackage}.${instance.importServiceName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/${instance.baseUrl}")
public class ${instance.className} {
    @Autowired
    private ${instance.importServiceName} ${instance.importServiceName?uncap_first};

    @GetMapping("/detail")
    public ${instance.resultClassName}<${instance.importEntityName}> detail(@RequestParam Long id) {
        return CommonResult.success(${instance.importServiceName?uncap_first}.findById(id));
    }

    @PostMapping("/save")
    public ${instance.resultClassName}<Void> save(@RequestBody ${instance.importEntityName} ${instance.importEntityName?uncap_first}) {
        ${instance.importServiceName?uncap_first}.save(${instance.importEntityName?uncap_first});
        return CommonResult.success();
    }

    @PostMapping("/page")
    public ${instance.resultClassName}<Page<${instance.importEntityName}>> page(@RequestBody ${instance.importPageQueryName} ${instance.importPageQueryName?uncap_first}) {
        return CommonResult.success(${instance.importServiceName?uncap_first}.findByPage(${instance.importPageQueryName?uncap_first}));
    }

    @GetMapping("/delete")
    public CommonResult<Void> delete(@RequestParam Long id){
        ${instance.importServiceName?uncap_first}.deleteById(id);
        return CommonResult.success();
    }

    @PostMapping("/update")
    public CommonResult<Void> update(@RequestBody ${instance.importEntityName} ${instance.importEntityName?uncap_first}){
        ${instance.importServiceName?uncap_first}.updateById(${instance.importEntityName?uncap_first});
        return CommonResult.success();
    }

}