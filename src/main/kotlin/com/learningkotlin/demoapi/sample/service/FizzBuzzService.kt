package com.learningkotlin.demoapi.sample.service

import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class FizzBuzzService {

    fun fizzOrBuzz(number: Int): String {
        var result = ""

        if (number % 3 == 0) result = result + "Fizz"
        if (number % 5 == 0) result = result + "Buzz"

        return result
    }

    fun fibo_sequence(number: Int): Array<Int> {
        val results = mutableListOf<Int>(1, 1)

        for (i in 1..number-2) {
            val t1 = results[i-1]
            val t2 = results[i]

            results.add(t1 + t2)
        }
        return results.toTypedArray()
    }
}