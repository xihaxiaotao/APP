package cn.appsys.service.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.backenduser.BackendUserMapper;
import cn.appsys.pojo.BackendUser;


@Service
public class BackendUserServiceImpl implements BackendUserService {

	
	@Autowired
	private  BackendUserMapper bMapper;
	@Override
	public BackendUser getBackendUser(BackendUser bUser) {
		// TODO Auto-generated method stub
		return bMapper.getBackendUser(bUser);
	}
	
}
