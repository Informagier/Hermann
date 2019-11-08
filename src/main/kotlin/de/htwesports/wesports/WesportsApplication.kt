package de.htwesports.wesports

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WesportsApplication

fun main(args: Array<String>) {
	runApplication<WesportsApplication>(*args)
}
