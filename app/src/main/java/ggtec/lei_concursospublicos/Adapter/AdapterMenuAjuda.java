package ggtec.lei_concursospublicos.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import ggtec.lei_concursospublicos.R;

/**
 * Created by Vagner on 27/02/2016.
 */
public class AdapterMenuAjuda extends RecyclerView.Adapter<AdapterMenuAjuda.ViewHolder> {

    String[] listaTexto = new String[]{
            "Como fazer um comentário na lei?",
            "Como fazer uma marcação na lei?",
            "Como ouvir a lei pelo Player de lei?",
            "Alternando entre modo de leitura e modo de edição.",
            "Acessando os menu de Títulos e de Artigos.",
            "Alterando o tamanho da fonte",
            "Pesquisando dentro da lei"};

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ViewHolder(TextView cv){
            super(cv);
            textView = cv;
        }
    }

    public AdapterMenuAjuda(){

    }

    @Override
    public AdapterMenuAjuda.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_ajuda,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView titulo = (TextView) holder.textView.findViewById(R.id.item_menu_ajuda_titulo);
        titulo.setText(listaTexto[position]);
        titulo.setTag(position);
    }

    @Override
    public int getItemCount() {
        int resp = 0;
        if(listaTexto != null){
            resp = listaTexto.length;
        }
        return resp;
    }
}