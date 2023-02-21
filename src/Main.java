public class Main {
    public static void main(String[] args) {
        Assistant assistant = new Assistant(System.in);
        assistant.askUsername();
        assistant.greetingsUser();
        assistant.printSynTable();
    }
}