package com.example.arkadiusz.polynomialderivatives;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import model.DegreeToOrderRelationException;
import model.Model;
import model.CalculationException;

/**
 * Main activity of the application.
 *
 * @author Arkadiusz Pilarski
 * @version 6.0
 */

public class MainActivity extends AppCompatActivity {


    /**
     * A method that sets the polynomial in readable format.
     *
     * @param coefficients stores coefficients values.
     * @param degree stores a polynomial degree value.
     * @return returns a string containing polynomial that has been set.
     */
    public String setPolynomial(double[] coefficients, int degree) {
        boolean firstViewed;
        int ifJustZeroes;
        int i;
        String result = "";

        if (coefficients != null) {
            firstViewed = false;
            ifJustZeroes = 0;
            result = "";

            //----------------------------------------------
            // Calculates how many coefficients of 0 value there
            // are and prevents such coefficients from displaying.
            for (i = degree; i >= 0; i--) {
                if (coefficients[i] == 0) {
                    ifJustZeroes += 1;
                    continue;
                }

                //----------------------------------------------
                // Creating a suitable display format
                if (coefficients[i] > 0 && firstViewed) {
                    result += ("+");
                }
                if (i == 0) {
                    result += (coefficients[i] + "");
                } else if (i != 1) {
                    result += (coefficients[i] + "x^" + i + " ");
                } else {
                    result += (coefficients[i] + "x ");
                }
                firstViewed = true;
            }

            //----------------------------------------------
            // Handling the situation with only-zero coefficients values
            if (ifJustZeroes == degree + 1) {
                result = ("0");
            }
        }
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Model model = new Model();
        final EditText degreeText = findViewById(R.id.degreeText);
        final EditText orderText = findViewById(R.id.orderText);
        final EditText coefficientsText = findViewById(R.id.coefficientsText);

        final TextView enteredText = findViewById(R.id.enteredText);
        final TextView showEnteredText = findViewById(R.id.showEnteredText);
        final TextView calculatedText = findViewById(R.id.calculatedText);
        final TextView showCalculatedText = findViewById(R.id.showCalculatedText);

        final TextView help1 = findViewById(R.id.help1Text);
        final TextView help2 = findViewById(R.id.help2Text);
        final TextView help3 = findViewById(R.id.help3Text);

        final Button calculateButton = findViewById(R.id.calculateButton);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(degreeText.getText().toString().isEmpty() | orderText.getText().toString().isEmpty() | coefficientsText.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter all necessary values!", Toast.LENGTH_SHORT).show();
                    enteredText.setVisibility(View.INVISIBLE);
                    calculatedText.setVisibility(View.INVISIBLE);
                    showEnteredText.setVisibility(View.INVISIBLE);
                    showCalculatedText.setVisibility(View.INVISIBLE);
                    help1.setVisibility(View.VISIBLE);
                    help2.setVisibility(View.VISIBLE);
                    help3.setVisibility(View.VISIBLE);
                    return;
                } else {
                    try {

                        int degree, order;
                        degree = Integer.parseInt(degreeText.getText().toString());
                        order = Integer.parseInt(orderText.getText().toString());
                        String result="";

                        String[] tab;
                        tab = coefficientsText.getText().toString().split(" ");
                        double[] coefficients = new double[tab.length];
                        int i = 0;
                        for (String coefficient : tab) {
                            coefficients[i++] = Double.parseDouble(coefficient);
                        }
                        // Checking parameters.
                        model.checkParameters(coefficients, degree, order);

                        // Displaying entered polynomial
                        enteredText.setTextColor(Color.rgb(200,0,0));
                        enteredText.setVisibility(View.VISIBLE);

                        result = setPolynomial(coefficients, degree);
                        showEnteredText.setText(result);
                        showEnteredText.setVisibility(View.VISIBLE);

                        // Calculating given derivative
                        model.checkDegreeOrderRelation(degree, order);
                        model.calculateDerivative(coefficients, coefficients, degree, order);

                        result = setPolynomial(coefficients, degree);
                        showCalculatedText.setText(result);
                        showCalculatedText.setVisibility(View.VISIBLE);


                        calculatedText.setTextColor(Color.rgb(200,0,0));
                        calculatedText.setVisibility(View.VISIBLE);

                        help1.setVisibility(View.INVISIBLE);
                        help2.setVisibility(View.INVISIBLE);
                        help3.setVisibility(View.INVISIBLE);
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "You have entered a wrong parameter!", Toast.LENGTH_SHORT).show();
                        enteredText.setVisibility(View.INVISIBLE);
                        calculatedText.setVisibility(View.INVISIBLE);
                        showEnteredText.setVisibility(View.INVISIBLE);
                        showCalculatedText.setVisibility(View.INVISIBLE);
                        help1.setVisibility(View.VISIBLE);
                        help2.setVisibility(View.VISIBLE);
                        help3.setVisibility(View.VISIBLE);
                    } catch (DegreeToOrderRelationException | CalculationException e) {
                        Toast.makeText(getApplicationContext(), "Calculations went wrong!", Toast.LENGTH_SHORT).show();
                        enteredText.setVisibility(View.INVISIBLE);
                        calculatedText.setVisibility(View.INVISIBLE);
                        showEnteredText.setVisibility(View.INVISIBLE);
                        showCalculatedText.setVisibility(View.INVISIBLE);
                        help1.setVisibility(View.VISIBLE);
                        help2.setVisibility(View.VISIBLE);
                        help3.setVisibility(View.VISIBLE);
                    }
                }
            }
        });


    }
}
