package com.api.junit.util;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.JaegerTracer;
import org.springframework.stereotype.Component;


@Component
public final class ObservabilityUtil {

    private ObservabilityUtil() {}

    public static JaegerTracer initTracer() {

        Configuration.SamplerConfiguration samplerConfiguration = Configuration.SamplerConfiguration
                .fromEnv()
                .withType("const")
                .withParam(1);

        Configuration.ReporterConfiguration reporterConfiguration = Configuration.ReporterConfiguration
                .fromEnv()
                .withLogSpans(true);

        Configuration configuration = new Configuration("API-Testing")
                .withSampler(samplerConfiguration)
                .withReporter(reporterConfiguration);

        return configuration.getTracer();
    }


}
