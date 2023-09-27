package com.michal.todo.service.impl;

import com.michal.todo.dto.TodoDto;
import com.michal.todo.entity.Todo;
import com.michal.todo.exception.ResourceNotFoundException;
import com.michal.todo.repository.TodoRepository;
import com.michal.todo.service.TodoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;
    private ModelMapper modelMapper;

    @Override
    public TodoDto addTodo(TodoDto todoDto) {

        //convert TodoDto into JPA
        Todo todo = modelMapper.map(todoDto, Todo.class);

        //save
        Todo savedTodo = todoRepository.save(todo);

        // conv back
        TodoDto todoDto1 = modelMapper.map(savedTodo, TodoDto.class);

        return todoDto1;
    }

    @Override
    public TodoDto getTodo(Long id) {

        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("wrong id: " + id)
        );

        TodoDto todoDto = modelMapper.map(todo, TodoDto.class);
        return todoDto;
    }

    @Override
    public List<TodoDto> getAllTodos() {
        List<Todo> todoList = todoRepository.findAll();

        List<TodoDto> todoDtoList = todoList.stream()
                .map((todo) -> modelMapper.map(todo, TodoDto.class)).toList();

        return todoDtoList;
    }

    @Override
    public TodoDto updateToDo(TodoDto todoDto, Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("cannot update todo with this id:" + todoDto.getId())
        );

        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todo.isCompleted());

        Todo savedTodo = todoRepository.save(todo);
        TodoDto todoDto1 = modelMapper.map(savedTodo, TodoDto.class);

        return todoDto1;
    }


}
