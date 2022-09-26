package me.chayut.austcacal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textViewQuotePrice;
    private EditText editTextFx, editTextPrice, editTextWeight, editTextShippingFee;
    SharedPreferences prefs;

    public static final String PREFS = "PREFS";
    public static final String PREF_SHIPPING = "PREF_SHIPPING";
    public static final String PREF_FX = "PREF_FX";
    public static final String PREF_PRICE = "PREF_PRICE";
    public static final String PREF_WEIGHT = "PREF_WEIGHT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewQuotePrice = findViewById(R.id.tv_quote_price);
        editTextFx = findViewById(R.id.et_fx);
        editTextPrice = findViewById(R.id.et_price);
        editTextWeight = findViewById(R.id.et_weight);
        editTextShippingFee = findViewById(R.id.et_shipping);

        prefs = getSharedPreferences(PREFS, MODE_PRIVATE);

        editTextFx.setText(prefs.getString(PREF_FX, "22.5"));
        editTextPrice.setText(prefs.getString(PREF_PRICE, ""));
        editTextWeight.setText(prefs.getString(PREF_WEIGHT, ""));
        editTextShippingFee.setText(prefs.getString(PREF_SHIPPING, "26"));
    }

    private void saveTextDisplay() {
        SharedPreferences.Editor editor = prefs.edit();  //Create a SharedPreferences editor
        editor.putString(PREF_FX, editTextFx.getText().toString());
        editor.putString(PREF_PRICE, editTextPrice.getText().toString());
        editor.putString(PREF_WEIGHT, editTextWeight.getText().toString());
        editor.putString(PREF_SHIPPING, editTextShippingFee.getText().toString());
        editor.apply();
    }

    private void calculate() {
        float fx, price, weight, shipping;
        float quote;

        try {
            fx = Float.parseFloat(editTextFx.getText().toString());
            price = Float.parseFloat(editTextPrice.getText().toString());
            weight = Float.parseFloat(editTextWeight.getText().toString());
            shipping = Float.parseFloat(editTextShippingFee.getText().toString());

            quote = price * 1.2f * fx + weight * fx * shipping;

            textViewQuotePrice.setText(String.format("%.0f", quote));
        } catch (Exception ex) {
            return;
        }
    }


    public void onClickedCalculate(View v) {
        saveTextDisplay();
        calculate();
    }

    public void onClickedClear1(View v) {
        clearAndSetFocus(editTextFx);
    }


    public void onClickedClear2(View v) {
        clearAndSetFocus(editTextPrice);
    }


    public void onClickedClear3(View v) {
        clearAndSetFocus(editTextWeight);
    }


    public void onClickedClear4(View v) {
        clearAndSetFocus(editTextShippingFee);
    }

    public void clearAndSetFocus(EditText editText) {
        editText.setText("");
        editText.requestFocus();
        InputMethodManager imm =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }
}
