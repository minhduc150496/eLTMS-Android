package com.project.xetnghiem.models;

public class ResultView extends LabTestingIndex {
    private boolean isHeader;
    private String headerName;

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }
}
