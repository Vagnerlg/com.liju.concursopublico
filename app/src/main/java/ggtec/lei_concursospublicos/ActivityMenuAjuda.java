package ggtec.lei_concursospublicos;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import ggtec.lei_concursospublicos.Adapter.AdapterMenuAjuda;
import ggtec.lei_concursospublicos.api_antiga.Usuario;

public class ActivityMenuAjuda extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu_ajuda);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.conteiner_lista);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new AdapterMenuAjuda());
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
                }
            }
        });
    }

    public void selectItem(View v){
        int position = (int) v.getTag();
        Intent intent = new Intent(getBaseContext(),ActivityAjuda.class);
        intent.putExtra(ActivityAjuda.KEY_TIPO_DICA,position);
        startActivity(intent);
    }
}
