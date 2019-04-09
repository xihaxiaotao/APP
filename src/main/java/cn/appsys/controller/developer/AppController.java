package cn.appsys.controller.developer;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.developer.AppCategoryService;
import cn.appsys.service.developer.AppInfoService;
import cn.appsys.service.developer.AppVersionService;
import cn.appsys.service.developer.DataDictionaryService;
import cn.appsys.tools.ExcelUtils;
import cn.appsys.tools.PageSupport;


@Controller
@RequestMapping(value="/app/dev/")
public class AppController {
	@Autowired
	private  AppInfoService  aService;
	
	@Autowired 
	private  DataDictionaryService  dService;
	
	@Autowired
	private  AppCategoryService cService;
	
	@Autowired
	private  AppVersionService  vService;
	
	//加载app信息列表，分页显示，三级分类
	@RequestMapping("/applist")
	public  String  getAppList(AppInfo appinfo,Model model,Integer pageIndex){
		PageSupport  page=new PageSupport();
		page.setCurrentPageNo(pageIndex==null?1:pageIndex);
		page.setTotalCount(aService.getAppCount(appinfo));
		page.setFrom((page.getCurrentPageNo()-1)*page.getPageSize());
		appinfo.setStatus(appinfo.getStatus()==null?null:appinfo.getStatus());
		appinfo.setFlatformId(appinfo.getFlatformId()==null?null:appinfo.getFlatformId());
		appinfo.setSoftwareName(appinfo.getSoftwareName()==null?null:appinfo.getSoftwareName());
		appinfo.setCategoryLevel1(appinfo.getCategoryLevel1()==null?null:appinfo.getCategoryLevel1());
		appinfo.setCategoryLevel2(appinfo.getCategoryLevel2()==null?null:appinfo.getCategoryLevel2());
		appinfo.setCategoryLevel3(appinfo.getCategoryLevel3()==null?null:appinfo.getCategoryLevel3());
		model.addAttribute("pages",page);

		model.addAttribute("querySoftwareName", appinfo.getSoftwareName());
		model.addAttribute("queryStatus", appinfo.getStatus());
		model.addAttribute("queryFlatformId", appinfo.getFlatformId());
		model.addAttribute("queryCategoryLevel1", appinfo.getCategoryLevel1());
		model.addAttribute("queryCategoryLevel2", appinfo.getCategoryLevel2());
		model.addAttribute("queryCategoryLevel3", appinfo.getCategoryLevel3());
		
		model.addAttribute("categoryLevel1List",cService.getAppCategoryListByParentId(null));
		model.addAttribute("categoryLevel2List",cService.getAppCategoryListByParentId(appinfo.getCategoryLevel1()));
		model.addAttribute("categoryLevel3List",cService.getAppCategoryListByParentId(appinfo.getCategoryLevel2()));
		
		model.addAttribute("statusList",dService.getDataDictionaryList("APP_STATUS"));
		model.addAttribute("flatFormList",dService.getDataDictionaryList("APP_FLATFORM"));
		model.addAttribute("appInfoList",aService.getAppInfoList(appinfo, page));
		return  "developer/appinfolist";
	}
	
	//三级联动
	@RequestMapping(value="/categorylevellist.json",method=RequestMethod.GET)
	@ResponseBody
	public List<AppCategory> getAppCategoryList (Integer pid){
		if(pid==null||pid.equals("")) pid = null;
		return cService.getAppCategoryListByParentId(pid);
	}
	
	
	//获取——数据字典表
	@RequestMapping(value="/datadictionarylist.json",method=RequestMethod.GET)
	@ResponseBody
	public List<DataDictionary> getDatadictionarylist (String tcode){
		return dService.getDataDictionaryList(tcode);
	}
	
	//页面跳转到——“新增APP页”
	@RequestMapping("/toAddApp")
	public  String  toAddApp(@ModelAttribute("appInfo") AppInfo info){
		return "developer/appinfoadd";
	}
	
