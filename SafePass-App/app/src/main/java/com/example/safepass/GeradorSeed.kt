package com.example.safepass

import java.security.MessageDigest
import java.util.Base64

class GeradorSeed(
    private val fontes: List<FonteDado>
) {
    suspend fun gerarSeed(): String {
        // aguarda todas as fontes e coleta os dados
        val dados = fontes.map { it.obterDado() }
        // verificação para ver se alguma fonte falhou
        if (dados.any { it.isBlank() }) return "ERRO_API"

        // transforma os dados em base64
        val combinado = dados.joinToString("")
        val base64 = Base64.getEncoder().encodeToString(combinado.toByteArray())

        // aplica o algoritmo SHA-256 sobre a string base64
        // o SHA-256 gera um hash fixo de 256 bits (64 caracteres hexa), que faz com que a seed resultante tenha alta entropia e seja praticamente impossível de prever.
        val hash = MessageDigest.getInstance("SHA-256").digest(base64.toByteArray())

        return hash.joinToString("") { "%02x".format(it) }
    }
}
