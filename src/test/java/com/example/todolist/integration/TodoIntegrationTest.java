package com.example.todolist.integration;

import com.example.todolist.entity.Todo;
import com.example.todolist.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoIntegrationTest {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void tearDown() {
        todoRepository.deleteAll();
    }

    @Test
    public void should_return_todos_when_find_all_todo() throws Exception {
        // Given
        Todo savedTodo = todoRepository.save(new Todo(1,"test",false));

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(savedTodo.getId()))
                .andExpect(jsonPath("$[0].text").value(savedTodo.getText()))
                .andExpect(jsonPath("$[0].done").value(savedTodo.getDone()));
    }

    @Test
    public void should_save_todo_when_save_given_one_todo() throws Exception {
        // Given
        String stringAsJson = "{\n" +
                "    \"text\" : \"Add this todo.\"\n" +
                "}";
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringAsJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.text").value("Add this todo."))
                .andExpect(jsonPath("$.done").value(false));
    }

}
