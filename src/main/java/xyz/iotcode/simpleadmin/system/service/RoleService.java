package xyz.iotcode.simpleadmin.system.service;

import xyz.iotcode.simpleadmin.system.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author 谢霜
 * @since 2019-06-04
 */
public interface RoleService extends IService<Role> {

    List<String> userRoleStringList(Long userId);

    List<Role> userRoleList(Long userId);

}
