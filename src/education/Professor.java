package education;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Professor {
    ArrayList<Student> students = new ArrayList<>();
    public Mark evaluateRandom(Task task){
        if (Objects.nonNull(task.getMark()))
            return Mark.randomMark(task.getMaxMarkValue());
        return task.getMark();
    }
    public void setNewTaskRandom(){
        var task = Task.Builder.generateRandomTask().build();
        for (var student:students){
            student.addTasksToDo(task);
        }
    }
    public void setNewTask(Task task){
        for (var student:students){
            student.addTasksToDo(task);
        }
    }
}
