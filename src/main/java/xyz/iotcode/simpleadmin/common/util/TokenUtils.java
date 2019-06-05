package xyz.iotcode.simpleadmin.common.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.wf.jwtp.provider.Token;
import org.wf.jwtp.util.SubjectUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xieshuang
 * @date 2019-06-05 10:28
 */
public class TokenUtils {

    /**
     * 获取当前登录的userId
     */
    public static Long getLoginUserId() {
        Token token = getLoginToken();
        return token == null ? null : Long.parseLong(token.getUserId());
    }

    public static Token getLoginToken() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        return SubjectUtil.getToken(request);
    }
}
