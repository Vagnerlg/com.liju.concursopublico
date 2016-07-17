package ggtec.lei_concursospublicos.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ggtec.lei_concursospublicos.R;
import ggtec.lei_concursospublicos.Eventos.onClickLei;
import ggtec.lei_concursospublicos.Sistema.ItemInfoLei;

/**
 * Created by Vagner on 24/11/2015.
 */
public class AdapterItemInfoLei extends RecyclerView.Adapter<AdapterItemInfoLei.ViewHolder> {

    Context context;
    ArrayList<ItemInfoLei> list = null;
    String origem;
    public static int TAG_ITEM_MENU = R.id.nome_lei ;
    public static int TAG_ORIGEM = R.id.numero_lei;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout cardView;
        public ViewHolder(LinearLayout cv){
            super(cv);
            cardView = cv;
        }
    }

    public AdapterItemInfoLei(Context con, ArrayList<ItemInfoLei> lista, String origem){
        context = con;
        list = lista;
        this.origem = origem;

    }

    @Override
    public AdapterItemInfoLei.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_lei,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(list != null) {

            TextView mNome = (TextView) holder.cardView.findViewById(R.id.nome_lei);
            TextView mNumero = (TextView) holder.cardView.findViewById(R.id.numero_lei);
            TextView mTipo = (TextView) holder.cardView.findViewById(R.id.tipo_lei);

            String nome = "" + list.get(position).getNome();
            nome = nome.replace("\n","").replace("\r","");
            String numero = "" + list.get(position).getNumAnoFormat();
            String tipo = ""+list.get(position).getTipoLeiHumano() + " número";

            if(list.get(position).getTabela().equalsIgnoreCase("cf")){
                numero = "de 1988";
                tipo = nome;
            }

            mNome.setText(nome);
            mNumero.setText(numero);
            mNumero.setTextColor(context.getResources().getColor(list.get(position).getColorGrupo()));
            mTipo.setText(tipo);

            holder.cardView.setTag(TAG_ITEM_MENU, list.get(position));
            holder.cardView.setTag(TAG_ORIGEM,origem);
            holder.cardView.setOnClickListener(new onClickLei(context));
        }
    }

    @Override
    public int getItemCount() {
        int resp = 0;
        if(list != null){
            resp = list.size();
        }

        return resp;
    }
}
