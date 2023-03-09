package education;

import java.util.Random;

public class Task{
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    private String title;
    private String description;
    private double difficulty;
    public static final Random random = new Random();
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
    private Task (int maxMarkValue){
        this.maxMarkValue = maxMarkValue;
    }
    private Task(Task task){
        this.title = task.title;
        this.description = task.description;
        this.difficulty = task.difficulty;
        this.maxMarkValue = task.getMaxMarkValue();
        this.mark = task.mark;
    }
    public Task copy(){
        return new Task(this);
    }
    public static Task generateRandomTask(){
        var difficulty = random.nextDouble();
        var task = new Task((int) ((difficulty * 3) + random.nextInt(2)) * 20);
        task.difficulty = difficulty;
        return task;
    }
    public void evaluate(Mark mark){
        this.mark = mark;
    }
}
