package xyz.iotcode.simpleadmin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 权限
 * </p>
 *
 * @author 谢霜
 * @since 2019-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_authorities")
@ApiModel(value="Authorities对象", description="权限")
public class Authorities extends Model<Authorities> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "授权标识")
    @TableId("authority")
    private String authority;

    @ApiModelProperty(value = "名称")
    @TableField("authority_name")
    private String authorityName;

    @ApiModelProperty(value = "模块")
    @TableField("parent_name")
    private String parentName;

    @ApiModelProperty(value = "排序号")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "url")
    @TableField("url")
    private String url;


    @Override
    protected Serializable pkVal() {
        return this.authority;
    }

}
