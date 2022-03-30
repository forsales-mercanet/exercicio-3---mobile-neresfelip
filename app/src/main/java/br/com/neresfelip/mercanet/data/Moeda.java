package br.com.neresfelip.mercanet.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import br.com.neresfelip.mercanet.data.local.Tabela;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(tableName = Tabela.TABLE_NAME)
@Data @NoArgsConstructor
public class Moeda implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Tabela.COLUMN_CODIGO)
    private int codigo;

    @ColumnInfo(name = Tabela.COLUMN_DESCRICAO)
    private String descricao;

    @ColumnInfo(name = Tabela.COLUMN_ABREVIATURA)
    private String abreviatura;

    @ColumnInfo(name = Tabela.COLUMN_COTACAO)
    private float cotacao;

}
