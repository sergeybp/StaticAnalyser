public class Test {

    private static final String badName = "TEST";

    public void test1(int in) {
        int x = 1000;
    }

    public void test2() {
        boolean flag = true;
        int x = 1000;
        if (!flag && flag) {
            int same = 1;
        } else {
            int same = 1;
        }
    }
}