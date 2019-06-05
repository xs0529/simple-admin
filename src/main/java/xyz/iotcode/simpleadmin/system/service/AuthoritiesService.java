package xyz.iotcode.simpleadmin.system.service;

import xyz.iotcode.simpleadmin.system.entity.Authorities;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.iotcode.simpleadmin.system.entity.RoleAuthorities;
import xyz.iotcode.simpleadmin.system.pojo.dto.AddRoleAuthDTO;

import java.util.Set;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author 谢霜
 * @since 2019-06-04
 */
public interface AuthoritiesService extends IService<Authorities> {

    Set<Authorities> getAuthByUserId(Long userId);

    Set<Authorities> getAuthByUsername(String username);

    String[] getAuthStrByUserId(Long userId);

    boolean addRoleAuth(AddRoleAuthDTO dto);

    boolean sync();
}
