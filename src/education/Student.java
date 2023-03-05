package education;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Student {
    Student() {}
    public Student(String name, double workAbility){
        this.name = name;
        this.workAbility = workAbility;
    }
    public static Student createRandomStudent(){
        /*
        Метод создает студента со случайным именем, из списка возможных имен, и показателем трудоспособности
        (У мальчиков она чуть более случайна).
         */
        var student = new Student();
        student.name = possibleNames.get((int)(Math.random() * ((possibleNames.size()))));
        if (student.isMale()){
            student.workAbility = 0.6 * Math.random() + 0.4;
        }
        else {
            student.workAbility = 0.5 * Math.random() + 0.5;
        }
        return student;
    }
    public String name;
    //возможные имена для студентов с генерацией случайным способом.
    public static List<String> possibleNames = new ArrayList<>(){{
        add("Vova");
        add("Andrew");
        add("Alena");
        add("Anna");
    }};
    public boolean isMale(){
        /*
        Метод возвращает true если студент мужчина, и false, если женшина.
         */
        if (name == null)
            throw new RuntimeException();
        return name.equals("Vova") || name.equals("Andrew");
    }
    public double workAbility;
    public double dailyWorkAbility(){
        /*
        Метод возвращает случайное значение трудоспособности студента в конкретный день,
        у мальчик трудоспособность меньше зависит от конкретного дня(более усредненная).
         */
        if (isMale()){
            return workAbility * 0.7 + Math.random() * 0.3;
        }
        else {
            return workAbility * 0.4 + Math.random() * 0.6;
        }
    }
    public void work(){
        /*
        Метод имитирует работу студентом в течении одного дня
        над списком не выполненных заданий.
         */
        //Если дел больше нет, то заканчиваем работу
        if (tasksToDo.size() == 0){
            System.out.printf(String.format("<%s>: all task complete!\n", name));
            return;
        }
        //кешируем значение сегодняшней трудоспособности
        var todayWorkAbility = dailyWorkAbility();
        //работаем пока не закончатся силы или невыполненные задания
        while (todayWorkAbility > 0 && tasksToDo.size() > 0) {
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
                    System.out.println("TaskComplete!");
                }
            }
        }
    }
    public ArrayList<Task> tasksToDo = new ArrayList<>();
    public ArrayList<Task> tasksCompleted = new ArrayList<>();
}
