package ${instance.packageName};

import lombok.Data;
<#if instance.importFieldTypes?seq_contains("Timestamp") ||  instance.importFieldTypes?seq_contains("Date")>
import java.util.Date;
</#if>
<#if instance.importFieldTypes?seq_contains("LocalDateTime")>
import java.time.LocalDateTime;
</#if>
<#if instance.importFieldTypes?seq_contains("BigDecimal")>
import java.math.BigDecimal;
</#if>
<#--<#list instance.importFieldTypes as fieldType>-->
<#--    <#if fieldType == "LocalDateTime">-->
<#--import java.time.LocalDateTime;-->
<#--    </#if>-->
<#--    <#if fieldType == "Date">-->
<#--import java.util.Date;-->
<#--    </#if>-->
<#--    <#if fieldType == "BigDecimal">-->
<#--import java.math.BigDecimal;-->
<#--    </#if>-->
<#--    <#if fieldType == "Timestamp">-->
<#--import java.util.Date;-->
<#--    </#if>-->
<#--    <#if fieldType == "Time">-->
<#--import java.util.Date;-->
<#--    </#if>-->
<#--</#list>-->

@Data
public class ${instance.className} {

<#list instance.queryFields as fieldInfo>
    /**
    * ${fieldInfo.fieldRemark}
    */
   private ${fieldInfo.fieldType} ${fieldInfo.fieldName};
</#list>
}