	//新增APP信息,上传文件
	@RequestMapping("/addApp")
	public  String  addApp(AppInfo info,@RequestParam(value="a_logoPicPath",required= false)MultipartFile file,HttpSession  session,HttpServletRequest request) throws Exception{
		if(!file.isEmpty()){
			String  path=session.getServletContext().getRealPath("/statics/uploadfiles");
			//原文件名
			String fileName=file.getOriginalFilename();
			//原文件名后缀
			String  prefix=fileName.substring(fileName.lastIndexOf(".")+1);
			System.out.println("后缀："+prefix);
			//文件大小不得超过50KB
			if(file.getSize()>50*1024){ 
				request.setAttribute("fileUploadError", "文件大小不得超过50KB!");
				return "developer/appinfoadd";
		    //判断文件类型
			}else if(prefix.equalsIgnoreCase("jpg")||prefix.equalsIgnoreCase("jpeg")||prefix.equalsIgnoreCase("png")){
				file.transferTo(new File(path+File.separator+fileName));
				DevUser user=(DevUser)session.getAttribute("devUserSession");
				//图片路径
				info.setLogoPicPath(request.getContextPath()+"/statics/uploadfiles/"+fileName);
				//系统路径
				info.setLogoLocPath(path+File.separator+fileName);
				info.setCreatedBy(user.getId());
				info.setDevId(user.getId());
				info.setCreationDate(new Date());
			}else{
				request.setAttribute("fileUploadError", "文件格式不正确!");
				return "developer/appinfoadd";
			}	
		}
		int num=aService.addAppInfo(info);
		if(num>0){
			System.out.println("新增成功");
			return "redirect:/app/dev/applist"; //新增成功
		}else{
			System.out.println("新增失败");
			return "developer/appinfoadd";//新增失败
		}

	}
	
	//APK名字重名验证
	@RequestMapping(value="/apkexist.json",method=RequestMethod.GET)
	@ResponseBody
	public Object checkAPKName (String APKName){
		Map<String,String>  nameMap=new HashMap<String,String>();
		AppInfo app=aService.getInfo(APKName, null);
        if(app!=null){
        	nameMap.put("APKName","exist");
        }else{
        	nameMap.put("APKName","noexist");
        }
		if(APKName==null){
			nameMap.put("APKName","empty");
		}
		return nameMap;
	}
	
	
	//修改App基础信息
	@RequestMapping("/toUpdateApp")
	public String  toUpdateApp(Integer id,Model model){
		//获取app详情信息
		AppInfo app=aService.getInfo(null, id);
		model.addAttribute("appInfo", app);
		return "developer/appinfomodify";
	}
	

	//修改APP基本信息
	@RequestMapping("/updateApp")
	public  String updateApp(AppInfo info,String status,@RequestParam(value="a_logoPicPath",required= false)MultipartFile file,HttpSession  session,HttpServletRequest request) throws Exception{
		DevUser user=(DevUser)session.getAttribute("devUserSession");
		
		if(!file.isEmpty()){
			String  path=session.getServletContext().getRealPath("/statics/uploadfiles");
			//原文件名
			String fileName=file.getOriginalFilename();
			//原文件名后缀
			String  prefix=fileName.substring(fileName.lastIndexOf(".")+1);
			System.out.println("后缀："+prefix);
			
			//文件大小不得超过50KB
			if(file.getSize()>50*1024){ 
				request.setAttribute("fileUploadError", "文件大小不得超过50KB!");
				return "developer/appinfomodify";
		    //判断文件类型
			}else if(prefix.equalsIgnoreCase("jpg")||prefix.equalsIgnoreCase("jpeg")||prefix.equalsIgnoreCase("png")){
				file.transferTo(new File(path+File.separator+fileName));
				//图片路径
				info.setLogoPicPath(request.getContextPath()+"/statics/uploadfiles/"+fileName);
				//系统路径
				info.setLogoLocPath(path+File.separator+fileName);
			}else{
				request.setAttribute("fileUploadError", "文件格式不正确!");
				return "developer/appinfomodify";
			}	
		}else{
			info.setLogoPicPath(null);
			info.setLogoLocPath(null);
		}
		info.setModifyBy(user.getId());
		info.setModifyDate(new Date());
		//如果点击“保存并再次提交审核”，app状态修改为 待审核
		if(status!=null){
			if(info.getStatusName().equals("审核未通过")){
				info.setStatus(1);  //如果app状态为  审核未通过，修改状态为  待审核
			}
		}else{
			info.setStatus(info.getStatus());
		}
		int num=aService.updateAppInfo(info);
		if(num>0){
			System.out.println("修改成功");
			return "redirect:/app/dev/applist"; //新增成功
		}else{
			System.out.println("修改失败");
			return "developer/appinfomodify";//新增失败
		}
	}
	
