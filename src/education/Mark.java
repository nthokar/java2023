package education;

import java.time.LocalDateTime;
import java.util.Date;

public class Mark {
    Mark() {}
    public static Mark randomEvaluate(int maxMark){
        var mark = new Mark();
        mark.value = (int) (Math.random() * maxMark);
        mark.evaluatedAt = LocalDateTime.now();
        mark.evaluatedBy = "SYSTEM";
        return mark;
    }
    private int value;
    private boolean isValid;
    private LocalDateTime evaluatedAt;
    private String evaluatedBy;
}
