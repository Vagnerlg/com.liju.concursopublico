package ggtec.lei_concursospublicos.Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import ggtec.lei_concursospublicos.Eventos.OnPositivoString;
import ggtec.lei_concursospublicos.R;

/**
 * Created by Vagner on 09/01/2016.
 */
public class SetString extends DialogFragment{

    OnPositivoString listerner;
    String title = "";
    String base = "";

    public void setListerner(OnPositivoString listerner){
        this.listerner = listerner;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setBase(String base){
        this.base = base;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        FrameLayout root = (FrameLayout) inflater.inflate(R.layout.dialogo_set_string, null);
        final android.widget.EditText textView = (android.widget.EditText) root.findViewById(R.id.edit_text);
        textView.setText(base);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(root)
                .setTitle("Alterar "+title)
                .setPositiveButton(R.string.alterar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(listerner != null){
                            listerner.onClick(textView.getText().toString().trim());
                        }
                    }
                })
                .setNegativeButton(R.string.voltar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
