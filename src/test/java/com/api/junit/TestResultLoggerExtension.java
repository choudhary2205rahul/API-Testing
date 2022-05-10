package com.api.junit;

import com.api.junit.util.ObservabilityUtil;
import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class TestResultLoggerExtension implements TestWatcher, AfterAllCallback {

    private Tracer tracer;


    private List<TestResultStatus> testResultsStatus = new ArrayList<>();


    private enum TestResultStatus {
        SUCCESSFUL, ABORTED, FAILED, DISABLED;
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        log.info("Test Disabled for test {}: with reason :- {}",
                context.getDisplayName(),
                reason.orElse("No reason"));

        //disabledTests.increment();
        testResultsStatus.add(TestResultStatus.DISABLED);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        log.info("Test Successful for test {}: ", context.getDisplayName());

        //successfulTests.increment();
        testResultsStatus.add(TestResultStatus.SUCCESSFUL);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        log.info("Test Aborted for test {}: ", context.getDisplayName());

        //abortedTests.increment();
        testResultsStatus.add(TestResultStatus.ABORTED);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        log.info("Test Failed for test {}: ", context.getDisplayName());

        //failedTests.increment();
        testResultsStatus.add(TestResultStatus.FAILED);
    }

    @Override
    public void afterAll(ExtensionContext context) {

        this.tracer = ObservabilityUtil.initTracer();

        Span span = tracer.buildSpan(context.getDisplayName()).start();

        Map<TestResultStatus, Long> summary = testResultsStatus.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // otel-collector run independently -> /metrics

        if (summary.get(TestResultStatus.SUCCESSFUL) == null) {
            summary.put(TestResultStatus.SUCCESSFUL, 0l);
        }

        if (summary.get(TestResultStatus.FAILED) == null) {
            summary.put(TestResultStatus.FAILED, 0l);
        }

        if (summary.get(TestResultStatus.ABORTED) == null) {
            summary.put(TestResultStatus.ABORTED, 0l);
        }

        if (summary.get(TestResultStatus.DISABLED) == null) {
            summary.put(TestResultStatus.DISABLED, 0l);
        }

        span.setTag("class.name", context.getDisplayName());
        span.setTag("successful", summary.get(TestResultStatus.SUCCESSFUL));
        span.setTag("fail", summary.get(TestResultStatus.FAILED));
        span.setTag("aborted", summary.get(TestResultStatus.ABORTED));
        span.setTag("disabled", summary.get(TestResultStatus.DISABLED));

        span.log("Test result summary for "+context.getDisplayName()+ " " + summary);

//        timer.record(System.nanoTime()-start, TimeUnit.NANOSECONDS);
        span.finish();
    }


}
