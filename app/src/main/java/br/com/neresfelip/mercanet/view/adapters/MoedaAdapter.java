package br.com.neresfelip.mercanet.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.neresfelip.mercanet.R;
import br.com.neresfelip.mercanet.data.Moeda;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MoedaAdapter extends RecyclerView.Adapter<MoedaAdapter.CurrencyHolder> {

    private final List<Moeda> listMoeda;
    private final Click click;

    @Override @NonNull
    public CurrencyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CurrencyHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_moeda, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyHolder holder, int position) {
        holder.bind(listMoeda.get(position));
    }

    @Override
    public int getItemCount() {
        return listMoeda.size();
    }

    class CurrencyHolder extends RecyclerView.ViewHolder {

        private final TextView tvDescricao;

        public CurrencyHolder(@NonNull View itemView) {
            super(itemView);
            tvDescricao = itemView.findViewById(R.id.tv_moeda_descricao);
        }

        void bind(Moeda moeda) {
            tvDescricao.setText(moeda.getDescricao());
            itemView.setOnClickListener(view -> click.onItemClick(moeda));
        }

    }

    public interface Click {
        void onItemClick(Moeda moeda);
    }

}
