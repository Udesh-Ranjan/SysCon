package dc;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DirectoryComparor {
    public static boolean areSameDirectories(final File file1, final File file2) {
        System.out.println("file 1 " + file1.getAbsolutePath());
        System.out.println("file 2 " + file2.getAbsolutePath());
        if (!file1.exists()) {
            System.out.println("..." + file1.getAbsolutePath() + " doesn't exists");
            return false;
        }
        if (!file2.exists()) {
            System.out.println("..." + file2.getAbsolutePath() + " doesn't exists");
            return false;
        }
        File[] array1 = file1.listFiles();
        File[] array2 = file2.listFiles();

        if (array1 == null) {
            System.out.println("folder is cannot be accessed " + file1.getAbsolutePath());
            System.out.println(file1.getAbsolutePath() + " is readable " + file1.canRead());
//            System.out.println("please run as admin");
        }
        if (array2 == null) {
            System.out.println("folder cannot be accessed " + file2.getAbsolutePath());
            System.out.println(file1.getAbsolutePath() + " is readable " + file2.canRead());
//            System.out.println("please run as admin");
        }
        if (array1 == null || array2 == null)
            return false;
        final List<File> content1 = Arrays.asList(array1);
        final List<File> content2 = Arrays.asList(array2);

        if (array1.length != array2.length) {
            System.out.println("Numbers of files are different in " + file1.getAbsolutePath() + "," + file2.getAbsolutePath());
            System.out.println("file1 length " + array1.length);
            System.out.println("file2 length " + array2.length);
            return false;
        }
        final List<File> files1 = new ArrayList<>();
        final List<File> files2 = new ArrayList<>();
        for (final File file : content1) {
            if (file.isFile() && file.exists())
                files1.add(file);
        }
        for (final File file : content2) {
            if (file.isFile() && file.exists())
                files2.add(file);
        }
        if (files1.size() != files2.size()) {
            System.out.println("Numbers of files are different in " + file1.getAbsolutePath() + "," + file2.getAbsolutePath());
            System.out.println("files1 files " + files1.size());
            System.out.println("files2 files " + files2.size());
            return false;
        }
        files1.sort(Comparator.comparing(File::getName));
        files2.sort(Comparator.comparing(File::getName));
        for (int i = 0; i < files1.size(); i++) {
            if (!files1.get(i).getName().equals(files2.get(i).getName())) {
                System.out.println("file name not same : " + files1.get(i).getName() + "," + files2.get(i).getName());
                return false;
            }
            if (!FileComparor.areSameFiles(files1.get(i), files2.get(i))) {
                System.out.println("fileComparor files are different : " + files1.get(i).getAbsolutePath() + "," + files2.get(i).getAbsolutePath());
                return false;
            }
        }
        files1.clear();
        files2.clear();
        for (final File file : content1) {
            if (file.isDirectory() && file.exists())
                files1.add(file);
        }
        for (final File file : content2) {
            if (file.isDirectory() && file.exists())
                files2.add(file);
        }
        if (files1.size() != files2.size()) {
            System.out.println("Numbers of files are different in " + file1.getAbsolutePath() + " " + file2.getAbsolutePath());
            return false;
        }
        files1.sort(Comparator.comparing(File::getName));
        files2.sort(Comparator.comparing(File::getName));
        for (int i = 0; i < files1.size(); i++) {
            if (!files1.get(i).getName().equals(files2.get(i).getName())) {
                System.out.println("dir name not same : " + files1.get(i).getName() + "," + files2.get(i).getName());
                return false;
            }
            final boolean flag = areSameDirectories(files1.get(i), files2.get(i));
            if (!flag) {
                return false;
            }
        }
        return true;
    }
}
