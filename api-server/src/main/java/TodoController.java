import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class TodoController {
    private final List<Todo> todos = new ArrayList<>();

    public void getAll(Context ctx) {
        ctx.json(todos);
    }

    public void getOne(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        ctx.json(todos.get(id));
    }

    public void create(Context ctx) {
        String text = ctx.formParam("text");
        todos.add(new Todo(text));
    }

    public void update(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        String text = ctx.formParam("text");
        todos.get(id).setText(text);
    }

    public void setDone(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        todos.get(id).setDone();
    }

    public void setUndone(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        todos.get(id).setUndone();
    }

    public void delete(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        todos.remove(id);
    }
}
