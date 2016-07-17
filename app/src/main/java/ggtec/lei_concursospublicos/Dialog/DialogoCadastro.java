package ggtec.lei_concursospublicos.Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import ggtec.lei_concursospublicos.ActivityLogin;
import ggtec.lei_concursospublicos.R;
import ggtec.lei_concursospublicos.Sistema.Internet;

/**
 * Created by Vagner on 17/01/2016.
 */
public class DialogoCadastro extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.info_cadatro)
                .setTitle(R.string.info_titulo_cadatro)
                .setIcon(R.drawable.ic_info_black_18dp)
                .setNegativeButton(R.string.voltar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                })
        .setPositiveButton(R.string.btn_faca_cadastro, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intentCadastro = new Intent(getContext(), ActivityLogin.class);
                intentCadastro.putExtra(ActivityLogin.KEY_TIPO,ActivityLogin.CADASTRO);
                startActivity(intentCadastro);
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}