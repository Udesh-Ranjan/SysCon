package dc;

import org.apache.commons.io.FileUtils;
import timer.Timer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileComparor {
    public static boolean areSameFiles(final File file1, final File file2) {
        Timer timer = new Timer();
        timer.start();
        final boolean foundBuildIn = buildIn(file1, file2);
//        System.out.println("Time taken in Millis : " + timer.getElapsedTimeInMillis());
//        timer.start();
//        boolean foundCustom = customComparer(file1, file2);
//        System.out.println("build in : " + foundCustom + " time taken in millis : " + timer.getElapsedTimeInMillis());
        return foundBuildIn /*&& foundCustom*/;
    }

    public static boolean buildIn(final File file1, final File file2) {
        try {
//            System.out.println("present working dir "+System.getProperty("user.dir"));
            return FileUtils.contentEquals(file1, file2);
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public static boolean customComparer(final File file1, final File file2) {
        try {
            if (!file1.exists())
                System.out.println(file1.getAbsolutePath() + " not found");

            if (!file2.exists())
                System.out.println(file2.getAbsolutePath() + " not found");

            if (!fileExists(file1, file2))
                return false;
            if (file1.length() != file2.length())
                return false;
            final BufferedReader br1 = new BufferedReader(new FileReader(file1));
            final BufferedReader br2 = new BufferedReader(new FileReader(file2));
            System.out.println("Comparision started...");
            int b1, b2;
            do {
                b1 = br1.read();
                b2 = br2.read();
                if (b1 != b2) {
//                    System.out.println("clearly didn't match");
                    return false;
                }
            } while (b1 >= 0 && b2 >= 0);
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public static boolean fileExists(final File... file) {
        boolean notFound = false;
        for (final File file1 : file)
            if (!file1.exists()) {
                notFound = true;
                break;
            }
        return !notFound;
    }
}
