package ggtec.lei_concursospublicos;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ActivityTssAjuda extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        FrameLayout root = (FrameLayout) findViewById(R.id.info_conteiner);
        root.setVisibility(View.VISIBLE);
        TextView titulo = (TextView) findViewById(R.id.info_titulo);
        titulo.setVisibility(View.VISIBLE);
        titulo.setText("Sintetizador de Voz");
        TextView descricao = (TextView) findViewById(R.id.info_mensagem);
        descricao.setVisibility(View.VISIBLE);
        descricao.setText("Para poder ouvir a lei é necessário ter instalado o aplicativo de sintetizador e voz do Google que não foi encontrado. Você gotaria de instalar?");
        TextView btn = (TextView) findViewById(R.id.info_btn);
        btn.setVisibility(View.VISIBLE);
        btn.setText("Sim, quero instalar");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = "com.google.android.tts";
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                finish();
            }
        });
    }
}
