package biz.demo.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SchedulerTask {

    private int count=0;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    SessionDAO sessionDao;
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Scheduled(fixedRate = 60000)
    public void reportCurrentTime() {
        System.out.println("现在时间：" + dateFormat.format(new Date()));
        System.out.println("当前在线人数：" + sessionDao.getActiveSessions().size());
    }
    
    @Scheduled(fixedDelay = 3500000)
    public void b() {
    	BaiduAudio.baiduAudio();
    	System.out.println("更新百度audio的key");
    }
}

//@Scheduled 参数可以接受两种定时的设置，一种是我们常用的cron="*/6 * * * * ?",一种是 fixedRate = 6000，两种都表示每隔六秒打印一下内容。
/**
fixedRate 说明

@Scheduled(fixedRate = 6000) ：上一次开始执行时间点之后6秒再执行
@Scheduled(fixedDelay = 6000) ：上一次执行完毕时间点之后6秒再执行
@Scheduled(initialDelay=1000, fixedRate=6000) ：第一次延迟1秒后执行，之后按 fixedRate 的规则每6秒执行一次
*/