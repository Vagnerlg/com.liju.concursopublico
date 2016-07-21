package ggtec.lei_concursospublicos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ggtec.lei_concursospublicos.Adapter.AdapterItemLei;
import ggtec.lei_concursospublicos.api_antiga.Trecho;
import ggtec.lei_concursospublicos.api_antiga.Lei;

public class ActivityPesquisaNaLei extends AppCompatActivity {

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    EditText textoPesquisa;
    TextView objNumResult;
    FrameLayout progressBar;
    FrameLayout conteinerErro;
    TextView textErro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pesquisa_na_lei);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        textoPesquisa = (EditText) findViewById(R.id.texto_pesquisa);
        objNumResult = (TextView) findViewById(R.id.num_result);
        objNumResult.setVisibility(View.GONE);
        progressBar = (FrameLayout) findViewById(R.id.progress_bar);
        conteinerErro = (FrameLayout) findViewById(R.id.conteiner_erro);
        textErro = (TextView) findViewById(R.id.text_erro);

        mRecyclerView = (RecyclerView) findViewById(R.id.conteiner_lista);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getBaseContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        toolbar.setNavigationIcon(R.drawable.ic_clear_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //setSupportActionBar(toolbar);
        String nome = "Pesquisa na lei ";
        if (getIntent() != null) {
            nome = nome + getIntent().getStringExtra("title");
        }
        toolbar.setTitle(nome);
        textoPesquisa.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    btnPesquisar(v);
                    handled = true;
                }
                return handled;
            }
        });
    }

    public void btnPesquisar(View v) {
        Log.d("DB_PESQUISA", "ok");
        if (getIntent() != null && textoPesquisa.getText().toString().length() > 0) {
            if (!getIntent().getStringExtra("tab_num").equalsIgnoreCase("")) {
                objNumResult.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.GONE);
                Lei lei = new Lei(getBaseContext(),findViewById(R.id.root), getIntent().getStringExtra("tab_num"));
                lei.pesquisa(textoPesquisa.getText().toString(),
                        new Lei.OnItemLei() {
                            @Override
                            public void resp(ArrayList<Trecho> trechos) {
                                objNumResult.setVisibility(View.VISIBLE);
                                if (trechos.size() == 0) {
                                    objNumResult.setText("'" + textoPesquisa.getText().toString() + "' não obteve resultado.");
                                } else {
                                    mRecyclerView.setVisibility(View.VISIBLE);
                                    if (trechos.size() == 1) {
                                        objNumResult.setText("'" + textoPesquisa.getText().toString() + "' obteve 1 resultado.");
                                    } else {
                                        objNumResult.setText("'" + textoPesquisa.getText().toString() + "' obteve " + trechos.size() + " resultados.");
                                    }
                                    AdapterItemLei mAdapter = new AdapterItemLei(getBaseContext(), trechos, AdapterItemLei.ORIGEM_ACITIVITY_PESQUISA_NA_LEI);
                                    mRecyclerView.setAdapter(mAdapter);
                                }
                            }
                        });
            }
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void clickItemLei(View v) {
        Intent intent = new Intent();
        intent.putExtra("extra", (Integer) v.getTag());
        setResult(RESULT_OK, intent);
        finish();
    }
}
