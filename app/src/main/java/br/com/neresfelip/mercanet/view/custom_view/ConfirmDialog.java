package br.com.neresfelip.mercanet.view.custom_view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import br.com.neresfelip.mercanet.R;

public class ConfirmDialog extends AlertDialog.Builder {

    /** Meu Alertdialog customizado no botão de salvar registro e excluir registro com um Toast
     * automático e a ação de sair da tela de cadastro também automática */

    public ConfirmDialog(Context context, int resPergunta, int resMsgConfirm, Acao acao) {
        super(context);

        setMessage(resPergunta);
        setPositiveButton(R.string.sim, (dialog, which) -> {
            acao.confirmAcao();
            Toast.makeText(context, resMsgConfirm, Toast.LENGTH_SHORT).show();
            ((Activity) context).onBackPressed();
        });
        setNegativeButton(R.string.cancelar, null);
        show();

    }

    public interface Acao {
        void confirmAcao();
    }

}
