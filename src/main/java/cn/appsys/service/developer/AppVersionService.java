package cn.appsys.service.developer;

import java.util.List;



import cn.appsys.pojo.AppVersion;


public interface AppVersionService {
	    //新增app版本信息
		public  int  addAppVersion(AppVersion  appVersion);
		//修改app版本信息
		public  int  updateAppVersion(AppVersion  appVersion);
		
		//查询某个app下的所有历史版本信息
		public  List<AppVersion>  getAppVersionList(Integer id);
		
		//根据Id查询最新版本的详情信息
		public  AppVersion  getAppVersionInfo(Integer Id,Integer appId);
		
		//删除apk文件
		public int deleteApkFile(Integer id);
		
		//删除某个app下的所有版本信息
		public  int delAllVersionByAppid(Integer id);
}
