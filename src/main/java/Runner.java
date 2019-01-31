import java.util.Random;

public class Runner {

    public static class R extends AbstractJavaRobot{}

    public static void main(String[] args) throws InterruptedException {
        long millis = 5*60*1000; // default 5 minutes
        if(args != null && args.length > 0)
            try{
                millis = Long.parseLong(args[0]);
            }catch (Exception e){/* DO NOTHING */}
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
            Thread.sleep(millis);
        }
    }

    static Random r = new Random();
    public static int getRand(){
        return r.nextBoolean() ? 1 : -1;
    }
}
