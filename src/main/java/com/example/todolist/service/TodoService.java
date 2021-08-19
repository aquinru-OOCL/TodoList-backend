package com.example.todolist.service;

import com.example.todolist.entity.Todo;
import com.example.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo addTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Integer todoId, Todo todoToBeUpdated) {
        Todo todo = todoRepository.findById(todoId).orElse(null);
        if (todo == null) {
            System.out.println("todo null");
        }

        return updateTodoInfo(todo, todoToBeUpdated);
    }

    private Todo updateTodoInfo(Todo todo, Todo todoToBeUpdated) {
        if (todoToBeUpdated.getDone() != null) {
            todo.setDone(todoToBeUpdated.getDone());
        }

        if (todoToBeUpdated.getText() != null) {
            todo.setText(todoToBeUpdated.getText());
        }

        todoRepository.save(todo);
        return todo;
    }

    public Todo getTodoById(Integer todoId) {
        return todoRepository.findById(todoId).orElse(null);
    }

    public void deleteTodo(Integer todoId) {
        Todo todo = todoRepository.findById(todoId).orElse(null);
        if (todo == null) {
            System.out.println("todo null");
        }

        todoRepository.delete(todo);
    }
}
