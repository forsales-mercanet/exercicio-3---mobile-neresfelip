package br.com.neresfelip.mercanet.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.com.neresfelip.mercanet.data.Moeda;

@Database(entities = {Moeda.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    /**
     * Esta classe cria a conexão com o banco de dados interno
     * */

    private static AppDatabase instance;

    public static AppDatabase getDatabase(Context context) {

        if(instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "mercanet")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;

    }

    public abstract MoedaDAO moedaDao();
}
