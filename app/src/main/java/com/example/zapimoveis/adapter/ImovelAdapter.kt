package com.example.zapimoveis.adapter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zapimoveis.R
import com.example.zapimoveis.model.Imovel
import com.example.zapimoveis.model.Item
import com.example.zapimoveis.utils.GlideApp
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class ImovelAdapter(
    private val dataSet: ArrayList<Imovel>,
    private val storage: FirebaseStorage
    ) :
    RecyclerView.Adapter<ImovelAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var tvImagem: ImageView
        var tvPrecoAluguel: TextView
        var tvPrecoCondominio: TextView
        var tvPrecoIptu: TextView
        var tvQtdQuartos: TextView
        var tvQtdBanheiros: TextView
        var tvQtdArea: TextView
        var tvQtdVagas: TextView
        var tvEndereco: TextView

        init {
            tvImagem = view.findViewById(R.id.tvImagem)
            tvPrecoAluguel = view.findViewById(R.id.tvPecoAluguel)
            tvPrecoCondominio = view.findViewById(R.id.tvPrecoCondominio)
            tvPrecoIptu = view.findViewById(R.id.tvPrecoIptu)
            tvQtdQuartos = view.findViewById(R.id.tvQtdQuartos)
            tvQtdBanheiros = view.findViewById(R.id.tvQtdBanheiros)
            tvQtdArea = view.findViewById(R.id.tvQtdArea)
            tvQtdVagas = view.findViewById(R.id.tvQtdVagas)
            tvEndereco = view.findViewById(R.id.tvEndereco)

        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.imovel, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val currentImovel = dataSet[position]

        //var primeiraImagem = currentImovel.imagensUrl?.get(0)
        var precoAluguel = currentImovel.precoAluguel;
        var precoCondominio = currentImovel.precoCondominio;
        var precoIptu = currentImovel.precoIptu;
        var qtdArea = currentImovel.area;

        if(precoAluguel == 0F)
            viewHolder.tvPrecoAluguel.visibility = View.GONE;
        else
            viewHolder.tvPrecoAluguel.text = String.format("R$ %s/mês", precoAluguel.toString())

        if(precoCondominio == 0F)
            viewHolder.tvPrecoCondominio.visibility = View.GONE;
        else
            viewHolder.tvPrecoCondominio.text = String.format("Cond. R$ %s", precoCondominio.toString())

        if(precoIptu == 0F)
            viewHolder.tvPrecoIptu.visibility = View.GONE;
        else
            viewHolder.tvPrecoIptu.text = String.format("IPTU R$ %s", precoIptu.toString())

        if(qtdArea == 0F)
            viewHolder.tvQtdArea.visibility = View.GONE;
        else
            viewHolder.tvQtdArea.text = String.format("%s m²",qtdArea.toString())

        formatQtdQuartos(currentImovel, viewHolder)
        formatQtdBanheiros(currentImovel, viewHolder)
        formatQtdVagas(currentImovel, viewHolder)
        formatEndereco(currentImovel, viewHolder)

        var imageTemp = currentImovel.imagens?.get(0)

        var storageReference = storage.reference.child(String.format("images/%s", imageTemp))

        GlideApp.with(viewHolder.tvImagem.context)
            .load(storageReference)
            .into(viewHolder.tvImagem)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    fun formatQtdQuartos(imovelAtual: Imovel, viewHolder: ViewHolder){

        var qtdQuartos = imovelAtual.qtdQuartos

        if (qtdQuartos == 0)
            viewHolder.tvQtdQuartos.visibility = View.GONE;

        else if (qtdQuartos > 1)
            viewHolder.tvQtdQuartos.text = String.format("%s quartos", imovelAtual.qtdQuartos.toString())

        else
            viewHolder.tvQtdQuartos.text = String.format("%s quarto", imovelAtual.qtdQuartos.toString())
    }

    fun formatQtdBanheiros(imovelAtual: Imovel, viewHolder: ViewHolder){

        var qtdBanheiros = imovelAtual.qtdBanheiros

        if (qtdBanheiros == 0)
            viewHolder.tvQtdBanheiros.visibility = View.GONE;

        else if (qtdBanheiros > 1)
            viewHolder.tvQtdBanheiros.text = String.format("%s banheiros", imovelAtual.qtdQuartos.toString())

        else
            viewHolder.tvQtdBanheiros.text = String.format("%s banheiro", imovelAtual.qtdBanheiros.toString())
    }

    fun formatQtdVagas(imovelAtual: Imovel, viewHolder: ViewHolder){

        var qtdVagas = imovelAtual.qtdVagas

        if (qtdVagas == 0)
            viewHolder.tvQtdVagas.visibility = View.GONE;

        else if (qtdVagas > 1)
            viewHolder.tvQtdVagas.text = String.format("%s vagas", imovelAtual.qtdVagas.toString())

        else
            viewHolder.tvQtdVagas.text = String.format("%s vaga", imovelAtual.qtdVagas.toString())
    }

    fun formatEndereco(imovelAtual: Imovel, viewHolder: ViewHolder){

        var bairro = imovelAtual.bairro
        var rua = imovelAtual.rua

        viewHolder.tvEndereco.text = String.format("%s - %s", rua, bairro)

    }
}