/*
 *  Copyright 2016 http://www.hswebframework.org
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package org.hswebframework.web.example.oauth2;

import org.hswebframework.web.authorization.basic.configuration.EnableAopAuthorize;
import org.hswebframework.web.commons.entity.DataStatus;
import org.hswebframework.web.commons.entity.factory.EntityFactory;
import org.hswebframework.web.dao.oauth2.server.OAuth2ClientDao;
import org.hswebframework.web.entity.oauth2.server.OAuth2ClientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashSet;


/**
 * @author zhouhao
 */
@SpringBootApplication
@Configuration
@EnableCaching
@EnableAopAuthorize
public class OAuth2ServerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(OAuth2ServerApplication.class);
    }


    @Autowired
    EntityFactory   entityFactory;
    @Autowired
    OAuth2ClientDao oAuth2ClientDao;

    @Override
    public void run(String... strings) throws Exception {
        //添加示例数据，实际项目应该在前端进行维护

        /*
            ------------------------添加OAuth2客户端---------------------
            ------------------client_id:hsweb_oauth2_example------------
            ------------------client_secret:hsweb_oauth2_example_secret--
         */
        OAuth2ClientEntity clientEntity = entityFactory.newInstance(OAuth2ClientEntity.class);

        clientEntity.setId("hsweb_oauth2_example");
        clientEntity.setSecret("hsweb_oauth2_example_secret");
        clientEntity.setOwnerId("admin");
        clientEntity.setName("测试");
        clientEntity.setType("test");
        clientEntity.setCreatorId("admin");
        clientEntity.setRedirectUri("http://localhost:8808/");
        clientEntity.setCreateTime(System.currentTimeMillis());
        clientEntity.setSupportGrantTypes(new HashSet<>(Collections.singletonList("*")));
        clientEntity.setStatus(DataStatus.STATUS_ENABLED);
        oAuth2ClientDao.insert(clientEntity);
    }

}
