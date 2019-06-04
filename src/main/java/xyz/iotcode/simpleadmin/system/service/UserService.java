package xyz.iotcode.simpleadmin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.iotcode.simpleadmin.system.entity.User;
import xyz.iotcode.simpleadmin.system.pojo.dto.QueryUserPageDTO;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author 谢霜
 * @since 2019-06-04
 */
public interface UserService extends IService<User> {

    IPage<User> getUserPage(QueryUserPageDTO dto);

    User getByUsername(String username);

    User add(User user);

    User update(User user);
}
