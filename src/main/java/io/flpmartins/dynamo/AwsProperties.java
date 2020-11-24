package io.flpmartins.dynamo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aws")
public class AwsProperties {

    /**
     * Custom service endpoint
     */
    private String endpoint;

    /**
     * Default region
     */
    private String region = "us-east-1";

    public String getEndpoint() {
        return endpoint;
    }

    public String getRegion() {
        return region;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
