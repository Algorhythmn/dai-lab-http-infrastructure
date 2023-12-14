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

    }

    public void delete(Context ctx) {

    }
}
