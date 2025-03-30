public class Question {
    private boolean flagged;

    public void flagQuestion() {
        this.flagged = true;
    }

    public void unflagQuestion() {
        this.flagged = false;
    }

    public boolean isFlagged() {
        return flagged;
    }
}
