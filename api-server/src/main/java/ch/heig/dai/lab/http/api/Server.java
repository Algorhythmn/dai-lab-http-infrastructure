package ch.heig.dai.lab.http.api;

import io.javalin.*;

public class Server {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(80);
        TodoController todoController = new TodoController();
        app.get("/api/todo/all", todoController::getAll);
        app.get("/api/todo/{id}", todoController::getOne);
        app.post("/api/todo/", todoController::create);
        app.put("/api/todo/{id}", todoController::update);
        app.put("/api/todo/{id}/setDone", todoController::setDone);
        app.put("/api/todo/{id}/setUndone", todoController::setUndone);
        app.delete("/api/todo/{id}", todoController::delete);
    }
}
