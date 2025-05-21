package com.example.safepass

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var editSenha: EditText
    private lateinit var btnMostrarSenha: ImageButton
    private lateinit var btnGerar: Button
    private lateinit var btnCopiar: ImageButton
    private lateinit var btnPersonalizar: Button

    private var senhaVisivel = false
    private var senhaAtual: String = ""
    private val senhaOculta = "••••••••••••••"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // linka com os elementos com os componentes de layout
        editSenha = findViewById(R.id.editSenha)
        btnMostrarSenha = findViewById(R.id.btnMostrarSenha)
        btnGerar = findViewById(R.id.btnGerar)
        btnCopiar = findViewById(R.id.btnCopiar)
        btnPersonalizar = findViewById(R.id.btnPersonalizar)

        // evita edição no campo da senha
        editSenha.apply {
            isFocusable = false
            isClickable = false
            isCursorVisible = false
            isLongClickable = false
            keyListener = null
        }

        // visibilidade da senha na interface + ícone
        mostrarSenha(false)
        btnMostrarSenha.setOnClickListener {
            senhaVisivel = !senhaVisivel
            mostrarSenha(senhaVisivel)
            btnMostrarSenha.setImageResource(
                if (senhaVisivel) R.drawable.ic_visibility
                else R.drawable.ic_visibility_off
            )
        }

        // Gera a senha ( ver LogicaSenha.kt )
        btnGerar.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                editSenha.setText("GERANDO SENHA...")
                senhaAtual = withContext(Dispatchers.IO) {
                    LogicaSenha.gerarSenhaComConfig(this@MainActivity)
                }
               // senhaVisivel = true
                editSenha.setText(senhaAtual)
                btnMostrarSenha.setImageResource(R.drawable.ic_visibility)
            }
        }

        // botão de copiar a senha
        btnCopiar.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = android.content.ClipData.newPlainText("Senha", senhaAtual)
            clipboard.setPrimaryClip(clip)
        }

        // abre a janela de config
        btnPersonalizar.setOnClickListener {
            val bottomSheet = JanelaConfig()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }

    // alterna visibilidade da senha
    private fun mostrarSenha(visivel: Boolean) {
        editSenha.setText(if (visivel) senhaAtual else senhaOculta)
    }
}
