package xyz.iotcode.simpleadmin.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author 谢霜
 * @since 2019-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
@ApiModel(value="User对象", description="用户")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableId("user_id")
    private Long userId;

    @ApiModelProperty(value = "账号")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty(value = "头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty(value = "性别")
    @TableField("sex")
    private String sex;

    @ApiModelProperty(value = "手机号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "邮箱是否验证，0未验证，1已验证")
    @TableField("email_verified")
    private Integer emailVerified;

    @ApiModelProperty(value = "真实姓名")
    @TableField("true_name")
    private String trueName;

    @ApiModelProperty(value = "身份证号")
    @TableField("id_card")
    private String idCard;

    @ApiModelProperty(value = "出生日期")
    @TableField("birthday")
    private Date birthday;

    @ApiModelProperty(value = "部门id")
    @TableField("department_id")
    private Integer departmentId;

    @ApiModelProperty(value = "状态，0正常，1冻结")
    @TableField("state")
    private Integer state;

    @ApiModelProperty(value = "注册时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private Date updateTime;

    @TableField(exist=false)
    @ApiModelProperty("用户角色")
    private List<Role> roles;

    @TableField(exist=false)
    @ApiModelProperty("用户权限")
    private Collection<Authorities> authorities;

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
