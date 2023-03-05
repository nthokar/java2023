package education;

import java.util.ArrayList;

public class Simulator {
    public static void main(String[] args) {
        var tasks = new ArrayList<Task>();
        for (int i = 0; i < 3; i++){
            var task = Task.generateRandomTask();
            tasks.add(task);
        }
        var students = new ArrayList<Student>();
        for (int i = 0; i < 3; i++){
            var st = Student.createRandomStudent();
            st.tasksToDo = new ArrayList<>(tasks);
            students.add(st);
        }
        var professor = new Professor();
        professor.students = students;
        for (;;){
            try {
                Thread.sleep(1000);
                System.out.println("new day :)");
                for (var student:students) {
                    Thread.sleep(1000);
                    student.work();
                }
                professor.EvaluateRandom();
            }
            catch (Exception e){
            }
        }
    }
}
