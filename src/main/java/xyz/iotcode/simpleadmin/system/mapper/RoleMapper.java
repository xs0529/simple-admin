package xyz.iotcode.simpleadmin.system.mapper;

import org.apache.ibatis.annotations.Param;
import xyz.iotcode.simpleadmin.system.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author 谢霜
 * @since 2019-06-04
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> getRoleByUserId(@Param("userId") Long userId);

}
