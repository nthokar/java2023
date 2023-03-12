package education;

import java.time.LocalDateTime;
import java.util.Random;

public class Mark {
    private static final Random random = new Random();
    Mark() {}
    public static Mark randomMark(int maxMark){
        var mark = new Mark();
        mark.value = random.nextInt(maxMark);
        mark.isValid = false;
        mark.evaluatedAt = LocalDateTime.now();
        mark.evaluatedBy = "SYSTEM";
        return mark;
    }
    private int value;
    public int getValue() {
        return value;
    }

    private boolean isValid;
    public boolean isValid() {
        return isValid;
    }

    private LocalDateTime evaluatedAt;
    private String evaluatedBy;
}
