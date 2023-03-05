package education;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void dailyWorkAbility() {
    }

    @Test
    void work() {
        // метод проверяет не влияет ли работа одного студента
        // на прогресс выполнение задания второго
        var st1 = new Student("Vova", 0.0001);
        var st2 = new Student("Andrew", 0.0002);

        var tasks = new ArrayList<Task>(){{add(Task.generateRandomTask());}};
        for (var task:tasks){
            try {
                st1.tasksToDo.add((Task) task.clone());
                st2.tasksToDo.add((Task) task.clone());
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        st1.work();
        double workAfterStudent1 = st1.tasksToDo.get(0).getDifficulty();
        st2.work();
        double workAfterStudent2 = st1.tasksToDo.get(0).getDifficulty();
        assertEquals(workAfterStudent1, workAfterStudent2);
    }
}