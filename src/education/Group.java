package education;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Group {
    private Professor professor;
    private List<Student> students = new ArrayList<>();
    public void setNewTask(Task task){
        for (var student:students){
            student.addTasksToDo(task);
        }
    }
    public void evaluateStudents(){
        for (var student:students){
            for (var task:student.getTasksCompleted()){
                if (Objects.nonNull(task.getMark())) {
                    professor.evaluateRandom(students);
                }
            }
        }
    }
}
