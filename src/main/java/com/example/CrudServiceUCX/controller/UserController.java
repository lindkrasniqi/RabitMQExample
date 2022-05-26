package com.example.CrudServiceUCX.controller;

import com.example.CrudServiceUCX.model.Message;
import com.example.CrudServiceUCX.model.User;
import com.example.CrudServiceUCX.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${TopicName}")
    private String topicName;
    @Value("${RoutingKey}")
    private String routingKey;

    @PostMapping
    public ResponseEntity<User> addNewUser (@RequestBody User user) {
        User result = userService.saveUser(user);
        if (result != null) {
            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers () {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser (@RequestBody User user, @PathVariable("id") String userId) {
        User result = userService.updateUser(user, Long.parseLong(userId));
        if (result != null) {
            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser (@PathVariable("id") String userId) {
        User result = userService.deleteUser(Long.parseLong(userId));
        if (result != null) {
            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{firstname}")
    public ResponseEntity<List<User>> getUserByFirstName(@PathVariable("firstname") String firstName) {
        rabbitTemplate.convertAndSend(topicName, routingKey, new Message("User data requested"));
        return ResponseEntity.ok().body(userService.getUserByName(firstName));
    }
}
