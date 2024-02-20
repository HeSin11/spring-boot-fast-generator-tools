package ${instance.packageName};

<#if instance.importFieldTypes?seq_contains("Timestamp") ||  instance.importFieldTypes?seq_contains("Date")>
import java.util.Date;
</#if>
<#if instance.importFieldTypes?seq_contains("LocalDateTime")>
import java.time.LocalDateTime;
</#if>
<#if instance.importFieldTypes?seq_contains("BigDecimal")>
import java.math.BigDecimal;
</#if>
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;

@Data
@TableName("${instance.tableName}")
public class ${instance.className}{
<#list instance.fieldInfos as fieldInfo>
    /**
    * ${fieldInfo.fieldRemark}
    */
    <#if fieldInfo.isPrimaryKey == true>
    @TableId(value = "${fieldInfo.columnName}", type=IdType.AUTO)
    <#else>
    @TableField(value = "${fieldInfo.columnName}")
    </#if>
    private ${fieldInfo.fieldType} ${fieldInfo.fieldName};
</#list>
}