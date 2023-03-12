package education;

import java.util.ArrayList;
import java.util.List;

public class Professor {
    ArrayList<Student> students = new ArrayList<>();

    public void evaluateRandom(List<Student> students){
        for (var student:students){
            for (var task:student.getTasksToDo()){
                task.evaluate(Mark.randomMark(task.getMaxMarkValue()));
            }
        }
    }
    public void evaluate() {
        //Unimplemented
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
