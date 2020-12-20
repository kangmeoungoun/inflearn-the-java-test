package me.whiteship.inflearnthejavatest;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;

/**
 * Created by jojoldu@gmail.com on 2020-11-26
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
public class FindSLowTestExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private  long THRESHOLD;
    public FindSLowTestExtension (long THRESHOLD){
        this.THRESHOLD = THRESHOLD;
    }


    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        ExtensionContext.Store store = getStore(context);
        store.put("START_TIME",System.currentTimeMillis());
    }
    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Method requiredTestMethod = context.getRequiredTestMethod();
        SlowFast annotation = requiredTestMethod.getAnnotation(SlowFast.class);

        String testMethodName = context.getRequiredTestMethod().getName();
        ExtensionContext.Store store = getStore(context);
        Long start_time = store.remove("START_TIME", long.class);
        long duration = System.currentTimeMillis() - start_time;
        if(duration >THRESHOLD && annotation ==null){
            System.out.printf("Please consider mark method [%s] with @SloowTest.\n",testMethodName);
        }
    }
    private ExtensionContext.Store getStore(ExtensionContext context) {
        String testClassName = context.getRequiredTestClass().getName();
        String testMethodName = context.getRequiredTestMethod().getName();
        return context.getStore(ExtensionContext.Namespace.create(testClassName, testMethodName));
    }
}
