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
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view)
    {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whippedcream_checkbox);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        boolean hasChocolate = chocolateCheckBox.isChecked();

        EditText name = (EditText) findViewById(R.id.name_editText);
        String nameOfPerson = name.getText().toString();

        int price=calculatePrice(hasWhippedCream, hasChocolate);
        String pm =createOrderSummary(price, hasWhippedCream, hasChocolate, nameOfPerson);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,"Order For " +nameOfPerson);
        intent.putExtra(Intent.EXTRA_TEXT, pm);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate)
    {
        int basePrice = 5;
        if(addWhippedCream)
            basePrice+=1;
        if(addChocolate)
            basePrice+=2;
        return quantity*basePrice;
    }


    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String nameOfPerson) {
        String priceMessage = getString(R.string.order_summary_name, nameOfPerson);
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    public void increment(View view)
    {
        if(quantity==100)
        {
            Toast.makeText(this, "You Can Not Have More Than 100 Coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity+1;
        display(quantity);
    }
    public void decrement(View view)
    {
        if(quantity==1)
        {
            Toast.makeText(this, "You Can Not Have Less Than 1 Coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity-1;
        display(quantity);

    }
}