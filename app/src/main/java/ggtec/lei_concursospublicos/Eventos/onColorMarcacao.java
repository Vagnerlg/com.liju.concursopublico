package ggtec.lei_concursospublicos.Eventos;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import ggtec.lei_concursospublicos.Outros.PainelEdicao;

/**
 * Created by Vagner on 26/12/2015.
 */
public class onColorMarcacao implements View.OnTouchListener {

    Context context;

    public onColorMarcacao(Context con){
        context = con;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(PainelEdicao.getInstanceBasic().isShowMarcacao()) {
            LinearLayout view = (LinearLayout) v;
            int posX = (int) event.getX();
            int totalPalavras = view.getChildCount();
            int contPalavras = 0;
            int location = 0;
            Boolean fim = false;
            while (location < posX && !fim) {
                if (contPalavras < totalPalavras) {
                    location = location + view.getChildAt(contPalavras).getWidth();
                    if (location < posX) {
                        contPalavras++;
                    }
                } else {
                    fim = true;
                }
            }
            if (!fim && contPalavras < totalPalavras) {
                location = location - view.getChildAt(contPalavras).getWidth();
                LinearLayout palavra = (LinearLayout) view.getChildAt(contPalavras);
                int totalLetras = palavra.getChildCount();
                int contLetras = 0;
                while (location < posX) {
                    if (contLetras < totalLetras) {
                        location = location + palavra.getChildAt(contLetras).getWidth();
                        if (location < posX) {
                            contLetras++;
                        } else {
                            Log.d("MOTION", location + " " + fim);
                            TextView textView = (TextView) palavra.getChildAt(contLetras);
                            textView.setBackgroundColor(context.getResources().getColor(PainelEdicao.getInstanceBasic().getColorSelect()));
                            textView.setTag(
                                    PainelEdicao.getInstanceBasic().getColorSigla(
                                        PainelEdicao.getInstanceBasic().getColorSelect()
                                    )
                            );
                        }
                    }
                }
            }
        }
        return true;
    }

}
