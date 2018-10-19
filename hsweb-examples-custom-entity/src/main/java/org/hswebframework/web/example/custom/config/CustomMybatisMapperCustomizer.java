package org.hswebframework.web.example.custom.config;

import org.hswebframework.web.dao.mybatis.MybatisMapperCustomizer;
import org.springframework.stereotype.Component;


/**
 * 自定义mybatis xml
 *
 * @author zhouhao
 */
@Component
public class CustomMybatisMapperCustomizer implements MybatisMapperCustomizer {
    @Override
    public String[] getExcludes() {
        return new String[]{
                "classpath*:org/hswebframework/**/OrganizationalMapper.xml"
        };
    }

    @Override
    public String[] getIncludes() {
        return new String[]{
                "classpath*:custom/mappers/OrganizationalMapper.xml"
        };
    }
}
