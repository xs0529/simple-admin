package xyz.iotcode.simpleadmin.system.controller;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.*;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;
import xyz.iotcode.simpleadmin.common.pojo.vo.Result;
import xyz.iotcode.simpleadmin.common.util.TokenUtils;
import xyz.iotcode.simpleadmin.system.entity.User;
import xyz.iotcode.simpleadmin.system.pojo.dto.QueryUserPageDTO;
import xyz.iotcode.simpleadmin.system.service.UserService;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author 谢霜
 * @since 2019-06-04
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequiresPermissions("user:page")
    @ApiOperation(value = "查询所有用户", nickname = "user:page")
    @PostMapping("page")
    public Result<IPage<User>> list(@RequestBody QueryUserPageDTO dto) {
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
    public Result update(@RequestBody User user) {
        boolean update = userService.update(user);
        if (update){
            return Result.ok();
        }
        return Result.fail();
    }

    @RequiresPermissions("user:state")
    @ApiOperation(value = "修改用户状态", nickname = "user:state")
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
        if (userService.update(user)) {
            return Result.ok();
        }
        return Result.fail();
    }

    @ApiModel("修改密码bean")
    @Data
    class UpdatePsw{
        @ApiModelProperty("旧密码")
        private String oldPsw;
        @ApiModelProperty("新密码")
        private String newPsw;

    }
    @RequiresPermissions("user:psw")
    @ApiOperation(value = "修改自己密码", nickname = "user:psw")
    @PostMapping("/psw")
    public Result updatePsw(@RequestBody UpdatePsw updatePsw) {
        if (StringUtils.isBlank(updatePsw.getNewPsw())||StringUtils.isBlank(updatePsw.getOldPsw())){
            return Result.fail("请传入原密码以及新密码");
        }
        if (!SecureUtil.sha256(updatePsw.getOldPsw()).equals(userService.getById(TokenUtils.getLoginUserId()).getPassword())) {
            return Result.fail("原密码不正确");
        }
        User user = new User();
        user.setUserId(TokenUtils.getLoginUserId());
        user.setPassword(updatePsw.newPsw);
        if (userService.update(user)) {
            return Result.ok("修改成功");
        }
        return Result.fail("修改失败");
    }

    @RequiresPermissions("user:resetPsw")
    @ApiOperation(value = "重置密码", nickname = "user:resetPsw")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "path"),
    })
    @PostMapping("/psw/{id}")
    public Result resetPsw(@PathVariable("id") Long userId) {
        User user = new User();
        user.setUserId(userId);
        user.setPassword(SecureUtil.sha256("123456"));
        if (userService.update(user)) {
            return Result.ok("重置密码成功");
        }
        return Result.fail("重置密码失败");
    }

    @RequiresPermissions("user:delete")
    @ApiOperation(value = "删除用户", nickname = "user:delete")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "用户id数组，多个id以,号隔开", required = true, dataType = "String"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @PostMapping("del")
    public Result delete(String ids) {
        if (userService.delAll(ids)) {
            return Result.ok("删除成功");
        }
        return Result.fail("删除失败");
    }

}

