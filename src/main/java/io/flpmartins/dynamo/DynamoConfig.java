package io.flpmartins.dynamo;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

@Configuration
public class DynamoConfig {

    @Value("${aws.endpoint:#{null}}")
    private Optional<String> endpoint;
    @Value("${aws.region:us-east-1}")
    private String region;

    @Bean
    @Profile("localstack")
    public AwsCredentialsProvider localstackCredentialsProvider() {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create("a", "a"));
    }

    @Bean
    @Profile("!localstack")
    public AwsCredentialsProvider profileCredentialsProvider() {
        return ProfileCredentialsProvider.builder()
                .profileName("pessoal")
                .build();
    }

    @Bean
    public DynamoDbAsyncClient buildDynamoAsyncClient(AwsCredentialsProvider credentialsProvider) {
        var builder = DynamoDbAsyncClient.builder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider);

        endpoint.map(URI::create)
                .ifPresent(builder::endpointOverride);

        return builder.build();
    }
}
