package xyz.iotcode.simpleadmin.system.pojo.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author xieshuang
 * @date 2019-06-05 10:43
 */
@Data
@ApiModel("给角色添加权限的DTO")
public class AddRoleAuthDTO {

    private Long roleId;

    private List<String> authList;
}
