package com.example.quiz_assignment;//package com.example.quiz_assignment;
//
//import java.util.ArrayList;
//
//public class QurstionModel {
////   private ArrayList<QurstionModel> dataList = new ArrayList<>();
//
//    private String question;
//        private String answer;
//        private int color;
//
//        public QurstionModel(String text1, String text2, int color) {
//            this.question = text1;
//            this.answer = text2;
//            this.color = color;
//        }
//
//        public String getQuestion() {
//            return question;
//        }
//
//        public void setQuestion(String question) {
//            this.question = question;
//        }
//
//        public String getAnswer() {
//            return answer;
//        }
//
//        public void setAnswer(String answer) {
//            this.answer = answer;
//        }
//
//        public int getColor() {
//            return color;
//        }
//
//        public void setColor(int color) {
//            this.color = color;
//        }
//
//    }

import java.util.ArrayList;

public class QuestionModel {
    private ArrayList<Question_Item> questionList;
    private ArrayList<Integer> colorList;

    public QuestionModel(ArrayList<Question_Item> questionList, ArrayList<Integer> colorList) {
        this.questionList = questionList;
        this.colorList = colorList;
    }

    public ArrayList<Question_Item> getQuestionList() {
        return questionList;
    }

    public ArrayList<Integer> getColorList() {
        return colorList;
    }
}
