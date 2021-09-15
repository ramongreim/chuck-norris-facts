package com.example.chucknorris_stone.ui.main

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MainRepository {

    fun getFilmes(callback:(filmes: List<Filme>) -> Unit){
        Thread(Runnable {//thread do java
            Thread.sleep(3000)
            callback.invoke(//retorno da minha lista será feita pelo callback
                listOf(
                    Filme(1, "Título 01"),
                    Filme(2, "Título 02")
                )

            )

        }).start()//obrigatorio para ele poder executar a nossa thread
        //aqui nao posso dar return
    }

    suspend fun getFilmesCoroutines(): List<Filme> {
        return withContext(Dispatchers.Default){//Com o courotines eu posso retornar
            delay(3000)
            listOf(
                Filme(1, "Título 01"),
                Filme(2, "Título 02")
            )

        }
    }
}