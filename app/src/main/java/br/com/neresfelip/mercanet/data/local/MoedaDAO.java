package br.com.neresfelip.mercanet.data.local;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;

import java.util.List;

import br.com.neresfelip.mercanet.data.Moeda;

@Dao
public interface MoedaDAO {

    /** Interface com os comandos para carregar os dados do banco de dados para o repositório */

    @Insert
    void insert(Moeda moeda);

    @Query("SELECT * FROM "+ Tabela.TABLE_NAME + " ORDER BY "+Tabela.COLUMN_DESCRICAO)
    LiveData<List<Moeda>> getAll();

    @Update
    void update(Moeda moeda);

    @Delete
    void delete(Moeda moeda);

    @Query("SELECT * FROM "+ Tabela.TABLE_NAME + " WHERE "+Tabela.COLUMN_CODIGO+ " = :codigo")
    Moeda get(int codigo);

}
