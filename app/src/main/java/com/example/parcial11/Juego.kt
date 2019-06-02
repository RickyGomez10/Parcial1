package com.example.parcial11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.parcial11.Database.Partido
import com.example.parcial11.ViewModel.PartidoViewModel
import com.example.parcial11.databinding.ActivityJuegoBinding
import kotlinx.android.synthetic.main.activity_juego.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class Juego : AppCompatActivity() {

    lateinit var scoreViewModel: ScoreViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)

        val binding = DataBindingUtil.setContentView(this, R.layout.activity_juego) as ActivityJuegoBinding

        val tvEquipoA : TextView  = findViewById<TextView>(R.id.tv_equipoA) as TextView
        val tvEquipoB : TextView = findViewById<TextView>(R.id.tv_equipoB) as TextView
        val btnTerminar : Button = findViewById<Button>(R.id.btn_Terminar)

        val equipoA = intent.getStringExtra("EquipoA")
        val equipoB = intent.getStringExtra("EquipoB")

        tvEquipoA.text = equipoA
        tvEquipoB.text = equipoB





        scoreViewModel = ViewModelProviders.of(this).get(ScoreViewModel::class.java)

        val viewmodel = ViewModelProviders.of(this).get(PartidoViewModel::class.java)


        binding.scoreTeam = scoreViewModel

        val puntosAObserver = Observer<Int> { newInt ->

            tv_score_team_a.text = newInt.toString()
        }
        val puntosBObserver = Observer<Int> { newInt ->

            tv_score_team_b.text = newInt.toString()
        }

        scoreViewModel.currentScoreA.observe(this, puntosAObserver)
        scoreViewModel.currentScoreB.observe(this,puntosBObserver)

        btnTerminar.setOnClickListener {
            val scoreA = tv_score_team_a.text.toString()
            val scoreB = tv_score_team_b.text.toString()

            //Se agrega la fecha actual del dispositivo
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            if(scoreB.toInt() > scoreA.toInt()){
                Toast.makeText(this,"El ganador es: "+tvEquipoB.text.toString(),Toast.LENGTH_LONG).show()
                viewmodel.insert(Partido(tvEquipoA.text.toString(),tvEquipoB.text.toString(),scoreA.toInt(),scoreB.toInt(),currentDate,tvEquipoB.text.toString()))
                finish()
            }else if(scoreB.toInt() < scoreA.toInt()){

                Toast.makeText(this,"El ganador es: "+tvEquipoA.text.toString(),Toast.LENGTH_LONG).show()
                viewmodel.insert(Partido(tvEquipoA.text.toString(),tvEquipoB.text.toString(),scoreA.toInt(),scoreB.toInt(),currentDate,tvEquipoA.text.toString()))
                finish()
            }else if(scoreB.toInt() == scoreA.toInt()){
                Toast.makeText(this,"Empatados",Toast.LENGTH_LONG).show()
                viewmodel.insert(Partido(tvEquipoA.text.toString(),tvEquipoB.text.toString(),scoreA.toInt(),scoreB.toInt(),currentDate,"Empate"))
                finish()

            }


        }



        // TODO: El ViewModel es restaurado si ya existía, si no, se crea uno nuevo.
        // TODO: Recuerde que el ViewModel solo sobrevive a cambios de configuración y no a la destrucción de la aplicación

    }


    // TODO: Accediendo y modificando datos almacenados en el ViewModel según el método utilizado

    fun addOneTeamA(v: View) {
        scoreViewModel.scoreTeamA += 1
        scoreViewModel.currentScoreA.value = scoreViewModel.scoreTeamA

    }

    fun addOneTeamB(v: View) {
        scoreViewModel.scoreTeamB += 1
        scoreViewModel.currentScoreB.value = scoreViewModel.scoreTeamB

    }

    fun addTwoTeamA(v: View) {
        scoreViewModel.scoreTeamA += 2
        scoreViewModel.currentScoreA.value = scoreViewModel.scoreTeamA

    }

    fun addTwoTeamB(v: View) {
        scoreViewModel.scoreTeamB += 2
        scoreViewModel.currentScoreB.value = scoreViewModel.scoreTeamB


    }

    fun addThreeTeamA(v: View) {
        scoreViewModel.scoreTeamA += 3
        scoreViewModel.currentScoreA.value = scoreViewModel.scoreTeamA

    }

    fun addThreeTeamB(v: View) {
        scoreViewModel.scoreTeamB += 3
        scoreViewModel.currentScoreB.value = scoreViewModel.scoreTeamB

    }

    fun resetScores(v: View) {
        scoreViewModel.scoreTeamB = 0
        scoreViewModel.currentScoreB.value = scoreViewModel.scoreTeamB

        scoreViewModel.scoreTeamA = 0
        scoreViewModel.currentScoreA.value = scoreViewModel.scoreTeamA

    } // TODO: Limpiando datos



}