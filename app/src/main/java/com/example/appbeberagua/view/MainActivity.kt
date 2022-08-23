package com.example.appbeberagua.view

import android.app.AlarmManager
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.appbeberagua.R
import com.example.appbeberagua.databinding.ActivityMainBinding
import com.example.appbeberagua.model.CalcularIngestaoDiaria
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var edit_peso :EditText
    private lateinit var edit_idade :EditText
    private lateinit var bt_calcular:Button
    private lateinit var txt_resultado_ml:TextView
    private lateinit var ic_redefinir_dados:ImageView
    private lateinit var bt_lembrete:Button
    private lateinit var bt_alarme:Button
    private lateinit var txt_hora:TextView
    private lateinit var txt_minutos:TextView

    private lateinit var calcularIngestaoDiaria:CalcularIngestaoDiaria
    private var resultadoMl = 0.0

    lateinit var  timePickDialog:TimePickerDialog
    lateinit var calendario:Calendar
    var horaAtual = 0
    var minutosAtuais = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // esconder barra de ação

        supportActionBar!!.hide()

        //iniciar findView

       // iniciarComponentes()

        //Iniciar classe Model

        calcularIngestaoDiaria = CalcularIngestaoDiaria()
        val bt_calcular = binding.btCalcular

            bt_calcular.setOnClickListener {

            if (binding.editPeso.text.toString().isEmpty()){

                Toast.makeText(this, R.string.toast_informe_peso,Toast.LENGTH_SHORT).show()

            }else if (binding.editIdade.text.toString().isEmpty()){

                Toast.makeText(this, R.string.toast_informe_idade,Toast.LENGTH_SHORT).show()
            } else{
                //bindview
                val peso = binding.editPeso.text.toString().toDouble()
                val idade= binding.editIdade.text.toString().toInt()

                //val peso = edit_peso.text.toString().toDouble()
                //val idade= edit_idade.text.toString().toInt()
                calcularIngestaoDiaria.CalcularTotalMl(peso,idade)
                resultadoMl = calcularIngestaoDiaria.ResultadoMl()
                val formatar = NumberFormat.getNumberInstance(Locale("pt","BR"))
                formatar.isGroupingUsed = false

                //bindView
                binding.txtResultadoMl.text = formatar.format(resultadoMl).toString() + " " + "ml"
                //txt_resultado_ml.text = formatar.format(resultadoMl).toString() + " " + "ml"

            }
        }

        binding.icRedefinir.setOnClickListener {
            val edit_peso = binding.editPeso
            val edit_idade= binding.editIdade
            val txt_resultado_ml = binding.txtResultadoMl

            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle(R.string.dialog_titulos)
                .setMessage(R.string.dialog_desc)
                .setPositiveButton("OK",{dialogInterface, i ->

                    edit_peso.setText("")
                    edit_idade.setText("")
                    txt_resultado_ml.text = ""
                })
            alertDialog.setNegativeButton("Cancelar",{dialogInterface, i ->

            })
            val  dialog = alertDialog.create()
            dialog.show()
        }
        binding.btDefinirLembrete.setOnClickListener {

            calendario = Calendar.getInstance()
            horaAtual = calendario.get(Calendar.HOUR_OF_DAY)
            minutosAtuais = calendario.get(Calendar.MINUTE)
            timePickDialog = TimePickerDialog(this,{timePick:TimePicker,hourOfDay:Int,minutes:Int ->
                binding.txtHora.text= String.format("%02d",hourOfDay)
                binding.txtMinutos.text= String.format("%02d",minutes)

            },horaAtual,minutosAtuais,true)
            timePickDialog.show()
        }

        binding.btAlarme.setOnClickListener {

            val txtHora = binding.txtHora
            val txtMinutos = binding.txtMinutos

            if (!txtHora.text.toString().isEmpty() && !txtMinutos.text.toString().isEmpty()){

                val intent = Intent(AlarmClock.ACTION_SET_ALARM)
                intent.putExtra(AlarmClock.EXTRA_HOUR,txtHora.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MINUTES,txtMinutos.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MESSAGE,getString(R.string.alarme_mensagem))
                startActivity(intent)

                if (intent.resolveActivity(packageManager) != null){
                    startActivity(intent)
                }
            }
        }
    }
   /* private fun iniciarComponentes(){
        edit_peso = findViewById(R.id.edit_peso)
        edit_idade= findViewById(R.id.edit_idade)
        bt_calcular= findViewById(R.id.bt_calcular)
        txt_resultado_ml=findViewById(R.id.txt_resultado_ml)
        ic_redefinir_dados=findViewById(R.id.ic_redefinir)
        bt_lembrete = findViewById(R.id.bt_definir_lembrete)
        bt_alarme = findViewById(R.id.bt_alarme)
        txt_hora = findViewById(R.id.txt_hora)
        txt_minutos=findViewById(R.id.txt_minutos)

    }*/
}