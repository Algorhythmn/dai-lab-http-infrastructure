public class Todo {
    private boolean isDone = false;
    private String text;

    public Todo(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setDone() {
        isDone = !isDone;
    }
}
