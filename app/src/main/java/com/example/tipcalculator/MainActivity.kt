package com.example.tipcalculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {

            try {
                calculateTip()
            } catch (e: NumberFormatException) {
                val toast = Toast.makeText(this, "Please Enter The Cost", Toast.LENGTH_SHORT)
                toast.show()
                binding.textView2.text = ""
            }
        }
        binding.costOfServiceEditingText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }

    }


    private fun calculateTip() {
        val numberField = binding.costOfServiceEditingText.text.toString()
        val cost = numberField.toDouble()

        val tipPercent = when (binding.radioGroup.checkedRadioButtonId) {
            R.id.option20 -> 0.2
            R.id.option18 -> 0.18
            else -> 0.15
        }
        var tip = cost * tipPercent
        if (binding.switch1.isChecked) {
            tip = kotlin.math.ceil(tip)
        }
        val formattedNumber = NumberFormat.getCurrencyInstance().format(tip)
        binding.textView2.text = getString(R.string.tip_amount, formattedNumber)
    }
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

}