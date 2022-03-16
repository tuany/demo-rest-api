package com.learningkotlin.demoapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemoRestApiApplication

fun main(args: Array<String>) {
	runApplication<DemoRestApiApplication>(*args)
}
