package br.com.neresfelip.mercanet.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import br.com.neresfelip.mercanet.data.Moeda;
import br.com.neresfelip.mercanet.data.MoedaRepository;
import br.com.neresfelip.mercanet.utils.TaskRunner;

public class CadastroViewModel extends AndroidViewModel {

    /**
     * Este é o ViewModel da tela de cadastro, onde acontece ele trará os dados de um repositório
     * e disponibilizará para a camada de View (CadastroFragment)
     * */

    private final MoedaRepository repository;
    private final MutableLiveData<Moeda> moedaLiveData;

    public CadastroViewModel(@NonNull Application application) {
        super(application);
        repository = new MoedaRepository(application);
        moedaLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Moeda> getMoedaLiveData() {
        return moedaLiveData;
    }

    public MutableLiveData<Moeda> updateLiveDataMoeda(int codigo) {
        getMoeda(codigo, moedaLiveData::setValue);
        return moedaLiveData;
    }

    public void getMoeda(int codigo, MoedaCallback callback) {

        new TaskRunner<Moeda>() {

            @Override
            protected Moeda onBackground() {
                return repository.getMoeda(codigo);
            }

            @Override
            protected void onForeground(Moeda moeda) { callback.callback(moeda); }

        }.executeAsync();

    }

    public void insertData(Moeda moeda) {
        repository.insert(moeda);
    }

    public void removeData(Moeda moeda) {
        repository.delete(moeda);
    }

    public void updateData(Moeda moeda) {
        repository.update(moeda);
    }

    public interface MoedaCallback {
        void callback(Moeda moeda);
    }

}
