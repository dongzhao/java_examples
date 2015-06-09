/*
package com.regulationworks.pj.tools.web.test;


import com.regulationworks.pj.tools.web.services.api.DashboardRestService;
import com.regulationworks.pj.tools.web.services.api.SecurityRestService;
import com.regulationworks.pj.tools.web.services.DashboardConfigService;
import com.regulationworks.pj.tools.web.services.SecurityConfigService;
import com.sun.jersey.api.core.ClassNamesResourceConfig;
import com.sun.jersey.spi.container.servlet.WebComponent;
import com.sun.jersey.spi.inject.SingletonTypeInjectableProvider;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
import com.sun.jersey.test.framework.spi.container.grizzly.web.GrizzlyWebTestContainerFactory;
import org.mockito.Mockito;

import javax.ws.rs.ext.Provider;

*/
/**
 * User: dzhao
 *//*

public class TestServer extends JerseyTest {

    public static DashboardConfigServiceImpl mockDashboardConfigService = Mockito.mock(DashboardConfigServiceImpl.class);

    public static SecurityConfigServiceImpl mockSecurityConfigService = Mockito.mock(SecurityConfigServiceImpl.class);

    @Override
    protected WebAppDescriptor configure(){
        return new WebAppDescriptor.Builder()
                .initParam(WebComponent.RESOURCE_CONFIG_CLASS, ClassNamesResourceConfig.class.getName())
                .initParam(
                        ClassNamesResourceConfig.PROPERTY_CLASSNAMES,
                        DashboardRestService.class.getName() + ";"
                                + MockDashboardRestServiceProvider.class.getName() + ";"
                                + SecurityRestService.class.getName() + ";"
                                + MockSecurityRestServiceProvider.class.getName())
                .build();
    }

    public TestContainerFactory getTestContainerFactory(){
        return new GrizzlyWebTestContainerFactory();
    }

    @Provider
    public static class MockDashboardRestServiceProvider extends SingletonTypeInjectableProvider{
        public MockDashboardRestServiceProvider() {
            super(DashboardConfigServiceImpl.class, mockDashboardConfigService);
        }
    }

    @Provider
    public static class MockSecurityRestServiceProvider extends SingletonTypeInjectableProvider{
        public MockSecurityRestServiceProvider() {
            super(SecurityConfigServiceImpl.class, mockSecurityConfigService);
        }
    }

}
*/
