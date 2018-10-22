package org.hswebframework.web.example.authorize;

import org.hswebframework.web.example.service.AppUserService;
import org.hswebframework.web.authorization.Authentication;
import org.hswebframework.web.authorization.annotation.Authorize;
import org.hswebframework.web.authorization.basic.web.ParsedToken;
import org.hswebframework.web.authorization.basic.web.UserTokenParser;
import org.hswebframework.web.authorization.simple.SimpleAuthentication;
import org.hswebframework.web.authorization.simple.SimpleRole;
import org.hswebframework.web.authorization.simple.SimpleUser;
import org.hswebframework.web.authorization.token.ThirdPartAuthenticationManager;
import org.hswebframework.web.authorization.token.UserTokenManager;
import org.hswebframework.web.controller.message.ResponseMessage;
import org.hswebframework.web.id.IDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@RestController
@RequestMapping("/app-user")
public class AppUserCustomizer implements
        ThirdPartAuthenticationManager, UserTokenParser //自定义权限管理
{

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private UserTokenManager userTokenManager;

    /**
     * App登录
     */
    @PostMapping("/login")
    public ResponseMessage<String> login(@RequestParam String username, @RequestParam String password) {
        AppUser appUser = appUserService.authenticate(username, password);

        if (null != appUser) {
            String token = IDGenerator.MD5.generate();
            userTokenManager.signIn(token,
                    getTokenType(),
                    appUser.getId(),
                    //30天没访问就过期
                    TimeUnit.MILLISECONDS.convert(30, TimeUnit.DAYS));

            return ResponseMessage.ok(token);
        }
        return ResponseMessage.error(404, "用户不存在");
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/me")
    @Authorize
    public ResponseMessage<AppUser> getCurrentAppUser(Authentication authentication) {
        //获取用户信息
        return ResponseMessage
                .ok(appUserService.getUserById(authentication.getUser().getId()))
                .exclude("password");
    }

    @Override
    public String getTokenType() {
        return "app-user";
    }

    @Override
    public Authentication getByUserId(String userId) {
        AppUser user = appUserService.getUserById(userId);
        if (user == null) {
            return null;
        }
        SimpleAuthentication userAutz = new SimpleAuthentication();

        userAutz.setUser(SimpleUser.builder()
                .id(user.getId())
                .type("app-user")
                .username(user.getUsername())
                .name(user.getNickName())
                .build());

        userAutz.setRoles(Arrays.asList(new SimpleRole("app-user", "app角色")));
        //设置更多权限信息

//        userAutz.setPermissions();
        return userAutz;
    }

    @Override
    public ParsedToken parseToken(HttpServletRequest request) {
        String token = request.getHeader("app-token");
        if (null != token) {
            return new ParsedToken() {
                @Override
                public String getToken() {
                    return token;
                }

                @Override
                public String getType() {
                    return getTokenType();
                }
            };
        }
        return null;
    }
}
