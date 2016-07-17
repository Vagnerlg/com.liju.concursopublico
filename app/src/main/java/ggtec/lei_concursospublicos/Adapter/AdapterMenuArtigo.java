package ggtec.lei_concursospublicos.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ggtec.lei_concursospublicos.R;
import ggtec.lei_concursospublicos.Sistema.ItemLei;

/**
 * Created by Vagner on 20/12/2015.
 */
public class AdapterMenuArtigo extends RecyclerView.Adapter<AdapterMenuArtigo.ViewHolder> {
    public ArrayList<ItemLei> list;

    public AdapterMenuArtigo(ArrayList<ItemLei> lista){
        list = lista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_artigo,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(list != null) {
            TextView mNome = (TextView) holder.cardView.findViewById(R.id.texto_menu_titulo);
            LinearLayout boxMenuArtigo = (LinearLayout) holder.cardView.findViewById(R.id.box_menu_artigo);
            String nome = "" + list.get(position).getTexto();
            nome = nome.replace("\n","").replace("\r","");
            mNome.setText(nome);
            boxMenuArtigo.setTag(list.get(position).getID());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout cardView;

        public ViewHolder(LinearLayout cv){
            super(cv);
            cardView = cv;
        }
    }
}
