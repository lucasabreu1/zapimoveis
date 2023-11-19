package com.example.zapimoveis

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zapimoveis.adapter.ImovelAdapter
import com.example.zapimoveis.model.Imovel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class MainActivity : AppCompatActivity() {

    private lateinit var dbRef: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private lateinit var rcImoveis : RecyclerView
    private lateinit var adapterImoveis : ImovelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.imoveis)

        dbRef = Firebase.firestore
        storage = Firebase.storage

        setRecyclerViewSettings()
        setRecyclerViewData()

    }

    private fun setRecyclerViewData(){

        GlobalScope.launch (Dispatchers.IO){

            try {
                var imoveis = ArrayList<Imovel>()
                var documents = dbRef
                    .collection("Imoveis")
                    .get()
                    .await().documents

                for(document in documents)
                {
                    var imovel = document.toObject<Imovel>()
                    if (imovel != null) {
                        imoveis.add(imovel)
                    }

                }
                withContext(Dispatchers.Main){
                    adapterImoveis = ImovelAdapter(imoveis, storage)
                    rcImoveis.adapter = adapterImoveis
                }

            } catch (e: Exception){

                Log.d("MainActivity", e.message.toString())
            }
        }
    }

    fun setRecyclerViewSettings(){

        rcImoveis = findViewById(R.id.lvImoveis)
        rcImoveis.layoutManager = LinearLayoutManager(this)
        rcImoveis.setHasFixedSize(true)
        var divider = DividerItemDecoration(this, RecyclerView.VERTICAL)
        divider.setDrawable(resources.getDrawable(R.drawable.imovel_divider))
        rcImoveis.addItemDecoration(divider)
    }

}