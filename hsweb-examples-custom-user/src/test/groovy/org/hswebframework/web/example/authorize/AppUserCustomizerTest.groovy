package org.hswebframework.web.example.authorize

import com.alibaba.fastjson.JSON
import org.hswebframework.web.example.Application
import org.hswebframework.web.tests.HswebSpecification
import org.hswebframework.web.tests.HswebTestApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Shared
import spock.lang.Specification
import static org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * @author zhouhao
 * @since
 */
@SpringBootTest(
        classes = [HswebTestApplication.class, Application.class],
        properties = ["classpath:application.yml"]
)
@WebAppConfiguration
@ContextConfiguration
class AppUserCustomizerTest extends Specification {
    @Autowired
    protected WebApplicationContext context;

    @Shared
    protected MockMvc mockMvc;

    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    def "APP登录测试"() {
        given: "进行登录"
        def response = mockMvc.perform(
                post("/app-user/login")
                        .param("username", "app-test")
                        .param("password", "app-test")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andReturn()
                .response
                .contentAsString
        def jsonResponse = JSON.parse(response);
        when: "登录成功"
        jsonResponse.status == 200
        jsonResponse.result != null

        then: "获取APP用户的信息"
        def userInfo = mockMvc.perform(get("/app-user/me")
        //带上token,AppUserCustomizer#parseToken 会进行解析
                .header("app-token", jsonResponse.result as String))
                .andReturn().response.contentAsString
        def user = JSON.parseObject(userInfo).getJSONObject("result").toJavaObject(AppUser.class);
        expect:
        user != null
        user.id == 'test-user'
        user.username == 'app-test'

    }
}
