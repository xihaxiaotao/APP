package cn.appsys.dao.datadictionary;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import cn.appsys.pojo.DataDictionary;

public interface DataDictionaryMapper {
	//查询——数据字典表
	public List<DataDictionary> getDataDictionaryList(@Param("typeCode")String typeCode);
}
