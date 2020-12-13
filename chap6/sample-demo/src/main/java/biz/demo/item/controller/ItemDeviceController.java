package biz.demo.item.controller;


import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import biz.common.facade.PageRequest;
import biz.demo.item.entity.ItemDevice;
import biz.demo.item.service.ItemDeviceService;

import java.time.LocalDateTime;

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
	public R getList(PageRequest pageRequest,
					 @ApiParam("设备名称") @RequestParam(required = false) String name,
					 Integer deviceType
					 ) {
		IPage<ItemDevice> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
		QueryWrapper<ItemDevice> qw = new QueryWrapper<ItemDevice>();
		// 条件查询
		if (name != null && !"".equals(name)) {
			qw.eq("name", name);
		}
		if (deviceType != null && !"".equals(deviceType)) {
			qw.eq("device_type", deviceType);
		}
		IPage<ItemDevice> l = itemDeviceService.page(page, qw);
		return R.ok(l);
	}
	@PostMapping
	public R create(@RequestBody ItemDevice entity) {
		entity.setCreateTime(LocalDateTime.now());
		itemDeviceService.save(entity);
		return R.ok("添加成功");
	}
	@PutMapping
	public R modify(@RequestBody ItemDevice entity) {
		entity.setCreateTime(LocalDateTime.now());
		itemDeviceService.updateById(entity);
		return R.ok("修改成功");
	}
	
	@GetMapping("/{id}")
	public R getOne(@PathVariable Integer id) {
		return R.ok(itemDeviceService.getById(id));
	}
	
	@DeleteMapping
	public R delete(Integer id) {
		itemDeviceService.removeById(id);
		return R.ok("已删除");
	}
}
