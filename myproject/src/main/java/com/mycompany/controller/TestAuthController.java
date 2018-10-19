package com.mycompany.controller;

import lombok.extern.slf4j.Slf4j;
import org.hswebframework.web.authorization.Authentication;
import org.hswebframework.web.authorization.exception.AccessDenyException;
import org.hswebframework.web.controller.message.ResponseMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author zhouhao
 * @since 1.0
 */
@RestController
@RequestMapping("/test-auth")
@Slf4j
public class TestAuthController {

    @GetMapping("/{permission}/{action}")
    public ResponseMessage<Boolean> test(@PathVariable String permission,
                                         @PathVariable String action,
                                         Authentication authentication) {
        //或者使用此方式获取当前登录用户权限
        //Authentication authentication= Authentication.current().orElseThrow(UnAuthorizedException::new);


        Set<Object> canQueryDepartment = authentication.getPermission("test")
                .map(testPermission -> testPermission.findScope("query", "department", "children"))
                .orElseThrow(AccessDenyException::new);

        return ResponseMessage.ok(authentication.hasPermission(permission, action));
    }
}
