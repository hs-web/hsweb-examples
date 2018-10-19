package com.mycompany.service.impl;

import com.mycompany.dao.TestDao;
import com.mycompany.entity.TestEntity;
import com.mycompany.service.TestService;
import org.hswebframework.web.dao.CrudDao;
import org.hswebframework.web.id.IDGenerator;
import org.hswebframework.web.service.GenericEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO 完成注释
 *
 * @author zhouhao
 * @since
 */
@Service
public class TestServiceImpl extends GenericEntityService<TestEntity,String>
        implements TestService {

    @Autowired
    private TestDao testDao;

    @Override
    protected IDGenerator<String> getIDGenerator() {
        return IDGenerator.MD5;
    }

    @Override
    public TestDao getDao() {
        return testDao;
    }
}
