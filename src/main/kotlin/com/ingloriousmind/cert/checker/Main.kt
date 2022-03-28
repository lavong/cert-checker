@file:JvmName("Main")

package com.ingloriousmind.cert.checker

import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.subcommands

fun main(vararg args: String) {
	NoOpCliktCommand(name = "cert-checker")
		.subcommands(
			HelloCommand(),
			FetchCertificatesCommand(),
		)
		.main(args)
}
