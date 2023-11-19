package com.example.zapimoveis.model

data class Imovel(
    var area: Float = 0F,
    var bairro: String = "",
    var precoAluguel: Float = 0F,
    var precoCondominio: Float = 0F,
    var precoIptu: Float = 0F,
    var qtdBanheiros: Int = 0,
    var qtdQuartos: Int = 0,
    var qtdVagas: Int = 1,
    var rua: String = "",
    var imagens: ArrayList<String>? = null,
    var imagensUrl: ArrayList<String>? = ArrayList()
)