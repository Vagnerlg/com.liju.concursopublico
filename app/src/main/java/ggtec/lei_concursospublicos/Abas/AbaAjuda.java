package ggtec.lei_concursospublicos.Abas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import ggtec.lei_concursospublicos.R;

/**
 * Created by Vagner on 25/02/2016.
 */
public class AbaAjuda extends Fragment {

    public static final String KEY_TITULO = "titulo";
    public static final String KEY_SUBTITULO = "subtitulo";
    public static final String KEY_IMAGEM = "imagem";
    public static final String KEY_DESCRICAO = "descricao";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        FrameLayout root = (FrameLayout) inflater.inflate(R.layout.aba_ajuda, null);
        TextView titulo = (TextView) root.findViewById(R.id.dica_titulo);
        TextView subtitulo = (TextView) root.findViewById(R.id.dica_subtiutlo);
        ImageView imagem = (ImageView) root.findViewById(R.id.dica_imagem);
        TextView descricao = (TextView) root.findViewById(R.id.dica_descricao);
        if(args.getString(KEY_TITULO) != null){
            titulo.setText(args.getString(KEY_TITULO));
        }else {
            titulo.setVisibility(View.GONE);
        }
        if(args.getString(KEY_SUBTITULO) != null){
            subtitulo.setText(args.getString(KEY_SUBTITULO));
        }else {
            subtitulo.setVisibility(View.GONE);
        }
        if(args.getInt(KEY_IMAGEM, -1) != -1){
            imagem.setImageResource(args.getInt(KEY_IMAGEM, -1));
        }else {
            imagem.setVisibility(View.GONE);
        }
        if(args.getString(KEY_DESCRICAO) != null){
            descricao.setText(args.getString(KEY_DESCRICAO));
        }else {
            descricao.setVisibility(View.GONE);
        }
        return root;
    }
}