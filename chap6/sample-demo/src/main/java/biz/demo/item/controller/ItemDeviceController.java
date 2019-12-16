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
import biz.demo.item.entity.ItemDevice;
import biz.demo.item.service.ItemDeviceService;

/**
 * <p>
 * 设备 前端控制器
 * </p>
 *
 * @author 轻量级SSM开发
 * @since 2019-11-01
 */
@RestController
@RequestMapping("/item/itemDevice")
public class ItemDeviceController {

	@Autowired
	ItemDeviceService itemDeviceService;
	
	@GetMapping
	public R getList(PageRequest pageRequest) {
		IPage<ItemDevice> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
		QueryWrapper<ItemDevice> qw = new QueryWrapper<ItemDevice>();
		
		IPage<ItemDevice> l = itemDeviceService.page(page, qw);
		return R.ok(l);
	}
	@PostMapping
	public R create(@RequestBody ItemDevice entity) {
		itemDeviceService.save(entity);
		return R.ok("添加成功");
	}
	@PutMapping
	public R modify(@RequestBody ItemDevice entity) {
		itemDeviceService.updateById(entity);
		return R.ok("修改成功");
	}
	
	@GetMapping("/{id}")
	public R getOne(@PathVariable Long id) {
		return R.ok(itemDeviceService.getById(id));
	}
	
	@DeleteMapping
	public R delete(Long id) {
		itemDeviceService.removeById(id);
		return R.ok("已删除");
	}
}
