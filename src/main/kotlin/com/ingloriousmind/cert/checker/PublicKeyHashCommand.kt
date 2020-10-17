package com.ingloriousmind.cert.checker

import com.github.ajalt.clikt.core.CliktCommand
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.ByteString.Companion.toByteString
import java.io.IOException
import java.security.cert.X509Certificate

class PublicKeyHashCommand : CliktCommand(
	name = "hash",
	help = "Prints public key hash"
) {
	override fun run() {
		val request = Request.Builder()
			.url("https://www.ingloriousmind.com/ttab/config.json")
			.build()
		val pinner = CertificatePinner.Builder()
			.add("*.ingloriousmind.com", "sha256/ABC")
			.build()

		val client = OkHttpClient.Builder()
			.addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
			.certificatePinner(pinner)
			.build()
		
		client.newCall(request).enqueue(object : Callback {
			override fun onFailure(call: Call, e: IOException) {
				echo(message = "onFailure: ${e.message}", err = true)
				e.printStackTrace()
			}

			override fun onResponse(call: Call, response: Response) {
				echo("onResponse")
				echo("handshake: ${response.handshake?.toString()}")

				echo("peer certificates:")
				response.handshake
					?.peerCertificates
					?.mapNotNull { it as? X509Certificate }
					?.forEach { x509 ->
						echo("  ${CertificatePinner.pin(x509)} | ${x509.subjectDN}")
						echo("  | notBefore=${x509.notBefore}")
						echo("  | notAfter=${x509.notAfter}")
						val validationError: String? = try {
							x509.checkValidity()
							null
						} catch (t: Throwable) {
							t.message
						}
						echo("  | valid=${validationError == null} ${validationError?.let { "error=$it" } ?: ""}")
					}
			}
		})
		println("coming soon (okhttp version=${OkHttp.VERSION})")
	}
}
