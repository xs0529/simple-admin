package xyz.iotcode.simpleadmin.system.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import xyz.iotcode.simpleadmin.common.exception.MyRuntimeException;
import xyz.iotcode.simpleadmin.system.entity.User;
import xyz.iotcode.simpleadmin.system.entity.UserRole;
import xyz.iotcode.simpleadmin.system.mapper.RoleMapper;
import xyz.iotcode.simpleadmin.system.mapper.UserMapper;
import xyz.iotcode.simpleadmin.system.pojo.dto.QueryUserPageDTO;
import xyz.iotcode.simpleadmin.system.service.UserService;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author 谢霜
 * @since 2019-06-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RoleMapper roleMapper;

    @Cacheable(value = "user:page", keyGenerator = "cacheKeyGenerator")
    @Override
    public IPage<User> getUserPage(QueryUserPageDTO dto) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(dto.getNickName())){
            queryWrapper.like(User::getNickName, dto.getNickName());
        }
        if (StringUtils.isNotBlank(dto.getSex())){
            queryWrapper.eq(User::getSex, dto.getSex());
        }
        if (StringUtils.isNotBlank(dto.getUsername())){
            queryWrapper.eq(User::getUsername, dto.getUsername());
        }
        IPage<User> page = page(new Page<>(dto.getPage(), dto.getSize()), queryWrapper);
        if (CollectionUtils.isEmpty(page.getRecords())){
            return page;
        }
        page.getRecords().forEach(user -> user.setRoles(roleMapper.getRoleByUserId(user.getUserId())));
        return page;
    }

    @Cacheable(value = "user:byUsername", key = "#username", unless = "#result==null")
    @Override
    public User getByUsername(String username) {
        return new User().selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User add(User user) {
        User one = getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, user.getUsername()));
        if (one!=null){
            throw new MyRuntimeException("用户名已存在！");
        }
        if (StringUtils.isBlank(user.getPassword())){
            user.setPassword("123456");
        }
        user.setPassword(SecureUtil.hmacMd5(user.getPassword()).toString());
        one.insert();
        if (!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles().forEach(role -> {
                UserRole userRole = new UserRole();
                userRole.setRoleId(role.getRoleId());
                userRole.setUserId(one.getUserId());
                userRole.insert();
            });
        }
        return one;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User update(User user) {
        String username = user.getUsername();
        user.setUsername(null);
        user.updateById();
        user.setUsername(username);
        if (!CollectionUtils.isEmpty(user.getRoles())){
            new UserRole().delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getUserId()));
            user.getRoles().forEach(role -> {
                UserRole userRole = new UserRole();
                userRole.setRoleId(role.getRoleId());
                userRole.setUserId(user.getUserId());
                userRole.insert();
            });
        }
        return user;
    }
}
