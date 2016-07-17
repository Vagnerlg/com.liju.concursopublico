package ggtec.lei_concursospublicos.Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import ggtec.lei_concursospublicos.ActivityLogin;
import ggtec.lei_concursospublicos.R;

/**
 * Created by Vagner on 26/02/2016.
 */
public class DialogoTtsGoogle extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("O Player de lei não encontrou o Sintetizador de voz do Google para ler a lei.\nÉ necessário instalar antes de proseguir.")
                .setTitle("Sintetizador de voz")
                .setIcon(R.drawable.ic_info_black_18dp)
                .setNegativeButton(R.string.voltar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                })
                .setPositiveButton(R.string.btn_faca_cadastro, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String appPackageName = "com.google.android.tts";
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}
