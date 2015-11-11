package com.example.stategridexampractice;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private TextView stemText;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private TextView answerText;
    private TextView stemNumber;

    Button startSolution;
    Button lastQuestion;

    RadioGroup radioGroup;
    //题目的指针。
    Integer t = 0;
    //总共的题数。
    final int N = 278;
    String answer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stemText = (TextView) findViewById(R.id.stem);
        stemNumber = (TextView) findViewById(R.id.stem_number);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);

        radioButton1 = (RadioButton) findViewById(R.id.radio_button1);
        radioButton2 = (RadioButton) findViewById(R.id.radio_button2);
        radioButton3 = (RadioButton) findViewById(R.id.radio_button3);
        radioButton4 = (RadioButton) findViewById(R.id.radio_button4);

        answerText = (TextView) findViewById(R.id.answer_text);

        //答题按钮的功能实现。
        startSolution = (Button) findViewById(R.id.start_solution);
        startSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startSolution.getText() != "下一题")
                    startSolution.setText("下一题");


                radioGroup.clearCheck();
                answerText.setText("请选择你认为正确的选项。");

                if (t < N) t++;
                stemNumber.setText("单项选择第" + t + "题，总共" + N +"道题。");
                stemText.setText(getFromRaw(t.toString() + "stem"));
                radioButton1.setText(getFromRaw(t.toString() + "choice1"));
                radioButton2.setText(getFromRaw(t.toString() + "choice2"));
                radioButton3.setText(getFromRaw(t.toString() + "choice3"));
                radioButton4.setText(getFromRaw(t.toString() + "choice4"));
                answer = getFromRaw(t.toString() + "answer");
            }
        });

        lastQuestion = (Button) findViewById(R.id.last_question);
        lastQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (startSolution.getText() != "下一题")
                    return;
                radioGroup.clearCheck();
                answerText.setText("请选择你认为正确的选项。");
                if (t > 1) t--;

                stemNumber.setText("单项选择第" + t + "题，总共" + N +"道题。");
                stemText.setText(getFromRaw(t.toString() + "stem"));
                radioButton1.setText(getFromRaw(t.toString() + "choice1"));
                radioButton2.setText(getFromRaw(t.toString() + "choice2"));
                radioButton3.setText(getFromRaw(t.toString() + "choice3"));
                radioButton4.setText(getFromRaw(t.toString() + "choice4"));
                answer = getFromRaw(t.toString() + "answer");
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int answerId = 0;
                switch (answer) {
                    case "A":answerId = radioButton1.getId();
                        break;
                    case "B":answerId = radioButton2.getId();
                        break;
                    case "C":answerId = radioButton3.getId();
                        break;
                    case "D":answerId = radioButton4.getId();
                        break;
                }
                if (answer != "")
                    if (answerId == checkedId) {
                        answerText.setText("回答正确。");
                    } else {
                        answerText.setText("回答错误，正确答案是" + answer + "。");
                    }
            }
        });
    }

    public String getFromRaw(String key) {
        StringBuilder result = new StringBuilder();
        BufferedReader reader = null;
        try {
            InputStream in = getResources().openRawResource(R.raw.tiku);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
