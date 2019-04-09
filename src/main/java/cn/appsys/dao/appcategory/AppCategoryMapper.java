package cn.appsys.dao.appcategory;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import cn.appsys.pojo.AppCategory;

public interface AppCategoryMapper {
	//查询一级，二级，三级分类
	public List<AppCategory> getAppCategoryListByParentId(@Param("parentId")Integer parentId);
}
