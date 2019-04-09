package cn.appsys.controller.backend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.service.backend.AppService;
import cn.appsys.service.developer.AppCategoryService;
import cn.appsys.service.developer.AppVersionService;
import cn.appsys.service.developer.DataDictionaryService;
import cn.appsys.tools.PageSupport;


@Controller
@RequestMapping(value="/app/backend/")
public class AppCheckController {
	
	@Autowired
	private  AppService aService;
	
	@Autowired 
	private  DataDictionaryService  dService;
	
	@Autowired
	private  AppCategoryService cService;
	
	@Autowired
	private  AppVersionService  vService;
	
	//查询所有待审核的app列表
	@RequestMapping("/appListToCheck")
	public  String appListToCheck(AppInfo appinfo,Model model,Integer pageIndex){
		PageSupport  page=new PageSupport();
		page.setCurrentPageNo(pageIndex==null?1:pageIndex);
		
		page.setFrom((page.getCurrentPageNo()-1)*page.getPageSize());
		appinfo.setStatus(appinfo.getStatus()==null?1:appinfo.getStatus());
		appinfo.setFlatformId(appinfo.getFlatformId()==null?null:appinfo.getFlatformId());
		appinfo.setSoftwareName(appinfo.getSoftwareName()==null?null:appinfo.getSoftwareName());
		appinfo.setCategoryLevel1(appinfo.getCategoryLevel1()==null?null:appinfo.getCategoryLevel1());
		appinfo.setCategoryLevel2(appinfo.getCategoryLevel2()==null?null:appinfo.getCategoryLevel2());
		appinfo.setCategoryLevel3(appinfo.getCategoryLevel3()==null?null:appinfo.getCategoryLevel3());
		page.setTotalCount(aService.getAppCount(appinfo));
		model.addAttribute("pages",page);

		model.addAttribute("querySoftwareName", appinfo.getSoftwareName());
		model.addAttribute("queryFlatformId", appinfo.getFlatformId());
		model.addAttribute("queryCategoryLevel1", appinfo.getCategoryLevel1());
		model.addAttribute("queryCategoryLevel2", appinfo.getCategoryLevel2());
		model.addAttribute("queryCategoryLevel3", appinfo.getCategoryLevel3());
		
		model.addAttribute("categoryLevel1List",cService.getAppCategoryListByParentId(null));
		model.addAttribute("categoryLevel2List",cService.getAppCategoryListByParentId(appinfo.getCategoryLevel1()));
		model.addAttribute("categoryLevel3List",cService.getAppCategoryListByParentId(appinfo.getCategoryLevel2()));
		model.addAttribute("flatFormList",dService.getDataDictionaryList("APP_FLATFORM"));
		model.addAttribute("appInfoList",aService.getAppListToCheck(appinfo, page));
		return  "backend/applist";
	}

	//三级联动
	@RequestMapping(value="/categorylevellist.json",method=RequestMethod.GET)
	@ResponseBody
	public List<AppCategory> getAppCategoryList (Integer pid){
		if(pid==null||pid.equals("")) pid = null;
		return cService.getAppCategoryListByParentId(pid);
	}
	
	
	
	//页面跳转到“审核”页面
	@RequestMapping("/check")
	public  String  toCheckApp(Integer vid,Integer aid,Model model){
		model.addAttribute("appInfo", aService.getInfo(null, aid));
		model.addAttribute("appVersion",vService.getAppVersionInfo(vid, null) );
		return "backend/appcheck";
	}
	
	//审核app
	@RequestMapping("/checkApp")
	public String checkApp(AppInfo appinfo){
		aService.appSale(appinfo);
		return "redirect:/app/backend/appListToCheck";
	}
	
}
