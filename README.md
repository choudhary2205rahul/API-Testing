# Testing Template

<ul>
<li>Unit Testing</li>
<li>Integration Testing</li>
<li>Jenkinsfile</li>
    <ul>
        <li>Test</li>
        <li>Build</li>
        <li>Html Publish</li>
        <li>Sonar Scan</li>
        <li>Snyk Scan</li>
        <li>Allure Report</li>
        <li>InfluxDB Report - Show Coverage in Grafana</li>
    </ul>
<li>Jenkins Metrics in Grafana using Jenkins Prometheus Plugin</li>
<li>Sonarqube code coverage to grafana using extension plugin</li>
</ul>

# Pending

<ul>
    <li>Integrate Karate & Gatling</li>
    <li>Docker Image & Push to Artifactory</li>
    <li>Docker Image Scan</li>
    <li>AWS</li>
</ul>


## Open Telemetry
<p>
    In early 2019, the OpenTelemetry project was announced as a merger of two existing open source projects: OpenTracing and OpenCensus.
    
    Signals : tracing, logging, metrics, baggage
    
    Data Models: The data model defines the representation of the components that form a specific signal.
    It provides the specifics of what fields each component must have and describes 
    how all the components interact with one another.
    
    A user who instruments their code by using the API and does not configure the 
    SDK will not see any telemetry produced by design.
    
    SDK: It implements the underlying system that generates, aggregates, and transmits telemetry data.
    
    The SDK provides the controls to configure how telemetry 
    should be collected, where it should be transmitted, and how.
    
    To be useful, the telemetry data captured by each signal must eventually 
    be exported to a data store, where storage and analysis can occur. 
    
    To accomplish this, each signal implementation offers a series of
    mechanisms to generate, process, and transmit/export telemetry.
    
    Pipeline: 
    
        Provider: A provider is a configurable factory that is used to give application code 
                  access to an entity used to generate telemetry data.
        Generator: To generate telemetry data, Generators are named differently depending on the signal: 
                    the tracing signal calls this a tracer, the metrics signal a meter.
    
</p>
