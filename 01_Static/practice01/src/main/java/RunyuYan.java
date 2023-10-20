public class RunyuYan {
    private static int count = 0;

    public RunyuYan() {
        count++;

        System.out.format("Construct class [%s] || Class counts: %d\n", this.getClass().getName(), count);
    }
}
