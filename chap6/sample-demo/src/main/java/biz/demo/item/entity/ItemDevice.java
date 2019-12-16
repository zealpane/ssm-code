package biz.demo.item.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 设备
 * </p>
 *
 * @author 轻量级SSM开发
 * @since 2019-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("g_item_device")
@ApiModel(value="ItemDevice对象", description="设备")
public class ItemDevice extends Model<ItemDevice> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String des;

    @ApiModelProperty(value = "uuid")
    private String uuid;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "最新数据json序列化")
    private String dataJson;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
