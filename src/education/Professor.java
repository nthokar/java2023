package education;

import java.util.ArrayList;

public class Professor {
    ArrayList<Student> students = new ArrayList<>();

    public void EvaluateRandom(){
        for (var student:students){
            for (var task:student.tasksCompleted){
                task.evaluate(Mark.randomEvaluate(task.getMaxMarkValue()));
            }
        }
    }
    public void Evaluate() {

    }
}
