package com.learningkotlin.demoapi.sample.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.learningkotlin.demoapi.sample.model.Fibonacci
import com.learningkotlin.demoapi.sample.model.FizzBuzz
import com.learningkotlin.demoapi.sample.model.Hello
import com.learningkotlin.demoapi.sample.service.FizzBuzzService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestParam

@RestController
@RequestMapping("/sample")
class SampleController(private val service: FizzBuzzService) {

    private val mapper by lazy { jacksonObjectMapper() }

    @GetMapping("/", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun index(@RequestHeader headers: MultiValueMap<String, String>): ResponseEntity<Any> {
        var response = HashMap<String, Any>()
        response["header_size"] = headers.size.toString()
        response["message"] = "Everything is alright!"
        response["original_headers"] = headers.toSingleValueMap()

        return genericJson(response, HttpStatus.OK)
    }

    @GetMapping("/fizz-buzz", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun fizzBuzz(@RequestParam("number") number: Int): ResponseEntity<Any> {
        val code: HttpStatus
        val result = service.fizzOrBuzz(number)
        val response: FizzBuzz

        if (result.isNotEmpty()) {
            code = HttpStatus.OK
            response = FizzBuzz(result)
        } else {
            code = HttpStatus.UNPROCESSABLE_ENTITY
            response = FizzBuzz("Something went wrong :(")
        }

        return genericJson(response, code)
    }

    @GetMapping("/say-hello", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun sayHello(): Hello {
        return Hello("Olá mundo!")
    }

    @GetMapping("/fibo", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun fibo(@RequestParam("number") number: Int): ResponseEntity<Any> {
        val response: Any
        val code: HttpStatus
        val maxNum = 20

        if(number > maxNum) {
            response = mapOf<String, Any>("message" to "number needs to be less than ${maxNum}")
            code = HttpStatus.UNPROCESSABLE_ENTITY
        } else {
            response = Fibonacci(service.fibo_sequence(number))
            code = HttpStatus.OK
        }
        return genericJson(response, code)
    }

    private fun genericJson(response: Any, code: HttpStatus): ResponseEntity<Any> {
        return ResponseEntity<Any>(/* body = */ mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response),
                                        /* status = */ code)
    }
}