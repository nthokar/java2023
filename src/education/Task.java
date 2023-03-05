package education;

public class Task implements Cloneable{
    public String id;
    public String title;
    private String description;
    private double difficulty;
    public double getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }
    private final int maxMarkValue;
    public int getMaxMarkValue() {
        return maxMarkValue;
    }
    private Mark mark;
    Task (int maxMarkValue){
        this.maxMarkValue = maxMarkValue;
    }
    Task(String id, String title, String description, double difficulty, int maxMark, Mark mark){
        this.id = id;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.maxMarkValue = maxMark;
        this.mark = mark;
    }
    public static Task generateRandomTask(){
        var difficulty = Math.random();
        var task = new Task((int) ((difficulty * 3) + Math.random() * 2) * 20);
        task.difficulty = difficulty;
        return task;
    }

    public void evaluate(Mark mark){
        this.mark = mark;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Task(id, title, description, difficulty, maxMarkValue, mark);
    }
}
