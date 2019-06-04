package xyz.iotcode.simpleadmin.system.service.impl;

import xyz.iotcode.simpleadmin.system.entity.Role;
import xyz.iotcode.simpleadmin.system.mapper.RoleMapper;
import xyz.iotcode.simpleadmin.system.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author 谢霜
 * @since 2019-06-04
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
