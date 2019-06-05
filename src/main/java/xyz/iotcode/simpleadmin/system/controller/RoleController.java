package xyz.iotcode.simpleadmin.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.wf.jwtp.annotation.RequiresPermissions;
import xyz.iotcode.simpleadmin.common.Result;
import xyz.iotcode.simpleadmin.system.entity.Role;
import xyz.iotcode.simpleadmin.system.service.RoleService;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author 谢霜
 * @since 2019-06-04
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequiresPermissions("role:list")
    @ApiOperation(value = "查询所有角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "搜索关键字", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("list")
    public Result<List<Role>> list(String keyword) {
        List<Role> list = roleService.list(new LambdaQueryWrapper<Role>().orderByDesc(Role::getCreateTime));
        // 筛选结果
        if (keyword != null && !keyword.trim().isEmpty()) {
            keyword = keyword.trim();
            Iterator<Role> iterator = list.iterator();
            while (iterator.hasNext()) {
                Role next = iterator.next();
                boolean b = next.getRoleName().contains(keyword) || next.getComments().contains(keyword);
                if (!b) {
                    iterator.remove();
                }
            }
        }
        return Result.ok(list);
    }

    @RequiresPermissions("role:add")
    @ApiOperation(value = "添加角色")
    @PostMapping("add")
    public Result add(@RequestBody Role role) {
        if (roleService.save(role)) {
            return Result.ok("添加成功");
        }
        return Result.fail("添加失败");
    }

    @RequiresPermissions("role:update")
    @ApiOperation(value = "修改角色")
    @PostMapping("update")
    public Result update(@RequestBody Role role) {
        if (roleService.updateById(role)) {
            return Result.ok("修改成功！");
        }
        return Result.fail("修改失败！");
    }

    @RequiresPermissions("role:del")
    @ApiOperation(value = "删除角色")
    @PostMapping("del")
    public Result delete(String ids) {
        if (roleService.removeByIds(Arrays.asList(ids.split(",")))) {
            return Result.ok("删除成功");
        }
        return Result.fail("删除失败");
    }

}

