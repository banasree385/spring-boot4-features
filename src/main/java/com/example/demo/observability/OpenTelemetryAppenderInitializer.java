package com.example.demo.observability;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.instrumentation.logback.appender.v1_0.OpenTelemetryAppender;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Installs the Spring-managed {@link OpenTelemetry} instance into the Logback
 * {@link OpenTelemetryAppender} once the application context is ready.
 *
 * <p>Spring Boot 4 auto-configures the OpenTelemetry SDK (including the OTLP log
 * record exporter), but the Logback appender declared in {@code logback-spring.xml}
 * is created by Logback before the Spring context exists. This listener bridges the
 * two so application logs are exported via OTLP to the collector.
 */
@Component
public class OpenTelemetryAppenderInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final OpenTelemetry openTelemetry;

    public OpenTelemetryAppenderInitializer(OpenTelemetry openTelemetry) {
        this.openTelemetry = openTelemetry;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        OpenTelemetryAppender.install(this.openTelemetry);
    }
}

