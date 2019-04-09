package cn.appsys.service.developer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.devuser.DevUserMapper;
import cn.appsys.pojo.DevUser;

@Service
public class DevUserServiceImpl implements DevUserService {

	@Autowired
	private DevUserMapper dMapper;
	
	@Override
	public DevUser getDevUser(DevUser user) {
		// TODO Auto-generated method stub
		return dMapper.getDevUser(user);
	}
	
}
