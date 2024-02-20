package com.generator.tools.freemarker.template;

import com.generator.tools.freemarker.template.config.GlobalDataConfig;
import com.generator.tools.freemarker.template.instance.TemplateInstance;
import com.generator.tools.util.PackagePathUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.Data;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public abstract class AbstractGeneratorTemplate<I extends TemplateInstance> implements GeneratorTemplate<I> {
    private static final String INSTANCE = "instance";

    protected GlobalDataConfig globalDataConfig;

    public AbstractGeneratorTemplate(GlobalDataConfig globalDataConfig) {
        this.globalDataConfig = globalDataConfig;
    }

    protected abstract List<I> getInstances();


    void generator(String templateName, String targetPath) throws Exception {

        Configuration configuration = new Configuration(Configuration.getVersion());
        configuration.setDefaultEncoding("UTF-8");
        configuration.setDirectoryForTemplateLoading(new File(PackagePathUtil.getFtlTemplatePath()));
        Template template = configuration.getTemplate(templateName);
        List<I> instances = getInstances();
        Assert.notEmpty(instances, "未找到可以创建的实例");
        PackagePathUtil.createPath(targetPath);
        instances.forEach(instance->{
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(targetPath + instance.getFileName());
                Map<String,I> dataMap = new HashMap<>();
                dataMap.put(INSTANCE, instance);
                template.process(dataMap, fileWriter);
            } catch (IOException | TemplateException e) {
                throw new RuntimeException(e);
            }finally {
                if (fileWriter != null){
                    try {
                        fileWriter.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
}
