package org.hswebframework.web.example.service.impl;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hswebframework.web.example.authorize.AppUser;
import org.hswebframework.web.example.service.AppUserService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用配置文件和HashMap管理用户信息
 *
 * @author zhouhao
 * @since 1.0.0
 */
@Service
@ConfigurationProperties(prefix = "app")
@Validated
public class SimpleInMemoryAppUserService implements AppUserService {

    @Getter
    @Setter
    private Map<String, AppUser> users = new HashMap<>();

    @PostConstruct
    public void init() {
        for (Map.Entry<String, AppUser> entry : users.entrySet()) {
            entry.getValue().setId(entry.getKey());
        }
    }

    @Override
    public AppUser authenticate(@NotBlank(message = "用户名不能为空") String username,
                                @NotBlank(message = "密码不能为空") String password) {

        return users.values().stream()
                .filter(user -> username.equals(user.getUsername()) && password.equals(user.getPassword()))
                .findFirst().orElse(null);
    }

    @Override
    public AppUser getUserById(String id) {
        return users.get(id);
    }
}
