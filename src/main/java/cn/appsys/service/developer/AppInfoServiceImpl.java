package cn.appsys.service.developer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.appinfo.AppInfoMapper;
import cn.appsys.pojo.AppInfo;
import cn.appsys.tools.PageSupport;



@Service
public class AppInfoServiceImpl implements AppInfoService {

	@Autowired
	private AppInfoMapper aMapper;
	
	@Override
	public List<AppInfo> getAppInfoList(AppInfo appinfo, PageSupport page) {
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
	public int addAppInfo(AppInfo appinfo) {
		// TODO Auto-generated method stub
		return aMapper.addAppInfo(appinfo);
	}

	@Override
	public int updateAppInfo(AppInfo appinfo) {
		// TODO Auto-generated method stub
		return aMapper.updateAppInfo(appinfo);
	}

	@Override
	public int updateVersionId(Integer versionId, Integer appId) {
		// TODO Auto-generated method stub
		return aMapper.updateVersionId(versionId, appId);
	}

	@Override
	public int deleteLogoPic(Integer id) {
		// TODO Auto-generated method stub
		return aMapper.deleteLogoPic(id);
	}

	@Override
	public int delApp(Integer id) {
		// TODO Auto-generated method stub
		return aMapper.delApp(id);
	}

	@Override
	public int appSale(AppInfo appinfo) {
		// TODO Auto-generated method stub
		return aMapper.appSale(appinfo);
	}

	@Override
	public List<AppInfo> getAppDownCount() {
		// TODO Auto-generated method stub
		return aMapper.getAppDownCount();
	}


	@Override
	public List<AppInfo> getAppInfoList2(AppInfo appinfo) {
		// TODO Auto-generated method stub
		return aMapper.getAppInfoList2(appinfo);
	}
	
}
