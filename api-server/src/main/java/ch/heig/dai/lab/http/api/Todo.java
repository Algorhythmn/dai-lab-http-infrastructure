package ch.heig.dai.lab.http.api;

public class Todo {
    private boolean isDone = false;
    private String text;

    public Todo() {
    }

    public Todo(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDone() {
        isDone = true;
    }

    public void setUndone() {
        isDone = false;
    }
}
