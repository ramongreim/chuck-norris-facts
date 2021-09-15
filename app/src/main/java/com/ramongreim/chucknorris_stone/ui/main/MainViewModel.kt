package com.example.chucknorris_stone.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel (private val repository: MainRepository): ViewModel() {//Recebendo esse repositorio ja construido via injeção de dependencia através do construtor do nosso viewmodel
    // primeira coisa a se fazer é receber via injeção de dependencia o nosso repositorio (boa pratica)
    //val repository = MainRepository()  -     class MainViewModel : ViewModel() { - nao fazer isso
    /*
     Essa get filmes apos obter a lista de filmes vai acionar um live data do architeture components
     para quem tiver na escuta , nesse caso o main fragment, para poder mostrar essa lista de filmes
     em algum componente de view


     */
    val filmesLiveData = MutableLiveData<List<Filme>>()

    fun getFilmes() {
        repository.getFilmes { filmes ->  //itfilmes função lambda
            //essa funcao getfilmes é do repository simulando pedido a API com delay de 3 segundos
            filmesLiveData.postValue(filmes) // passando a lista de filmes para o livedata
            //.value = filmes entrega o resultado na thread que ele veio - se vier de um thread fora da thread principal ele vai executar  na threadprincipal
            //.postvalue entrega na thread principal no android

            //agora vamos ao fragmento escutar esse livedata para poder mostrar essa lista de filmes em algum componente

        }
    }

    fun getFilmesCoroutines(){
        CoroutineScope(Dispatchers.Main).launch {
            //so aceita função suspensa dentro de um escopo de coroutine
            val filmes = withContext(Dispatchers.Default) {//async. está sendo executado num thread default , thread que esta sendo criada pelo couroutine
                repository.getFilmesCoroutines()
            } //faço com que esse trecho de codigo ele suspende tudo o que for executado aqui será executado
            //em outra thread e minha thread principal vai ficar esperando esse trecho de codigo ser executado
            //e depois que for executado ele será retornado normalmente para minha variavel filmes
            //e o codigo será executado a partir da proxima linha

            //aqui abaixo ele será executado de forma sequencial sem necessidade de callback
            //agora podemos chamar o value pois estamos no contexto dessa coroutine que está sendo executado na thread princialp
            //
            filmesLiveData.value = filmes
        }
    }

    class MainViewModelFactory(
        private val repository: MainRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(repository) as T
        } //faz com o ViewModel Factory implemente um viewmodel provider factory

    }
    //Dessa forma cria-se um view model para que ele seja passado
}