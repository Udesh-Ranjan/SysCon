/**
 * @author :  dev parzival
 * @date :  18 Apr, 2021
 */
public class MainClass {
    private final MainFrame mainFrame;

    public MainClass() {
        mainFrame = new MainFrame(500, 500);
        mainFrame.setTitle("SysCon");
        mainFrame.setVisible(true);
    }

    public static void main(String $[]) {
        System.out.println(System.getProperty("Present working directory : "+"dir"));
        final MainClass mainClass = new MainClass();
    }
}
