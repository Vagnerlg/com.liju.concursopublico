package ggtec.lei_concursospublicos.Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.LinearLayout;
import android.widget.TextView;

import ggtec.lei_concursospublicos.R;

/**
 * Created by Vagner on 05/02/2016.
 */
public class DialogoInfoLei extends DialogFragment {

    private String tit = null;
    private String desc = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LinearLayout root = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.dialogo_info_lei, null);
        TextView titulo = (TextView) root.findViewById(R.id.info_titulo);
        TextView descricao = (TextView) root.findViewById(R.id.info_descricao);
        if(tit != null){
            titulo.setText(tit);
        }
        if(desc != null){
            descricao.setText(desc);
        }
        builder.setView(root)
                .setNegativeButton("FECHAR", null);
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void setTitulo(String tit){
        this.tit = tit;
    }

    public void setDescricao(String desc){
        this.desc = desc;
    }
}