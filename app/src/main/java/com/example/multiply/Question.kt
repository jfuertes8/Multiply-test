package com.example.multiply

class Question(
    val num1 : Int,
    val num2 : Int
) {
    fun check(userInput: Int) : Boolean {
        return userInput == getResult()
    }

    fun getResult(): Int {
        return num1 * num2
    }

}
