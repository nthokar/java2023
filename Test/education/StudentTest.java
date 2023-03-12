package education;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    @Test
    void createRandomStudent(){
        var student1 = Student.Builder.createRandomStudent();
        var student2 = Student.Builder.createRandomStudent();

        assertNotEquals(student1, student2);
        assertEquals(student1.build().getClass().getName(), Student.class.getName());
    }
    @Test
    void addTaskToDo(){
        var taskToDo = Task.Builder.generateRandomTask().build();
        var student = Student.Builder.createRandomStudent();

        student.addTaskToDo(taskToDo).addTaskToDo(taskToDo);

        assertEquals(2, student.build().getTasksToDo().size());
    }

    @Test
    void work() {
        // метод проверяет не влияет ли работа одного студента
        // на прогресс выполнение задания второго
        var st1 = new Student("Vova", 0.0001);
        var st2 = new Student("Andrew", 0.0002);

        var tasks = new ArrayList<Task>(){{add(Task.Builder.generateRandomTask().build());}};
        for (var task:tasks){
            try {
                st1.addTasksToDo(task.copy());
                st2.addTasksToDo(task.copy());
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        st1.work();
        double workAfterStudent1 = st1.getTasksToDo().get(0).getDifficulty();
        st2.work();
        double workAfterStudent2 = st1.getTasksToDo().get(0).getDifficulty();
        assertEquals(workAfterStudent1, workAfterStudent2);
    }
}