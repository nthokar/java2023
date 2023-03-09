package education;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class Student {
    Student() {}
    Student(String name, double workAbility){
        this.name = name;
        this.workAbility = workAbility;
    }
    public static class Builder {
        private String name;
        private boolean male;
        private double workAbility;
        private final List<Task> tasksToDo = new ArrayList<>();
        public Builder name(String val){
            name = val;
            return this;
        }
        public Builder male(boolean val){
            male = val;
            return this;
        }
        public Builder workAbility(double val){
            workAbility = val;
            return this;
        }
        public Builder addTaskToDo(Task val){
            tasksToDo.add(val);
            return this;
        }

        public Student build(){
            return new Student(this);
        }
    }
    private Student(Builder builder){
        this.name = builder.name;
        this.workAbility = builder.workAbility;
        this.tasksToDo = builder.tasksToDo;
        this.male = builder.male;
    }
    public static Student createRandomStudent(){
        /*
        Метод создает студента со случайным именем, из списка возможных имен, и показателем трудоспособности
        (У мальчиков она чуть более случайна).
         */
        var student = new Student();
        student.name = possibleNames.get(random.nextInt(possibleNames.size()));
        if (student.male){
            student.workAbility = 0.6 * Math.random() + 0.4;
        }
        else {
            student.workAbility = 0.5 * Math.random() + 0.5;
        }
        return student;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    private String name;
    //возможные имена для студентов с генерацией случайным способом.
    private static final List<String> possibleNames = new ArrayList<>();
    static {
        possibleNames.add("Vova");
        possibleNames.add("Andrew");
        possibleNames.add("Alena");
        possibleNames.add("Anna");
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    private boolean male;

    public double dailyWorkAbility(){
        /*
        Метод возвращает случайное значение трудоспособности студента в конкретный день,
        у мальчик трудоспособность меньше зависит от конкретного дня(более усредненная).
         */
        if (male){
            return workAbility * 0.7 + Math.random() * 0.3;
        }
        else {
            return workAbility * 0.4 + Math.random() * 0.6;
        }
    }

    private double workAbility;

    public void work(){
        /*
        Метод имитирует работу студентом в течении одного дня
        над списком не выполненных заданий.
         */
        //Если дел больше нет, то заканчиваем работу
        if (tasksToDo.isEmpty()){
            logger.info(String.format("<%s>: all task complete!%n", name));
            return;
        }
        //кешируем значение сегодняшней трудоспособности
        var todayWorkAbility = dailyWorkAbility();
        //работаем пока не закончатся силы или невыполненные задания
        while (todayWorkAbility > 0 && !tasksToDo.isEmpty()) {
            var curTask = tasksToDo.get(0);
            if (curTask != null){
                //если задание нам не по силам то делаем его часть и отдыхаем.
                if ((curTask.getDifficulty() - todayWorkAbility) > 0 ){
                    curTask.setDifficulty(curTask.getDifficulty() - todayWorkAbility);
                    todayWorkAbility = 0;
                }
                //если задание по силам то тогда выполняем его и переходим к следующему.
                else {
                    todayWorkAbility = todayWorkAbility - curTask.getDifficulty();
                    tasksCompleted.add(curTask);
                    tasksToDo.remove(0);
                    logger.info(String.format("<%s>: task complete!%n", name));
                }
            }
        }
    }

    public List<Task> getTasksToDo() {
        return tasksToDo;
    }

    private List<Task> tasksToDo = new ArrayList<>();

    public void setTasksToDo(List<Task> tasksToDo) {
        this.tasksToDo = tasksToDo;
    }

    public List<Task> getTasksCompleted() {
        return tasksCompleted;
    }

    public void setTasksCompleted(List<Task> tasksCompleted) {
        this.tasksCompleted = tasksCompleted;
    }
    private List<Task> tasksCompleted = new ArrayList<>();
    private static final Random random = new Random();
    private static final Logger logger = Logger.getLogger(Student.class.getName());
}
