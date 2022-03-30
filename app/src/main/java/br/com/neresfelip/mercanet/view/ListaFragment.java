package br.com.neresfelip.mercanet.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import br.com.neresfelip.mercanet.R;
import br.com.neresfelip.mercanet.data.Moeda;
import br.com.neresfelip.mercanet.databinding.FragmentListaBinding;
import br.com.neresfelip.mercanet.view.adapters.MoedaAdapter;
import br.com.neresfelip.mercanet.view_model.ListaViewModel;

public class ListaFragment extends Fragment implements MoedaAdapter.Click {

    /** Este é o fragmento que exibe as listas de moedas */

    private FragmentListaBinding binding;

    private ListaViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentListaBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(ListaFragment.this).get(ListaViewModel.class);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView listMoeda = binding.rvListMoeda;
        listMoeda.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));

        /** Arquitetura MVVM */
        /** Definindo um observador para uma lista de moedas que receberá da camada ViewModel */
        viewModel.getListMoeda().observe(getViewLifecycleOwner(), moedas -> {
            binding.tvListaVazia.setVisibility(moedas.size() > 0 ? View.GONE : View.VISIBLE);
            listMoeda.setAdapter(new MoedaAdapter(moedas, this));
        });

        binding.btAddMoeda.setOnClickListener(v ->
                NavHostFragment.findNavController(ListaFragment.this)
                        .navigate(R.id.action_ListaFragment_to_CadastroFragment)
        );

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(Moeda moeda) {
        /** Programei o fragmento para escutar os clicks no adapter da RecyclerView para ele quem realizar a ação */

        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.arg_name), moeda.getCodigo());

        NavHostFragment.findNavController(ListaFragment.this)
                .navigate(R.id.action_ListaFragment_to_CadastroFragment, bundle);

    }

}