package com.example.safepass

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.SecureRandom

object LogicaSenha {

    suspend fun gerarSenhaComConfig(context: Context): String = withContext(Dispatchers.IO) {
        val config = ConfigUsuario.carregarConfig(context) // carrega os parametros da senha

        // forma a lista para chamar a classe GeradorSeed
        val fontes = mutableListOf<FonteDado>(FonteLocal())
        if (config.nivelComplexidade >= 2) { // add a api de clima
            if (!VerificaConexao.temInternet(context)) {
                return@withContext "SEM_CONEXAO"
            }
            fontes.add(ApiClima())
        }
        if (config.nivelComplexidade >= 3) { // add a api do site "random.org"
            fontes.add(ApiRandom())
        }
        val seed = GeradorSeed(fontes).gerarSeed()
        if (seed == "ERRO_API") return@withContext "ERRO_API"

        return@withContext gerarSenha(
            seed,
            config.numCaracteres,
            config.usarMaiusculas,
            config.usarMinusculas,
            config.usarNumeros,
            config.usarSimbolos
        )
    }

    private fun gerarSenha( // etapa final da geração da senha, baseada na seed obtida pelo GeradorSeed
        seedHex: String,
        tamanho: Int,
        usarMaiusculas: Boolean,
        usarMinusculas: Boolean,
        usarNumeros: Boolean,
        usarSimbolos: Boolean
    ): String {
        if (seedHex.length != 64 || seedHex.all { it == '0' } || seedHex.isBlank()) {
            return "ERRO_API"
        }

        val seedBytes = seedHex.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
        val rng = SecureRandom.getInstanceStrong().apply { setSeed(seedBytes) } // criação de objeto SecureRandom usando a seed obtida
                                                                                //(usando "Strong" para a implementação mais forte em JVM)

        // dicionário dos grupos de caracteres, os escolhidos pelo usuário serão utilizados
        val grupos = mutableListOf<String>()
        if (usarMaiusculas) grupos.add(('A'..'Z').joinToString(""))
        if (usarMinusculas) grupos.add(('a'..'z').joinToString(""))
        if (usarNumeros) grupos.add(('0'..'9').joinToString(""))
        if (usarSimbolos) grupos.add("!@#\$%&*?.,")
        if (grupos.isEmpty()) return "ERRO_CONFIG"

        val senha = mutableListOf<Char>()
        grupos.forEach { grupo ->
            senha.add(grupo[rng.nextInt(grupo.length)]) // garante pelo menos 1 de cada grupo, selecionado aleatoriamente com SecureRandom
        }

        while (senha.size < tamanho) {
            val grupo = grupos[rng.nextInt(grupos.size)]
            senha.add(grupo[rng.nextInt(grupo.length)])
        }

        senha.shuffle(rng) // embaralha a senha com o objeto SecureRandom.
        return senha.joinToString("")
    }
}
