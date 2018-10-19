package com.mycompany.controller

import com.mycompany.TestApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Shared
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * @author zhouhao
 * @since 1.0
 */
@WebAppConfiguration
@ContextConfiguration
@SpringBootTest(classes = [TestApplication.class], properties = ["classpath:application.yml"])
class TestControllerTest extends Specification {
    @Autowired
    private ConfigurableApplicationContext context;

    @Shared
    private MockMvc mockMvc;

    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    def "Test Create Data"() {
        setup:
        def testData = """
            {"name":"测试数据","status":1,"comment":"说明"}
            """
        and:
        mockMvc.perform(
                post("/test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testData)
        ).andExpect(status().is(201))

    }
}
