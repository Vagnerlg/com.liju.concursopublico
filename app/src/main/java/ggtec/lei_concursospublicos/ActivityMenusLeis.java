package ggtec.lei_concursospublicos;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ggtec.lei_concursospublicos.Adapter.AdapterMenusLeis;

public class ActivityMenusLeis extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_menus_leis);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabView);
        tabLayout.setVisibility(View.GONE);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        AdapterMenusLeis adapterAbas = new AdapterMenusLeis(
                getSupportFragmentManager(),
                getIntent().getStringExtra("tab_num")
        );
        pager.setAdapter(adapterAbas);

        //Botoes das abas
        pager.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
        tabLayout.setupWithViewPager(pager);
        tabLayout.setSelectedTabIndicatorHeight(7);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        toolbar.setNavigationIcon(R.drawable.ic_clear_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayShowCustomEnabled(true);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent();
        intent.putExtra("extra", "extra");
        setResult(RESULT_CANCELED);
        finish();
    }

    public void menuResult(View v){
        Intent intent = new Intent();
        intent.putExtra("extra",(Integer) v.getTag());
        setResult(RESULT_OK, intent);
        finish();
    }
    public void clickItemLei(View v) {
        Intent intent = new Intent();
        intent.putExtra("extra",(Integer) v.getTag());
        setResult(RESULT_OK, intent);
        finish();
    }
}