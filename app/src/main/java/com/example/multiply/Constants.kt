package com.example.multiply

object Constants {
    /*Aquí vamos a generar las preguntas dependiendo de la pantalla desde donde venga el usuario*/
    fun getQuestions(practice : Boolean = true, tableNumber : Int, chosenQuestions : Int = 10): ArrayList<Question> {

        val questionList = ArrayList<Question>()

        if(practice) {
            for(i in 1..10) {
               questionList.add(Question(tableNumber, i))
            }
        } else {
            for(i in 1..chosenQuestions) {
                questionList.add(Question(tableNumber, (1..30).random()))
            }
        }

        return questionList
    }

}