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
        /*System.out.println("arguqments length : "+$.length);
        System.out.println("arguments passed");

        for (final String str : $)
            System.out.println(str);
        */
        System.out.println(System.getProperty("Present working directory : " + "dir"));
        if ($.length != 0) {
            StringBuilder cmd = new StringBuilder("");
            for (int i = 0; i < $.length; i++) {
                final String str = $[i];
                cmd.append(i == $.length - 1 ? str.trim() : str.trim() + " ");
            }
            execute(cmd.toString());
        } else {
            final MainClass mainClass = new MainClass();
        }
    }

    public static void execute(final String cmd) {
        System.out.println("cmd : " + cmd + ".");
        String arr[] = cmd.split(" ");
        if (arr.length >= 1) {
            if (arr[0].equals("mutevolume")) {
                System.out.println("mute : system volume");
                SoundController.muteVolume();
            }
            if (arr[0].equals("unmutevolume")) {
                System.out.println("unmute : system volume");
                SoundController.unmuteVolume();
            }
            if (arr.length >= 2) {
                if (arr[0].equals("setvolume")) {
                    if (isParsableDouble(arr[1])) {
                        SoundController.setVolume(Double.parseDouble(arr[1]));
                    } else System.out.println("Not a valid value : " + arr[1]);
                }
                if (arr[0].equals("setbrightness")) {
                    if (isParsableDouble(arr[1])) {
                        BrightnessController.setBrightness(Double.parseDouble(arr[1]));
                    } else System.out.println("Not a valid value : " + arr[1]);
                }
            }
        }


    }

    public static boolean isParsableDouble(final String str) {
        try {
            Double.parseDouble(str);
        } catch (Exception exception) {
            return false;
        }
        return true;
    }
}
