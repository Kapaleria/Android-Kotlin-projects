package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //private var strNumber:String="" //This creates many duplicate string objects in memory
    private var strNumber=StringBuilder()// remedy: use StringBuilder class

    private lateinit var tvDisplay: TextView
    private lateinit var button2: Button
    private lateinit var numberButtons:Array<Button>
    private lateinit var operatorButtons:List<Button>
    private var operator:Operator=Operator.NONE
    private var isOperatorClicked:Boolean=false//at first the operator is not clicked
    private var operand1:Int=0// store previous number before putting another one



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //region var and val
        //        val num1 = 2
//        var num2 = 7
//        val sum = num1 + num2
        //endregion

        initializeComponents()//constructor

    }

    private fun initializeComponents() {
        tvDisplay = findViewById(R.id.tvDisplay)
        //tvDisplay.text=sum.toString()

        button2 = findViewById(R.id.button2)

        numberButtons = arrayOf(button0,button1,button2,button3,button4,button5,button6,button7,button8,button9)
        operatorButtons=listOf(buttonAdd,buttonDiv,buttonMul, buttonSub)
        for (i in numberButtons){
            i.setOnClickListener{numberButtonClick(i)}
        }
        for(i in operatorButtons){
            i.setOnClickListener{operatorButtonClick(i)}
        }

        buttonEquals.setOnClickListener{
            buttonEqualClick()
        }
        
    }

    private fun buttonEqualClick() {
        val operand2=strNumber.toString().toInt()
        val result= when(operator){
            Operator.ADD->operand1 + operand2
            Operator.SUB->operand1 - operand2
            Operator.MUL->operand1 * operand2
            Operator.DIV->operand1 / operand2
           else->0
        }
        strNumber.clear()
        strNumber.append(result.toString())
        //tvDisplay.text=strNumber , we wanted to solve the multiple zero problem
        //so we introduced updateDisplay()
        updateDisplay()
        isOperatorClicked=true
    }

    private fun updateDisplay() {
        //to avoid crushing when the maximum number size is put in, we use try{}

        try{
            val textValue=strNumber.toString().toInt()
            // tvDisplay.text = strNumber
            tvDisplay.text = textValue.toString()
        }
        catch(e:IllegalArgumentException){
            strNumber.clear()
            tvDisplay.text="ERROR"
        }
    }

    private fun operatorButtonClick(btn: Button) {
        if(btn.text=="+") operator=Operator.ADD
        else if(btn.text=="-")operator=Operator.SUB
        else if(btn.text=="*")operator=Operator.MUL
        else if (btn.text=="/")operator=Operator.DIV

        else operator=Operator.NONE
        isOperatorClicked=true








    }

    //region setOnClickListener and Append
    //        button2.setOnClickListener { button2Clicked() }
//        button4.setOnClickListener { button4Clicked() }
//        button8.setOnClickListener { button8Clicked() }
//    }
//
//    private fun button8Clicked() {
//       // strNumber+="8"
//        strNumber.append("8")
//        tvDisplay.text = strNumber
//    }
//
//    private fun button4Clicked() {
//       // strNumber+="4"
//        strNumber.append("4")
//        tvDisplay.text = strNumber
//    }
//
//    private fun button2Clicked() {
//        //strNumber+="2"
//        strNumber.append("2")
//        tvDisplay.text = strNumber
//    }
    //endregion
private fun numberButtonClick(btn:Button){
   //when the operator has been clicked
        if(isOperatorClicked==true){
            operand1=strNumber.toString().toInt() //convert the number to string and then to Int
            strNumber.clear()//we can now remove and store the previous number
            isOperatorClicked=false// toggle isOperatorClicked back to false

        }

    strNumber.append(btn.text)
      //  tvDisplay.text=strNumber
        updateDisplay()

}

}

enum class Operator{ADD, SUB, MUL, DIV, NONE}