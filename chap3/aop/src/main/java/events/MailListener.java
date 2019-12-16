package events;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class MailListener {
	@Async
	@EventListener
	public void onApplicationEvent(UnlockRecordEvent event) {
		System.out.println("我监听到了一条开锁时间， 我想发个邮件通知下");
	}
}