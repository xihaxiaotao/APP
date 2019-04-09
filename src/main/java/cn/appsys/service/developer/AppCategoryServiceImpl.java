package cn.appsys.service.developer;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.appcategory.AppCategoryMapper;
import cn.appsys.pojo.AppCategory;
@Service
public class AppCategoryServiceImpl implements AppCategoryService {

	@Autowired
	private AppCategoryMapper aMapper;
	@Override
	public List<AppCategory> getAppCategoryListByParentId(Integer parentId) {
		// TODO Auto-generated method stub
		return aMapper.getAppCategoryListByParentId(parentId);
	}

}
