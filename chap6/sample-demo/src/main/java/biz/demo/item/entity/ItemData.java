package biz.demo.item.entity;

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
 * 
 * </p>
 *
 * @author 轻量级SSM开发
 * @since 2019-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("g_item_data")
@ApiModel(value="ItemData对象", description="")
public class ItemData extends Model<ItemData> {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Double temperature;

    private Double humidity;

    private Double pm2d5;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
