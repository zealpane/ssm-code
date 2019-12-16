package biz.demo.ac.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author 轻量级SSM开发
 * @since 2019-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("g_ac_user_info")
@ApiModel(value="AcUserInfo对象", description="用户信息")
public class AcUserInfo extends Model<AcUserInfo> {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    @ApiModelProperty(value = "身份证号码")
    private String idCard;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "住址")
    private String address;


    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
