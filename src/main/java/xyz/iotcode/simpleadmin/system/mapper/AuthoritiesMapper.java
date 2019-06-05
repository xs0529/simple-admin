package xyz.iotcode.simpleadmin.system.mapper;

import org.apache.ibatis.annotations.Param;
import xyz.iotcode.simpleadmin.system.entity.Authorities;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.iotcode.simpleadmin.system.entity.RoleAuthorities;
import xyz.iotcode.simpleadmin.system.pojo.dto.AddRoleAuthDTO;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author 谢霜
 * @since 2019-06-04
 */
public interface AuthoritiesMapper extends BaseMapper<Authorities> {

    Set<Authorities> getAuthByUserId(@Param("userId") Long userId);

    Set<Authorities> getAuthByUsername(@Param("username") String username);

    boolean saveAll(@Param("dto") AddRoleAuthDTO dto);
}
