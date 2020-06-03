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
import kotlinx.android.synthetic.main.activity_registrar.*
import org.json.JSONObject

class MainRegistrar : AppCompatActivity() {

    val URL = "http://192.168.1.68/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)
    }

    fun Cancelar(view: View) {
        val actReg: Intent = Intent(this, MainLogin::class.java)
        startActivity(actReg)
    }

    fun GuardarAlumno(view: View) {
        if (txtNoControlRegistro.text.toString().isEmpty() || txtNombreRegistro.text.toString().isEmpty() || txtContrasenaRegistro.text.toString().isEmpty()) {
            Toast.makeText(this, "Algun Campo del Registro se Encuentra Vacio", Toast.LENGTH_SHORT).show()
        } else {
            val no_control = txtNoControlRegistro.text.toString()
            val contrasena_alumno = txtContrasenaRegistro.text.toString()
            val nom_alumno = txtNombreRegistro.text.toString()

            var jsonEntrada = JSONObject()
            jsonEntrada.put("no_control", no_control)
            jsonEntrada.put("nom_alumno", nom_alumno)
            jsonEntrada.put("contrasena_alumno", contrasena_alumno)
            sendRequest(URL + "screenotes/insertAlumno.php", jsonEntrada)

            val actHome: Intent = Intent(this, MainActivity::class.java)
            actHome.putExtra(MainActivity.EXTRA_CONTROL, no_control)
            actHome.putExtra(MainActivity.EXTRA_CONTRASENA, contrasena_alumno)
            startActivity(actHome)
        }
    }

    private fun sendRequest(wsURL: String, jsonEnt: JSONObject) {
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, wsURL, jsonEnt,
            Response.Listener { response ->
                val succ = response["success"]
                val msg = response["message"]
                if (succ == 200) {
                    txtNoControlRegistro.setText("")
                    txtNombreRegistro.setText("")
                    txtContrasenaRegistro.setText("")
                    Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_LONG).show()
                    //Toast.makeText(this, "Success:${succ}  Message:${msg}", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "${error.message}", Toast.LENGTH_SHORT).show()
                Log.d(" ERROR ", "${error.message}")
                Toast.makeText(this, "Error en la URL", Toast.LENGTH_SHORT).show()
            }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}
