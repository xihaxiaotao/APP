package cn.appsys.service.developer;

import java.util.List;


import cn.appsys.pojo.AppCategory;

public interface AppCategoryService {
	//查询一级，二级，三级分类
    public List<AppCategory> getAppCategoryListByParentId(Integer parentId);
}
