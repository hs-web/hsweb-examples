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
class CustomOrgEntityTest extends HswebCrudWebApiSpecification {
    @Override
    protected String getBaseApi() {
        return "/organizational"
    }

    def "测试拓展组机构字段"() {
        given: "新增一个机构"
        def response = doAddRequest("""{"name":"hsweb","full":"hsweb group","code":"0","leader":"zhouhao"}""")
        when: "新增成功"
        response.status == 200
        then: "获取新增的机构"
        def orgId = response.result as String
        def org = doGetRequest(orgId).result
        expect: "判断自定义的字段已经设置成功"
        org != null
        org.leader == "zhouhao"
    }

}
