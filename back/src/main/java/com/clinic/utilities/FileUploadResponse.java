package com.clinic.utilities;

public class FileUploadResponse {
    private String fileName;

    private long size;

    public FileUploadResponse() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    // getters and setters are not shown for brevity

}
