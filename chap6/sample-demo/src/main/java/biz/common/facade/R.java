package biz.common.facade;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
/**
 * 返回值包装
 * @param <T>
 */
@Getter @Setter
public class R<T> {

	public static final int SUCCESS = 0;
	public static final int UNLOGIN = 1;
	public static final int UNAUTHORIZED = 2;
	public static final int ERROR = 3;
	
	public static final int UNBINDING = 4;
	
	@ApiModelProperty(value = "返回码", required = true)
	private Integer code;
	@ApiModelProperty(value = "返回值包装", required = true)
	private T content;

	/**
	 * 通用错误code时调用
	 * @param content
	 * @return
	 */
	public static <Y> R<Y> error(Y content) {
		R<Y> r = new R<Y>(ERROR);
		r.setContent(content);
		return r;
	}
	/**
	 * 未登录
	 * @param content
	 * @return
	 */
	public static <Y> R<Y> unLogin(Y content) {
		R<Y> r = new R<Y>(UNLOGIN);
		r.setContent(content);
		return r;
	}
	/**
	 * 未绑定
	 * @param content
	 * @return
	 */
	public static <Y> R<Y> unBinding(Y content) {
		R<Y> r = new R<Y>(UNBINDING);
		r.setContent(content);
		return r;
	}
	/**
	 * 无权限
	 */
	public static <Y> R<Y> unAuthorized(Y content) {
		R<Y> r = new R<Y>(UNAUTHORIZED);
		r.setContent(content);
		return r;
	}
	/**
	 * 成功时调用
	 * @param content
	 * @return
	 */
	public static <Y> R<Y> ok(Y content) {
		R<Y> r = new R<Y>(SUCCESS);
		r.setContent(content);
		return r;
	}
	
	/*分界线：以下为构造函数和getter、setter*/
	
	public R(Integer code, T content) {
		this.code = code;
		this.content = content;
	}

	public R(Integer code) {
		this.code = code;
	}
}
