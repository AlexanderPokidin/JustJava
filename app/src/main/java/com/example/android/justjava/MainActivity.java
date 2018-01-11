/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int quantity = 2;
    private boolean hasWhippedCream;
    private boolean hasChocolate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100){
            Toast maxToast = Toast.makeText(getApplicationContext(), "There is nothing more", Toast.LENGTH_SHORT);
            maxToast.show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1){
            Toast minToast = Toast.makeText(getApplicationContext(), "There is nothing less", Toast.LENGTH_SHORT);
            minToast.show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        hasWhippedCream = ((CheckBox) findViewById(R.id.whipped_cream_checkbox)).isChecked();
        hasChocolate = ((CheckBox) findViewById(R.id.chocolate_checkbox)).isChecked();
        int price = calculatePrice();
        EditText editName = (EditText) findViewById(R.id.user_name);
        String userName = editName.getText().toString();
//        Log.i("MainActivity", "Message: " + hasWhippedCream);
//        Log.i("MainActivity", "Message: " + hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, createOrderSubject(userName));
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(userName, hasWhippedCream, hasChocolate, price));
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

    private String createOrderSubject(String userName){
        String subjectMessage = "JustJava order for " + userName;
        return subjectMessage;
    }

    private String createOrderSummary(String userName, boolean hasWhippedCream, boolean hasChocolate, int price){
        String priceMessage = "Name: " + userName + "\n";
        priceMessage += "Add whipped cream? " + hasWhippedCream + "\n";
        priceMessage += "Add chocolate? " + hasChocolate + "\n";
        priceMessage += "Quantity: " + quantity + "\n";
        priceMessage += "Total: $" + price + "\n";
        priceMessage += "Thank You!";
        return priceMessage;
    }

    /**
     * Calculates the price of the order.
     *
     */
    private int calculatePrice() {
        int basePrice = 5;
        if (hasWhippedCream){
            basePrice++;
        }
        if (hasChocolate){
            basePrice += 2;
        }
        return quantity * basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}