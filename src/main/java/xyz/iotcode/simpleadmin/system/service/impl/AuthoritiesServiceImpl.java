package xyz.iotcode.simpleadmin.system.service.impl;

import xyz.iotcode.simpleadmin.system.entity.Authorities;
import xyz.iotcode.simpleadmin.system.mapper.AuthoritiesMapper;
import xyz.iotcode.simpleadmin.system.service.AuthoritiesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
