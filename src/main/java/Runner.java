import java.util.Random;

public class Runner {

    public static class R extends AbstractJavaRobot{}

    public static void main(String[] args) throws InterruptedException {
        R robot = new R();
        Random r = new Random();
        while(true) {
            double[] d;
            try {
                 d = robot.getCurrentMousePosition();
            }catch (NullPointerException e){
                /*DO NOUGHING*/
                continue;
            }
            int[] ran = {(int) (d[0]+getRand()), (int) (d[1]+getRand())};
            robot.MM(ran[0],ran[1]);
            Thread.sleep(60000);
        }
    }

    static Random r = new Random();
    public static int getRand(){
        return r.nextBoolean() ? 1 : -1;
    }
}
