package com.generator.tools.util;

import com.generator.tools.freemarker.generator.config.GeneratorPackageConfig;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.File;

public class PackagePathUtil {
    public static String getPackageName(String parentPackagePath){
        String packageNameNoPrefix = parentPackagePath.indexOf("/") == 0 ? parentPackagePath.substring(1) : parentPackagePath;
        String packageNameNoSuffix = packageNameNoPrefix.lastIndexOf("/") == packageNameNoPrefix.length() - 1 ?  packageNameNoPrefix.substring(0, packageNameNoPrefix.length() - 1) : packageNameNoPrefix;
        return packageNameNoSuffix.replace("/", ".");
    }

    public static String getFilePath(String parentPackagePath, String childPath){
        String packageFilePath = GeneratorPackageConfig.baseResultPath + parentPackagePath + "/";
        return StringUtils.hasText(childPath) ? (packageFilePath + childPath + "/") : packageFilePath;
    }

    public static String getFtlTemplatePath(){
        return System.getProperty("user.dir") + "/src/main/resources/templates/";
    }

    public static void createPath(String path){
        //判断路径是否存在
        File file = new File(path);
        if (!file.exists()) {
            boolean createPathFlag = file.mkdirs();
            Assert.isTrue(createPathFlag, "创建文件夹失败");
        }
    }
}
