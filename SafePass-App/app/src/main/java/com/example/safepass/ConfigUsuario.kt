package com.example.safepass

import android.content.Context
import android.content.SharedPreferences


// para salvar os parametros escolhidos pelo usuario e não perder as escolhas ao fechar a janela de configuraçao
class ConfigUsuario {

    var numCaracteres: Int = 12
    var nivelComplexidade: Int = 3

    var usarNumeros: Boolean = true
    var usarMinusculas: Boolean = true
    var usarMaiusculas: Boolean = true
    var usarSimbolos: Boolean = false

    fun salvarConfig(context: Context) {
        val prefs: SharedPreferences =
            context.getSharedPreferences("config", Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putInt("numCaracteres", numCaracteres)
            putInt("nivelComplexidade", nivelComplexidade)
            putBoolean("usarNumeros", usarNumeros)
            putBoolean("usarMinusculas", usarMinusculas)
            putBoolean("usarMaiusculas", usarMaiusculas)
            putBoolean("usarSimbolos", usarSimbolos)
            apply()
        }
    }

    companion object {
        fun carregarConfig(context: Context): ConfigUsuario {
            val prefs: SharedPreferences =
                context.getSharedPreferences("config", Context.MODE_PRIVATE)
            val config = ConfigUsuario()
            config.numCaracteres = prefs.getInt("numCaracteres", 12)
            config.nivelComplexidade = prefs.getInt("nivelComplexidade", 3)
            config.usarNumeros = prefs.getBoolean("usarNumeros", true)
            config.usarMinusculas = prefs.getBoolean("usarMinusculas", true)
            config.usarMaiusculas = prefs.getBoolean("usarMaiusculas", true)
            config.usarSimbolos = prefs.getBoolean("usarSimbolos", false)
            return config
        }
    }
}
