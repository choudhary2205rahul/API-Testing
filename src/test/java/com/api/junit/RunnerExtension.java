package com.api.junit;

import com.api.junit.util.ObservabilityUtil;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class RunnerExtension implements AfterTestExecutionCallback {

   // private static Tracer tracer;

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {

//        tracer = ObservabilityUtil.initTracer(context.getTestClass().get().getName());
//
//        Span span = tracer.buildSpan(context.getTestClass().get().getName() + "-" + context.getDisplayName()).start();
//
//        span.setTag("class.name", context.getTestClass().get().getName());
//        span.setTag("method.name", context.getDisplayName());
//
//        Boolean testResult = context.getExecutionException().isPresent();
//
//        span.log("result "+ context.getTestClass().get().getName() + " - " + context.getDisplayName() + " is " + (testResult ? "Failed" : "Passed"));
//
//        span.finish();
    }


}
