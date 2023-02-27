public class Math {
    public static Double fact(int x){
        if (x < 0) return Double.NaN;
        return x > 1 ? fact(x-1)*x : x;
    }

    public static int sign(int x){
        return Integer.compare(x, 0);
    }
}
