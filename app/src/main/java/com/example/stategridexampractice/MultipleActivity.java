package com.example.stategridexampractice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

/**
 * Created by Duan on 2015/11/14.
 */


public class MultipleActivity extends AppCompatActivity {

    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;

    Button solutionButton;
    Button lastQuestion;
    Button transStem;

    private TextView answerText;
    private TextView stemText;
    private TextView stemNumber;
    private EditText transInput;

    private int N = 167;
    private Integer t = 0;
    private String answer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiple_choice_layout);
        answerText = (TextView) findViewById(R.id.answer_text);
        stemText = (TextView) findViewById(R.id.stem);
        stemNumber = (TextView) findViewById(R.id.stem_number);
        solutionButton = (Button) findViewById(R.id.start_solution);
        lastQuestion = (Button) findViewById(R.id.last_question);

        checkBox1 = (CheckBox) findViewById(R.id.m_choice1);
        checkBox2 = (CheckBox) findViewById(R.id.m_choice2);
        checkBox3 = (CheckBox) findViewById(R.id.m_choice3);
        checkBox4 = (CheckBox) findViewById(R.id.m_choice4);

        transInput = (EditText) findViewById(R.id.trans_input);

        solutionButton.setText("开始");
        answerText.setText("请选择你认为正确的选项。");

        solutionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (solutionButton.getText() != "答题") {
                    if (t < N) t++;
                    solutionButton.setText("答题");
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);
                    answerText.setText("请选择你认为正确的选项。");
                    stemNumber.setText("多项选择第" + t + "题，总共" + N + "道题。");
                    stemText.setText(getFromRaw(t.toString() + "stem"));
                    checkBox1.setText(getFromRaw(t.toString() + "choice1"));
                    checkBox2.setText(getFromRaw(t.toString() + "choice2"));
                    checkBox3.setText(getFromRaw(t.toString() + "choice3"));
                    checkBox4.setText(getFromRaw(t.toString() + "choice4"));
                    answer = getFromRaw(t.toString() + "answer");
                    return;
                }

                if (solutionButton.getText() == "开始") {
                    t = 1;
                    solutionButton.setText("答题");
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);
                    answerText.setText("请选择你认为正确的选项。");
                    stemNumber.setText("多项选择第" + t + "题，总共" + N + "道题。");
                    stemText.setText(getFromRaw(t.toString() + "stem"));
                    checkBox1.setText(getFromRaw(t.toString() + "choice1"));
                    checkBox2.setText(getFromRaw(t.toString() + "choice2"));
                    checkBox3.setText(getFromRaw(t.toString() + "choice3"));
                    checkBox4.setText(getFromRaw(t.toString() + "choice4"));
                    answer = getFromRaw(t.toString() + "answer");
                    return;
                }

                if (checkAnswer()) {
                    answerText.setText("回答正确。");
                } else {
                    answerText.setText("回答错误，正确答案是" + answer + "。");
                }
                solutionButton.setText("下一题");
            }
        });

        lastQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (t == 0)
                    return;
                if (t > 1) t--;
                checkBox1.setChecked(false);
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                checkBox4.setChecked(false);
                solutionButton.setText("答题");
                answerText.setText("请选择你认为正确的选项。");
                stemNumber.setText("多项选择第" + t + "题，总共" + N + "道题。");
                stemText.setText(getFromRaw(t.toString() + "stem"));
                checkBox1.setText(getFromRaw(t.toString() + "choice1"));
                checkBox2.setText(getFromRaw(t.toString() + "choice2"));
                checkBox3.setText(getFromRaw(t.toString() + "choice3"));
                checkBox4.setText(getFromRaw(t.toString() + "choice4"));
                answer = getFromRaw(t.toString() + "answer");
            }
        });

        transStem = (Button) findViewById(R.id.trans_stem);
        transStem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String transInputString = transInput.getText().toString();
                int transInputInt = 0;
                //判断文本框中的字符串是否为数字且不为空，如果是则转换为int。
                if (isInteger(transInputString) && !transInputString.equals(""))
                    transInputInt = Integer.parseInt(transInputString);
                if (transInputInt > 0 && transInputInt <= N) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);
                    t = transInputInt;
                    if (solutionButton.getText().equals("下一题"))
                        solutionButton.setText("答题");
                    stemNumber.setText("多项选择第" + t + "题，总共" + N + "道题。");
                    stemText.setText(getFromRaw(t.toString() + "stem"));
                    checkBox1.setText(getFromRaw(t.toString() + "choice1"));
                    checkBox2.setText(getFromRaw(t.toString() + "choice2"));
                    checkBox3.setText(getFromRaw(t.toString() + "choice3"));
                    checkBox4.setText(getFromRaw(t.toString() + "choice4"));
                    answer = getFromRaw(t.toString() + "answer");
                    transInput.setText("");
                    transInput.setHint("请输入要跳转到的题号。");
                } else {
                    transInput.setText("");
                    transInput.setHint("请输入正确的题号。");
                    transInput.clearFocus();
                }
            }
        });
    }



    private boolean checkAnswer() {
        int tnum = 0;
        int anum = 0;
        for (int i = 0; i < answer.length(); i++) {
            if (answer.charAt(i) == 'A') tnum += 1;
            if (answer.charAt(i) == 'B') tnum += 2;
            if (answer.charAt(i) == 'C') tnum += 4;
            if (answer.charAt(i) == 'D') tnum += 8;
        }
        if (checkBox1.isChecked()) anum += 1;
        if (checkBox2.isChecked()) anum += 2;
        if (checkBox3.isChecked()) anum += 4;
        if (checkBox4.isChecked()) anum += 8;
        return tnum == anum;
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public String getFromRaw(String key) {
        StringBuilder result = new StringBuilder();
        BufferedReader reader = null;
        try {
            InputStream in = getResources().openRawResource(R.raw.mtiku);
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            String temp;
            line = reader.readLine();
            temp = line.substring(0, key.length());
            while (!temp.equals(key)) {
                line = reader.readLine();
                if (line.length() >= key.length())
                    temp = line.substring(0, key.length());
            }

            while (line != null) {
                String tempKey = line.substring(0, key.length());
                //去掉文本中的KEY。
                if (tempKey.equals(key))
                    result.append(line.substring(key.length(), line.length()));
                else
                    result.append(line);
                //遇到;时去掉分号并把字符串return。
                if (line.endsWith(";")) {
                    result.delete(result.length()-1, result.length());
                    return result.toString();
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "没有找到" + key + "的内容。";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.single_choice) {
            Intent intent = new Intent(MultipleActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.multiple_answer) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}