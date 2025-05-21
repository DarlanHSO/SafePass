package com.example.safepass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class JanelaConfig : BottomSheetDialogFragment() { // activity da janela de configurações (a "bottom sheet dialog")

    private lateinit var config: ConfigUsuario // vai armazenar as configurações atuais

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // infla o layout XML da janela de config
        return inflater.inflate(R.layout.activity_config, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // carrega as preferências salvas do usuário
        config = ConfigUsuario.carregarConfig(requireContext())

        // links com elementos visuais do layout
        val txtNumCaracteres = view.findViewById<TextView>(R.id.valorCaracteres)
        val txtNivelComplexidade = view.findViewById<TextView>(R.id.valorComplexidade)
        val sliderNumCaracteres = view.findViewById<SeekBar>(R.id.sliderCaracteres)
        val sliderNivelComplexidade = view.findViewById<SeekBar>(R.id.sliderComplexidade)
        val checkboxNumeros = view.findViewById<CheckBox>(R.id.checkboxNumeros)
        val checkboxMinusculas = view.findViewById<CheckBox>(R.id.checkboxMinusculas)
        val checkboxMaiusculas = view.findViewById<CheckBox>(R.id.checkboxMaiusculas)
        val checkboxSimbolos = view.findViewById<CheckBox>(R.id.checkboxSimbolos)

        // valores de definição (e limites) dos sliders + enviados para ConfigUsuario
        sliderNumCaracteres.max = 20
        sliderNivelComplexidade.max = 4
        sliderNumCaracteres.progress = config.numCaracteres - 4
        sliderNivelComplexidade.progress = config.nivelComplexidade - 1

        txtNumCaracteres.text = config.numCaracteres.toString()
        txtNivelComplexidade.text = config.nivelComplexidade.toString()

        // estado inicial dos checkboxes com base na configuração salva
        checkboxNumeros.isChecked = config.usarNumeros
        checkboxMinusculas.isChecked = config.usarMinusculas
        checkboxMaiusculas.isChecked = config.usarMaiusculas
        checkboxSimbolos.isChecked = config.usarSimbolos

        // sliders
        sliderNumCaracteres.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                config.numCaracteres = progress + 4
                txtNumCaracteres.text = config.numCaracteres.toString()
                config.salvarConfig(requireContext())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        sliderNivelComplexidade.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                config.nivelComplexidade = progress + 1
                txtNivelComplexidade.text = config.nivelComplexidade.toString()
                config.salvarConfig(requireContext())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // função para impedir desmarcar todas as opções na janela de config
        fun impedirDesmarcarTodos(atual: CheckBox, outros: List<CheckBox>): Boolean {
            return !atual.isChecked && outros.none { it.isChecked }
        }

        // checkboxes
        // (cada checkbox tem um listener que impede que todas as opções fiquem desmarcadas ao mesmo tempo)
        checkboxNumeros.setOnCheckedChangeListener { buttonView, isChecked ->
            if (impedirDesmarcarTodos(checkboxNumeros, listOf(checkboxMinusculas, checkboxMaiusculas, checkboxSimbolos))) {
                buttonView.isChecked = true
                return@setOnCheckedChangeListener
            }
            config.usarNumeros = isChecked
            config.salvarConfig(requireContext())
        }

        checkboxMinusculas.setOnCheckedChangeListener { buttonView, isChecked ->
            if (impedirDesmarcarTodos(checkboxMinusculas, listOf(checkboxNumeros, checkboxMaiusculas, checkboxSimbolos))) {
                buttonView.isChecked = true
                return@setOnCheckedChangeListener
            }
            config.usarMinusculas = isChecked
            config.salvarConfig(requireContext())
        }

        checkboxMaiusculas.setOnCheckedChangeListener { buttonView, isChecked ->
            if (impedirDesmarcarTodos(checkboxMaiusculas, listOf(checkboxNumeros, checkboxMinusculas, checkboxSimbolos))) {
                buttonView.isChecked = true
                return@setOnCheckedChangeListener
            }
            config.usarMaiusculas = isChecked
            config.salvarConfig(requireContext())
        }

        checkboxSimbolos.setOnCheckedChangeListener { buttonView, isChecked ->
            if (impedirDesmarcarTodos(checkboxSimbolos, listOf(checkboxNumeros, checkboxMinusculas, checkboxMaiusculas))) {
                buttonView.isChecked = true
                return@setOnCheckedChangeListener
            }
            config.usarSimbolos = isChecked
            config.salvarConfig(requireContext())
        }
    }
}
