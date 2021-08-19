package com.example.todolist.controller;

import com.example.todolist.entity.Todo;
import com.example.todolist.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public Todo addTodo(@RequestBody Todo todo) {
        return todoService.addTodo(todo);
    }

    @GetMapping("/{todoId}")
    public Todo getTodoById(@PathVariable Integer todoId) {
        return todoService.getTodoById(todoId);
    }

    @PutMapping("/{todoId}")
    public Todo updateTodo(@PathVariable Integer todoId, @RequestBody Todo todo) {
        return todoService.updateTodo(todoId, todo);
    }

    @DeleteMapping("/{todoId}")
    public void deleteTodo(@PathVariable Integer todoId) {
        todoService.deleteTodo(todoId);
    }

}
