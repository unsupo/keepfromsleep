import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

public abstract class AbstractJavaRobot {

    static Dimension dim;
    String path = "";// = "C:/Users/jarndt/Documents/pEclipse/stuff/";
    Robot robot;
    public AbstractJavaRobot(){
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void setAbsolutePath(String path){
        this.path = path;
    }

    public boolean compareImages(BufferedImage a, BufferedImage b) {
        if (a.getHeight() != b.getHeight() || a.getWidth() != b.getWidth())
            return false;
        for (int i = 0; i < a.getWidth(); i++)
            for (int j = 0; j < a.getHeight(); j++)
                if (a.getRGB(i, j) != b.getRGB(i, j))
                    return false;
        return true;
    }

    public void createBufferedImageFile(BufferedImage image, String path, String...type) throws IOException{
        String value = "png", absolutePath = path;
        if(type.length == 1){
            value = type[0];
        }
        if(!path.contains("\\") || !path.contains("/")){
            absolutePath = this.path + path;
        }
        File outputfile = new File(absolutePath);
        ImageIO.write(image, value, outputfile);
    }


    public BufferedImage getScreenCapture() {
        return robot.createScreenCapture(new Rectangle(Toolkit
                .getDefaultToolkit().getScreenSize()));
    }public BufferedImage getScreenCapture(int x, int y, int w, int h) {
        BufferedImage wholeScreen = robot.createScreenCapture(new Rectangle(Toolkit
                .getDefaultToolkit().getScreenSize()));

        return wholeScreen.getSubimage(x, y, w, h);
    }

    public void writefile(String filename, List<Point> list) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filename, "UTF-8");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        writer.println(list);
        writer.close();
    }

    public List<Point> readfile(String filename) {
        Scanner inFile1 = null;
        try {
            inFile1 = new Scanner(new File(filename)).useDelimiter(", ");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String token1 = "";
        List<Point> temps = new ArrayList<Point>();
        // List<String> temps = new ArrayList<String>();
        // while loop
        while (inFile1.hasNext()) {
            // find next line
            token1 = inFile1.next();
            token1 = token1.replaceAll("[^0-9,]", "");
            String[] s = token1.split(",");
            Point p = new Point();
            p.setLocation(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
            temps.add(p);
            // String[] s = token1.split("=");
            // temps.add(new Point(Integer.valueOf(s[2])));
        }
        inFile1.close();
        return temps;
    }


    public boolean lookforimage(String file, boolean... param) {
        BufferedImage current = robot.createScreenCapture(new Rectangle(Toolkit
                .getDefaultToolkit().getScreenSize()));
        BufferedImage icon = null;
        try {
            icon = ImageIO.read(new File(file));
        } catch (IOException e) {
            System.out.println("Unable to open file");
        }
        for (int x = 0; x < current.getWidth(); x++)
            for (int y = 0; y < current.getHeight(); y++) {
                boolean matches = true;
                for (int x2 = 0; x2 < icon.getWidth() && matches; x2++)
                    for (int y2 = 0; y2 < icon.getHeight() && matches; y2++)
                        if (icon.getRGB(x2, y2) != current.getRGB(x + x2, y
                                + y2))
                            matches = false;
                if (matches) {
                    try {
                        if (param[0])
                            MM(x, y);
                    } catch (Exception e) {
                        MM(x, y);
                    }
                    return true;
                }
            }
        return false;
    }

    public void lookforcolor(Color c) {
        // (int)(dim.width*.366) (int)(dim.width*.4)
        for (int i = 0; i < dim.width * 1; i++) {
            for (int j = 0; j < dim.height * 1; j++) {
                if (robot.getPixelColor(i, j).equals(c)) {
                    System.out.println(i + " " + j);
                    MM(i, j);
                    return;
                }

            }
        }
    }

    public void openapp(String name) {
        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.keyRelease(KeyEvent.VK_WINDOWS);

        typeASCII(name);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        robot.delay(1000);
    }

    public void MM(int x, int y) {
        robot.mouseMove(x, y);
    }

    public void MM(double x, double y, boolean isPercent) {
        robot.mouseMove((int) (dim.width * x), (int) (dim.height * y));
    }

    public void MM(int x, int y, int actionLC1RC2CC3) {
        robot.mouseMove(x, y);
        click(actionLC1RC2CC3);
    }

    public void ctrl(int v) {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(v);
        robot.keyRelease(v);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }

    public void shift(int v) {
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(v);
        robot.keyRelease(v);
        robot.keyRelease(KeyEvent.VK_SHIFT);
    }

    public void alt(int v) {
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(v);
        robot.keyRelease(v);
        robot.keyRelease(KeyEvent.VK_ALT);
    }

    public void keypr(int v) {
        robot.keyPress(v);
        robot.keyRelease(v);
    }

    public void keypr(int v, int d) {
        robot.keyPress(v);
        robot.delay(d);
        robot.keyRelease(v);
    }

    public void keypr(int v, int d, int times) {
        for (int i = 0; i < times; i++) {
            robot.keyPress(v);
            robot.delay(d);
            robot.keyRelease(v);
        }
    }

    public void drag(int x, int y, int x1, int y1) {
        MM(x, y);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        MM(x1, y1);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public void drag(double x, double y, double x1, double y1) {
        MM(x, y, true);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        MM(x1, y1, true);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public void click(int... x) {
        try {
            switch (x[0]) {
                case 1:
                    robot.mousePress(InputEvent.BUTTON1_MASK);
                    robot.delay(200);
                    robot.mouseRelease(InputEvent.BUTTON1_MASK);
                    robot.delay(200);
                    break;
                case 2:
                    robot.mousePress(InputEvent.BUTTON2_MASK);
                    robot.delay(200);
                    robot.mouseRelease(InputEvent.BUTTON2_MASK);
                    robot.delay(200);
                    break;
                case 3:
                    robot.mousePress(InputEvent.BUTTON3_MASK);
                    robot.delay(200);
                    robot.mouseRelease(InputEvent.BUTTON3_MASK);
                    robot.delay(200);
                    break;
            }
        } catch (Exception e) {
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.delay(200);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            robot.delay(200);
        }
    }

    public void leftClick() {
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(200);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(200);
    }

    public void rightClick() {
        robot.mousePress(InputEvent.BUTTON3_MASK);
        robot.delay(200);
        robot.mouseRelease(InputEvent.BUTTON3_MASK);
        robot.delay(200);
    }

    public boolean performSpecialCharacter(String testString, int startIndex, String character, int action){
        if(startIndex<testString.length()-character.length()+1
                && testString.substring(startIndex,startIndex+character.length()).equals(character)) {
            robot.keyPress(action);
            return true;
        }
        return false;
    }

    public void type(String test) {
        HashMap<String,Integer> special = new HashMap<>();
        special.put("\\n",KeyEvent.VK_ENTER);
        special.put("\\t",KeyEvent.VK_TAB);
        special.put("\\d",KeyEvent.VK_DOWN);
        special.put("\\u",KeyEvent.VK_UP);
        special.put("\\r",KeyEvent.VK_RIGHT);
        special.put("\\l",KeyEvent.VK_LEFT);
        special.put("\\bs",KeyEvent.VK_BACK_SPACE);
        special.put("\\del",KeyEvent.VK_DELETE);
        special.put("\\home",KeyEvent.VK_HOME);
        special.put("\\pgup",KeyEvent.VK_PAGE_UP);
        special.put("\\pgdn",KeyEvent.VK_PAGE_DOWN);
        special.put("\\esc",KeyEvent.VK_ESCAPE);

        for (int i = 0; i < test.length(); i++) {
            int j = i;
            for(Map.Entry<String, Integer> s : special.entrySet())
                if(performSpecialCharacter(test, i,s.getKey(),s.getValue())) {
                    i+=s.getKey().length()-1;
                    break;
                }
            if(j != i) continue;

            switch (test.charAt(i)) {
                case '!':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    keypr(KeyEvent.VK_1);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '@':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    keypr(KeyEvent.VK_2);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '#':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    keypr(KeyEvent.VK_3);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '$':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    keypr(KeyEvent.VK_4);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '%':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    keypr(KeyEvent.VK_5);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '^':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    keypr(KeyEvent.VK_6);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '&':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    keypr(KeyEvent.VK_7);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '*':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    keypr(KeyEvent.VK_8);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '(':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    keypr(KeyEvent.VK_9);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case ')':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    keypr(KeyEvent.VK_0);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '_':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    keypr(KeyEvent.VK_MINUS);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '+':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    keypr(KeyEvent.VK_EQUALS);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case ':':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    keypr(KeyEvent.VK_SEMICOLON);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '?':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    keypr(KeyEvent.VK_SLASH);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '}':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    keypr(KeyEvent.VK_BRACERIGHT);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '{':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    keypr(KeyEvent.VK_BRACELEFT);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                case '~':
                    robot.keyPress(KeyEvent.VK_DOWN);
                    robot.keyRelease(KeyEvent.VK_DOWN);
                    break;
                case '\'':
                    keypr(KeyEvent.VK_QUOTE);
                    break;
                case '|':
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    keypr(KeyEvent.VK_BACK_SLASH);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    break;
                default:
                    boolean b = !(test.charAt(i)+"").toLowerCase().equals(test.charAt(i)+"");
                    if(b) robot.keyPress(KeyEvent.VK_SHIFT);
                    typeASCII("" + test.charAt(i));
                    if(b) robot.keyRelease(KeyEvent.VK_SHIFT);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }
    }

    public void type(int i) {
        robot.delay(40);
        robot.keyPress(i);
        robot.keyRelease(i);
    }

    public void typeASCII(String s) {
        byte[] bytes = s.getBytes();
        for (byte b : bytes) {
            int code = b;
            // keycode only handles [A-Z] (which is ASCII decimal [65-90])
            if (code > 96 && code < 123)
                code = code - 32;
            robot.delay(40);
            robot.keyPress(code);
            robot.keyRelease(code);
        }
    }

    public Robot getRobot() {
        return robot;
    }

    public double[] getCurrentMousePosition(){
        Point value = MouseInfo.getPointerInfo().getLocation();
        return new double[]{value.getX(),value.getY()};
    }

}