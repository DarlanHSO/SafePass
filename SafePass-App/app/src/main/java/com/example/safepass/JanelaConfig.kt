package com.example.safepass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class JanelaConfig : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_config, container, false)

        val txtNumCaracteres = view.findViewById<TextView>(R.id.valorCaracteres)
        val txtNivelComplexidade = view.findViewById<TextView>(R.id.valorComplexidade)

        val sliderNumCaracteres = view.findViewById<SeekBar>(R.id.sliderCaracteres)
        val sliderNivelComplexidade = view.findViewById<SeekBar>(R.id.sliderComplexidade)

        // configurando a extensao dos valores dos sliders
        sliderNumCaracteres.max = 24 - 4
        sliderNivelComplexidade.max = 5 - 1
        sliderNumCaracteres.progress = 0
        sliderNivelComplexidade.progress = 0
        txtNumCaracteres.text = (sliderNumCaracteres.progress + 4).toString()
        txtNivelComplexidade.text = (sliderNivelComplexidade.progress + 1).toString()


        sliderNumCaracteres.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val valorReal = progress + 4
                txtNumCaracteres.text = valorReal.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        sliderNivelComplexidade.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val valorReal = progress + 1
                txtNivelComplexidade.text = valorReal.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        return view // checkboxes ainda não são utilizadas aqui, o valor só vai ser repassado no backend futuramente
    }
}
