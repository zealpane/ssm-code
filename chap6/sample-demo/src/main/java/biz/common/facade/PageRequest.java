package biz.common.facade;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 页码请求
 * @author 泽朋
 *
 */
@Data
public class PageRequest implements Serializable {

	private static final long serialVersionUID = -4869594085374385813L;

	// 这个参数默认为true，即表示要分页查询
	private boolean isPaging = true;
	
	@ApiModelProperty(value="每页显示条数",example="10") // 为什么其它属性大多用不了
	private int size = 10;

	@ApiModelProperty(value="当前页",example="1")
    private int current = 1;
    
}
