package ch.heig.dai.lab.http.api;

import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class TodoController {
    private final Map<Integer, Todo> todos = new HashMap<>();
    private int idCount = 0;

    public void getAll(Context ctx) {
        // System.out.println("getAll");
        ctx.json(todos);
    }

    public void getOne(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Todo todo = todos.get(id);
        if (todo == null) {
            ctx.status(404);
        } else {
            ctx.json(todo);
        }
    }

    public void create(Context ctx) {
        Todo todo = ctx.bodyAsClass(Todo.class);
        todos.put(++idCount, todo);
        ctx.status(201);
    }

    public void update(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        String text = ctx.formParam("text");
        Todo todo = todos.get(id);
        if (todo == null) {
            ctx.status(404);
        } else {
            todo.setText(text);
            ctx.status(200);
        }
    }

    public void setDone(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Todo todo = todos.get(id);
        if (todo == null) {
            ctx.status(404);
        } else {
            todo.setDone();
            ctx.status(200);
        }
    }

    public void setUndone(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Todo todo = todos.get(id);
        if (todo == null) {
            ctx.status(404);
        } else {
            todo.setUndone();
            ctx.status(200);
        }
    }

    public void delete(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        todos.remove(id);
        ctx.status(204);
    }
}
