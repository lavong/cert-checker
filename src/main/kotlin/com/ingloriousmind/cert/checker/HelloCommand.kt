package com.ingloriousmind.cert.checker

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int

class HelloCommand : CliktCommand(
	name = "hello",
	help = "Prints hello world a given amount of times"
) {
	private val count: Int by option(names = arrayOf("--count", "-c"), help = "Number of greetings").int().default(1)

	private val name: String by option(names = arrayOf("--name", "-n"), help = "The person to greet").default("World")

	override fun run() {
		repeat(count) {
			echo("Hello $name!")
		}
	}
}
