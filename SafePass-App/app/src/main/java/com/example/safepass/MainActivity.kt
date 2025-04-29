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

        mostrarSenha(false)

        btnMostrarSenha.setOnClickListener {
            senhaVisivel = !senhaVisivel
            mostrarSenha(senhaVisivel)
            btnMostrarSenha.setImageResource(
                if (senhaVisivel) R.drawable.ic_visibility
                else R.drawable.ic_visibility_off
            )
        }

        btnGerar.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                editSenha.setText("GERANDO SENHA...")
                senhaAtual = withContext(Dispatchers.IO) {
                    LogicaSenha.gerarSenhaComApi(this@MainActivity)
                }
                senhaVisivel = true
                editSenha.setText(senhaAtual)
                btnMostrarSenha.setImageResource(R.drawable.ic_visibility)
            }
        }

        btnCopiar.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = android.content.ClipData.newPlainText("Senha", senhaAtual)
            clipboard.setPrimaryClip(clip)
        }

        btnPersonalizar.setOnClickListener {
            val bottomSheet = JanelaConfig()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }

    private fun mostrarSenha(visivel: Boolean) {
        editSenha.setText(if (visivel) senhaAtual else senhaOculta)
    }

}
