package br.com.neresfelip.mercanet.view_model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.com.neresfelip.mercanet.data.Moeda;
import br.com.neresfelip.mercanet.data.MoedaRepository;

public class ListaViewModel extends AndroidViewModel {

    /**
     * Este é o ViewModel da tela de listagem de registro, onde acontece ele trará os dados de um repositório
     * e disponibilizará para a camada de View (ListaFragment)
     * */

    private final MoedaRepository repository;
    private final LiveData<List<Moeda>> listMoedaLiveData;

    private final MutableLiveData<Moeda> moedaLiveData = new MutableLiveData<>();

    public ListaViewModel(@NonNull Application application) {
        super(application);
        repository = new MoedaRepository(application);
        listMoedaLiveData = repository.getAllMoedas();
    }

    public LiveData<List<Moeda>> getListMoeda() {
        return listMoedaLiveData;
    }

}
