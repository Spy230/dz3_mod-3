package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // поля
    private TextView equationOne, equationTwo, equationThree, equationFour; // поля вывода примеров
    private EditText solvingOne, solvingTwo, solvingThree, solvingFour; // поля ввода ответов
    private int[] equationValue; // массив шести чисел (для трёх примеров)
    private boolean right = false; // флаг правильности решения примеров

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // привязка к полям
        equationOne = findViewById(R.id.equationOne);
        equationTwo = findViewById(R.id.equationTwo);
        equationThree = findViewById(R.id.equationThree);
        equationFour = findViewById(R.id.equationfour);
        solvingOne = findViewById(R.id.solvingOne);
        solvingTwo = findViewById(R.id.solvingTwo);
        solvingThree = findViewById(R.id.solvingThree);
        solvingFour = findViewById(R.id.solvingfour);

        // формирование массива случайных чисел
        equationValue = valueArrayRandom();

        // заполнение строк примерами для решения
        equationOne.setText(equationValue[0] + " + " + equationValue[1] + " = ");
        equationTwo.setText(equationValue[2] + " + " + equationValue[3] + " = ");
        equationThree.setText(equationValue[4] + " + " + equationValue[5] + " = ");
        equationFour.setText(equationValue[6] + " * " + equationValue[7] + " = ");

        // обработка фокусировки/снятия фокусировки с EditText
        solvingOne.setOnFocusChangeListener(focusListener);
        solvingTwo.setOnFocusChangeListener(focusListener);
        solvingThree.setOnFocusChangeListener(focusListener);
        solvingFour.setOnFocusChangeListener(focusListener);

        // добавим слушателя нажатия кнопки
        Button buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // проверим, ответил ли пользователь на все вопросы
                if (!right) {
                    Toast.makeText(MainActivity.this, "Сначала ответьте на все вопросы!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // генерирует новые случайные числа
                equationValue = valueArrayRandom();

                // обновляет текст примеров
                equationOne.setText(equationValue[0] + " + " + equationValue[1] + " = ");
                equationTwo.setText(equationValue[2] + " + " + equationValue[3] + " = ");
                equationThree.setText(equationValue[4] + " + " + equationValue[5] + " = ");
                equationFour.setText(equationValue[6] + " * " + equationValue[7] + " = ");

                // сбрасыват цвет EditText
                solvingOne.setBackgroundColor(Color.WHITE);
                solvingTwo.setBackgroundColor(Color.WHITE);
                solvingThree.setBackgroundColor(Color.WHITE);
                solvingFour.setBackgroundColor(Color.WHITE);

                // сбрасывает флаг
                right = false;
            }
        });
    }

    // создание слушателя фокусировки/снятие фокусировки с EditText
    private View.OnFocusChangeListener focusListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            int viewId = view.getId();
            if (viewId == R.id.solvingOne && !b) {
                checkAnswer(solvingOne, equationValue[0], equationValue[1]);
            } else if (viewId == R.id.solvingTwo && !b) {
                checkAnswer(solvingTwo, equationValue[2], equationValue[3]);
            } else if (viewId == R.id.solvingThree && !b) {
                checkAnswer(solvingThree, equationValue[4], equationValue[5]);
            } else if (viewId == R.id.solvingfour && !b) {
                checkAnswer(solvingFour, equationValue[6], equationValue[7]);
            }
        }
    };

    // метод для проверки ответа
    private void checkAnswer(EditText editText, int firstNumber, int secondNumber) {
        String inputText = editText.getText().toString().trim();
        if (!inputText.isEmpty()) {
            try {
                int value = Integer.parseInt(inputText);
                if (value == (firstNumber + secondNumber) || value == (firstNumber * secondNumber)) {
                    editText.setBackgroundColor(Color.GREEN);
                    right = true;
                } else {
                    editText.setBackgroundColor(Color.RED);
                    Toast.makeText(MainActivity.this, "Неправильный ответ!", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                editText.setBackgroundColor(Color.RED);
                Toast.makeText(MainActivity.this, "Введите число!", Toast.LENGTH_SHORT).show();
            }
        } else {
            editText.setBackgroundColor(Color.RED);
        }
    }
    // метод генерации массива 8 случайных чисел
    private int[] valueArrayRandom() {
        Random random = new Random(); // создание объекта класса Random
        int[] arrayValue = new int[8]; // создание массива для заполнения
        for (int i = 0; i < 8; i++) { // цикл заполнения массива случайными числами
            arrayValue[i] = random.nextInt(99) + 1;
        }
        return arrayValue;
    }
}
