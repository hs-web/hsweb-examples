package org.hswebframework.web.example.custom.entity

import org.hswebframework.web.example.custom.Application
import org.hswebframework.web.tests.HswebCrudWebApiSpecification
import org.hswebframework.web.tests.HswebTestApplication
import org.springframework.boot.test.context.SpringBootTest

/**
 * @author zhouhao
 * @since 1.0.0
 */
@SpringBootTest(
        classes = [HswebTestApplication.class, Application.class],
        properties = ["classpath:application.yml"]
)
class CustomUserEntityTest extends HswebCrudWebApiSpecification {
    @Override
    protected String getBaseApi() {
        return "/user"
    }

    def "测试拓展用户字段"() {
        given: "新增一个用户"
        def response = doAddRequest("""{"username":"admin","name":"张三","password":"admin","nickName":"张三"}""")
        when: "新增成功"
        response.status == 200
        then: "获取新增的用户"
        def userId = response.result as String
        def user = doGetRequest(userId).result
        expect: "判断自定义的字段已经设置成功"
        user != null
        user.nickName == "张三"
    }

}
