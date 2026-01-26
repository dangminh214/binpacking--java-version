import controller.TestFramework;

public class Main {

    public static void main(String[] args) {
        int instanceNumber = 10000;
        int minW = 1;
        int maxW = 50;
        int minH = 1;
        int maxH = 50;
        int boxL = 100;

        TestFramework tf = new TestFramework(
            instanceNumber,
            minW,
            maxW,
            minH,
            maxH,
            boxL
        );

        tf.generateInstances();
        tf.runGreedy();
    }
}
