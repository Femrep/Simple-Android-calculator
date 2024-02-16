package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    private TextView answer_text, solution_text;
    private boolean counter = false;
    private boolean solution = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        answer_text = findViewById(R.id.answerText);
        solution_text = findViewById(R.id.solutionText);
    }

    public void numberButton(View view){
        Button btn = (Button) view;
        String buttonText = btn.getText().toString();

        if (!counter){
            answer_text.setText("");
        }

        String active_text = answer_text.getText().toString();

        if (!(buttonText.equals(".") && active_text.contains("."))) {
            answer_text.setText(active_text + buttonText);
        }

        counter = true;
    }
    public void numberButton0(View view) {
        Button btn = (Button) view; // Cast the view to a Button
        String buttonText = btn.getText().toString();
        String tempText = answer_text.getText().toString();
        if ("0".equals(tempText)) {
            answer_text.setText("0");
        } else {
            if (!counter) {
                answer_text.setText("");
            }

            String active_text = answer_text.getText().toString();
            answer_text.setText(active_text + buttonText);
            counter = true;
        }
    }


    public void dotButton(View view){
        Button btn = (Button) view;
        String buttonText = btn.getText().toString();

        if (!counter){
            answer_text.setText("0");
        }

        String active_text = answer_text.getText().toString();

        if (!active_text.contains(".")) {
            answer_text.setText(active_text + buttonText);
        }

        counter = true;
    }

    public void operator(View view){
        Button btn =(Button) view;
        if(!"".equals(answer_text.getText().toString())){
            if (!solution){
                solution_text.setText("");
            }
            solution = true;
            String newtext = solution_text.getText().toString();

            String tempText = answer_text.getText().toString() + " " + btn.getText().toString() + " ";
            answer_text.setText("");
            solution_text.setText(newtext + tempText);

            counter = false;
        }
    }

    public void equal(View view){
        solution_text.setText(solution_text.getText().toString()+answer_text.getText().toString());
        String expression = solution_text.getText().toString();

        try {
            double result = evaluateExpression(expression);
            answer_text.setText(Double.toString(result));
            solution_text.setText("");
            solution = false;

            // Set counter to false after successful calculation
            counter = false;
        } catch (Exception e) {
            answer_text.setText("Error");
        }
    }


    private double evaluateExpression(String expression) throws Exception {
        String[] tokens = expression.split(" ");
        Stack<Double> operands = new Stack<>();
        Stack<String> operators = new Stack<>();

        for (String token : tokens) {
            if (isNumeric(token)) {
                operands.push(Double.parseDouble(token));
            } else if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    applyOperator(operands, operators.pop());
                }
                operators.pop(); // Pop the opening bracket
            } else if (isOperator(token)) {
                while (!operators.isEmpty() && hasPrecedence(token, operators.peek())) {
                    applyOperator(operands, operators.pop());
                }
                operators.push(token);
            }
        }

        while (!operators.isEmpty()) {
            applyOperator(operands, operators.pop());
        }

        return operands.pop();
    }



    private void applyOperator(Stack<Double> operands, String operator) throws Exception {
        if (operands.size() < 2) {
            throw new Exception("Invalid expression");
        }

        double operand2 = operands.pop();
        double operand1 = operands.pop();

        switch (operator) {
            case "+":
                operands.push(operand1 + operand2);
                break;
            case "-":
                operands.push(operand1 - operand2);
                break;
            case "*":
                operands.push(operand1 * operand2);
                break;
            case "/":
                if (operand2 != 0) {
                    operands.push(operand1 / operand2);
                } else {
                    throw new Exception("Division by zero");
                }
                break;
            default:
                throw new Exception("Invalid operator");
        }
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    private boolean isOperator(String str) {
        return str.matches("[+\\-*/]");
    }

    private boolean hasPrecedence(String op1, String op2) {
        return (op2.equals("*") || op2.equals("/")) && (op1.equals("+") || op1.equals("-"));
    }


    public void Clear(View view){
        answer_text.setText("");
        counter=false;

    }

    public void allClear(View view){
        solution_text.setText("");
        answer_text.setText("");
        solution=false;
        counter=false;

    }

    public void log(View view){
        try {
            String numStr = answer_text.getText().toString();
            double num = Double.parseDouble(numStr);
            double result = Math.log10(num);
            answer_text.setText(String.valueOf(result));

        } catch (NumberFormatException e) {
            answer_text.setText("Error");
        }

        // Set counter to false after logarithm operation
        counter = false;
    }

    public void sqrt(View view){
        try {
            String numStr = answer_text.getText().toString();
            double num = Double.parseDouble(numStr);
            double result = Math.sqrt(num);
            answer_text.setText(String.valueOf(result));

        } catch (NumberFormatException e) {
            answer_text.setText("Error");
        }

        // Set counter to false after square root operation
        counter = false;
    }
    public void sin(View view){
        try {
            String numStr = answer_text.getText().toString();
            double num = Double.parseDouble(numStr);
            double result = Math.sin(Math.toRadians(num)); // Convert degrees to radians for sin
            answer_text.setText(String.valueOf(result));
            solution_text.setText("");
        } catch (NumberFormatException e) {
            answer_text.setText("Error");
        }

        // Set counter to false after sine operation
        counter = false;
    }

    public void Cos(View view){
        try {
            String numStr = answer_text.getText().toString();
            double num = Double.parseDouble(numStr);
            double result = Math.cos(Math.toRadians(num)); // Convert degrees to radians for cos
            answer_text.setText(String.valueOf(result));
            solution_text.setText("");
        } catch (NumberFormatException e) {
            answer_text.setText("Error");
        }

        // Set counter to false after cosine operation
        counter = false;
    }



    public void OpenBr(View view){
        Button btn = (Button) view;
        String buttonText = btn.getText().toString();

        if (!counter){
            answer_text.setText("");
        }

        String active_text = answer_text.getText().toString();
        answer_text.setText(active_text + buttonText);

        counter = true;
    }

    public void CloseBr(View view){
        Button btn = (Button) view;
        String buttonText = btn.getText().toString();

        if (!counter){
            answer_text.setText("");
        }

        String active_text = answer_text.getText().toString();
        answer_text.setText(active_text + buttonText);

        counter = true;
    }



}