package com.example.calculatorapp;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Scriptable;

/**
 * The main Activity class shows the main activity for the calculator application.
 * In this app, it will have the user interaction, button clicks and expression evaluation which using Javascript
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Declare the student's name and student ID
    private String studentName = " Luong";
    private String studentID = "49928223";


    TextView resultTV, solutionTV; // Displaying the result and the current expression
    //Calculator Buttons
    MaterialButton buttonC, buttonBrackOpen, buttonBrackClose;
    MaterialButton button0,button1,button2,button3,button4,button5,button6,button7,button8,button9;
    MaterialButton buttonMul, buttonPlus, buttonSub, buttonDivide, buttonEquals;

    MaterialButton buttonAC, buttonDot;

    /**
     * This method called when the activity is created and set up the UI and also all the button for that
     * @param savedInstanceState an object which contain the activity's
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);




    // Initialize the result and the solution
        resultTV = findViewById(R.id.result_tv);

        solutionTV = findViewById(R.id.solution_tv);
// Display the student Info for the name and the ID
        displayStudentInfo();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    // Assign the click listeners to the calculator buttons
        assignID(buttonC, R.id.button_c);
        assignID(buttonBrackOpen, R.id.button_open_bracket);
        assignID(buttonBrackClose, R.id.button_close_bracket);
        assignID(button0, R.id.button_0);
        assignID(button1, R.id.button_1);
        assignID(button2, R.id.button_2);
        assignID(button3, R.id.button_3);
        assignID(button4, R.id.button_4);
        assignID(button5, R.id.button_5);
        assignID(button6, R.id.button_6);
        assignID(button7, R.id.button_7);
        assignID(button8, R.id.button_8);
        assignID(button9, R.id.button_9);
        assignID(buttonAC, R.id.button_ac);
        assignID(buttonMul, R.id.button_times);
        assignID(buttonDivide, R.id.button_divide);
        assignID(buttonPlus, R.id.button_plus);
        assignID(buttonSub, R.id.button_minus);
        assignID(buttonEquals, R.id.button_equals);
        assignID(buttonDot, R.id.button_dot);


    }

    /**
     *Assigns the ID to a MaterialButton and sets its click listener
     * @param btn The MaterialButton to be assigned
     * @param id The ID of the button
     */
    void assignID(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    /**
     * This will handles the button click events for the calculators
     * Also, upadte the expression and performs the calculations based on the input
     *
     * @param view The view that was clicked
     */
    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataTocalculate = solutionTV.getText().toString();
    // Clear all the view if we click at the AC
        if(buttonText.equals("AC")) {
            solutionTV.setText("");
            resultTV.setText("0");
            return;
        }
        // This one will show the result if "=" is pressed for this one
        if(buttonText.equals("=")) {
            solutionTV.setText(resultTV.getText());
            return;
        }
    // Remove the last character if we press "C"
        if(buttonText.equals("C")) {
            dataTocalculate = dataTocalculate.substring(0,dataTocalculate.length()-1);
        }else {
            dataTocalculate = dataTocalculate +buttonText;
        }

    //Update the expression
        solutionTV.setText(dataTocalculate);
// get the result of the calculation
        String finalResult =  getResults(dataTocalculate);
        if(!finalResult.equals("Err")) {
            resultTV.setText(finalResult);
        }
    }

    /**
     * This one will display the student's name and the ID in the result and the solution Textviews
     */
   private void displayStudentInfo() {

        resultTV.setText(studentName);
        solutionTV.setText(studentID);
    }

    /**
     * This one will show the mathematical expression using JavaScript and returns the result
     * @param data  The mathematical expression to evaluate
     * @return The result of the evaluation or "Err" if there's an error for that
     */

    String getResults(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            return context.evaluateString(scriptable, data,"Javascript",1, null).toString();
            
        }catch (Exception e) {
            return "Err";
        }
    }
}