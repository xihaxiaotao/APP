package cn.appsys.dao.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import cn.appsys.pojo.AppInfo;
import cn.appsys.tools.PageSupport;

public interface AppInfoMapper {
	//模糊查询，分页显示app信息列表
	public  List<AppInfo>  getAppInfoList(@Param("app")AppInfo appinfo,@Param("page")PageSupport  page);
	
	//导出所有数据(模糊查询)到Excel
	public  List<AppInfo>  getAppInfoList2(@Param("app")AppInfo appinfo);
	
	//获取总记录数
	public  int  getAppCount(@Param("app")AppInfo appinfo);
	
	//APK名字重名验证,获取app详情信息
	public  AppInfo  getInfo(@Param("APKName")String apkName,@Param("id")Integer id);
	
	//新增APP基本信息
	public  int  addAppInfo(AppInfo appinfo);
	
	//修改APP基本信息
	public  int  updateAppInfo(AppInfo appinfo);
	
	//修改app版本id
	public int updateVersionId(@Param(value="versionId")Integer versionId,@Param(value="id")Integer appId);
	
	//删除Logo图片
	public  int  deleteLogoPic(@Param("id")Integer id);
	
	//删除app
	public  int  delApp(@Param("id")Integer id);
	
	//app上架/下架
	public  int  appSale(AppInfo appinfo);
	
	//查询所有APP的下载次数,生成数据图
	public  List<AppInfo>  getAppDownCount();
	
	
	
}
