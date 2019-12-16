package biz.demo.item.service.impl;

import biz.demo.item.entity.ItemAlarm;
import biz.demo.item.mapper.ItemAlarmMapper;
import biz.demo.item.service.ItemAlarmService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 告警 服务实现类
 * </p>
 *
 * @author 轻量级SSM开发
 * @since 2019-11-01
 */
@Service
public class ItemAlarmServiceImpl extends ServiceImpl<ItemAlarmMapper, ItemAlarm> implements ItemAlarmService {

}
