package ggtec.lei_concursospublicos.Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import ggtec.lei_concursospublicos.R;
import ggtec.lei_concursospublicos.api_antiga.Config;

/**
 * Created by Vagner on 14/01/2016.
 */
public class DialogoFonte extends DialogFragment {

    private DialogInterface.OnClickListener listener;
    private int fonteNova = 0;
    private TextView novo;
    //fonte versao 1 de 10 a 24
    //fonte versao 2 de 14 a 26
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        FrameLayout root = (FrameLayout) inflater.inflate(R.layout.dialogo_fonte, null);
        TextView atual = (TextView) root.findViewById(R.id.atual);
        novo = (TextView) root.findViewById(R.id.novo);

        atual.setTextSize((float) Config.getInstance(getContext()).getFonte());
        SeekBar seekBar = (SeekBar) root.findViewById(R.id.seekBar);
        fonteNova = Config.getInstance(getContext()).getFonte();
        seekBar.setProgress(parseFonteForProgress(fonteNova));
        novo.setTextSize((float)fonteNova);
        atual.setTextSize((float)fonteNova);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                parseProgressForFonte(progress);
                novo.setTextSize((float) fonteNova);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        builder.setView(root)
                .setTitle("Tamanho da fonte")
                .setPositiveButton("Confirmar", listener)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }

    public void setListerner(DialogInterface.OnClickListener onClickListener) {
        this.listener = onClickListener;
    }

    public int getFonteNova(){
        return this.fonteNova;
    }

    private void parseProgressForFonte(int progress){
        if (progress == 0) {
            fonteNova = 14;
        }
        if (progress == 1) {
            fonteNova = 16;
        }
        if (progress == 2) {
            fonteNova = 18;
        }
        if (progress == 3) {
            fonteNova = 20;
        }
        if (progress == 4) {
            fonteNova = 22;
        }
        if (progress == 5) {
            fonteNova = 24;
        }
        if (progress == 6) {
            fonteNova = 26;
        }
    }

    private int parseFonteForProgress(int fonteNova){
        int progress = 0;
        if (fonteNova == 14) {
            progress =0;
        }
        if (fonteNova == 16) {
            progress = 1;
        }
        if (fonteNova == 18) {
            progress = 2;
        }
        if (fonteNova == 20) {
            progress = 3;
        }
        if (fonteNova == 22) {
            progress = 4;
        }
        if (fonteNova == 24) {
            progress = 5;
        }
        if (fonteNova == 26) {
            progress = 6;
        }
        return progress;
    }
}