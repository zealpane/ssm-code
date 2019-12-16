package biz.demo.item.controller;


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
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import biz.demo.common.facade.PageRequest;
import biz.demo.item.entity.ItemAlarm;
import biz.demo.item.entity.ItemAlarm;
import biz.demo.item.service.ItemAlarmService;

/**
 * <p>
 * 告警 前端控制器
 * </p>
 *
 * @author 轻量级SSM开发
 * @since 2019-11-01
 */
@RestController
@RequestMapping("/item/itemAlarm")
public class ItemAlarmController {

	@Autowired
	ItemAlarmService itemAlarmService;
	
	@GetMapping
	public R getList(PageRequest pageRequest) {
		IPage<ItemAlarm> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
		QueryWrapper<ItemAlarm> qw = new QueryWrapper<ItemAlarm>();
		
		IPage<ItemAlarm> l = itemAlarmService.page(page, qw);
		return R.ok(l);
	}
	@PostMapping
	public R create(@RequestBody ItemAlarm entity) {
		itemAlarmService.save(entity);
		return R.ok("添加成功");
	}
	@PutMapping
	public R modify(@RequestBody ItemAlarm entity) {
		itemAlarmService.updateById(entity);
		return R.ok("修改成功");
	}
	
	@GetMapping("/{id}")
	public R getOne(@PathVariable Long id) {
		return R.ok(itemAlarmService.getById(id));
	}
	
	@DeleteMapping
	public R delete(Long id) {
		itemAlarmService.removeById(id);
		return R.ok("已删除");
	}
}
