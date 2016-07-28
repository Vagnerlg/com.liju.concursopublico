package ggtec.lei_concursospublicos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import ggtec.lei_concursospublicos.Adapter.AdapterItemInfoLei;
import ggtec.lei_concursospublicos.api_antiga.ItemInfoLei;
import ggtec.lei_concursospublicos.api_antiga.ListaInfoLei;
import ggtec.lei_concursospublicos.api_antiga.Usuario;

public class ActivityPesquisaDeLei extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pesquisa_de_lei);

        final RecyclerView recycler;
        final FrameLayout progressBar;
        final FrameLayout conteinerErro;
        final TextView textErro;
        final EditText objNumero;
        final EditText objAno;
        final EditText objDescricao;
        TextView objErro;


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recycler = (RecyclerView) findViewById(R.id.conteiner_lista);
        progressBar = (FrameLayout) findViewById(R.id.load_conteiner);
        conteinerErro = (FrameLayout) findViewById(R.id.erro_conteiner);
        textErro = (TextView) findViewById(R.id.erro_mensagem);
        recycler.setVisibility(View.GONE);
        objNumero = (EditText) findViewById(R.id.numero);
        objAno = (EditText) findViewById(R.id.ano);
        objDescricao = (EditText) findViewById(R.id.descricao);
        final TextView resposta = (TextView) findViewById(R.id.resposta);
        toolbar.inflateMenu(R.menu.send_fale_conosco);
        toolbar.setNavigationIcon(R.drawable.ic_clear_white_24dp);
        toolbar.setTitle("Pesquisa de lei");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.send) {
                    recycler.setVisibility(View.GONE);
                    resposta.setText(null);
                    if (objNumero.getText().length() > 0 || objDescricao.getText().length() > 0) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(getBaseContext().INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(toolbar.getWindowToken(), 0);
                        ListaInfoLei listaInfoLei = new ListaInfoLei(getBaseContext(),findViewById(R.id.root));
                        listaInfoLei.pesquisa(objNumero.getText().toString(), objAno.getText().toString(), objDescricao.getText().toString(),
                                new ListaInfoLei.OnItemInfoLei() {
                                    @Override
                                    public void resp(ArrayList<ItemInfoLei> itemInfoLeis) {
                                        resposta.setText(objDescricao.getText()+" obteve "+itemInfoLeis.size()+" resultados.");
                                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getBaseContext());
                                        recycler.setLayoutManager(mLayoutManager);

                                        AdapterItemInfoLei mAdapter = new AdapterItemInfoLei(
                                                getBaseContext(),
                                                itemInfoLeis,
                                                "Pesquisa de Lei : " + objNumero.getText().toString() + "," + objAno.getText().toString() + "," + objDescricao.getText().toString());
                                        recycler.setAdapter(mAdapter);
                                        recycler.setVisibility(View.VISIBLE);
                                    }
                                });
                    } else {
                        conteinerErro.setVisibility(View.VISIBLE);
                        textErro.setText("Preencha pelo menos Nº lei e/ou descrição");
                    }
                } else {
                    //envio de pesquisa
                }
                return false;
            }
        });
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