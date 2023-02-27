import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathTest {
    @Test
    void factOfNegative(){
        assertEquals(Double.NaN, Math.fact(-1));
    }
    @Test
    void factOfPositive() {
        assertEquals(1, Math.fact(1));
        assertEquals(2, Math.fact(2));
        assertEquals(720, Math.fact(6));
    }
    @Test
    void factOfZero(){
        assertEquals(0, Math.fact(0));
    }
    @Test
    void signOfNegative() {
        assertEquals(-1, Math.sign(-10));
    }
    @Test
    void signOfZero() {
        assertEquals(0, Math.sign(0));
    }

    @Test
    void signOfPositive(){
        assertEquals(1, Math.sign(10));
    }
}