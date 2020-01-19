package com.leonardo.gamestatus

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreference: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private var nickName: String = ""
    private var vidas: Int = 0
    private var monedas: Int = 0
    private var nivel: Int = 0
    private var darkModeStatus: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreference = getSharedPreferences("com.leonardo.gamestatus", Context.MODE_PRIVATE)
        editor = sharedPreference.edit()

        loadInitialValues()

        btnAumentarVidas.setOnClickListener {
            vidas += 1
            tvNumVidas.text = vidas.toString()
        }

        btnDisminuirVidas.setOnClickListener {
            if(vidas > 0) {
                vidas -= 1
            }
            tvNumVidas.text = vidas.toString()
        }

        btnAumentarMonedas.setOnClickListener {
            monedas += 1
            tvNumMonedas.text = monedas.toString()
        }

        btnDisminuirMonedas.setOnClickListener {
            if(monedas > 0) {
                monedas -= 1
            }
            tvNumMonedas.text = monedas.toString()
        }

        btnAumentarNivel.setOnClickListener {
            if(nivel < 100) {
                nivel += 10
            }
            progressbarNivel.progress = nivel
        }

        btnDisminuirNivel.setOnClickListener {
            if(nivel > 0) {
                nivel -= 10
            }
            progressbarNivel.progress = nivel
        }

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if(!isChecked) {
                switchDarkMode.isChecked = false
                switchDarkMode.text = "OFF"
                darkModeStatus = false
            } else {
                switchDarkMode.isChecked = true
                switchDarkMode.text = "ON"
                darkModeStatus = true
            }
        }

        btnGuardarCambios.setOnClickListener {
            guardarCambios()
        }

    }

    private fun loadInitialValues() {
        nickName = sharedPreference.getString(getString(R.string.key_nickname), "")!!
        vidas = sharedPreference.getInt(getString(R.string.key_vidas), 0)
        monedas = sharedPreference.getInt(getString(R.string.key_monedas), 0)
        nivel = sharedPreference.getInt(getString(R.string.key_nivel), 0)
        darkModeStatus = sharedPreference.getBoolean(getString(R.string.key_dark_mode), false)

        setInitialValues(nickName, vidas, monedas, nivel, darkModeStatus)
    }

    private fun setInitialValues(nickName: String, vidas: Int, monedas: Int, nivel: Int, darkModeStatus: Boolean) {
        textInputEditTextNicknama.setText(nickName)
        tvNumVidas.text = vidas.toString()
        tvNumMonedas.text = monedas.toString()
        progressbarNivel.progress = nivel
        switchDarkMode.isChecked = darkModeStatus
        if(switchDarkMode.isChecked) {
            switchDarkMode.text = "ON"
        } else {
            switchDarkMode.text = "OFF"
        }
    }

    private fun guardarCambios() {
        editor.putString(getString(R.string.key_nickname), textInputEditTextNicknama.text.toString())
        editor.putInt(getString(R.string.key_vidas), vidas)
        editor.putInt(getString(R.string.key_monedas), monedas)
        editor.putInt(getString(R.string.key_nivel), nivel)
        editor.putBoolean(getString(R.string.key_dark_mode), darkModeStatus)
        editor.commit()
    }
}
