package com.example.screenotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class MainLogin : AppCompatActivity() {

    val URL = "http://192.168.1.68/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun Registrar(view: View) {
        val actReg: Intent = Intent(this, MainRegistrar::class.java)
        startActivity(actReg)
    }

    fun Entrar(view: View) {
        if (txtNoControlLogin.text.toString().isEmpty() || txtContrasenaLogin.text.toString().isEmpty()) {
            Toast.makeText(this, "Algun Campo se Encuentra Vacio", Toast.LENGTH_LONG).show()
        } else {
            val no_control = txtNoControlLogin.text.toString()
            val contrasena = txtContrasenaLogin.text.toString()
            val wsURL = URL + "screenotes/loginAlumno.php"
            var jsonEntrada = JSONObject()
            jsonEntrada.put("no_control", no_control)
            jsonEntrada.put("contrasena_alumno", contrasena)
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, wsURL, jsonEntrada,
                Response.Listener { response ->
                    val succ = response["success"]
                    val msg = response["message"]
                    if (succ == 200) {
                        val sensadoJson = response.getJSONArray("user")
                        val no_control = sensadoJson.getJSONObject(0).getString("no_control")
                        val contrasena = sensadoJson.getJSONObject(0).getString("contrasena_alumno")

                        val actHome = Intent(this, MainActivity::class.java)
                        actHome.putExtra(MainActivity.EXTRA_CONTROL, no_control)
                        actHome.putExtra(MainActivity.EXTRA_CONTRASENA, contrasena)
                        startActivity(actHome)
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, "Error en el Alumno : " + error.message.toString(), Toast.LENGTH_LONG).show()
                    Log.d("ERROR :", error.message.toString())
                }
            )
            VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
        }
    }
}
