package com.jje.las.domain;

import java.io.File;

public class MonitFile {

    private String id;
    private String fileName;
    private String path;
    private String type;
    private String encoding="UTF-8";

    public MonitFile() {
    }

    public MonitFile(String path, String type) {
        this.path = path;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public File getRealFile(){
        File file = new File(getPath());
        if (!file.exists()) {
            throw new RuntimeException("File doesn't exists."+file);
        }
        return file;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}
