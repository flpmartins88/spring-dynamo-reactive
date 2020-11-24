package io.flpmartins.dynamo;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

@Component
public class TaskRepository {

    private final DynamoDbAsyncClient dynamoDbAsyncClient;

    public TaskRepository(DynamoDbAsyncClient dynamoDbAsyncClient) {
        this.dynamoDbAsyncClient = dynamoDbAsyncClient;
    }

    public Mono<Void> save(Task task) {
        var request = PutItemRequest.builder()
                .tableName(Task.TASK_TABLE)
                .item(task.toAttributeMap())
                .build();

        return Mono.fromFuture(dynamoDbAsyncClient.putItem(request))
                .filter(it -> it.sdkHttpResponse().isSuccessful())
                .switchIfEmpty(Mono.error(new Exception("Não foi possível salvar")))
                .flatMap(it -> Mono.empty().then())
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Flux<Task> findAll() {
        var request = ScanRequest.builder()
                .tableName(Task.TASK_TABLE)
                .build();

        return Mono.fromFuture(dynamoDbAsyncClient.scan(request))
                .map(ScanResponse::items)
                .flatMapMany(Flux::fromIterable)
                .map(Task::fromAttributeMap)
                .subscribeOn(Schedulers.boundedElastic());

    }

}
