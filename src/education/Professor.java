package education;

import java.util.ArrayList;

public class Professor {
    ArrayList<Student> students = new ArrayList<>();

    public void evaluateRandom(){
        for (var student:students){
            for (var task:student.getTasksToDo()){
                task.evaluate(Mark.randomMark(task.getMaxMarkValue()));
            }
        }
    }
    public void evaluate() {
        //Unimplemented
    }
}
