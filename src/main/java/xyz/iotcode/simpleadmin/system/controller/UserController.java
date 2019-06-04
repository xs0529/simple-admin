package xyz.iotcode.simpleadmin.system.controller;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;
import xyz.iotcode.simpleadmin.common.Result;
import xyz.iotcode.simpleadmin.system.entity.User;
import xyz.iotcode.simpleadmin.system.pojo.dto.QueryUserPageDTO;
import xyz.iotcode.simpleadmin.system.service.UserService;

import java.util.Arrays;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author 谢霜
 * @since 2019-06-04
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequiresPermissions("user:page")
    @ApiOperation(value = "查询所有用户", nickname = "user:page")
    @PostMapping
    public Result<IPage> list(@RequestBody QueryUserPageDTO dto) {
        return Result.ok(userService.getUserPage(dto));
    }

    @RequiresPermissions("user:add")
    @ApiOperation(value = "添加用户", nickname = "user:add")
    @PostMapping("add")
    public Result<User> add(@RequestBody User user) {
        return Result.ok(userService.add(user));
    }

    @RequiresPermissions("user:update")
    @ApiOperation(value = "修改用户", nickname = "user:update")
    @PostMapping("update")
    public Result<User> update(@RequestBody User user) {
        return Result.ok(userService.update(user));
    }

    @RequiresPermissions("user:state")
    @ApiOperation(value = "修改用户状态", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "state", value = "状态：0正常，1冻结", required = true, dataType = "Integer", paramType = "form"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @PostMapping("/state")
    public Result updateState(Long userId, Integer state) {
        if (state == null || (state != 0 && state != 1)) {
            return Result.fail("state值需要在[0,1]中");
        }
        User user = new User();
        user.setUserId(userId);
        user.setState(state);
        if (userService.updateById(user)) {
            return Result.ok();
        }
        return Result.fail();
    }

    /*@RequiresPermissions("user:psw")
    @ApiOperation(value = "修改自己密码", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPsw", value = "原密码", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "newPsw", value = "新密码", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @PutMapping("/psw")
    public Result updatePsw(String oldPsw, String newPsw, HttpServletRequest request) {
        if (!oldPsw.equals(userService.selectById(getLoginUserId(request)).getPassword())) {
            return JsonResult.error("原密码不正确");
        }
        User user = new User();
        user.setUserId(getLoginUserId(request));
        user.setPassword(newPsw);
        if (userService.updateById(user)) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }*/

    @RequiresPermissions("user:resetPsw")
    @ApiOperation(value = "重置密码", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @PostMapping("/psw/{id}")
    public Result resetPsw(@PathVariable("id") Long userId) {
        User user = new User();
        user.setUserId(userId);
        user.setPassword(SecureUtil.hmacMd5("123456").toString());
        if (userService.updateById(user)) {
            return Result.ok("重置密码成功");
        }
        return Result.fail("重置密码失败");
    }

    @RequiresPermissions("user:delete")
    @ApiOperation(value = "删除用户", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "用户id数组，多个id以,号隔开", required = true, dataType = "String"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @DeleteMapping("del")
    public Result delete(String ids) {
        String[] split = ids.split(",");
        if (userService.removeByIds(Arrays.asList(split))) {
            return Result.ok("删除成功");
        }
        return Result.fail("删除失败");
    }

}

