package com.chanhbc.filemanager.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.chanhbc.filemanager.App;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class FileManager {
    private String path;
    private File file;
    private File[] files;
    private ArrayList<File> listFile;
    private ArrayList<File> allFile = new ArrayList<>();
    private ArrayList<File> listFileSearch;
    private String pathParent;
    private int count1 = 0;
    private int count2 = 0;
    private int count3 = 0;
    private SharedPreferences sp;

    public FileManager(String path) {
        this.path = path;
    }

    public FileManager(File file) {
        this.file = file;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String getPathParent() {
        return pathParent;
    }

    public File getFile() {
        return file;
    }

    public ArrayList<File> getListFile() {
        listFile = new ArrayList<>();
        file = new File(path);
        pathParent = file.getParent();
        if (file.isDirectory()) {
            files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().charAt(0) != '.')
                    listFile.add(files[i]);
            }
        }
        sortTypeFile(listFile);
        return listFile;
    }

    public ArrayList<File> sortTypeFile(ArrayList<File> files) {
        ArrayList<File> files1 = new ArrayList<>();
        ArrayList<File> files21 = new ArrayList<>();
        ArrayList<File> files22 = new ArrayList<>();
        ArrayList<File> files23 = new ArrayList<>();
        ArrayList<File> files24 = new ArrayList<>();
        ArrayList<File> files25 = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            if (file.isDirectory()) {
                files1.add(files.get(i));
            } else if (file.isFile()) {
                if (isFileImage(file)) {
                    files21.add(file);
                } else if (isFileVideo(file)) {
                    files21.add(file);
                } else if (isFileMusic(file)) {
                    files21.add(file);
                } else if (isFileDocument(file)) {
                    files21.add(file);
                } else {
                    files25.add(file);
                }
            }
        }
        files1 = sortNameFile(files1);
        files21 = sortNameFile(files21);
        files22 = sortNameFile(files22);
        files23 = sortNameFile(files23);
        files24 = sortNameFile(files24);
        files25 = sortNameFile(files25);
        ArrayList<File> files2 = new ArrayList<>();
        for (int i = 0; i < files1.size(); i++) {
            files2.add(files1.get(i));
        }
        for (int i = 0; i < files21.size(); i++) {
            files2.add(files21.get(i));
        }
        for (int i = 0; i < files22.size(); i++) {
            files2.add(files22.get(i));
        }
        for (int i = 0; i < files23.size(); i++) {
            files2.add(files23.get(i));
        }
        for (int i = 0; i < files24.size(); i++) {
            files2.add(files24.get(i));
        }
        for (int i = 0; i < files25.size(); i++) {
            files2.add(files25.get(i));
        }
        return files2;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public ArrayList<File> sortNameFile(ArrayList<File> files) {
        count1++;
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                if (f1 == null || f2 == null) {
                    return 0;
                }
                String d1 = f1.getName();
                String d2 = f2.getName();
                if (count1 % 2 == 1) {
                    return d2.compareTo(d1);
                } else {
                    return d1.compareTo(d2);
                }
            }
        });
        return files;
    }

    public ArrayList<File> searchFile(String key) {
        listFileSearch = new ArrayList<>();
        if (key.length() == 0) {
            return listFileSearch;
        }
        for (int i = 0; i < allFile.size(); i++) {
            File f = allFile.get(i);
            if (f.getName().contains(key)) {
                listFileSearch.add(f);
            }
        }
        return listFileSearch;
    }

    public void allFile(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            allFile.add(f);
            if (f.isDirectory()) {
                allFile(f.getAbsolutePath());
            }
        }
    }

    public ArrayList<File> getAllFile() {
        return allFile;
    }

    public void setAllFile(ArrayList<File> allFile) {
        this.allFile = allFile;
    }


    public ArrayList<File> sortDateFile(ArrayList<File> files) {
        count2++;
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                if (f1 == null || f2 == null) {
                    return 0;
                }
                String d1 = getDateTimeFile2(f1);
                String d2 = getDateTimeFile2(f2);
                if (count2 % 2 == 0) {
                    return d2.compareTo(d1);
                } else {
                    return d1.compareTo(d2);
                }
            }
        });
        return files;
    }

    public ArrayList<File> sortSizeFile(ArrayList<File> files) {
        count3++;
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                if (f1 == null || f2 == null) {
                    return 0;
                }
                double d1 = f1.length();
                double d2 = f2.length();
                if (d1 == d2) {
                    return 0;
                }
                if (count3 % 2 == 0) {
                    if (d1 > d2) {
                        return 1;
                    } else {
                        return -1;
                    }
                } else {
                    if (d1 > d2) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            }
        });
        return files;
    }

    public String getFileName() {
        return file.toString().substring(file.toString().lastIndexOf("/") + 1, file.toString().length());
    }

    public String cutFileName(String fileName) {
        if (fileName.length() > 30)
            return fileName.substring(0, 30) + "...";
        else
            return fileName;
    }

    public int getNumberFileChild() {
        int number = 0;
        File[] files = file.listFiles();
        for (int j = 0; j < files.length; j++) {
            if (files[j].getName().charAt(0) != '.')
                number++;
        }
        return number;
    }

    public String getDateTimeFile(File file) {
        Date date = new Date(file.lastModified());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return simpleDateFormat.format(date);
    }

    //dùng sắp xếp yyyy/MM/dd HH:mm
    public String getDateTimeFile2(File file) {
        Date date = new Date(file.lastModified());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return simpleDateFormat.format(date);
    }

    public String getSize(File file) {
        if (file.isFile()) {
            long size = file.length();
            if (size < 1024)
                return size + " B";
            if (size < 1024 * 1024)
                return ((double) (size * 100 / 1024) / 100) + " KB";
            if (size < 1024 * 1024 * 1024)
                return ((double) (size * 100 / (1024 * 1024)) / 100) + " MB";
            return ((double) (size * 100 / (1024 * 1024 * 1024)) / 100) + " GB";
        } else {
            return "";
        }
    }

    private boolean fileNameEndWith(File file, String endWith) {
        return file.getName().endsWith(endWith);
    }

    public boolean isFileMusic(File file) {
        if (!file.isFile())
            return false;
        return fileNameEndWith(file, ".mp3") || fileNameEndWith(file, ".flac");
    }

    public boolean isFileImage(File file) {
        if (!file.isFile())
            return false;
        return fileNameEndWith(file, ".png") || fileNameEndWith(file, ".jpg") || fileNameEndWith(file, ".jpeg")
                || fileNameEndWith(file, ".bmp") || fileNameEndWith(file, ".gif");
    }

    public boolean isFileVideo(File file) {
        if (!file.isFile())
            return false;
        return fileNameEndWith(file, ".mp4");
    }

    public boolean isDirectory(File file) {
        return file.isDirectory();
    }

    public boolean isFileDocument(File file) {
        if (!file.isFile())
            return false;
        return fileNameEndWith(file, ".doc") || fileNameEndWith(file, ".txt") || fileNameEndWith(file, ".docx") ||
                fileNameEndWith(file, ".xlsx") || fileNameEndWith(file, ".pdf") || fileNameEndWith(file, ".pptx") ||
                fileNameEndWith(file, ".xml") || fileNameEndWith(file, ".xls");
    }

    public boolean isFileApp(File file) {
        if (!file.isFile())
            return false;
        return fileNameEndWith(file, ".apk");
    }

    public void addFileListFavorites(File file) {
        sp = (App.getContext()).getSharedPreferences("ListFavorites", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(file.getAbsolutePath(), file.getAbsolutePath());
        editor.apply();
    }

    public ArrayList<File> getFileListFavorites(String key){
        ArrayList<File> favorites = new ArrayList<>();
        sp = (App.getContext()).getSharedPreferences(key, Context.MODE_PRIVATE);
        for(String key1: sp.getAll().keySet()){
            String p = sp.getString(key1,"Error!");
            favorites.add(new File(p));
        }
        return favorites;
    }

    public void removeFileListFavorites(File file){
        sp = (App.getContext()).getSharedPreferences("ListFavorites", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(file.getAbsolutePath());
        editor.commit();
    }

}
