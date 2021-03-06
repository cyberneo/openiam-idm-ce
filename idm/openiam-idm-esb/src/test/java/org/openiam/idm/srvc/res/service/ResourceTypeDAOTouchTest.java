package org.openiam.idm.srvc.res.service;

import org.openiam.idm.srvc.res.domain.ResourceTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.annotations.Test;

/**
 * Smoke Test for DAO service of ResourceType entity
 *
 * @author vitaly.yakunin
 */
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml","classpath:dozer-application-context-test.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class ResourceTypeDAOTouchTest extends AbstractTransactionalTestNGSpringContextTests {
    @Autowired
    private ResourceTypeDAO resourceTypeDAO;

    @Test
    public void touchAdd() {
        resourceTypeDAO.add(new ResourceTypeEntity());
    }

    @Test
    public void touchFindAllResourceTypes() {
        resourceTypeDAO.findAllResourceTypes();
    }

    @Test
    public void touchFindByExample() {
        resourceTypeDAO.findByExample(new ResourceTypeEntity());
    }

    @Test
    public void touchFindById() {
        resourceTypeDAO.findById("");
    }

    @Test
    public void touchRemove() {
        ResourceTypeEntity resourceType = new ResourceTypeEntity();
        resourceTypeDAO.add(resourceType);
        resourceTypeDAO.remove(resourceType);
    }

    @Test
    public void touchRemoveAllResourceTypes() {
        resourceTypeDAO.removeAllResourceTypes();
    }

    @Test
    public void touchUpdate() {
        resourceTypeDAO.update(new ResourceTypeEntity());
    }

}
