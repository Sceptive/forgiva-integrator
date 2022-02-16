package com.sceptive.forgiva.integrator.test.webservices.adminservices;

import com.sceptive.forgiva.integrator.logging.Fatal;
import com.sceptive.forgiva.integrator.logging.Info;
import com.sceptive.forgiva.integrator.services.gen.api.AdminApi;
import com.sceptive.forgiva.integrator.services.gen.model.PostAdminResourceusageRequest;
import com.sceptive.forgiva.integrator.test.IFTests;
import com.sceptive.forgiva.integrator.test.ITest;
import com.sceptive.forgiva.integrator.test.webservices.AdminServicesTests;
import com.sceptive.forgiva.integrator.test.webservices.General;
import org.testng.Assert;

import javax.ws.rs.Path;
import java.lang.reflect.Method;

public class AccessToAdminPages implements ITest {

@Override
public void launch(final IFTests _parent) {

    AdminServicesTests parent = (AdminServicesTests) _parent;

    try {
        General.create_and_login_as_test_user((ps_response, pl_response, header, client_pk) -> {
            parent.userDefHeader = header;
        });
        PostAdminResourceusageRequest _request =
                new PostAdminResourceusageRequest().header(parent.userDefHeader);
        for (Method m : AdminApi.class.getMethods()) {
            Path p = m.getAnnotation(Path.class);
            if (p != null) {
                Info.get_instance()
                    .print("Checking [/admin%s]",
                           p.value());
                General.launch_test_request("/admin" + p.value(),
                                            _request,
                                            PostAdminResourceusageRequest.class,
                                            401,
                                            (response) -> {
                                            });
            }
        }
    }
    catch (Exception e) {
        Fatal.get_instance()
             .print(e);
        Assert.fail("Has exception creating test users");
    }
    Info.get_instance().print("Access To Admin Pages");
}
}