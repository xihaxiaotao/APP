package cn.appsys.dao.appversion;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppVersion;

public interface AppVersionMapper {
	//新增app版本信息
	public  int  addAppVersion(AppVersion  appVersion);
	
	//修改app版本信息
	public  int  updateAppVersion(AppVersion  appVersion);
	
	//查询某个app下的所有历史版本信息
	public  List<AppVersion>  getAppVersionList(@Param("id")Integer id);
	
	
	//根据Id查询最新版本的详情信息
	public  AppVersion  getAppVersionInfo(@Param("id")Integer Id,@Param("appId")Integer appId);
	
	//删除apk文件
	public int deleteApkFile(@Param("id")Integer id);
	
	//删除某个app下的所有版本信息
	public  int delAllVersionByAppid(@Param("id")Integer id);

}
