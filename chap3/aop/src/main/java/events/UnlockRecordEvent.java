package events;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;
public class UnlockRecordEvent extends ApplicationContextEvent {
	private static final long serialVersionUID = 1L;
	public UnlockRecordEvent(ApplicationContext source) {
		super(source);
		System.out.println("开了个锁， 新增一条记录");
	}
}