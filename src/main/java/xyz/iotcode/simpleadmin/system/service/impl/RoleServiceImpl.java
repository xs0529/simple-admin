package xyz.iotcode.simpleadmin.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import xyz.iotcode.simpleadmin.system.entity.Role;
import xyz.iotcode.simpleadmin.system.mapper.RoleMapper;
import xyz.iotcode.simpleadmin.system.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<String> userRoleStringList(Long userId) {
        List<Role> roles = this.userRoleList(userId);
        if (CollectionUtils.isEmpty(roles)){
            return Collections.emptyList();
        }
        return roles.stream().map(Role::getLabel).collect(Collectors.toList());
    }

    @Override
    public List<Role> userRoleList(Long userId) {
        return roleMapper.getRoleByUserId(userId);
    }
}
