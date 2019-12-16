package biz.demo.ac;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginMember {
	@ApiModelProperty(example = "111", name = "账号(暂为手机号)")
	private String username;
	@ApiModelProperty(example = "111", name = "密码")
	private String password;
	@ApiModelProperty(hidden = true)
	private String clientId;
	@ApiModelProperty(example = "false", name = "记住我")
	private boolean rememberMe;
}
