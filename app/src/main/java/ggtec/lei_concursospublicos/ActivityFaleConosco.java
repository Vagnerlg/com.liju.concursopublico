package ggtec.lei_concursospublicos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONObject;

import ggtec.lei_concursospublicos.Sistema.Link;
import ggtec.lei_concursospublicos.Sistema.Usuario;

public class ActivityFaleConosco extends AppCompatActivity {

    EditText objEmail;
    EditText objTexto;
    EditText objNome;
    TextView objMensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_fale_conosco);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.send_fale_conosco);
        toolbar.setNavigationIcon(R.drawable.ic_clear_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        objMensagem = (TextView) findViewById(R.id.mensagem);
        objMensagem.setVisibility(View.GONE);
        objEmail = (EditText) findViewById(R.id.email);
        if (Usuario.getInstance(getBaseContext()).getEmail() != null) {
            objEmail.setText(Usuario.getInstance(getBaseContext()).getEmail());
        }
        objNome = (EditText) findViewById(R.id.nome);
        if (Usuario.getInstance(getBaseContext()).getNome() != null) {
            objNome.setText(Usuario.getInstance(getBaseContext()).getNome());
        }
        objTexto = (EditText) findViewById(R.id.edit_texto);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.send) {
                    objMensagem.setVisibility(View.GONE);
                    if (objNome.getText().length() > 0 && objEmail.getText().length() > 0 && objTexto.getText().length() > 0) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(getBaseContext().INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(toolbar.getWindowToken(), 0);
                        //enviar
                        new Link(getBaseContext())
                                .faleConosco(objNome.getText().toString(), objEmail.getText().toString(), objTexto.getText().toString())
                                .send(new Link.OnJSONObject() {
                                    @Override
                                    public void resp(JSONObject jsonObject) {
                                        objMensagem.setVisibility(View.VISIBLE);
                                        objMensagem.setText("Mensagem enviada com sucesso");
                                        objMensagem.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                startActivity(new Intent(getBaseContext(), ActivityMain.class));
                                            }
                                        }, 1000);
                                    }

                                    @Override
                                    public void erro(String tipo, String mensagem) {
                                        Toast.makeText(getBaseContext(), mensagem, Toast.LENGTH_LONG);
                                    }
                                });
                    } else {
                        objMensagem.setVisibility(View.VISIBLE);
                        objMensagem.setText("Preencha todos os campos para enviar a mensagem");
                    }
                    return true;
                }
                return false;
            }
        });
        toolbar.setTitle("Fale conosco");
        //setSupportActionBar(toolbar);
        Usuario.getInstance(getBaseContext()).isPremium(new Usuario.OnBoll() {
            @Override
            public void resp(Boolean boll) {
                if (!boll) {
                    final AdView adView;
                    adView = (AdView) findViewById(R.id.base_ad_word);
                    adView.setVisibility(View.VISIBLE);
                    AdRequest adRequest = new AdRequest.Builder().build();
                    adView.loadAd(adRequest);
                }
            }
        });
    }
}