package xyz.iotcode.simpleadmin.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import org.wf.jwtp.annotation.RequiresPermissions;
import xyz.iotcode.simpleadmin.common.Result;
import xyz.iotcode.simpleadmin.system.entity.Authorities;
import xyz.iotcode.simpleadmin.system.entity.Role;
import xyz.iotcode.simpleadmin.system.entity.RoleAuthorities;
import xyz.iotcode.simpleadmin.system.pojo.dto.AddRoleAuthDTO;
import xyz.iotcode.simpleadmin.system.service.AuthoritiesService;
import xyz.iotcode.simpleadmin.system.service.RoleAuthoritiesService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author 谢霜
 * @since 2019-06-04
 */
@Api(tags = "权限管理")
@RestController
@RequestMapping("/authorities")
public class AuthoritiesController {

    @Autowired
    private AuthoritiesService authoritiesService;

    @RequiresPermissions("authorities:sync")
    @ApiOperation(value = "同步权限")
    @PostMapping("/sync")
    public Result add() {
       if (authoritiesService.sync()){
           return Result.ok("同步成功");
       }
       return Result.fail("同步失败");
    }

    @RequiresPermissions("authorities:list")
    @ApiOperation(value = "查询所有权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "String", paramType = "query"),
    })
    @GetMapping
    public Result<List<Authorities>> list(Integer roleId, String keyword) {
        List<Authorities> list = authoritiesService.list();
        if (StringUtils.isNotBlank(keyword)&& !CollectionUtils.isEmpty(list)){
            keyword = keyword.trim();
            Iterator<Authorities> iterator = list.iterator();
            while (iterator.hasNext()) {
                Authorities next = iterator.next();
                boolean b = next.getAuthorityName().contains(keyword) || next.getParentName().contains(keyword);
                if (!b) {
                    iterator.remove();
                }
            }
        }
        return Result.ok(list);
    }

    @RequiresPermissions("authorities:roleAdd")
    @ApiOperation(value = "给角色添加权限")
    @PostMapping("/role")
    public Result addRoleAuth(@RequestBody AddRoleAuthDTO dto) {
        boolean b = authoritiesService.addRoleAuth(dto);
        if (b) {
            return Result.ok();
        }
        return Result.fail();
    }

    @RequiresPermissions("authorities:roleDel")
    @ApiOperation(value = "移除角色权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "String", paramType = "json"),
            @ApiImplicitParam(name = "authId", value = "权限id", required = true, dataType = "String", paramType = "json"),
    })
    @DeleteMapping("/role")
    public Result deleteRoleAuth(Long roleId, String authId) {
        boolean delete = new RoleAuthorities().delete(new LambdaQueryWrapper<RoleAuthorities>().eq(RoleAuthorities::getRoleId, roleId).eq(RoleAuthorities::getAuthority, authId));
        if (delete){
            return Result.ok();
        }
        return Result.fail();
    }

}

