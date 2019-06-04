package xyz.iotcode.simpleadmin.common.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("分页DTO")
public class PageDTO {

    @ApiModelProperty(value = "页码", required = true, example = "1")
    private Integer page;
    @ApiModelProperty(value = "每页数量", required = true, example = "10")
    private Integer size;
}
