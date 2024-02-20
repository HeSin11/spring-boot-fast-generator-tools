package ${instance.packageName};
import ${instance.importEntityPackage}.${instance.importEntityName};
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ${instance.className} extends BaseMapper<${instance.importEntityName}> {

}
