package me.chayut.austcacal

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.EditText
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private var textViewQuotePrice: TextView? = null
    private var editTextFx: EditText? = null
    private var editTextPrice: EditText? = null
    private var editTextWeight: EditText? = null
    private var editTextShippingFee: EditText? = null
    private var prefs: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewQuotePrice = findViewById(R.id.tv_quote_price)
        editTextFx = findViewById(R.id.et_fx)
        editTextPrice = findViewById(R.id.et_price)
        editTextWeight = findViewById(R.id.et_weight)
        editTextShippingFee = findViewById(R.id.et_shipping)
        prefs = getSharedPreferences(PREFS, MODE_PRIVATE)
        editTextFx!!.setText(prefs!!.getString(PREF_FX, "22.5"))
        editTextPrice!!.setText(prefs!!.getString(PREF_PRICE, ""))
        editTextWeight!!.setText(prefs!!.getString(PREF_WEIGHT, ""))
        editTextShippingFee!!.setText(prefs!!.getString(PREF_SHIPPING, "26"))
    }

    private fun saveTextDisplay() {
        val editor = prefs!!.edit() //Create a SharedPreferences editor
        editor.putString(PREF_FX, editTextFx!!.text.toString())
        editor.putString(PREF_PRICE, editTextPrice!!.text.toString())
        editor.putString(PREF_WEIGHT, editTextWeight!!.text.toString())
        editor.putString(PREF_SHIPPING, editTextShippingFee!!.text.toString())
        editor.apply()
    }

    private fun calculate() {
        val fx: Float
        val price: Float
        val weight: Float
        val shipping: Float
        val quote: Float
        try {
            fx = editTextFx!!.text.toString().toFloat()
            price = editTextPrice!!.text.toString().toFloat()
            weight = editTextWeight!!.text.toString().toFloat()
            shipping = editTextShippingFee!!.text.toString().toFloat()
            quote = price * 1.2f * fx + weight * fx * shipping
            textViewQuotePrice!!.text = String.format("%.0f", quote)
        } catch (ex: Exception) {
            return
        }
    }

    fun onClickedCalculate(v: View?) {
        saveTextDisplay()
        calculate()
    }

    fun onClickedClear1(v: View?) {
        clearAndSetFocus(editTextFx)
    }

    fun onClickedClear2(v: View?) {
        clearAndSetFocus(editTextPrice)
    }

    fun onClickedClear3(v: View?) {
        clearAndSetFocus(editTextWeight)
    }

    fun onClickedClear4(v: View?) {
        clearAndSetFocus(editTextShippingFee)
    }

    fun clearAndSetFocus(editText: EditText?) {
        editText!!.setText("")
        editText.requestFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    companion object {
        const val PREFS = "PREFS"
        const val PREF_SHIPPING = "PREF_SHIPPING"
        const val PREF_FX = "PREF_FX"
        const val PREF_PRICE = "PREF_PRICE"
        const val PREF_WEIGHT = "PREF_WEIGHT"
    }
}