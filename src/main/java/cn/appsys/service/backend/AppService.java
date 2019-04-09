package cn.appsys.service.backend;

import java.util.List;


import cn.appsys.pojo.AppInfo;
import cn.appsys.tools.PageSupport;


public interface AppService {
	//查询所有待审核的app列表
	 public  List<AppInfo>  getAppListToCheck(AppInfo appinfo,PageSupport  page);
	 
	//获取总记录数
	public  int  getAppCount(AppInfo appinfo);
	
	//APK名字重名验证,获取app详情信息
	public  AppInfo  getInfo(String apkName,Integer id);
	
	//审核通过/审核未通过
	public  int  appSale(AppInfo appinfo);
}
