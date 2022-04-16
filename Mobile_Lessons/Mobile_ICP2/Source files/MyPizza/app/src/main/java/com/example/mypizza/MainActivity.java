package com.example.mypizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//Actions for Pizza Order page for creating order with summary, email summary.
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Variables/Objects
    private static final String MAIN_ACTIVITY_TAG = "MainActivity";
    final int pizzaPrice = 10;
    final int toppingsPrice = 1;
    int quantity = 1;
    Spinner pizzaType;
    ArrayAdapter<CharSequence> adapter;
    CheckBox olives;
    CheckBox greenPepper;
    EditText userInputNameView;
    EditText emailInput;
    Button sendBtn;
    Button orderButton;
    Button summaryButton;
    Button finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assigning Objects used
        olives = (CheckBox)  findViewById(R.id.olives);
        greenPepper = (CheckBox) findViewById(R.id.greenPepper);
        userInputNameView = (EditText) findViewById(R.id.userInput);
        pizzaType = findViewById(R.id.pizzaTypeSpinner);

        //Assigning array to spinner to select pizza types
        adapter = ArrayAdapter.createFromResource(this, R.array.pizzaOptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pizzaType.setAdapter(adapter);
        //pizzaType.setOnItemSelectedListener(this);
    }

    //Save Instance state bundle for order
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("olivesKey", olives.isChecked());
        savedInstanceState.putBoolean("greenPepperKey", greenPepper.isChecked());
        savedInstanceState.putString("nameInputKey", userInputNameView.getText().toString());
        savedInstanceState.putInt("pizzaTypeKey", pizzaType.getSelectedItemPosition());
        savedInstanceState.putInt("pizzaQuantity", quantity);

        super.onSaveInstanceState(savedInstanceState);
    }

    //Restore instance state with saved bundle of order
     @Override
     protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Boolean savedOlivesChecked = savedInstanceState.getBoolean("olivesKey");
        olives.setChecked(savedOlivesChecked);

        Boolean savedGreenPeppersChecked = savedInstanceState.getBoolean("greenPepperKey");
        greenPepper.setChecked(savedGreenPeppersChecked);

        String savedNameInput = savedInstanceState.getString("nameInputKey");
        userInputNameView.setText(savedNameInput);

        int savedPizzaType = savedInstanceState.getInt("pizzaTypeKey");
        pizzaType.setSelection(savedPizzaType);

        int savedPizzaQuantity = savedInstanceState.getInt("pizzaQuantity");
        quantity = savedPizzaQuantity;
    }

    //Displays message of selected Pizza type spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    //On nothing selected from Pizza type spinner
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    /**
     * This method is called when the order button is clicked.
     */

    //Submit Order function get selected data for the pizza order. Used with 'submit' button when clicked
    public void submitOrder(View view) {

        // get user input
        String userInputName = userInputNameView.getText().toString();

        //Make sure name EditText box filled out. If not, triggers message.
        if (TextUtils.isEmpty(userInputName)) {
            Toast.makeText(this, "Must fill out Name", Toast.LENGTH_SHORT).show();
        }

        else {
            //Get selected pizza type
            String spinnerText = pizzaType.getSelectedItem().toString();
            Toast.makeText(pizzaType.getContext(), spinnerText, Toast.LENGTH_SHORT).show();

            // check if olives is selected
            boolean hasOlives = olives.isChecked();

            // check if greenPepper is selected
            boolean hasGreenPeppers = greenPepper.isChecked();

            // calculate and store the total price
            float totalPrice = calculatePrice(hasOlives, hasGreenPeppers);

            // create and store the order summary
            String orderSummaryMessage = createOrderSummary(userInputName, spinnerText, hasOlives, hasGreenPeppers, totalPrice);

            // Code for making the buttons work(i.e implement the implicit and explicit intents)
            orderButton = findViewById(R.id.order);
            summaryButton = findViewById(R.id.summary);

            showButton(orderButton);
            showButton(summaryButton);

            //Button click listener for Order Button to trigger sending an email function
            orderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendEmail(userInputName, orderSummaryMessage);
                }
            });

            //Button click listener for Summary Button to trigger going to summary page/activity,
            //takes the summary input in form of a string
            summaryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent summaryIntent = new Intent(MainActivity.this, MainActivity2.class);
                    summaryIntent.putExtra("Summary", orderSummaryMessage);
                    startActivity(summaryIntent);
                }
            });
        }
}

    //Function to make a button visible
    public void showButton(Button btn) {
        btn.setVisibility(View.VISIBLE);
    }

    //Function to make a EditText visible
    public void showEditText(EditText txtBox) {txtBox.setVisibility(View.VISIBLE);}

    //Function send email with summary to the user
    public void sendEmail(String name, String output) {
        // Write the relevant code for triggering email
        emailInput = (EditText) findViewById(R.id.emailInput);
        sendBtn = (Button) findViewById(R.id.sendBtn);
        finish = (Button) findViewById(R.id.finish);

        showEditText(emailInput);
        showButton(sendBtn);
        showButton(finish);

        //Button click listener to send email with user email, summary and name
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(emailInput.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Must fill out Email", Toast.LENGTH_SHORT).show();
                }
                else {
                    String email = emailInput.getText().toString();

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.putExtra(Intent.EXTRA_EMAIL, email);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Summary for: " + name);
                    intent.putExtra(Intent.EXTRA_TEXT, output);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Email", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    //Return string for boolean
    private String boolToString(boolean bool) {
        return bool ? (getString(R.string.yes)) : (getString(R.string.no));
    }

    //Returns String of Pizza Order Summary
    private String createOrderSummary(String userInputName, String spinnerText, boolean hasOlives, boolean hasGreenPeppers, float price) {
        String orderSummaryMessage = getString(R.string.orderSummaryName, userInputName) + "\n" +
                "Pizza Type: " + spinnerText + "\n" +
                getString(R.string.orderSummaryOlives, boolToString(hasOlives)) + "\n" +
                getString(R.string.orderSummaryGreenPeppers, boolToString(hasGreenPeppers)) + "\n" +
                getString(R.string.orderSummaryQuantity, quantity) + "\n" +
                getString(R.string.orderSummaryTotal, price);
        return orderSummaryMessage;
    }

    /**
     * Method to calculate the total price
     *
     * @return total Price
     */

    //Calculates and return the price of the pizza order with the quantity
    private float calculatePrice(boolean hasOlives, boolean hasGreenPeppers) {
        int basePrice = pizzaPrice;

        if (hasOlives) {
            basePrice += toppingsPrice;
        }
        if (hasGreenPeppers) {
            basePrice += toppingsPrice;
        }
        return quantity * basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */

    //Display quantity of Pizzas with TextView when incremented or decremented
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantityAmount);
        quantityTextView.setText("" + number);
    }

    /**
     * This method increments the quantity of Pizzas by one
     *
     * @param view on passes the view that we are working with to the method
     */

    //Increment the quantity of pizzas and update number shown
    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select less than 50 Pizzas");
            Context context = getApplicationContext();
            String lowerLimitToast = getString(R.string.tooMuchPizza);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, lowerLimitToast, duration);
            toast.show();
            return;
        }
    }

    /**
     * This method decrements the quantity of Pizzas by one
     *
     * @param view passes on the view that we are working with to the method
     */

    //Decrement the quantity of pizzas and update number shown
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select at least 1 Pizza");
            Context context = getApplicationContext();
            String upperLimitToast = getString(R.string.tooLittlePizza);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return;
        }
    }
}