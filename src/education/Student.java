package education;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Logger;

public class Student {
    private static final Random random = new Random();
    private static final Logger logger = Logger.getLogger(Student.class.getName());
    Student(String name, double workAbility){
        this.name = name;
        this.workAbility = workAbility;
    }
    private Student(Builder builder){
        this.name = builder.name;
        this.workAbility = builder.workAbility;
        this.tasksToDo = builder.tasksToDo;
        this.male = builder.male;
    }
    public static class Builder {
        private String name;
        private boolean male;
        private double workAbility;
        private final List<Task> tasksToDo = new ArrayList<>();
        //возможные имена для студентов с генерацией случайным способом.
        private static final List<String> possibleMaleNames = new ArrayList<>();
        static {
            possibleMaleNames.add("Vova");
            possibleMaleNames.add("Andrew");
        }
        private static final List<String> possibleFemaleNames = new ArrayList<>();
        static {
            possibleFemaleNames.add("Alena");
            possibleFemaleNames.add("Anna");
        }
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
        public static Builder createRandomStudent(){
        /*
        Метод создает студента со случайным именем, из списка возможных имен, и показателем трудоспособности
        (У мальчиков она чуть более случайна).
         */
            var builder = new Builder();
            builder.male = random.nextInt(2) == 0;
            if (builder.male){
                builder.name = possibleMaleNames.get(random.nextInt(possibleMaleNames.size()));
                builder.workAbility = 0.6 * Math.random() + 0.4;
            }
            else {
                builder.name = possibleFemaleNames.get(random.nextInt(possibleFemaleNames.size()));
                builder.workAbility = 0.5 * Math.random() + 0.5;
            }
            return builder;
        }
        public Builder addTaskToDo(Task val){
            val.give();
            tasksToDo.add(val);
            return this;
        }
        public Student build(){
            return new Student(this);
        }
    }

    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private boolean male;
    public boolean isMale() {
        return male;
    }
    public void setMale(boolean male) {
        this.male = male;
    }

    private double motivation = 0.5;
    private void updateMotivation(){
        /*  Мотивация студента зависит от добросогестного отношения преподователя.
            Если задания проверяются в срок и они оценки валидны(то есть оценки зависят от выполненой работы,
            если студент старался и задание удалось то оценка соотвествует) то студенты счастливы и их интерес к
            предмету повышается.
        */
        var lastCompletedTask = tasksCompleted.get(tasksCompleted.size() - 1);
        if (Objects.nonNull(lastCompletedTask.getMark())){
            motivation += random.nextDouble() * 0.1;

            if (lastCompletedTask.getMark().isValid()){
                motivation += random.nextDouble() * 0.3;
            }
        }
        else{
            motivation -= random.nextDouble() * 0.3;
        }
    }
    private final double workAbility;
    public double dailyWorkAbility(){
        /*
        Метод возвращает случайное значение трудоспособности студента в конкретный день,
        у мальчик трудоспособность меньше зависит от конкретного дня(более усредненная).
         */
        updateMotivation();
        if (male){
            return workAbility * 0.7 + random.nextDouble() * 0.3 + motivation;
        }
        else {
            return workAbility * 0.4 + random.nextDouble() * 0.6 + motivation;
        }
    }
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
                //если задание по силам то тогда сдаём его и переходим к следующему.
                else {
                    todayWorkAbility = todayWorkAbility - curTask.getDifficulty();
                    handTask(curTask);
                    logger.info(String.format("<%s>: task complete!%n", name));
                }
            }
        }
    }

    private List<Task> tasksToDo = new ArrayList<>();
    public List<Task> getTasksToDo() {
        return tasksToDo;
    }
    public List<Task> addTasksToDo(Task task) {
        task.give();
        tasksToDo.add(task);
        return tasksToDo;
    }
    public void setTasksToDo(List<Task> tasksToDo) {
        this.tasksToDo = tasksToDo;
    }
    private final List<Task> tasksCompleted = new ArrayList<>();
    public List<Task> getTasksCompleted() {
        return tasksCompleted;
    }
    private void handTask(Task task){
        if (!tasksToDo.contains(task))
            return;
        tasksToDo.remove(task);
        task.hand();
        tasksCompleted.add(task);
    }
}
