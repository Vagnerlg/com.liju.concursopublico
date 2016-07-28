package ggtec.lei_concursospublicos;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import ggtec.lei_concursospublicos.Adapter.AdapterAjuda;
import ggtec.lei_concursospublicos.api_antiga.Ajuda;
import ggtec.lei_concursospublicos.api_antiga.Config;

/**
 * Created by Vagner on 25/02/2016.
 */
public class ActivityAjuda extends AppCompatActivity {

    public static final String KEY_TIPO_DICA = "dica";
    private TextView progresso;
    private Ajuda ajuda;
    private TextView proximo;
    private TextView anterior;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ajuda);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        proximo = (TextView) findViewById(R.id.dica_btn_proximo);
        anterior = (TextView) findViewById(R.id.dica_btn_anterior);
        progresso = (TextView) findViewById(R.id.dica_progresso);

        ajuda = new Ajuda(getIntent().getIntExtra(KEY_TIPO_DICA, -1));

        if (Ajuda.AJUDA_GERAL == getIntent().getIntExtra(KEY_TIPO_DICA, -1)) {
            anterior.setText("Não, Obrigado!");
        }

        if(ajuda.getTotal()>0){
            proximo.setText("Próximo");
        }

        progresso.setText("1 de " + ajuda.getTotal());


        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new AdapterAjuda(getSupportFragmentManager(), ajuda));
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                progresso.setText((position + 1) + " de " + ajuda.getTotal());
                proximo.setText("Próximo");
                anterior.setText("Anterior");
                if ((position + 1) == ajuda.getTotal()) {
                    proximo.setText("Fechar");
                }
                if (position == 0) {
                    anterior.setText("");
                }
                if (Ajuda.AJUDA_GERAL == getIntent().getIntExtra(KEY_TIPO_DICA, -1) && position == 0) {
                    anterior.setText("Não, Obrigado!");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (Ajuda.AJUDA_GERAL == getIntent().getIntExtra(KEY_TIPO_DICA, -1)) {
            anterior.setText("Não, Obrigado!");
        }
    }

    public void btnControle(View v){
        if(v.getId() == R.id.dica_btn_anterior){
            if(((TextView) v).getText().toString().equalsIgnoreCase("Não, Obrigado!")){
                Config.getInstance(getBaseContext()).noAutomatcDicaInicial();
                finish();
            }else if (pager.getCurrentItem() != 0) {
                pager.setCurrentItem((pager.getCurrentItem() - 1));
            }
        }
        if(v.getId() == R.id.dica_btn_proximo){
            if ((pager.getCurrentItem()+1) < ajuda.getTotal()) {
                pager.setCurrentItem((pager.getCurrentItem() + 1));
            } else {
                if (Ajuda.AJUDA_GERAL == getIntent().getIntExtra(KEY_TIPO_DICA, -1)) {
                    Config.getInstance(getBaseContext()).noAutomatcDicaInicial();
                }
                finish();
            }
            if (Ajuda.AJUDA_GERAL == getIntent().getIntExtra(KEY_TIPO_DICA, -1)) {
                anterior.setText("Anterior");
            }
        }
    }
}