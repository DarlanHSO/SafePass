package com.example.safepass

object LogicaSenha {
    fun gerarSenhaSimples(context: android.content.Context): String {
        val config = ConfigUsuario.carregarConfig(context)

        // Prefixo para visualizaçao do slider de complexidade
        val prefixo = "n${config.nivelComplexidade}"

        // texto para demonstrar cada checkbox
        val blocos = mutableListOf<String>()
        if (config.usarMinusculas) blocos.add("a")
        if (config.usarMaiusculas) blocos.add("A")
        if (config.usarNumeros) blocos.add("1")
        if (config.usarSimbolos) blocos.add("!")


        if (blocos.isEmpty()) {
            blocos.add("x") // só pra não quebrar e mostrar algo, no futuro será impossível não escolher alguma checkbox
        }

        // Gera  senha até atingir o número desejado de caracteres
        val corpo = buildString {
            while (length + prefixo.length < config.numCaracteres) {
                for (bloco in blocos) {
                    if (length + prefixo.length < config.numCaracteres) {
                        append(bloco.first())
                    }
                }
            }
        }

        return prefixo + corpo.take(config.numCaracteres - prefixo.length)
    }
}
