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

import static org.junit.jupiter.api.Assertions.assertFalse;
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

    @Test
    public void should_update_done_state_when_update_given_todo_id() throws Exception {
        // Given
        Todo todo = new Todo(1, "for update", false);
        Integer todoId = todoRepository.save(todo).getId();
        String todoText = todoRepository.save(todo).getText();

        String stringAsJson = "{\n" +
                "    \"done\" : true\n" +
                "}";
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/todos/" + todoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringAsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(todoId))
                .andExpect(jsonPath("$.text").value(todoText))
                .andExpect(jsonPath("$.done").value(true));
    }

    @Test
    public void should_delete_todo_when_delete_given_todo_id() throws Exception {
        // Given
        Todo todo = new Todo(1, "for delete", false);
        Integer todoId = todoRepository.save(todo).getId();

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/todos/" + todoId))
                .andExpect(status().isOk());
        assertFalse(todoRepository.findById(todoId).isPresent());
    }

    @Test
    public void should_return_exception_message_when_update_given_non_existent_id() throws Exception {
        // Given

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/todos/0"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Todo not found"))
                .andExpect(jsonPath("$.status").value("404 NOT_FOUND"))
                .andReturn();
    }


}
