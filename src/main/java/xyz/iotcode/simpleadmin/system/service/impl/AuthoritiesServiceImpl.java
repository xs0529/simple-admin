package xyz.iotcode.simpleadmin.system.service.impl;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import xyz.iotcode.simpleadmin.common.config.ServerConfig;
import xyz.iotcode.simpleadmin.system.entity.Authorities;
import xyz.iotcode.simpleadmin.system.entity.RoleAuthorities;
import xyz.iotcode.simpleadmin.system.mapper.AuthoritiesMapper;
import xyz.iotcode.simpleadmin.system.pojo.dto.AddRoleAuthDTO;
import xyz.iotcode.simpleadmin.system.service.AuthoritiesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author 谢霜
 * @since 2019-06-04
 */
@Service
public class AuthoritiesServiceImpl extends ServiceImpl<AuthoritiesMapper, Authorities> implements AuthoritiesService {

    @Autowired
    private AuthoritiesMapper authoritiesMapper;
    @Autowired
    private ServerConfig serverConfig;

    @Cacheable(value = "user:auth:byUserId", key = "#userId", unless = "#result==null")
    @Override
    public Set<Authorities> getAuthByUserId(Long userId) {
        return authoritiesMapper.getAuthByUserId(userId);
    }

    @Cacheable(value = "user:auth:byUsername", key = "#username", unless = "#result==null")
    @Override
    public Set<Authorities> getAuthByUsername(String username) {
        return authoritiesMapper.getAuthByUsername(username);
    }

    @Override
    public String[] getAuthStrByUserId(Long userId) {
        Set<Authorities> authByUserId = this.getAuthByUserId(userId);
        if (CollectionUtils.isEmpty(authByUserId)){
            return new String[0];
        }else {
            return authByUserId.toArray(new String[authByUserId.size()]);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addRoleAuth(AddRoleAuthDTO dto) {
        if (dto.getRoleId()==null||CollectionUtils.isEmpty(dto.getAuthList())){
            throw new IllegalArgumentException("参数错误，请传入正确的参数");
        }
        RoleAuthorities roleAuthorities = new RoleAuthorities();
        roleAuthorities.delete(new LambdaQueryWrapper<RoleAuthorities>().eq(RoleAuthorities::getRoleId, dto.getRoleId()));
        return authoritiesMapper.saveAll(dto);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean sync() {
        String body = HttpRequest.get(serverConfig.getUrl()+"/v2/api-docs").execute().body();
        JSONObject jsonObject = JSONObject.parseObject(body);
        JSONObject paths = jsonObject.getJSONObject("paths");
        int sort = 1;
        List<Authorities> authoritiesList = new ArrayList<>();
        paths.entrySet().forEach(stringObjectEntry -> {
            JSONObject value = (JSONObject) stringObjectEntry.getValue();
            value.entrySet().forEach(stringObjectEntry1 -> {
                JSONObject value1 = (JSONObject) stringObjectEntry1.getValue();
                Authorities authorities = new Authorities();
                authorities.setSort(sort);
                authorities.setUrl(stringObjectEntry.getKey());
                authorities.setAuthority(value1.getString("operationId"));
                authorities.setAuthorityName(value1.getString("summary"));
                authorities.setParentName((String) value1.getJSONArray("tags").get(0));
                authoritiesList.add(authorities);
            });
        });
        this.remove(null);
        this.saveBatch(authoritiesList);
        return true;
    }
}
