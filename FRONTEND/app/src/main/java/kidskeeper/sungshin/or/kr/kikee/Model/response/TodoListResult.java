package kidskeeper.sungshin.or.kr.kikee.Model.response;

import java.util.ArrayList;

import kidskeeper.sungshin.or.kr.kikee.Model.request.TodoList;

/**
 * Created by LG on 2018-08-23.
 */

public class TodoListResult {
    private String message;
    private ArrayList<TodoList> todos;

    public TodoListResult(String messgae, ArrayList<TodoList> todoLists) {
        this.message = messgae;
        this.todos = todoLists;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<TodoList> getTodos() {
        return todos;
    }

    public void setTodos(ArrayList<TodoList> todos) {
        this.todos = todos;
    }
}
