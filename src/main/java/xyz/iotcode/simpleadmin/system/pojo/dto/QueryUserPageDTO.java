package xyz.iotcode.simpleadmin.system.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xyz.iotcode.simpleadmin.common.pojo.dto.PageDTO;

@Data
@ApiModel("查询用户分页数据的DTO")
public class QueryUserPageDTO extends PageDTO {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "性别")
    private String sex;

}
