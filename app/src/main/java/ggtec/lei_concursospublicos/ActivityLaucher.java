package ggtec.lei_concursospublicos;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import ggtec.lei_concursospublicos.api_antiga.Inicial;
import ggtec.lei_concursospublicos.api_antiga.Usuario;

public class ActivityLaucher extends AppCompatActivity {

    private ImageView imageView;
    private ProgressBar progressBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicializar();
        progressBar.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        new Inicial(getBaseContext()).versao(new Inicial.OnResp() {
            @Override
            public void resp(Integer resp) {
                progressBar.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                if (Usuario.getInstance(getBaseContext()).isCadastrado()) {
                    imageView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getBaseContext(), ActivityMain.class);
                            startActivity(intent);
                        }
                    }, 2000);
                } else {
                    imageView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getBaseContext(), ActivityLogin.class);
                            startActivity(intent);
                        }
                    }, 2000);
                }
            }
        });
    }

    private void inicializar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        setContentView(R.layout.layout_laucher);
        imageView = (ImageView) findViewById(R.id.logo_laucher);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        textView = (TextView) findViewById(R.id.texto);
    }


    @Override
    public void onStop(){
        super.onStop();
        finish();
    }
}