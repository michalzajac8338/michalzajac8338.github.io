package com.michal.todo.service;

import com.michal.todo.TodoManagementApplication;
import com.michal.todo.dto.TodoDto;

import java.util.List;

public interface TodoService {

    TodoDto addTodo(TodoDto todoDto);
    TodoDto getTodo(Long id);
    List<TodoDto> getAllTodos();
    TodoDto updateToDo(TodoDto todoDto, Long id);
    void deleteTodo(Long id);
    TodoDto completeTodo(Long id);
    TodoDto incompleteTodo(Long id);

}
