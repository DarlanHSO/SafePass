package com.example.safepass

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

// api "random.org"
class ApiRandom : FonteDado {
    override suspend fun obterDado(): String = withContext(Dispatchers.IO) {
        try {
            val apiKey = "def0ef36-4f3d-494d-9a4c-0ee79b0f91d1"
            val url = URL("https://api.random.org/json-rpc/4/invoke")
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/json")
            conn.doOutput = true

            val payload = JSONObject().apply {
                put("jsonrpc", "2.0")
                put("method", "generateStrings")
                put("params", JSONObject().apply {
                    put("apiKey", apiKey)
                    put("n", 1)
                    put("length", 32)
                    put("characters", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789")
                    put("replacement", true)
                })
                put("id", 1)
            }

            OutputStreamWriter(conn.outputStream).use { it.write(payload.toString()) }

            if (conn.responseCode == 200) {
                conn.inputStream.bufferedReader().use { return@withContext it.readText() }
            }
        } catch (_: Exception) {}
        return@withContext ""
    }
}
