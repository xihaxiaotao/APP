package cn.appsys.service.developer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.appversion.AppVersionMapper;
import cn.appsys.pojo.AppVersion;


@Service
public class AppVersionServiceImpl implements AppVersionService {

	@Autowired
	private AppVersionMapper  vMapper;
	
	@Override
	public int addAppVersion(AppVersion appVersion) {
		// TODO Auto-generated method stub
		return vMapper.addAppVersion(appVersion);
	}

	@Override
	public int updateAppVersion(AppVersion appVersion) {
		// TODO Auto-generated method stub
		return vMapper.updateAppVersion(appVersion);
	}

	@Override
	public List<AppVersion> getAppVersionList(Integer id) {
		// TODO Auto-generated method stub
		return vMapper.getAppVersionList(id);
	}

	@Override
	public AppVersion getAppVersionInfo(Integer Id,Integer appId) {
		// TODO Auto-generated method stub
		return vMapper.getAppVersionInfo(Id,appId);
	}

	@Override
	public int deleteApkFile(Integer id) {
		// TODO Auto-generated method stub
		return vMapper.deleteApkFile(id);
	}

	@Override
	public int delAllVersionByAppid(Integer id) {
		// TODO Auto-generated method stub
		return vMapper.delAllVersionByAppid(id);
	}
	
}
