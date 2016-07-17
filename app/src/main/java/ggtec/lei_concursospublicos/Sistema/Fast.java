package ggtec.lei_concursospublicos.Sistema;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import ggtec.lei_concursospublicos.ActivityLogin;
import ggtec.lei_concursospublicos.R;

/**
 * Created by Vagner on 17/01/2016.
 */
public class Fast {

    public static void configInfoSemNet(View conteiner){
        TextView infoTitulo = (TextView) conteiner.findViewById(R.id.info_titulo);
        TextView infoMsg = (TextView) conteiner.findViewById(R.id.info_mensagem);
        infoTitulo.setText(R.string.info_titulo_sem_net);
        infoMsg.setText(R.string.info_sem_net);
    }

    public static void configInfoTimeOut(View conteiner){
        TextView infoTitulo = (TextView) conteiner.findViewById(R.id.info_titulo);
        TextView infoMsg = (TextView) conteiner.findViewById(R.id.info_mensagem);
        infoTitulo.setText(R.string.info_titulo_time_out);
        infoMsg.setText(R.string.info_time_out);
    }

    public static void configInfoErroGeral(View conteiner){
        TextView infoTitulo = (TextView) conteiner.findViewById(R.id.info_titulo);
        TextView infoMsg = (TextView) conteiner.findViewById(R.id.info_mensagem);
        infoTitulo.setText(R.string.info_titulo_erro_geral);
        infoMsg.setText(R.string.info_erro_geral);
    }

    public static void configInfoCadastro(final Activity activity){
        FrameLayout conteiner = (FrameLayout) activity.findViewById(R.id.info_conteiner);
        conteiner.setVisibility(View.VISIBLE);
        TextView infoTitulo = (TextView) activity.findViewById(R.id.info_titulo);
        TextView infoMsg = (TextView) activity.findViewById(R.id.info_mensagem);
        TextView infoBtn = (TextView) activity.findViewById(R.id.info_btn);
        ImageView imageView = (ImageView) conteiner.findViewById(R.id.info_ilustration);
        imageView.setImageResource(R.drawable.ilustration_cadastro);
        infoBtn.setVisibility(View.VISIBLE);
        infoTitulo.setText(R.string.info_titulo_cadatro);
        infoMsg.setText(R.string.info_cadatro);
        infoBtn.setText(R.string.btn_faca_cadastro);
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ActivityLogin.class);
                intent.putExtra(ActivityLogin.KEY_TIPO, ActivityLogin.CADASTRO);
                activity.startActivity(intent);
            }
        });
    }

    public static void configInfoCadastroFragment(final Fragment activity, final Context context, View root){
        FrameLayout conteiner = (FrameLayout) root.findViewById(R.id.info_conteiner);
        conteiner.setVisibility(View.VISIBLE);
        TextView infoTitulo = (TextView) root.findViewById(R.id.info_titulo);
        TextView infoMsg = (TextView) root.findViewById(R.id.info_mensagem);
        TextView infoBtn = (TextView) root.findViewById(R.id.info_btn);
        ImageView imageView = (ImageView) conteiner.findViewById(R.id.info_ilustration);
        imageView.setImageResource(R.drawable.ilustration_cadastro);
        infoBtn.setVisibility(View.VISIBLE);
        infoTitulo.setText(R.string.info_titulo_cadatro);
        infoMsg.setText(R.string.info_cadatro);
        infoBtn.setText(R.string.btn_faca_cadastro);
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ActivityLogin.class);
                intent.putExtra(ActivityLogin.KEY_TIPO,ActivityLogin.CADASTRO);
                activity.startActivity(intent);
            }
        });
    }

    public static void intentCadastro(Activity context){
        Intent intentCadastro = new Intent(context,ActivityLogin.class);
        intentCadastro.putExtra(ActivityLogin.KEY_TIPO, ActivityLogin.CADASTRO);
        context.startActivity(intentCadastro);
    }

    public static void configInfo(View conteiner,String mensagem){
        FrameLayout root = (FrameLayout) conteiner.findViewById(R.id.info_conteiner);
        root.setVisibility(View.VISIBLE);
        TextView infoTitulo = (TextView) conteiner.findViewById(R.id.info_titulo);
        TextView infoMsg = (TextView) conteiner.findViewById(R.id.info_mensagem);
        infoTitulo.setVisibility(View.GONE);
        infoMsg.setText(mensagem);
    }
}
