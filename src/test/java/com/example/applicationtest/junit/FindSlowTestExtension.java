package com.example.applicationtest.junit;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class FindSlowTestExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private final Long THRESHOLD;

    public FindSlowTestExtension(Long THRESHOLD) {
        this.THRESHOLD = THRESHOLD;
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        ExtensionContext.Store store = getStore(context);
        store.put("START_TIME", System.currentTimeMillis());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        ExtensionContext.Store store = getStore(context);
        SlowTest annotation = context.getRequiredTestMethod().getAnnotation(SlowTest.class);

        Long startTime = store.remove("START_TIME", long.class);
        Long duration = System.currentTimeMillis() - startTime;

        if (duration > THRESHOLD && annotation == null) {
            throw new IllegalStateException("[" + context.getRequiredTestClass().getName() + "] - "
                    + context.getRequiredTestMethod().getName() + "메서드는 @SlowTest 어노테이션을 붙일 것을 권장합니다.");
        }
    }

    private ExtensionContext.Store getStore(ExtensionContext context) {
        String testClassName = context.getRequiredTestClass().getName();
        String testMethodName = context.getRequiredTestMethod().getName();
        ExtensionContext.Store store = context.getStore(ExtensionContext.Namespace.create(testClassName, testMethodName));
        return store;
    }
}