	//页面跳转到--新增app版本新息
	@RequestMapping("/toAddAppVersion")
	public  String  toAddAPPVersion(Model model,Integer id,AppVersion appVersion){
		//查询所有历史版本列表
		appVersion.setAppId(id);
		model.addAttribute("appVersionList", vService.getAppVersionList(id));
		model.addAttribute("appVersion",appVersion);
		return  "developer/appversionadd";
	}
	
	//新增app版本新息
	@RequestMapping("/addAppVersion")
	public  String addAppVersion(AppVersion  appVersion,@RequestParam(value="a_downloadLink",required= false)MultipartFile file,HttpSession session,HttpServletRequest request) throws Exception{
		//如果文件非空
		if(!file.isEmpty()){
			//获取根路径
			String path=session.getServletContext().getRealPath("/statics/uploadfiles");
			//获取原文件名
			String fileName=file.getOriginalFilename();
			//获取文件名后缀
			String prefix=FilenameUtils.getExtension(fileName);
			//String prefix2=fileName.substring(fileName.lastIndexOf(".")+1);
			System.out.println("apk文件后缀："+prefix);
			if(file.getSize()>1024*1024*500){  //文件大小不超过500MB
				request.setAttribute("fileUploadError", "文件大小不超过500MB!");
				return "developer/appversionadd";
			}else if(prefix.equalsIgnoreCase("apk")){
				file.transferTo(new File(path+File.separator+fileName));//执行上传
				DevUser  user=(DevUser)session.getAttribute("devUserSession");
				appVersion.setCreatedBy(user.getId());
				appVersion.setCreationDate(new Date());
				//项目路径
				appVersion.setDownloadLink(request.getContextPath()+"/statics/uploadfiles/"+fileName);
				//系统路径
				appVersion.setApkLocPath(path+File.separator+fileName);
				//文件名
				appVersion.setApkFileName(fileName);
				
			}else{
				request.setAttribute("fileUploadError", "只能是apk文件!");
				return "developer/appversionadd";
			}
		}
		int num=vService.addAppVersion(appVersion);
		if(num>0){
			//修改app基本信息中的versionid
			aService.updateVersionId(appVersion.getId(), appVersion.getAppId());  //versionid,appid
			System.out.println("新增版本成功");
			return "redirect:/app/dev/applist"; //新增成功
		}else{
			System.out.println("新增版本失败");
			return "developer/appversionadd";//新增失败
		}
	}
	
	
	//页面跳转到-“修改app版本页”
	@RequestMapping("/toUpdateAppVersion")
	public String  toUpdateAppVersion(Integer vid,Integer aid,Model model){
		//查询最新版本详情信息
		model.addAttribute("appVersion", vService.getAppVersionInfo(vid,null));
		model.addAttribute("appVersionList", vService.getAppVersionList(aid));
		return "developer/appversionmodify";
	}
	
