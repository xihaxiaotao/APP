package cn.appsys.service.developer;

import java.util.List;


import cn.appsys.pojo.DataDictionary;

public interface DataDictionaryService {
	//查询——数据字典表
	public List<DataDictionary> getDataDictionaryList(String typeCode);
	
}
