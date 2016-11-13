package com.chanhbc.filemanager.model;

import java.io.File;

public class MyFile extends File {
    private File file;
    public MyFile(String pathname) {
        super(pathname);
        file = new File(pathname);
    }

    public MyFile[] getFile(){
        return (MyFile[]) file.listFiles();
    }

    @Override
    public File[] listFiles() {
        return super.listFiles();
    }
}
