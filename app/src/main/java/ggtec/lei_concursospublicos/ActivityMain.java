package ggtec.lei_concursospublicos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import ggtec.lei_concursospublicos.Adapter.AdapterAbas;
import ggtec.lei_concursospublicos.Outros.BaseAnalytics;
import ggtec.lei_concursospublicos.Sistema.Fast;
import ggtec.lei_concursospublicos.Sistema.Usuario;

import static ggtec.lei_concursospublicos.R.*;

public class ActivityMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ImageView atulizarPlano;
    private TextView planoUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.layout_main);
        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Lei");
        getSupportActionBar().setSubtitle("Concurso Público");
        //botao flutuante
        FloatingActionButton fab = (FloatingActionButton) findViewById(id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), ActivityPesquisaDeLei.class));
                BaseAnalytics.getInstance(getBaseContext()).evento(
                        BaseAnalytics.CAT_USUARIO,
                        "Pesquisa de Lei",
                        "de Main Floating");
            }
        });
        //Gaveta de menu lateral esquerdo
        DrawerLayout drawer = (DrawerLayout) findViewById(id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, string.navigation_drawer_open, string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //cabaçalho de infomações do usuario na gaveta lateral
        LinearLayout headerNavegation = (LinearLayout) navigationView.inflateHeaderView(layout.header_user);
        TextView nomeUser = (TextView) headerNavegation.findViewById(id.nome_user);
        final TextView emailUser = (TextView) headerNavegation.findViewById(id.email_user);
        planoUser = (TextView) headerNavegation.findViewById(id.plano_user);
        atulizarPlano = (ImageView) headerNavegation.findViewById(id.header_atualizar);
        nomeUser.setText(Usuario.getInstance(getBaseContext()).getNome());
        emailUser.setText(Usuario.getInstance(getBaseContext()).getEmail());

        //anuncio e informações de plano de usuario
        Usuario.getInstance(getBaseContext()).isPremium(new Usuario.OnBoll() {
            @Override
            public void resp(Boolean response) {
                if (!response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final AdView adView;
                            adView = (AdView) findViewById(id.base_ad_word);
                            adView.setVisibility(View.VISIBLE);
                            AdRequest adRequest = new AdRequest.Builder().build();
                            adView.loadAd(adRequest);
                            adView.post(new Runnable() {
                                @Override
                                public void run() {
                                    ViewPager pager = (ViewPager) findViewById(id.pager);
                                    int lateral = getResources().getDimensionPixelSize(dimen.margin_lateral);
                                    int top = getResources().getDimensionPixelSize(dimen.height_bar_2x);
                                    pager.setPadding(0,0,0,adView.getHeight());
                                }
                            });
                        }
                    });
                    if (!Usuario.getInstance(getBaseContext()).isCadastrado()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                emailUser.setText("Faça seu cadastro !");
                                emailUser.setTextColor(getResources().getColor(color.colorPrimary));
                                emailUser.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //intent para cadastro
                                        Fast.intentCadastro(ActivityMain.this);
                                        BaseAnalytics.getInstance(getBaseContext()).evento(
                                                BaseAnalytics.CAT_USUARIO,
                                                "Login/Cadastro",
                                                "de header Drayer");
                                    }
                                });
                            }
                        });
                    }else{
                        atulizarPlano.setVisibility(View.VISIBLE);
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            planoUser.setText("Premium até " + Usuario.getInstance(getBaseContext()).premiumLimit());
                            atulizarPlano.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });
        iniciarPager();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        iniciarPager();
    }

    private void iniciarPager(){
        //pager view
        ViewPager pager = (ViewPager) findViewById(id.pager);
        TabLayout tabLayout = (TabLayout) findViewById(id.tabView);
        pager.setAdapter(new AdapterAbas(getSupportFragmentManager(), getBaseContext()));
        tabLayout.setupWithViewPager(pager);
        tabLayout.setSelectedTabIndicatorHeight(5);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_conta) {
            // Handle the camera action
            startActivity(new Intent(getBaseContext(), ActivityConta.class));
            BaseAnalytics.getInstance(getBaseContext()).evento(
                    BaseAnalytics.CAT_USUARIO,
                    "Conta",
                    "de menu Drayer");
        } else if (id == R.id.nav_premium) {
            startActivity(new Intent(getBaseContext(), ActivityPremium.class));
            BaseAnalytics.getInstance(getBaseContext()).evento(
                    BaseAnalytics.CAT_USUARIO,
                    "Premium",
                    "de menu Drayer");
        } else if (id == R.id.nav_avaliar) {
            final String appPackageName = "ggtec.lei_concursospublicos";
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
            BaseAnalytics.getInstance(getBaseContext()).evento(
                    BaseAnalytics.CAT_USUARIO,
                    "Avaliação do google",
                    "de menu Drayer");
        } else if (id == R.id.nav_ajuda) {
            startActivity(new Intent(getBaseContext(),ActivityMenuAjuda.class));
            BaseAnalytics.getInstance(getBaseContext()).evento(
                    BaseAnalytics.CAT_USUARIO,
                    "Ajuda",
                    "de menu Drayer");
        } else if (id == R.id.nav_feedback) {
            startActivity(new Intent(getBaseContext(), ActivityFaleConosco.class));
            BaseAnalytics.getInstance(getBaseContext()).evento(
                    BaseAnalytics.CAT_USUARIO,
                    "Fale conosco",
                    "de menu Drayer");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void atualizarPlano(View v){
        Usuario.getInstance(getBaseContext()).UpdatePremium(new Usuario.OnBoll() {
            @Override
            public void resp(Boolean boll) {
                if(true){
                    planoUser.setText("Premium até " + Usuario.getInstance(getBaseContext()).premiumLimit());
                }
            }
        });
    }
}