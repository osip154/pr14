package com.example.pr14_mirzakamilov_pr23103;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
    TextView tvResult;
    String currentNumber = "";
    float firstNum = 0;
    String operation = "";
    boolean isNewOp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);

        int[] numberIds = {
                R.id.btn0, R.id.btn00, R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot
        };

        for (int id : numberIds) {
            findViewById(id).setOnClickListener(this);
        }

        // Привязываем кнопки (Операции)
        int[] opIds = {
                R.id.btnAC, R.id.btnBack, R.id.btnPercent,
                R.id.btnDiv, R.id.btnMult, R.id.btnSub, R.id.btnAdd, R.id.btnEq
        };

        for (int id : opIds) {
            findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        String buttonText = ((Button) v).getText().toString();
        int id = v.getId();

        // Логика нажатия на цифры и запятую
        if (id == R.id.btn0 || id == R.id.btn00 || id == R.id.btn1 || id == R.id.btn2 ||
                id == R.id.btn3 || id == R.id.btn4 || id == R.id.btn5 || id == R.id.btn6 ||
                id == R.id.btn7 || id == R.id.btn8 || id == R.id.btn9 || id == R.id.btnDot) {

            if (isNewOp) {
                currentNumber = "";
                isNewOp = false;
            }

            // Защита от нескольких запятых
            if (id == R.id.btnDot && currentNumber.contains(",")) return;

            currentNumber += buttonText;
            tvResult.setText(currentNumber);
            return;
        }

        // Логика очистки (AC)
        if (id == R.id.btnAC) {
            currentNumber = "0";
            firstNum = 0;
            operation = "";
            isNewOp = true;
            tvResult.setText("0");
            return;
        }

        // Логика удаления последнего символа (Backspace)
        if (id == R.id.btnBack) {
            if (currentNumber.length() > 0) {
                currentNumber = currentNumber.substring(0, currentNumber.length() - 1);
                if (currentNumber.isEmpty()) currentNumber = "0";
                tvResult.setText(currentNumber);
            }
            return;
        }

        // Логика равно (=)
        if (id == R.id.btnEq) {
            float secondNum = 0;
            if (!currentNumber.isEmpty()) {
                // Заменяем запятую на точку для правильного парсинга Java
                secondNum = Float.parseFloat(currentNumber.replace(",", "."));
            }

            float res = 0;

            switch (operation) {
                case "+": res = firstNum + secondNum; break;
                case "-": res = firstNum - secondNum; break;
                case "x": res = firstNum * secondNum; break;
                case "/":
                    if (secondNum != 0) res = firstNum / secondNum;
                    else res = 0; // Защита от деления на 0
                    break;
                case "%": res = firstNum % secondNum; break;
            }

            // Вывод результата
            tvResult.setText(String.valueOf(res));
            currentNumber = String.valueOf(res);
            isNewOp = true; // Следующий ввод начнет новое число
            return;
        }

        // Если это не цифра, не AC, и не =, значит это операция (+, -, *, /)
        if (!currentNumber.isEmpty()) {
            firstNum = Float.parseFloat(currentNumber.replace(",", "."));
            operation = buttonText;
            isNewOp = true;
        }
    }
}