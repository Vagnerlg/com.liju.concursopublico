package ggtec.lei_concursospublicos.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ggtec.lei_concursospublicos.R;
import ggtec.lei_concursospublicos.api_antiga.Trecho;

/**
 * Created by Vagner on 20/12/2015.
 */
public class AdapterMenuTitulo extends RecyclerView.Adapter<AdapterMenuTitulo.ViewHolder> {
    public ArrayList<Trecho> list;
    private float dp;
    private Context context;

    public AdapterMenuTitulo(ArrayList<Trecho> lista,float dp,Context con){
        list = lista;
        this.dp = dp;
        context = con;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_titulo,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(list != null) {
            TextView mNome = (TextView) holder.cardView.findViewById(R.id.texto_menu_titulo);
            mNome.setTypeface(Typeface.DEFAULT,Typeface.NORMAL);
            if(list.get(position).getTipo() < 2){
                mNome.setPadding((int) (dp * 24), (int) (dp * 16), (int) (dp * 16), (int) (dp * 16));
                mNome.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                mNome.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
            }
            if(list.get(position).getTipo() == 2){
                mNome.setPadding((int) (dp * 24), (int) (dp * 16), (int) (dp * 16), (int) (dp * 16));
                mNome.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            }
            if(list.get(position).getTipo() == 3){
                mNome.setPadding((int)(dp *48),(int)(dp *16),(int)(dp *16),(int)(dp *16));
                mNome.setTextColor(context.getResources().getColor(R.color.textColorPrimary));
            }
            if(list.get(position).getTipo() == 4){
                mNome.setPadding((int)(dp *72),(int)(dp *16),(int)(dp *16),(int)(dp *16));
                mNome.setTextColor(context.getResources().getColor(R.color.textColorSecondary));
            }
            mNome.setText(list.get(position).getTexto());
            mNome.setTag(list.get(position).getID());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView cardView;

        public ViewHolder(TextView cv){
            super(cv);
            cardView = cv;
        }
    }
}
