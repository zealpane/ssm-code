package biz.demo.ac.service;

import biz.demo.ac.entity.AcUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author 轻量级SSM开发
 * @since 2019-11-01
 */
public interface AcUserService extends IService<AcUser> {

	/**
	 * 如果比对成功，则返回当前用户信息
	 * @param username
	 * @param password
	 * @return
	 */
	AcUser login(String username, String password);

}