	//修改app版本信息
	@RequestMapping("/updateAppVersion")
	public String updateAppVersion(AppVersion appVersion,HttpSession session,HttpServletRequest request,@RequestParam(value="a_apkFile",required= false)MultipartFile file) throws Exception{
		DevUser  user=(DevUser)session.getAttribute("devUserSession");
		//如果文件非空
		if(!file.isEmpty()){
			//获取根路径
			String path=session.getServletContext().getRealPath("/statics/uploadfiles");
			//获取原文件名
			String fileName=file.getOriginalFilename();
			//获取文件名后缀
			String prefix=FilenameUtils.getExtension(fileName);
			//String prefix2=fileName.substring(fileName.lastIndexOf(".")+1);
			System.out.println("apk文件后缀："+prefix);
			if(file.getSize()>1024*1024*500){  //文件大小不超过500MB
				request.setAttribute("fileUploadError", "文件大小不超过500MB!");
				return "developer/appversionadd";
			}else if(prefix.equalsIgnoreCase("apk")){
				file.transferTo(new File(path+File.separator+fileName));//执行上传
				
				appVersion.setModifyBy(user.getId());
				appVersion.setModifyDate(new Date());
				//项目路径
				appVersion.setDownloadLink(request.getContextPath()+"/statics/uploadfiles/"+fileName);
				//系统路径
				appVersion.setApkLocPath(path+File.separator+fileName);
				//文件名
				appVersion.setApkFileName(fileName);
				
			}else{
				request.setAttribute("fileUploadError", "只能是apk文件!");
				return "developer/appversionadd";
			}
		}else{
			appVersion.setModifyBy(user.getId());
			appVersion.setModifyDate(new Date());
			appVersion.setApkFileName(null);
			appVersion.setApkLocPath(null);
			appVersion.setDownloadLink(null);
		}
		
		int num=vService.updateAppVersion(appVersion);
		if(num>0){
			return "redirect:/app/dev/applist";
		}else{
			return "developer/appversionmodify";
		}	
	}
	
	

	//删除logo图片或者apk文件
	@RequestMapping("/delfile.json")
	@ResponseBody
	public  Object  delLogoOrApk(Integer id,String flag) throws Exception{
		Map<String,String>  resultMap=new HashMap<String,String>();
		String  filePath="";
		if(flag.equals("logo")){  //删除Logo图片
			filePath=(aService.getInfo(null, id)).getLogoLocPath();
			System.out.println("logo路径"+filePath);
			File file=new File(filePath);
			if(file.exists()){
				System.out.println("文件存在");
				file.delete();//删除服务器中的apk文件
			}
			int num=aService.deleteLogoPic(id); //id为aid
			resultMap.put("result", num>0?"success":"failed");
		}else if(flag.equals("apk")){  //删除apk文件
			filePath=(vService.getAppVersionInfo(null,id)).getApkLocPath();
			System.out.println("apk路径"+filePath);
			File file=new File(filePath);
			if(file.exists()){
				System.out.println("文件存在");
				file.delete();//删除服务器中的app图片
			}
			int num=vService.deleteApkFile(id);//id为vid
			resultMap.put("result", num>0?"success":"failed");
		}
		return  resultMap;
	}
	
	
	
	//  查看app详情信息(基本信息+版本信息)
	//  REST风格
	//	@RequestMapping(value="/appview/{id}")
	//	(@PathVariable Integer id)
	@RequestMapping("/appview")
	public String  getAppInfoAndVersionInfo(Integer id,Model model){
		model.addAttribute("appInfo", aService.getInfo(null, id));
		model.addAttribute("appVersionList", vService.getAppVersionList(id));
		return "developer/appinfoview";
	}
	
	
	
