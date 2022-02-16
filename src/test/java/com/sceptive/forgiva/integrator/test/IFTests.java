package com.sceptive.forgiva.integrator.test;

import com.sceptive.forgiva.integrator.logging.Info;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class IFTests {

    @BeforeMethod
    public void beforeMethod(Method method) {
        Test test = method.getAnnotation(Test.class);
        Info.get_instance().print("Running [%s] => %s", test.testName(), test.description());
    }
}
