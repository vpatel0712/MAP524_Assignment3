package com.example.quiz_assignment;

public class Question_Item {
    private String question;
    private String answer;

    public Question_Item(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}