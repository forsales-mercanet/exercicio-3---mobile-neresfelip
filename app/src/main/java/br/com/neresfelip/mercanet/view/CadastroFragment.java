package br.com.neresfelip.mercanet.view;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import br.com.neresfelip.mercanet.R;
import br.com.neresfelip.mercanet.databinding.FragmentCadastroBinding;
import br.com.neresfelip.mercanet.data.Moeda;
import br.com.neresfelip.mercanet.utils.Utils;
import br.com.neresfelip.mercanet.view.custom_view.ConfirmDialog;
import br.com.neresfelip.mercanet.view.view_utils.CheckAlteracaoCampos;
import br.com.neresfelip.mercanet.view.view_utils.CodigoAlteracaoListener;
import br.com.neresfelip.mercanet.view.view_utils.DecimalMask;
import br.com.neresfelip.mercanet.view_model.CadastroViewModel;

public class CadastroFragment extends Fragment {
    /** Este é o fragmento que exibe o formulário de cadastro */

    private FragmentCadastroBinding binding;
    private CadastroViewModel viewModel;

    private int codigo;

    private MenuItem btDeletar, btSalvar;

    private ConfirmDialog.Acao btSalvarConfirmAcao;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        Bundle args = getArguments();
        assert args != null;

        /** Este framento recebe o código da moeda (em integer) para buscar no banco de dados interno
         * a moeda gravada. Caso receba valor 0, ele entende que é um novo cadastro */
        codigo = args.getInt(getString(R.string.arg_name));

        binding = FragmentCadastroBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(CadastroFragment.this).get(CadastroViewModel.class);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        binding.etAbreviatura.setFilters(
                new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(5)}
        );

        binding.etCotacao.addTextChangedListener(new DecimalMask(binding.etCotacao));
        binding.etCotacao.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus) {
                binding.etCotacao.setSelection(binding.etCotacao.getText().length());
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_cadastro, menu);

        btDeletar = menu.getItem(0);
        btSalvar = menu.getItem(1);

        /** o ViewModel deste fragmento inicia após o OptionMenu ser criado, pois ele precisa
         * deles para realizar ação de exibir ou ocultar, ou definir a ação para o botão de salvar */
        viewModel.updateLiveDataMoeda(codigo).observe(CadastroFragment.this, moeda -> {
            setCamposFromMoeda(moeda);
            binding.etCodigo.setOnFocusChangeListener(new CodigoAlteracaoListener(viewModel, moeda));
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_salvar:

                if(validarCampos()) {

                    new ConfirmDialog(requireContext(),
                            R.string.save_reg_pergunta,
                            R.string.save_reg_toast_msg_confirm,
                            btSalvarConfirmAcao //a ação pode ser de inserir ou editar o cadastro, como o observer do viewmodel definiu
                    );

                }
                break;

            case R.id.action_apagar:

                new ConfirmDialog(requireContext(),
                        R.string.exclui_reg_pergunta,
                        R.string.exclui_reg_toast_msg_confirm,
                        () -> viewModel.removeData(getMoedaFromCampos())
                );

                break;

        }

        return true;

    }

    private boolean validarCampos() {

        boolean valido = true;

        /* Aqui define que o campo de código não pode ficar vazio, nem receber o valor 0 (pois este fragmento
        ao receber um código 0, entende que o usuário está cadastrando um novo registro)*/
        if(binding.etCodigo.getText().toString().isEmpty()) {
            binding.etCodigo.setError(getString(R.string.erro_campo_vazio));
            valido = false;
        } else if(Integer.parseInt(binding.etCodigo.getText().toString()) < 1) {
            binding.etCodigo.setError(getString(R.string.erro_campo_zero));
            valido = false;
        }

        if(binding.etDescricao.getText().toString().trim().isEmpty()) {
            binding.etDescricao.setError(getString(R.string.erro_campo_vazio));
            valido = false;
        }

        return valido;

    }

    // este método gera o objeto moeda de acordo com os valores definidos pelo usuário nos campos
    private Moeda getMoedaFromCampos() {

        Moeda moeda = new Moeda();

        moeda.setCodigo(Integer.parseInt(binding.etCodigo.getText().toString()));
        moeda.setDescricao(binding.etDescricao.getText().toString().trim());
        moeda.setAbreviatura(binding.etAbreviatura.getText().toString().trim());
        moeda.setCotacao(Float.parseFloat(binding.etCotacao.getText().toString()));

        return moeda;

    }

    private void setCamposFromMoeda(Moeda moeda) {

        if(moeda != null) {

            binding.tvTitulo.setText(R.string.editar_moeda);

            binding.etCodigo.setText(String.valueOf(moeda.getCodigo()));
            binding.etDescricao.setText(moeda.getDescricao());
            binding.etAbreviatura.setText(moeda.getAbreviatura());
            binding.etCotacao.setText(Utils.doubleToDecimalString(moeda.getCotacao()));

            btSalvarConfirmAcao = () -> viewModel.updateData(getMoedaFromCampos());
            btSalvar.setVisible(false);

            btDeletar.setVisible(true);

            /** esta classe serve para verificar se o usuário alterou qualquer valor nos campos
             * de um registro salvo na memória, para então exibir o botão de salvar (caso tenha alterado)
             * ou esconder novamente (caso o usuário tenha voltado os mesmos valores que o registro tinha
             * originalmente)
             * */
            new CheckAlteracaoCampos(
                    btSalvar::setVisible,
                    binding.etCodigo,
                    binding.etDescricao,
                    binding.etAbreviatura,
                    binding.etCotacao
            );

        } else {

            binding.tvTitulo.setText(R.string.nova_moeda);

            binding.etDescricao.setText(null);
            binding.etAbreviatura.setText(null);
            binding.etCotacao.setText("0.0");

            btSalvarConfirmAcao = () -> viewModel.insertData(getMoedaFromCampos());

            btDeletar.setVisible(false);

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}