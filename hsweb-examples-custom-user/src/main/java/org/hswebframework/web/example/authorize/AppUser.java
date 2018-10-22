package org.hswebframework.web.example.authorize;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhouhao
 * @since 1.0.0
 */
@Getter
@Setter
public class AppUser {

    private String id;

    private String username;

    private String nickName;

    private String password;

}
