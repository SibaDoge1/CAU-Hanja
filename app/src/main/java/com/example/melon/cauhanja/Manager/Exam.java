package com.example.melon.cauhanja.Manager;

import java.util.ArrayList;

public class Exam {
    private int id;
    private int type;
    private String content;
    private String question;
    private ArrayList<String> example;
    private int level;
    private int answer;
    private float errorRate;

    public Exam(){
       example = new ArrayList<>();
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public void setContent(String buf) {

        buf = buf.replaceFirst("①","\t");
        buf = buf.replaceFirst("②","\t");
        buf = buf.replaceFirst("③","\t");
        buf = buf.replaceFirst("④","\t");
        buf = buf.replaceFirst("⑤","\t");

        String[] str = buf.split("\t");

        content = str[0];
        example.add(str[1]);
        example.add(str[2]);
        example.add(str[3]);
        example.add(str[4]);
        example.add(str[5]);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setErrorRate(float errorRate) {
        this.errorRate = errorRate;
    }

    public int getId() {
        return id;
    }

    public String getExample(int num) {
        return example.get(num);
    }
    public String getQuestion() {
        return question;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public int getAnswer() {
        return answer;
    }

    public float getErrorRate() {
        return errorRate;
    }
}
