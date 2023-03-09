package education;

import java.time.LocalDateTime;
import java.util.Random;

public class Mark {
    public static final Random random = new Random();
    Mark() {}
    public static Mark randomMark(int maxMark){
        var mark = new Mark();
        mark.value = random.nextInt(maxMark);
        mark.evaluatedAt = LocalDateTime.now();
        mark.evaluatedBy = "SYSTEM";
        return mark;
    }
    private int value;
    private boolean isValid;
    private LocalDateTime evaluatedAt;
    private String evaluatedBy;
}
