package biz.demo.ac.service.impl;

import biz.demo.ac.entity.AcUser;
import biz.demo.ac.mapper.AcUserMapper;
import biz.demo.ac.service.AcUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author 轻量级SSM开发
 * @since 2019-11-01
 */
@Service
public class AcUserServiceImpl extends ServiceImpl<AcUserMapper, AcUser> implements AcUserService {

	@Autowired
	AcUserMapper acUserMapper;
	
	@Override
	public AcUser login(String username, String password) {
		AcUser user = acUserMapper.selectByAuth(username, password);
		return user;
	}

}
