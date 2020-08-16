package marce.projects.adivinhanumero.viewmodel

import androidx.lifecycle.MutableLiveData
import marce.projects.adivinhanumero.data.AdivinhaRepository

class AdivinhaViewModel(private val repository: AdivinhaRepository) {
    val number = MutableLiveData<Int>()
    val displayDigits = MutableLiveData<ArrayList<Int>>()
    val message = MutableLiveData<String>()
    val success = MutableLiveData<Boolean>()

    fun load() {
        repository.receiveNumber({ adivinhaData ->
            number.postValue(adivinhaData.value)
        }, { statusCode ->
            number.postValue(statusCode)
        })
    }

    fun prepareNumber(number: Int) {
        val digits = arrayListOf<Int>()

        if (number == 0) {
            digits.add(number)
        } else {
            var digit = number
            while (digit > 0) {
                digits.add(digit % 10)
                digit /= 10
            }
        }

        displayDigits.postValue(digits)
    }

    fun compareNumber(userInput: Int) {
        when {
            userInput == number.value -> {
                message.postValue("Acertou!")
                success.postValue(true)
            }
            userInput < number.value!! -> {
                message.postValue("É maior")
                success.postValue(false)
            }
            else -> {
                message.postValue("É menor")
                success.postValue(false)
            }
        }
    }
}