package com.ingloriousmind.cert.checker

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import okhttp3.*
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Proxy
import java.security.cert.X509Certificate
import javax.net.ssl.SSLPeerUnverifiedException
import kotlin.system.exitProcess

class FetchCertificatesCommand : CliktCommand(name = "certs") {
	override fun help(context: Context) = "Prints certificates"

	private val url: String by argument(name = "url", help = "The url to fetch")
		.convert { if (it.startsWith("https://")) it else "https://$it" }

	private val verbose by option("-v", "--verbose", help = "Display verbose information").flag(default = false)

	private val proxyHost by option("-proxy", "--proxy-host", help = "Proxy hostname to use")

	private val proxyPort by option("-port", "--proxy-port", help = "Proxy port to use").int().default(8080)

	override fun run() {
		val request = Request.Builder().url(url).build()
		val client = OkHttpClient.Builder()
			.apply { proxyHost?.let { proxy(Proxy(Proxy.Type.HTTP, InetSocketAddress(it, proxyPort))) } }
			.build()

		client.newCall(request).enqueue(object : Callback {
			override fun onFailure(call: Call, e: IOException) {

				if (e is SSLPeerUnverifiedException) {
					echo("peer certificate chain:")
					e.message?.lines()
						?.filter { it.contains("sha256/") }
						?.onEach { echo(it) }
						?: echo("none")
				} else {
					if (verbose) {
						e.printStackTrace()
					} else {
						echo(message = e.message, err = true)
					}
				}

				exitProcess(1)
			}

			override fun onResponse(call: Call, response: Response) {

				echo("\npeer certificate chain:")
				response.handshake
					?.peerCertificates
					?.mapNotNull { it as? X509Certificate }
					?.onEach { x509 -> echo("  ${CertificatePinner.pin(x509)} | ${x509.subjectX500Principal.name}") }

				if (verbose) {
					response.handshake?.let { echo("\nHandshake:\n$it") }

					response.handshake
						?.peerCertificates
						?.mapNotNull { it as? X509Certificate }
						?.forEach { x509 ->
							echo("\n----- X509 Certificate -----\n$x509")
						}
				}

				exitProcess(0)
			}
		})

		echo("connecting to '$url'...")
		client.proxy?.let { echo("(via proxy $it)") }
	}
}
