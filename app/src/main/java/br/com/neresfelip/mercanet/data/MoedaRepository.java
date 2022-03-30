package br.com.neresfelip.mercanet.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.neresfelip.mercanet.data.local.AppDatabase;
import br.com.neresfelip.mercanet.data.local.MoedaDAO;
import br.com.neresfelip.mercanet.utils.TaskRunner;

public class MoedaRepository {

    /** Na arquitetura MVVM, eu utilizo esta camada de repositório para fazer a conexão entre o ViewModel
     * com o banco de dados interno ou remoto.
     * Como este app conta apenas com a conexão local, então basicamente ela repete os comandos dos viewModels
     * */

    private final MoedaDAO moedaDAO;

    public MoedaRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        moedaDAO = db.moedaDao();
    }

    public LiveData<List<Moeda>> getAllMoedas() {
        return moedaDAO.getAll();
    }

    public Moeda getMoeda(int codigo) {
        return moedaDAO.get(codigo);
    }

    public void insert(Moeda moeda) {
        AsyncTask.execute(()-> moedaDAO.insert(moeda));
    }

    public void update(Moeda moeda) {
        AsyncTask.execute(()-> moedaDAO.update(moeda));
    }

    public void delete(Moeda moeda) {
        AsyncTask.execute(()-> moedaDAO.delete(moeda));
    }

}
