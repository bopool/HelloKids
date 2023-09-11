package com.bpdev.hellokids.model;

public class ResultText {

    private String result;
    private String text;


    public ResultText(String result, String text) {
        this.result = result;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
