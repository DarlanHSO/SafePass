package com.example.safepass

import android.util.Log
import kotlinx.coroutines.delay
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.random.Random

// api "weatherapi"
class ApiClima : FonteDado {
    override suspend fun obterDado(): String {
        val key = "cd940c5a57424e03a11193341250604"
        val maxTentativas = 200

        for (i in 1..maxTentativas) { // utiliza números aleatorios de latitude e longitude para obter um local aleatório e seus dados climáticos
            try {
                val lat = Random.nextDouble(-90.0, 90.0).format(4)
                val lon = Random.nextDouble(-180.0, 180.0).format(4)
                val url = URL("https://api.weatherapi.com/v1/current.json?key=$key&q=$lat,$lon&aqi=no")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"

                Log.d("API", "Tentativa $i chamando: $url")

                if (conn.responseCode == 200) {
                    return BufferedReader(InputStreamReader(conn.inputStream)).readText()
                } else {
                    Log.e("API", "resposta n foi 200: ${conn.responseCode}")
                }
            } catch (e: Exception) {
                Log.e("API", "Erro tentativa $i: ${e.message}")
            }

            // espera entre tentativas
            delay(300)
        }

        return ""
    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)
}
