package biz.demo.ac.mapper;

import biz.demo.ac.entity.AcUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author 轻量级SSM开发
 * @since 2019-11-01
 */
public interface AcUserMapper extends BaseMapper<AcUser> {

	AcUser selectByAuth(String username, String password);

}
