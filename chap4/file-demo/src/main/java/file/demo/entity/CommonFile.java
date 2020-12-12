package file.demo.entity;

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
 * @author 泽朋
 * @since 2019-04-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="CommonFile对象", description="")
public class CommonFile {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "图片路径")
    private String imgPath;

    @ApiModelProperty(value = "图片名称")
    private String imgName;

    @ApiModelProperty(value = "文件类型")
    private Integer type;
    private Long fId;
    
    private String domain;
    private Long fileSize;
    private Long userId;
    private Integer status;

}
