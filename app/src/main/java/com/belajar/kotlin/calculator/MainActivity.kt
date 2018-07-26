package com.belajar.kotlin.calculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    private val operationList : MutableList<String> = arrayListOf()
    private val inputList : MutableList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        displayNumbers("")
    }

    private fun extractString (numbers : List<String>, connect : String = "") : String{
        if (numbers.isEmpty()) return ""
        return numbers.reduce{acc, s-> acc + connect + s}
    }

    private fun displayNumbers (numberShown : String){
        val calculationString = extractString(operationList, "")
        tvNumber.text = calculationString

        tvInput.text = numberShown
    }

    fun inputNumber (view: View){
        val input = view as Button
        val number = input.text

        inputList.add(number.toString())

        val txtNumber = extractString(inputList)
        displayNumbers(txtNumber)
    }

    fun inputOperator (view: View){
        val inputOp = view as Button

        if (inputList.isEmpty()) return

        operationList.add(extractString(inputList))
        inputList.clear()
        operationList.add(inputOp.text.toString())
        displayNumbers(inputOp.text.toString())
    }

    private fun clear(){
        operationList.clear()
        inputList.clear()
    }

    fun clearInput(view: View){
        clear()
        displayNumbers("")
    }

    fun equalNumber(view: View){
        operationList.add(extractString(inputList))
        inputList.clear()

        val answer = calculate(operationList)

        displayNumbers(answer.toString())

        operationList.clear()
        inputList.add(answer.toString())
    }


    private fun calculate(calculationList : List<String>) : Int {
        var curentOperator = ""
        var currentNumber = 0

        calculationList.forEach{operator ->

            when{
                operator.equals("+") || operator.equals("-") || operator.equals("x") || operator.equals("/")
                -> curentOperator = operator

                curentOperator.equals("-") -> currentNumber -= operator.toInt()
                curentOperator.equals("x") -> currentNumber *= operator.toInt()
                curentOperator.equals("/") -> currentNumber /= operator.toInt()

                else -> currentNumber += operator.toInt()
            }
        }

        return currentNumber
    }
}
