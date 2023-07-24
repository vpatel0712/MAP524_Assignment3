package com.example.quiz_assignment;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.quiz_assignment.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Question_Item> questionList;
    private ArrayList<Integer> colorList;
    int questions, correctAnswers = 0;
    int totalquestions, totalCorrectAnswer = 0;
    private int currentQuestionIndex = 0;
    int q = 0;
    Context context;
    Resources resources;


    int listSize = 8;
    private int clickCount = 0;
    int progress = 0;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    QuestionBank questionBank = new QuestionBank();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        String selectedLanguage = Helper.getLanguage(this);
        if (selectedLanguage.isEmpty()) {
            context = Helper.setLocale(this, "");
        } else {
            context = Helper.setLocale(this, selectedLanguage);
        }
        resources = context.getResources();
        totalquestions = Preferences_Utils.getTotalQuestions(this, 0);
        totalCorrectAnswer = Preferences_Utils.getTotalCorrectAnswers(this, 0);
        if (savedInstanceState != null) {
            correctAnswers = savedInstanceState.getInt("correct", 0);
            questions = savedInstanceState.getInt("total", 0);
            currentQuestionIndex = savedInstanceState.getInt("current", 0);
            clickCount = savedInstanceState.getInt("clickCount", 0);

        }


        addQuestions();
        updateQuestionInFragment(null);
        Ui();


    }

    private void Ui() {

        binding.layout.btnTrue.setText(resources.getString(R.string.txt_true));
        binding.layout.btnFalse.setText(resources.getString(R.string.txt_false));

        binding.layout.btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCount++;

                if (currentQuestionIndex >= questionList.size()) {
//                    Toast.makeText(MainActivity.this, "enddddddddd", Toast.LENGTH_SHORT).show();

                }
                updateQuestionInFragment("True");
                progress = (clickCount * 100) / questionList.size();
                binding.layout.progressBar.setProgress(progress);

            }
        });


        binding.layout.btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCount++;

                if (currentQuestionIndex >= questionList.size()) {
                    Toast.makeText(MainActivity.this, "end", Toast.LENGTH_SHORT).show();

                }
                updateQuestionInFragment("False");
                int progress = (clickCount * 100) / questionList.size();
                binding.layout.progressBar.setProgress(progress);
            }
        });
    }

    private void addQuestions() {

        questionList = new ArrayList<>();
        colorList = new ArrayList<>();
        colorList.add(Color.YELLOW);
        colorList.add(Color.YELLOW);
        colorList.add(Color.YELLOW);
        colorList.add(Color.YELLOW);
        colorList.add(Color.YELLOW);
        colorList.add(Color.YELLOW);
        colorList.add(Color.YELLOW);
        colorList.add(Color.YELLOW);
        colorList.add(R.color.black);
        colorList.add(R.color.teal_200);
        // Assuming you have created QuestionItem instances and a list of colors
        ArrayList<Question_Item> questionItems = new ArrayList<>();

        questionItems.add(new Question_Item(resources.getString(R.string.question1), "False"));
        questionItems.add(new Question_Item(resources.getString(R.string.question2), "False"));
        questionItems.add(new Question_Item(resources.getString(R.string.question3), "True"));
        questionItems.add(new Question_Item(resources.getString(R.string.question4), "True"));
        questionItems.add(new Question_Item(resources.getString(R.string.question5), "False"));
        questionItems.add(new Question_Item(resources.getString(R.string.question6), "True"));
        questionItems.add(new Question_Item(resources.getString(R.string.question7), "True"));
        questionItems.add(new Question_Item(resources.getString(R.string.question8), "True"));

        questionBank.addQuestion(questionItems, colorList);
        questionBank.setListSizes(listSize, listSize);
        questionList = questionBank.getQuestionList();
        colorList = questionBank.getColorList();


    }

    private void updateQuestionInFragment(String userAnswer) {

        if (currentQuestionIndex < questionList.size()) {
            String question = questionList.get(currentQuestionIndex).getQuestion();
            String answer = questionList.get(currentQuestionIndex).getAnswer();
            int color = colorList.get(currentQuestionIndex);

            if (userAnswer != null) {
                if (userAnswer.equals(answer)) {
                    correctAnswers++;
                    Toast.makeText(this, resources.getString(R.string.txt_true), Toast.LENGTH_SHORT).show();
                    if (currentQuestionIndex >= questionList.size() - 1) {
                        showResultDialog(question);
//

                    } else {
                        currentQuestionIndex++;
                    }
                    question = questionList.get(currentQuestionIndex).getQuestion();
                    answer = questionList.get(currentQuestionIndex).getAnswer();
                    color = colorList.get(currentQuestionIndex);

                } else {
                    Toast.makeText(this, resources.getString(R.string.txt_false), Toast.LENGTH_SHORT).show();
                    q = questions;

                    if (currentQuestionIndex >= questionList.size() - 1) {
                        showResultDialog(question);
//
                    } else {
                        currentQuestionIndex++;
                    }
                    question = questionList.get(currentQuestionIndex).getQuestion();
                    answer = questionList.get(currentQuestionIndex).getAnswer();
                    color = colorList.get(currentQuestionIndex);

                }
            }
            questions++;
            loadFragment(new FirstFragment(), question, color);
        }

    }

    private void showResultDialog(String question) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        q = questions;
        builder.setTitle(resources.getString(R.string.result)).setMessage(resources.getString(R.string.your_result) + correctAnswers + resources.getString(R.string.out_of) + questions).setCancelable(false).setPositiveButton(resources.getString(R.string.save), (dialogInterface, i) -> {
            totalquestions = totalquestions + q;
            totalCorrectAnswer = totalCorrectAnswer + correctAnswers;
            Preferences_Utils.saveTotalQuestions(this, totalquestions);
            Preferences_Utils.saveTotalCorrectAnswers(this, totalCorrectAnswer);
            questionBank.shuffleQuestions();
            questionBank.shuffleColors();
            currentQuestionIndex = 0;
            clickCount = 0;
            correctAnswers = 0;
            questions = 0;
            binding.layout.progressBar.setProgress(0);
            updateQuestionInFragment(null);
        }).setNegativeButton(resources.getString(R.string.ignore), (dialogInterface, i) -> {
            questionBank.shuffleQuestions();
            questionBank.shuffleColors();
            currentQuestionIndex = 0;
            clickCount = 0;
            correctAnswers = 0;
            questions = 0; // Reset 'questions' here to avoid double increment
            binding.layout.progressBar.setProgress(0);
            updateQuestionInFragment(null);
        }).show();
    }

    private void loadFragment(FirstFragment firstFragment, String question, int color) {
        FragmentManager fm = getSupportFragmentManager();
        Bundle args = new Bundle();
        args.putString("question", question);
        args.putInt("color", color);
        firstFragment.setArguments(args);
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, firstFragment);

        // Commit the transaction
        fragmentTransaction.commit();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menu.findItem(R.id.action_getAverage).setTitle(resources.getString(R.string.action_getAverage));
        menu.findItem(R.id.action_selectQue).setTitle(resources.getString(R.string.action_select_question));
        menu.findItem(R.id.action_resetResult).setTitle(resources.getString(R.string.action_reset_result));
        menu.findItem(R.id.action_selectLanguage).setTitle(resources.getString(R.string.action_select_language));
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_getAverage) {
            showAverageDialog();
            return true;
        }
        if (id == R.id.action_selectQue) {
            showCustomAlertDialog(this);
            return true;
        }
        if (id == R.id.action_resetResult) {
            showResetConfirmationDialog();
            return true;
        }
        if (id == R.id.action_selectLanguage) {
            showLanguageDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showLanguageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_select_language, null);
        builder.setView(dialogView);

        TextView tvEnglish = dialogView.findViewById(R.id.tvEnglish);
        TextView tvUrdu = dialogView.findViewById(R.id.tvMarathi);

        String selectedLanguage = Helper.getLanguage(this);

        if (selectedLanguage.equals("mr")) {
            tvEnglish.setBackgroundColor(Color.TRANSPARENT);
            tvEnglish.setTextColor(Color.BLACK);
            tvUrdu.setBackgroundColor(getResources().getColor(R.color.purple_500));
            tvUrdu.setTextColor(Color.WHITE);
        } else {
            // Default case (English is selected)
            tvEnglish.setBackgroundColor(getResources().getColor(R.color.purple_500));
            tvEnglish.setTextColor(Color.WHITE); // Change text color for English
            tvUrdu.setBackgroundColor(Color.TRANSPARENT); // Set no background for Urdu
            tvUrdu.setTextColor(Color.BLACK); // Change text color for Urdu
        }

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onLanguageSelected(View view) {
        String selectedLanguage = (String) view.getTag();
        context = Helper.setLocale(this, selectedLanguage);
        resources = context.getResources();
        Preferences_Utils.saveLanguage(this, selectedLanguage);
        recreate();
    }

    private void showAverageDialog() {
        int a = Preferences_Utils.getTotalCorrectAnswers(this, 0);
        int b = Preferences_Utils.getTotalQuestions(this, 0);
//        int c=a/b;
        Utilities_Dialog.showAlertDialog(this, null, resources.getString(R.string.message_average) + a + "/" + b, resources.getString(R.string.ok), resources.getString(R.string.cancel), new Runnable() {
            @Override
            public void run() {
                // Positive button callback logic
                // Add your functionality here
            }
        }, new Runnable() {
            @Override
            public void run() {
                // Negative button callback logic
                // Add your functionality here
            }
        });
    }


    private void showCustomAlertDialog(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        final EditText editText = dialog.findViewById(R.id.edit_text);
        final TextView text = dialog.findViewById(R.id.text_title);

        text.setText(resources.getString(R.string.enter_question));

        // Show the AlertDialog with OK button to dismiss
        Button buttonOk = dialog.findViewById(R.id.button_ok);
        Button buttonCancle = dialog.findViewById(R.id.button_cancel);

        buttonCancle.setText(resources.getString(R.string.cancel));
        buttonOk.setText(resources.getString(R.string.ok));
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text entered in the EditText

                String enteredText = editText.getText().toString();
                if (enteredText.isEmpty()) {
                    // Show an error message to the user that the EditText is empty
                    Toast.makeText(context, "Please enter a valid number", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    listSize = Integer.parseInt(enteredText);
//                    if (questionList.size() <= listSize) {
                    if (listSize <= 0 || listSize > 8) {

//                if (questionBank.getQuestionList().size() <= listSize) {

                        Toast.makeText(context, resources.getText(R.string.invalidNumber), Toast.LENGTH_SHORT).show();

                    } else {
                        questionBank.setListSizes(listSize, listSize);
                        addQuestions();
//                        updateQuestionInFragment(null);
                    }
                }



                dialog.dismiss(); // Dismiss the dialog when the OK button is clicked
            }
        });

        dialog.show();
    }


    private void showResetConfirmationDialog() {
        Preferences_Utils.deleteTotalCorrectAnswers(this);
        Preferences_Utils.deleteTotalQuestions(this);
        totalquestions = Preferences_Utils.getTotalQuestions(this, 0);
        totalCorrectAnswer = Preferences_Utils.getTotalCorrectAnswers(this, 0);

        Utilities_Dialog.showAlertDialog(this, null, resources.getString(R.string.message_average) + totalCorrectAnswer + "/" + totalquestions, resources.getString(R.string.ok), resources.getString(R.string.cancel), new Runnable() {
            @Override
            public void run() {
                // Positive button callback logic
                // Add your functionality here
                Toast.makeText(MainActivity.this, "OK clicked", Toast.LENGTH_SHORT).show();
            }
        }, new Runnable() {
            @Override
            public void run() {
                // Negative button callback logic
                // Add your functionality here
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the relevant variables to the outState bundle
        outState.putInt("correct", correctAnswers);
        outState.putInt("total", questions);
        outState.putInt("current", currentQuestionIndex);
        outState.putInt("clickCount", clickCount);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore the saved instance state
        correctAnswers = savedInstanceState.getInt("correct", 0);
        questions = savedInstanceState.getInt("total", 0);
        currentQuestionIndex = savedInstanceState.getInt("current", 0);
        clickCount = savedInstanceState.getInt("clickCount", 0);

    }

}