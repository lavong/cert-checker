@file:JvmName("Main")

package com.ingloriousmind.cert.checker

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int

fun main(vararg args: String) {
	NoOpCliktCommand(name = "cert-checker")
		.subcommands(
			HelloCommand(),
			PublicKeyHashCommand(),
		)
		.main(args)
}

private class HelloCommand : CliktCommand(
	name = "hello",
	help = "Prints hello world"
) {
	val count: Int by option(
		names = arrayOf("--count", "-c"),
		help = "Number of greetings",
	).int().default(1)

	val name: String by option(
		names = arrayOf("--name", "-n"),
		help = "The person to greet",
	).default("World")

	override fun run() {
		repeat(count) {
			echo("Hello $name!")
		}
	}
}
