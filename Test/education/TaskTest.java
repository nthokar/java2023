package education;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    void generateRandomTask() {
        //
        var task1 = Task.Builder.generateRandomTask();
        var task2 = Task.Builder.generateRandomTask();

        assertNotEquals(task1, task2);
        assertEquals(task1.build().getClass().getName(), Task.class.getName());
    }

    @Test
    void copy() {
        var task1 = Task.Builder.generateRandomTask().build();
        var copy = task1.copy();

        assertNotSame(task1, copy);
        assertEquals(task1, copy);
    }
}