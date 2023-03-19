package education;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

public class Task{
    private static final Random random = new Random();
    public static class Builder {
        private String title;
        private Double difficulty;
        private Integer maxMarkValue;
        public Task.Builder title(String val){
            title = val;
            return this;
        }
        public Task.Builder difficulty(double val){
            difficulty = val;
            return this;
        }
        public Task.Builder maxMarkValue(int val){
            maxMarkValue = val;
            return this;
        }
        public Task build(){
            return new Task(this);
        }
        public static Task.Builder generateRandomTask(){
            var builder = new Builder();
            builder.difficulty = random.nextDouble();
            builder.maxMarkValue = (int) ((builder.difficulty * 3) + random.nextInt(2)) * 20;
            return builder;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj instanceof Builder) {
                var builder = (Builder) obj;
                return Objects.equals(title, builder.title) &&
                        Objects.equals(difficulty, builder.difficulty) &&
                        Objects.equals(maxMarkValue, builder.maxMarkValue);
            }
            return false;
        }
    }
    private Task(Builder builder){
        this.title = builder.title;
        this.difficulty = builder.difficulty;
        this.maxMarkValue = builder.maxMarkValue;
    }

    private Task(Task task){
        this.title = task.title;
        this.difficulty = task.difficulty;
        this.maxMarkValue = task.getMaxMarkValue();
        this.mark = task.mark;
    }
    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private double difficulty;
    @Getter
    private final int maxMarkValue;
    @Getter
    private Mark mark;
    public void evaluate(Professor professor){
        professor.evaluateRandom(this);
    }
    @Getter
    private LocalDateTime handedAt;

    public void hand(){
        handedAt = LocalDateTime.now();
    }
    @Getter
    private LocalDateTime gavenAt;
    public void give() {
        gavenAt = LocalDateTime.now();
    }

    public Task copy(){
        return new Task(this);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Task) {
            var task = (Task) obj;
            return Objects.equals(title, task.title) &&
                    Objects.equals(difficulty, task.difficulty) &&
                    Objects.equals(maxMarkValue, task.maxMarkValue);
        }
        return false;
    }
}
