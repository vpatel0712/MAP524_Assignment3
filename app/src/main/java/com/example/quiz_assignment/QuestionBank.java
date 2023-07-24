package com.example.quiz_assignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class QuestionBank {
    private ArrayList<Question_Item> questionList;
    private ArrayList<Integer> colorList;

    public void QuestionBank() {
        questionList = new ArrayList<>();
        colorList = new ArrayList<>();
    }

    public void addQuestion(ArrayList<Question_Item> question, ArrayList<Integer> color) {
        questionList=question;
        colorList=color;
    }
    public void addQuestionList(ArrayList<Question_Item> questions, ArrayList<Integer> colors) {
        questionList.addAll(questions);
        colorList.addAll(colors);
    }

    public Question_Item getRandomQuestion() {
        if (questionList.isEmpty()) {
            return null;
        }

        int randomIndex = new Random().nextInt(questionList.size());
        return questionList.get(randomIndex);
    }

    public void shuffleQuestions() {
        long seed = System.nanoTime();
        Collections.shuffle(questionList);
    }
    public void shuffleColors() {
       Collections.shuffle(colorList);
    }

    public ArrayList<Question_Item> getQuestionList() {
        return questionList;
    }

    public ArrayList<Integer> getColorList() {
        return colorList;
    }

    public void setListSizes(int questionSize, int colorSize) {
        if (questionSize >= 0 && questionSize <= questionList.size()) {
            questionList = new ArrayList<>(questionList.subList(0, questionSize));
        }

        if (colorSize >= 0 && colorSize <= colorList.size()) {
            colorList = new ArrayList<>(colorList.subList(0, colorSize));
        }
    }
}
