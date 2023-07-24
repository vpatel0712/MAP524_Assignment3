package com.example.quiz_assignment;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences_Utils {
    private static final String PREF_FILE_NAME = "MyPreferences"; // Replace with your desired file name
    private static final String KEY_TOTAL_QUESTIONS = "totalQuestions";
    private static final String KEY_TOTAL_CORRECT_ANSWERS = "totalCorrectAnswers";

    private static final String KEY_LANGUAGE = "language";

    // Method to write data to SharedPreferences for total questions
    public static void saveTotalQuestions(Context context, int totalQuestions) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_TOTAL_QUESTIONS, totalQuestions);
        editor.apply();
    }

    // Method to read total questions from SharedPreferences
    public static int getTotalQuestions(Context context, int defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_TOTAL_QUESTIONS, defaultValue);
    }

    // Method to write data to SharedPreferences for total correct answers
    public static void saveTotalCorrectAnswers(Context context, int totalCorrectAnswers) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_TOTAL_CORRECT_ANSWERS, totalCorrectAnswers);
        editor.apply();
    }

    // Method to read total correct answers from SharedPreferences
    public static int getTotalCorrectAnswers(Context context, int defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_TOTAL_CORRECT_ANSWERS, defaultValue);
    }

    public static void deleteTotalQuestions(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_TOTAL_QUESTIONS);
        editor.apply();
    }

    public static void deleteTotalCorrectAnswers(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_TOTAL_CORRECT_ANSWERS);
        editor.apply();
    }

    public static void saveLanguage(MainActivity mainActivity, String selectedLanguage) {
        SharedPreferences sharedPreferences = mainActivity.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LANGUAGE, selectedLanguage);
        editor.apply();
    }

}
