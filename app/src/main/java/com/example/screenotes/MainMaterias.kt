package com.example.screenotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson

class MainMaterias : AppCompatActivity() {

    val URL = "http://192.168.1.68/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materias)

        var listarMaterias: ArrayList<DataMat>? = null
        val listMat = ArrayList<DataMat>()
        var lista: RecyclerView? = null
        var layoutManager: RecyclerView.LayoutManager? = null
        val wsURL = URL + "screenotes/getMaterias.php"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, wsURL, null,
            Response.Listener { response ->
                val testJson = response.getJSONArray("tests")
                listarMaterias = ArrayList()
                for (i in 0 until testJson.length()) {
                    val id_materia = testJson.getJSONObject(i).getString("id_materia")
                    val nom_materia = testJson.getJSONObject(i).getString("nom_materia")
                    listarMaterias?.add(DataMat(id_materia, nom_materia))
                    listMat.add(DataMat(id_materia, nom_materia))
                }
                lista = findViewById(R.id.listaMaterias)
                lista?.setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this)
                lista?.layoutManager = layoutManager
                var adaptador = AdaptadorCustomMateria(listMat, object : ClickListenerMat {
                    override fun onClick(view: View, index: Int) {
                        Toast.makeText(applicationContext, "Mat" + listMat.get(index), Toast.LENGTH_SHORT).show()
                    }
                })
                lista?.adapter = adaptador
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error : " + error.message.toString(), Toast.LENGTH_LONG).show()
                Log.d("Notas : ", error.message.toString())
            }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}