	//删除app
	//先删除所有的apk文件和app图片,然后删除app的所有版本,最后删除app
	@RequestMapping("/delapp.json")
	@ResponseBody
	public Object  delApp(Integer id){//id为appinfoid
		Map<String,String>  resultMap=new HashMap<String,String>();
		String  filePath="";
		filePath=(vService.getAppVersionInfo(null,id)).getApkLocPath();
		if(filePath!=null){
			File file=new File(filePath);
			if(file.exists()){
				System.out.println("文件存在");
				file.delete();//删除服务器中的apk文件
			}
		}
        vService.delAllVersionByAppid(id); //删除所有app版本
       
		filePath=(aService.getInfo(null, id)).getLogoLocPath();
		if(filePath!=null){
			File file2=new File(filePath);
			if(file2.exists()){
				System.out.println("文件存在");
				file2.delete();//删除服务器中的app图片
			}
		}
		int num2=aService.delApp(id);
	    resultMap.put("delResult", num2>0?"true":"false");
	    if(id==null){
	    	resultMap.put("delResult","notexist");
	    }
		return resultMap;
	}
	
	
	//上架/下架
	@RequestMapping("/sale.json")
	@ResponseBody
	public  Object  appSale(Integer id,AppInfo appinfo){
		Map<String,Object>  resultMap=new HashMap<String,Object>();
		appinfo.setId(id);
		AppInfo appinfo2=aService.getInfo(null, id);
		appinfo.setStatus(appinfo2.getStatus());
		if(appinfo2.getStatus()==2||appinfo2.getStatus()==5){
			appinfo.setStatus(4);
			appinfo.setOnSaleDate(new Date());
		}else if(appinfo2.getStatus()==4){
			appinfo.setStatus(5);
			appinfo.setOffSaleDate(new Date());
		}
		
		int num=aService.appSale(appinfo);
		resultMap.put("errorCode", 0);
		if(num>0){
			resultMap.put("resultMsg", "success");
		}else{
			resultMap.put("resultMsg", "failed");
		}
		return resultMap;
	}
	
	
	//导出表格(导出所有数据)
	@RequestMapping("/exportAll")
	@ResponseBody
	public  int   export(String softwareName,Integer status,Integer flatformId,
				         Integer categoryLevel1, Integer categoryLevel2,
				         Integer categoryLevel3){
		 System.out.println("导出所有数据");
		 String [] title={"软件ID", "软件名称", "APK名称", "软件大小", "所属平台", "所属分类","状态", "下载次数", "最新版本"};
		 String sheetName="APP信息表(所有)";

		 AppInfo  appinfo=new AppInfo();
		    
		 appinfo.setSoftwareName(softwareName==null?null:softwareName);
		 appinfo.setStatus(status==null?null:status);
		 appinfo.setFlatformId(flatformId==null?null:flatformId);
		 appinfo.setCategoryLevel1(categoryLevel1==null?null:categoryLevel1);
		 appinfo.setCategoryLevel2(categoryLevel2==null?null:categoryLevel2);
		 appinfo.setCategoryLevel3(categoryLevel3==null?null:categoryLevel3);
		 
		 //生成Excel的路径
		 String filePath="E:APP所有数据信息.xls";
		 List<AppInfo>  appInfoList=aService.getAppInfoList2(appinfo);
		 ExcelUtils.writeExcel(filePath,sheetName, title, appInfoList);
		 return  1;
	}
	
	
	//导出表格(导出本页数据)
	@RequestMapping("/exportThis")
	@ResponseBody
	public int  exportThisPage(String softwareName,Integer status,Integer flatformId,
			                     Integer categoryLevel1, Integer categoryLevel2,
			                     Integer categoryLevel3,Integer pageIndex){
		System.out.println("导出本页数据");
		String [] title={"软件ID", "软件名称", "APK名称", "软件大小", "所属平台", "所属分类","状态", "下载次数", "最新版本"};
		String sheetName="APP信息表(本页)";
		PageSupport  page=new PageSupport();
		page.setCurrentPageNo(pageIndex==null?1:pageIndex);
		page.setFrom((page.getCurrentPageNo()-1)*page.getPageSize());
		
	    AppInfo  appinfo=new AppInfo();
	    
	    appinfo.setSoftwareName(softwareName==null?null:softwareName);
	    appinfo.setStatus(status==null?null:status);
	    appinfo.setFlatformId(flatformId==null?null:flatformId);
	    appinfo.setCategoryLevel1(categoryLevel1==null?null:categoryLevel1);
	    appinfo.setCategoryLevel2(categoryLevel2==null?null:categoryLevel2);
	    appinfo.setCategoryLevel3(categoryLevel3==null?null:categoryLevel3);
	    //生成Excel的路径
	    String filePath="E:APP局部数据信息.xls";
		List<AppInfo>  appInfoList=aService.getAppInfoList(appinfo,page);
		ExcelUtils.writeExcel(filePath,sheetName, title, appInfoList);
		return 1;
	}
	
	
	
	//页面跳转到数据图页面
	@RequestMapping("/toAppEchartsData")
	public  String  toDate(){
		return "developer/appInfoEcharts";
	}
	
	//生成数据图
	@RequestMapping(value="/echartsData")
    @ResponseBody
    public List<AppInfo> initChart(){
        return aService.getAppDownCount();
    }

	

	
	
}
