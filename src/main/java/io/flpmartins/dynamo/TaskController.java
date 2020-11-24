package io.flpmartins.dynamo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public Flux<Task> findAll() {
        return taskRepository.findAll();
    }

    @PostMapping
    public Mono<ResponseEntity<String>> save(@RequestBody Mono<TaskRequest> taskRequest) {
        return taskRequest.map(TaskRequest::toDomain)
                .flatMap(taskRepository::save)
                .map(it -> ResponseEntity.ok().build());
    }

}
