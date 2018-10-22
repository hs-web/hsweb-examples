package org.hswebframework.web.example.service;

import org.hibernate.validator.constraints.NotBlank;
import org.hswebframework.web.example.authorize.AppUser;

/**
 * @author zhouhao
 * @since 1.0.0
 */
public interface AppUserService {
    AppUser authenticate(@NotBlank(message = "用户名不能为空") String username,
                         @NotBlank(message = "密码不能为空") String password);

    AppUser getUserById(String id);
}
