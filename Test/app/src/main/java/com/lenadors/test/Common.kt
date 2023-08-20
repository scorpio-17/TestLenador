package com.lenadors.test

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class Common {
    companion object {

        fun calculateTaxAmount(totalAmount: Double, taxRatePercentage: Double): Double {
            return totalAmount * (taxRatePercentage / 100)
        }

        fun generateRandomSixDigitNumber(): Int {
            var randomNumber = 0
            repeat(6) {
                val digit = Random.nextInt(
                    0,
                    10
                ) // Generates a random number between 0 (inclusive) and 10 (exclusive)
                randomNumber = randomNumber * 10 + digit
            }
            return randomNumber
        }

        fun getCurrentDateAndTime(): String {
            val currentDateTime = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            return dateFormat.format(currentDateTime)
        }
    }
}