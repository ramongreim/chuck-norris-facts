package com.example.chucknorris_stone.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.chucknorris_stone.R
import kotlinx.android.synthetic.main.main_fragment.*

//cria o view model
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
                this,
                MainViewModel.MainViewModelFactory(MainRepository()) //Cria uma instancia do meu repositorio e injeta no nosso view model

        ).get(MainViewModel::class.java)

        viewModel.filmesLiveData.observe(viewLifecycleOwner, Observer{filmes-> //callback com a lista de filmes
            //agora temos uma lista de filmes retornada no nosso fragmento

            textViewFilmes.text = filmes[0].titulo



        })
        //em seguida precisamos fazer toda a chamada para que ele possa chegar ao nosso repositorio
        viewModel.getFilmesCoroutines()
        // TODO: Use the ViewModel
    }

}