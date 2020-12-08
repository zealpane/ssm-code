package biz.demo.ac.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import biz.common.facade.PageRequest;
import biz.common.facade.R;
import biz.demo.ac.LoginMember;
import biz.demo.ac.entity.AcUser;
import biz.demo.ac.service.AcUserService;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author 轻量级SSM开发
 * @since 2019-11-01
 */
@RestController
@RequestMapping("/ac/acUser")
public class AcUserController {

	@Autowired
	AcUserService acUserService;
	
	@ApiOperation(value = "登录")
	@PostMapping("/login")
	public R login(@RequestBody LoginMember login) {
		Subject subject = SecurityUtils.getSubject();

		
		AcUser user = acUserService.login(login.getUsername(), String.valueOf(login.getPassword()));
		
		if (user == null) {// 密码错误
			return R.error("账号或密码错误");
		}
		
		UsernamePasswordToken token = new UsernamePasswordToken(user.getId() + "", login.getPassword());
		token.setRememberMe(login.isRememberMe());

		try {
			subject.login(token);
		} catch (Exception e) {
			return R.error("登录失败");
		}
		return R.ok(user);
	}
	
	@GetMapping
	public R getList(PageRequest pageRequest) {
		IPage<AcUser> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
		QueryWrapper<AcUser> qw = new QueryWrapper<AcUser>();
		
		IPage<AcUser> l = acUserService.page(page, qw);
		return R.ok(l);
	}
	@PostMapping
	public R create(@RequestBody AcUser entity) {
		acUserService.save(entity);
		return R.ok("添加成功");
	}
	@PutMapping
	public R modify(@RequestBody AcUser entity) {
		acUserService.updateById(entity);
		return R.ok("修改成功");
	}
	
	@GetMapping("/{id}")
	public R getOne(@PathVariable Long id) {
		return R.ok(acUserService.getById(id));
	}
	
	@DeleteMapping
	public R delete(Long id) {
		acUserService.removeById(id);
		return R.ok("已删除");
	}
}
