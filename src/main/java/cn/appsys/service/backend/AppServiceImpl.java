package cn.appsys.service.backend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.appinfo.AppInfoMapper;
import cn.appsys.pojo.AppInfo;
import cn.appsys.tools.PageSupport;



@Service
public class AppServiceImpl implements AppService {

	@Autowired
	private AppInfoMapper  aMapper;
	
	@Override
	public List<AppInfo> getAppListToCheck(AppInfo appinfo,PageSupport  page) {
		// TODO Auto-generated method stub
		return aMapper.getAppInfoList(appinfo, page);
	}

	@Override
	public int getAppCount(AppInfo appinfo) {
		// TODO Auto-generated method stub
		return aMapper.getAppCount(appinfo);
	}

	@Override
	public AppInfo getInfo(String apkName, Integer id) {
		// TODO Auto-generated method stub
		return aMapper.getInfo(apkName, id);
	}

	@Override
	public int appSale(AppInfo appinfo) {
		// TODO Auto-generated method stub
		return aMapper.appSale(appinfo);
	}
	
	
	
}
