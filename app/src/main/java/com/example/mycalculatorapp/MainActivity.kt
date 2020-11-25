package com.example.mycalculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    //text view that used to use for display input
    lateinit var txtInput: TextView

    //represent wether the lastly pressed is numeric or not
    var lastnumeric: Boolean = false

    //represent that current state is error or not
    var stateError: Boolean = false

    //if true, Do not add another dot
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtInput = findViewById(R.id.txtInput)
    }

    fun onDigit(view:View){
    if (stateError){
        //if current state error, replace error message
        txtInput.text = (view as Button).text
        stateError = false
    }else{
        //if not, already there is a valid expression so append to it
        txtInput.append((view as Button).text)
    }
        //set the flag
        lastnumeric = true
    }

    fun onDecimalPoint(view: View){
        if (lastnumeric && !stateError && !!lastDot){
            txtInput.append(".")
            lastnumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View){
        if (lastnumeric && !stateError){
            txtInput.append((view as Button).text)
            lastnumeric = false
            lastDot = false
        }
    }

    fun onClear(view: View){
        this.txtInput.text = ""
        lastnumeric = false
        stateError = false
        lastDot = false
    }

    fun onEqual(view: View){
        // If the current state is error, nothing to do.
        // If the last input is a number only, solution can be found.
        if(lastnumeric && !stateError){
            //read the expression
            val txt = txtInput.text.toString()
            //create the expression(A class from xp4j library)
            val expression = ExpressionBuilder(txt).build()
            try {
                //calculate the result and show it on display
                val result = expression.evaluate()
                txtInput.text = result.toString()
                lastDot = true
            }catch (ex: ArithmeticException){
                txtInput.text = "Error"
                stateError = true
                lastnumeric = false
            }
        }
    }

}
