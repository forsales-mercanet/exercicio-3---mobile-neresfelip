package br.com.neresfelip.mercanet.view.view_utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class CheckAlteracaoCampos implements TextWatcher {

    /** esta classe serve para verificar se o usuário alterou qualquer valor nos campos
     * de um registro salvo na memória, para então exibir o botão de salvar (caso tenha alterado)
     * ou esconder novamente (caso o usuário tenha voltado os mesmos valores que o registro tinha
     * originalmente)
     * */

    private final EditText[] campos;
    private final String[] valoresOriginais;

    private final Alteracao alteracao;

    public CheckAlteracaoCampos(Alteracao alteracao, EditText... campos) {
        this.campos = campos;
        this.alteracao = alteracao;

        this.valoresOriginais = new String[campos.length];
        for(int i=0; i<campos.length; i++) {
            campos[i].addTextChangedListener(this);
            valoresOriginais[i] = campos[i].getText().toString();
        }

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        for(int i=0; i<campos.length; i++) {
            if(!campos[i].getText().toString().equals(valoresOriginais[i])) {
                alteracao.result(true);
                return;
            }
        }

        alteracao.result(false);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {}

    public interface Alteracao {
        void result(boolean existeAlteracao);
    }

}
