package com.example.safepass

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64
import android.util.Log
import java.io.IOException



object LogicaSenha {

    suspend fun gerarSenhaComApi(context: Context): String = withContext(Dispatchers.IO) {
        val config = ConfigUsuario.carregarConfig(context)
        val nivelRastreabilidade = config.nivelComplexidade.coerceIn(1, 5)
        val funcoesApi = listOf(::chamarApiClima)

        val hashes = mutableListOf<String>()

        // n1 - dados locais
        if (nivelRastreabilidade == 1) {
            val entropiaLocal = System.nanoTime().toString() + System.currentTimeMillis().toString()
            hashes.add(entropiaLocal)
        } else {
            // n2 - somente API (sem dados locais por enquanto)
            for (i in 0 until nivelRastreabilidade) {
                val indiceApi = i % funcoesApi.size
                val hash = funcoesApi[indiceApi]()
                if (hash.isBlank()) {
                    return@withContext "ERRO_API"
                }
                hashes.add(hash)
            }
        }

        val finalHash = combinarHashes(hashes)
        return@withContext gerarSenha(
            finalHash,
            config.numCaracteres,
            config.usarMaiusculas,
            config.usarMinusculas,
            config.usarNumeros,
            config.usarSimbolos
        )
    }

    private fun combinarHashes(hashes: List<String>): String {
        val combinado = hashes.joinToString("")
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(combinado.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    private fun gerarSenha(
        seed64: String,
        tamanho: Int,
        usarMaiusculas: Boolean,
        usarMinusculas: Boolean,
        usarNumeros: Boolean,
        usarSimbolos: Boolean
    ): String {
        if (seed64.length != 64 || seed64.all { it == '0' } || seed64.isBlank()) {
            return "ERRO_API"
        }

        val seedBytes = seed64.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
        val secureRng = SecureRandom.getInstanceStrong().apply { setSeed(seedBytes) }
        val rng = kotlin.random.Random(secureRng.nextLong())

        val grupos = mutableListOf<String>()
        if (usarMaiusculas) grupos.add(('A'..'Z').joinToString(""))
        if (usarMinusculas) grupos.add(('a'..'z').joinToString(""))
        if (usarNumeros) grupos.add(('0'..'9').joinToString(""))
        if (usarSimbolos) grupos.add("!@#\$%&*?.,")

        if (grupos.isEmpty()) {
            return "ERRO_CONFIG"
        }

        val senha = mutableListOf<Char>()

        // Garante pelo menos um caractere de cada grupo
        grupos.forEach { senha.add(it.random(rng)) }

        // Preenche o restante balanceando os grupos
        while (senha.size < tamanho) {
            val grupo = grupos.random(rng)
            senha.add(grupo.random(rng))
        }

        return senha.shuffled(rng).joinToString("")
    }

    private fun chamarApiClima(): String {
        val key = "cd940c5a57424e03a11193341250604"
        val maxTentativas = 200

        for (i in 1..maxTentativas) {
            try {
                val lat = kotlin.random.Random.nextDouble(-90.0, 90.0).format(4)
                val lon = kotlin.random.Random.nextDouble(-180.0, 180.0).format(4)
                val url =
                    URL("https://api.weatherapi.com/v1/current.json?key=$key&q=$lat,$lon&aqi=no")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"

                Log.d("API", "Tentativa $i chamando: $url")

                if (conn.responseCode == 200) {
                    val response = BufferedReader(InputStreamReader(conn.inputStream)).readText()
                    val base64 = Base64.getEncoder().encodeToString(response.toByteArray())
                    val digest = MessageDigest.getInstance("SHA-256")
                    val hashBytes = digest.digest(base64.toByteArray())
                    return hashBytes.joinToString("") { "%02x".format(it) }
                } else {
                    Log.e("API", "Resposta não foi 200: ${conn.responseCode}")
                }
            } catch (e: Exception) {
                Log.e("API", "Erro na tentativa $i: ${e.message}")
            }
            Thread.sleep(300) // Dá uma segurada entre as tentativas pra não estourar limite da API
        }

        // Se chegar aqui depois de 200 tentativas e não conseguir, lança erro
        throw IOException("Não foi possível obter resposta válida da API após $maxTentativas tentativas.")
    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)

}