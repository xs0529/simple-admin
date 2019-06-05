package xyz.iotcode.simpleadmin.system.controller;

import cn.hutool.crypto.SecureUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wf.jwtp.provider.Token;
import org.wf.jwtp.provider.TokenStore;
import xyz.iotcode.simpleadmin.common.Result;
import xyz.iotcode.simpleadmin.common.util.TokenUtils;
import xyz.iotcode.simpleadmin.system.entity.User;
import xyz.iotcode.simpleadmin.system.service.AuthoritiesService;
import xyz.iotcode.simpleadmin.system.service.RoleService;
import xyz.iotcode.simpleadmin.system.service.UserService;

import java.util.List;

/**
 * @author xieshuang
 * @date 2019-06-05 9:23
 */
@Api(tags = "个人信息")
@RestController
@RequestMapping("index")
public class MainController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthoritiesService authoritiesService;
    @Autowired
    private TokenStore tokenStore;

    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "rememberMe", value = "记住我", required = true)
    })
    @PostMapping("login")
    public Result login(String username, String password, @RequestParam(defaultValue = "false") boolean rememberMe) {
        User user = userService.getByUsername(username);
        if (user == null) {
            return Result.fail("账号不存在");
        } else if (!user.getPassword().equals(SecureUtil.sha256(password))) {
            return Result.fail("密码错误");
        } else if (user.getState() != 0) {
            return Result.fail("账号被锁定");
        }
        long overdue = 60*60*24*30;
        if (rememberMe){
            overdue = overdue*7;
        }
        List<String> stringList = roleService.userRoleStringList(user.getUserId());
        String[] roles = stringList.toArray(new String[stringList.size()]);
        String[] permissions = authoritiesService.getAuthStrByUserId(user.getUserId());
        Token token = tokenStore.createNewToken(String.valueOf(user.getUserId()), permissions, roles, overdue);
        return Result.ok("登录成功", token.getAccessToken());
    }

    @ApiOperation(value = "用户信息")
    @PostMapping("userInfo")
    public Result<User> getUser(){
        Long loginUserId = TokenUtils.getLoginUserId();
        User byUserId = userService.getByUserId(loginUserId);
        if (byUserId==null){
            return Result.fail("用户不存在");
        }
        byUserId.setRoles(roleService.userRoleList(loginUserId));
        byUserId.setAuthorities(authoritiesService.getAuthByUserId(loginUserId));
        return Result.ok(byUserId);
    }
}
