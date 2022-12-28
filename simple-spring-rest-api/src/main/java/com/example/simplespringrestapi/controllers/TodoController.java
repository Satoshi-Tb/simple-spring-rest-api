package com.example.simplespringrestapi.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.simplespringrestapi.models.Todo;

@RestController
public class TodoController {
    private final List<Todo> _todoItems = new ArrayList<Todo>() {
        {
            add(new Todo(1, "todo1"));
            add(new Todo(2, "todo2"));
            add(new Todo(2, "todo3"));
        }
    };


    @RequestMapping(method = RequestMethod.GET, path = "/todos")
    public List<Todo> getTodoItems() {
        return _todoItems;
    }


    @RequestMapping(method = RequestMethod.GET, path = "/todos/{id}")
    public Todo getTodoItem(@PathVariable int id) {
        var found = getTodoById(id);

        if (found == null) {
            // 404
            return new Todo();
        }

        return found;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/todos")
    public Todo createTodoItem(@RequestBody Todo newItem) {
        if (isExists(newItem.getId())) {
            // 重複
            return new Todo();
        }

        _todoItems.add(newItem);
        return newItem;
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/todos/{id}")
    public Todo updateTodoItems(@PathVariable int id, @RequestBody Todo updItem) {
        var found = getTodoById(id);

        if (found == null) {
            // 404
            return new Todo();
        }
        _todoItems.remove(found);
        _todoItems.add(updItem);
        return found;
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/todos/{id}")
    public Todo deleteTodoItems(@PathVariable int id) {
        var found = getTodoById(id);

        if (found == null) {
            // 404
            return new Todo();
        }

        _todoItems.remove(found);
        return found;
    }

    private Todo getTodoById(int id) {
        return _todoItems.stream().filter(item -> item.getId() == id).findAny().orElse(null);
    }

    private boolean isExists(int id) {
        return _todoItems.stream().anyMatch(item -> item.getId() == id);
    }
}
