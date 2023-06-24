@file:JvmName("Main")

package com.ingloriousmind.cert.checker

import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.versionOption

fun main(vararg args: String) {
	NoOpCliktCommand(name = "cert-checker")
		.subcommands(
			HelloCommand(),
			FetchCertificatesCommand(),
		)
		.versionOption("0.4.0")
		.main(args)
}
