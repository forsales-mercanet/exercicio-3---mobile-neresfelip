package br.com.neresfelip.mercanet.view.view_utils;

import android.view.View;
import android.widget.EditText;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import br.com.neresfelip.mercanet.R;
import br.com.neresfelip.mercanet.data.Moeda;
import br.com.neresfelip.mercanet.view_model.CadastroViewModel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CodigoAlteracaoListener implements View.OnFocusChangeListener {

    /** Esta classe verifica se o usuário altera o valor no campo código para determinar que, se ele
     *  digitar um código de um registro que NÃO existe no banco de dados interno, ele irá inserir um novo registro.
     *  Se ele digitar um código que já EXISTE no banco de dados interno, ele irá carregar esse registro e determinar que
     *  qualquer alteração que o usuário fizer nos campos dele é uma edição.
     * */

    private final CadastroViewModel viewModel;

    /* ele traz esta variável apenas para verificar o usuário abriu o fragmento em forma de inserção de novo registro
    * (se ela estiver nula) ou se abriu em forma de edição de registro (se ela vier com os dados deste registro), para
    * então ele exibir a mensagem correta na tela no AlertDialog lá em baixo
    * */
    private final Moeda moeda;

    private boolean onBackPressed;

    @Override
    public void onFocusChange(View view, boolean hasFocus) {

        EditText etCodigo = (EditText) view;

        String txt = etCodigo.getText().toString();

        if (hasFocus) {
            //correção para evitar que ela chame o AlertDialog quando o usuário sai da tela de cadastro e volta para a tela inicial
            prepareOnBackPress(((FragmentActivity) view.getContext()));
        } else if (!txt.isEmpty() && !onBackPressed) {

            int codigoDigitado = Integer.parseInt(txt);

            viewModel.getMoeda(codigoDigitado, moedaTemp -> {

                if (moeda != null) {

                    if (moedaTemp == null) {
                        alertDialog(R.string.msg_alteracao_cod_novo_reg, null, etCodigo);
                    } else if (moedaTemp.getCodigo() != moeda.getCodigo()) {
                        alertDialog(R.string.msg_alteracao_cod_edit_reg, moedaTemp, etCodigo);
                    }

                } else if (moedaTemp != null) {
                    alertDialog(R.string.msg_codigo_existe, moedaTemp, etCodigo);
                }

            });

        }

    }

    private void alertDialog(int resMsg, Moeda moedaTemp, EditText etCodigo) {

        new AlertDialog.Builder(etCodigo.getContext())
                .setMessage(resMsg)
                .setPositiveButton(R.string.continuar, (dialog, i) -> viewModel.getMoedaLiveData().setValue(moedaTemp))
                .setNegativeButton(R.string.cancelar, (dialog, i) -> dialog.cancel())
                .setOnCancelListener(dialogInterface -> {

                    if (moeda != null)
                        etCodigo.setText(String.valueOf(moeda.getCodigo()));
                    else {
                        etCodigo.setText(null);
                        etCodigo.requestFocus();
                    }

                })
                .show();

    }

    private void prepareOnBackPress(FragmentActivity activity) {

        //correção para evitar que ela chame o AlertDialog quando o usuário sai da tela de cadastro e volta para a tela inicial

        OnBackPressedDispatcher onBackPressedDispatcher = activity.getOnBackPressedDispatcher();
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {

            @Override
            public void handleOnBackPressed() {
                onBackPressed = true;
                setEnabled(false);
                activity.onBackPressed();
            }

        };

        onBackPressedDispatcher.addCallback(onBackPressedCallback);

    }

}
