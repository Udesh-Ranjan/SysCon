import dc.DirectoryComparor;
import dc.FileComparor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
        System.out.println("Present working dir " + System.getProperty("user.dir"));
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

    public static String[] split(final String str) {
        final List<String> list = new ArrayList<>();
        boolean flag = false;
        int prevIndex = -1;
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < str.length(); i++) {
            final char ch = str.charAt(i);
            if (ch == ' ') {
                if (flag) {
                    continue;
                }
                if (stringBuilder.toString().length() > 0)
                    list.add(stringBuilder.toString().trim());
                stringBuilder = new StringBuilder();
            }
            if (ch == '\"') {
                if (flag) {
                    list.add(str.substring(prevIndex, i + 1).trim());
                    flag = false;
                    stringBuilder = new StringBuilder("");
                    continue;
                }
                flag = true;
                prevIndex = i;
                continue;
            }
            stringBuilder.append(ch);
        }
        if (!stringBuilder.isEmpty())
            list.add(stringBuilder.toString().trim());
        System.out.println("after parsing : " + list);
        return list.toArray(String[]::new);
    }

    public static void execute(final String cmd) {
        System.out.println("cmd : " + cmd + ".");
//        String arr[] = cmd.split(" ");
        String arr[] = split(cmd);
        for (String str : arr) {
            System.out.print(str + "-");
        }
        System.out.println();
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
                if (arr.length >= 3) {
                    if (arr[0].equals("fc")) {
                        if (FileComparor.areSameFiles(new File(arr[1]), new File(arr[2])))
                            System.out.println("files matched");
                        else System.out.println("files didn't match");
                    }
                    if (arr[0].equals("dc")) {
                        String file1 = filter(arr[1]);
                        String file2 = filter(arr[2]);
                        if (!DirectoryComparor.areSameDirectories(new File(file1), new File(file2)))
                            System.out.println(arr[1] + " and " + arr[2] + " didn't match");
                        else System.out.println(arr[1] + " and " + arr[2] + " matched");
                    }
                }
            }
        }
    }

    public static String filter(final String str) {
        final StringBuilder sb = new StringBuilder(str.length());
        for (final char ch : str.toCharArray()) {
            if (ch == '\"' || ch == '\'')
                continue;
            sb.append(ch);
        }
        return sb.toString();
    }

    public static boolean isParsableDouble(final String str) {
        try {
            Double.parseDouble(str);
        } catch (final Exception exception) {
            return false;
        }
        return true;
    }
}
