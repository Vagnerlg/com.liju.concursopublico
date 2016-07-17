package ggtec.lei_concursospublicos;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import ggtec.lei_concursospublicos.Dialog.SetString;
import ggtec.lei_concursospublicos.Eventos.OnPositivoString;
import ggtec.lei_concursospublicos.Sistema.Fast;
import ggtec.lei_concursospublicos.Sistema.Usuario;

public class ActivityConta extends AppCompatActivity {

    TextView plano;
    TextView nome;
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_conta);
        nome = (TextView) findViewById(R.id.texto_nome);
        email = (TextView) findViewById(R.id.texto_email);
        plano = (TextView) findViewById(R.id.texto_plano);
        nome.setText(Usuario.getInstance(getBaseContext()).getNome());
        email.setText(Usuario.getInstance(getBaseContext()).getEmail());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Conta");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_36dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        Usuario.getInstance(getBaseContext()).isPremium(new Usuario.OnBoll() {
            @Override
            public void resp(Boolean response) {
                if (!response) {
                    final AdView adView;
                    adView = (AdView) findViewById(R.id.base_ad_word);
                    adView.setVisibility(View.VISIBLE);
                    AdRequest adRequest = new AdRequest.Builder().build();
                    adView.loadAd(adRequest);
                    plano.setText("Básico.");
                } else {
                    plano.setText("Premium até " + Usuario.getInstance(getBaseContext()).premiumLimit());
                }
            }
        });
        if (!Usuario.getInstance(getBaseContext()).isCadastrado()) {
            Fast.configInfoCadastro(ActivityConta.this);
        }
    }

    public void sendConfig(View view) {
        int id = view.getId();
        if (id == R.id.titulo_nome || id == R.id.texto_nome) {
            final SetString nomeUser = new SetString();
            nomeUser.setTitle(getResources().getString(R.string.nome));
            nomeUser.setBase(Usuario.getInstance(getBaseContext()).getNome());
            nomeUser.setListerner(new OnPositivoString() {
                @Override
                public void onClick(final String editText) {
                    if (!editText.equals(Usuario.getInstance(getBaseContext()).getNome())) {
                        Usuario.getInstance(getBaseContext()).setNome(editText, new Usuario.OnBoll() {
                            @Override
                            public void resp(Boolean boll) {
                                nome.setText(editText);
                            }
                        });
                    }
                }
            });
            nomeUser.show(getSupportFragmentManager(), "diago");

        } else if (id == R.id.titulo_email || id == R.id.texto_email) {
            final SetString emailUser = new SetString();
            emailUser.setTitle(getResources().getString(R.string.email));
            emailUser.setBase(Usuario.getInstance(getBaseContext()).getEmail());
            emailUser.setListerner(new OnPositivoString() {
                @Override
                public void onClick(final String editText) {
                    if (!editText.equals(Usuario.getInstance(getBaseContext()).getEmail()) && editText.matches(".+@.+\\.[a-z]+")) {
                        Usuario.getInstance(getBaseContext()).setEmail(editText, new Usuario.OnString() {
                            @Override
                            public void resp(String resp) {
                                if (resp.equalsIgnoreCase("Sucess")) {
                                    email.setText(editText);
                                } else {
                                    Toast.makeText(getBaseContext(), resp, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getBaseContext(), "Email invalido ou igual.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            emailUser.show(getSupportFragmentManager(), "diago");
        } else if (id == R.id.titulo_plano || id == R.id.texto_plano) {
            startActivity(new Intent(getBaseContext(), ActivityPremium.class));
        }
    }
}
