package ggtec.lei_concursospublicos;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import ggtec.lei_concursospublicos.Adapter.AdapterItemLei;
import ggtec.lei_concursospublicos.Dialog.DialogoCadastro;
import ggtec.lei_concursospublicos.Dialog.DialogoFonte;
import ggtec.lei_concursospublicos.Dialog.DialogoInfoLei;
import ggtec.lei_concursospublicos.Outros.PainelEdicao;
import ggtec.lei_concursospublicos.Service.MediaPlayerService;
import ggtec.lei_concursospublicos.Sistema.Ajuda;
import ggtec.lei_concursospublicos.Sistema.Config;
import ggtec.lei_concursospublicos.Sistema.Debug;
import ggtec.lei_concursospublicos.Sistema.ItemInfoLei;
import ggtec.lei_concursospublicos.Sistema.ItemLei;
import ggtec.lei_concursospublicos.Sistema.ListaLei;
import ggtec.lei_concursospublicos.Sistema.ListaInfoLei;
import ggtec.lei_concursospublicos.Sistema.Usuario;

public class ActivityLei extends AppCompatActivity {

    //View grupos
    RecyclerView recyclerViewListaLei;
    Toolbar toolbarTop;
    LinearLayout linearLayoutToolbarBottom;
    FrameLayout frameLayoutProgressBar;
    FrameLayout conteinerErro;
    TextView textErro;
    View root;

    //View aciliares
    AdapterItemLei adapterItemLei;
    RecyclerView.LayoutManager layoutManagerRecycler;
    View viewWindow = null;

    //Painel
    PainelEdicao painelEdicao;
    ItemInfoLei infolei;

    //Variavaveis globais
    Boolean buscaNet = false;
    Boolean full = false;

    float dp;

    int limit = 1;
    int position = -1;
    int totalItens = 0;
    final int KEY_NEW_NET = 1;
    final int KEY_ADD_NET = 2;
    final int KEY_MENU_NET = 3;
    final static int KEY_RESULT_MENU = 100;

    String tab_num = "";
    String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lei);

        //inicializações
        viewWindow = getWindow().getDecorView();
        toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        toolbarTop.inflateMenu(R.menu.menu_top_lei);
        toolbarTop.setOnMenuItemClickListener(new onItemMenuTop());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        linearLayoutToolbarBottom = (LinearLayout) findViewById(R.id.toolbar_bottom);
        dp = getResources().getDisplayMetrics().density;

        painelEdicao = PainelEdicao.getNewInstance(linearLayoutToolbarBottom);

        recyclerViewListaLei = (RecyclerView) findViewById(R.id.conteiner_lista);
        recyclerViewListaLei.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int zonadeUpload = limit - 30;
                if((limit+2) < totalItens){
                    if(zonadeUpload < adapterItemLei.getLimit() && !buscaNet){
                        internet(KEY_ADD_NET);
                    }
                }
            }
        });
        recyclerViewListaLei.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                Debug.d("holderRecycler "+holder.getAdapterPosition());
                Debug.d("Position " + position);
            }
        });
        recyclerViewListaLei.setHasFixedSize(true);
        layoutManagerRecycler = new LinearLayoutManager(getBaseContext());
        recyclerViewListaLei.setLayoutManager(layoutManagerRecycler);
        frameLayoutProgressBar = (FrameLayout) findViewById(R.id.progress_bar);
        conteinerErro = (FrameLayout) findViewById(R.id.conteiner_erro);
        textErro = (TextView) findViewById(R.id.text_erro);

        Bundle itemMenuB = getIntent().getExtras();
        tab_num = itemMenuB.getString("tab_num");
        ListaInfoLei listaLeilimit = new ListaInfoLei(getBaseContext());
        limit = listaLeilimit.getLimitHistorico(tab_num);
        if(limit > 1){
            limit = limit -1;
        }

        Usuario.getInstance(getBaseContext()).isPremium(new Usuario.OnBoll() {
            @Override
            public void resp(Boolean boll) {
                if (!boll) {
                    final AdView adView;
                    adView = (AdView) findViewById(R.id.base_ad_word);
                    adView.setVisibility(View.VISIBLE);
                    AdRequest adRequest = new AdRequest.Builder().build();
                    adView.loadAd(adRequest);
                    adView.post(new Runnable() {
                        @Override
                        public void run() {
                            recyclerViewListaLei.setPadding(0,0,0,adView.getHeight());
                        }
                    });
                }
            }
        });

        noFull();
        internet(KEY_NEW_NET);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == KEY_RESULT_MENU) {
                if (data.getIntExtra("extra", -1) != -1) {
                    limit = data.getIntExtra("extra", 1) - 1;
                    internet(KEY_MENU_NET);
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (viewWindow != null) {
            noFull();
        }
    }

    public void clickItemLei(View view) {
        clearSelectItem();
        if (full) {
            noFull();
        } else {
            position = (int) view.getTag();
            adapterItemLei.list.get(position).setSelecionado(true);
            adapterItemLei.notifyItemChanged(position);
            painelEdicao.indexComentario(adapterItemLei.list.get(position));
        }
    }

    public void sendComent(View view) {
        if (Usuario.getInstance(getBaseContext()).isCadastrado()) {
            Bundle comentario = painelEdicao.getComentario();
            String textoC = comentario.getString("comentario");
            if(textoC.equalsIgnoreCase("")){
                textoC = null;
            }
            if (textoC != null) {
                adapterItemLei.list.get(position).setComentario(textoC, comentario.getBoolean("publico"),tab_num,getBaseContext());
                adapterItemLei.list.get(position).setSelecionado(false);
                adapterItemLei.notifyItemChanged(position);
                full(400);
                InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            } else {
                Snackbar.make(view, "Escreva algum comentário", Snackbar.LENGTH_LONG).show();
            }
        } else {
            DialogoCadastro dialogoCadastro = new DialogoCadastro();
            dialogoCadastro.show(getSupportFragmentManager(), "dialogo");
        }
    }

    public void sendMarcacao(View v) {
        LinearLayout div = (LinearLayout) adapterItemLei.getViewMarcacao();
        String beforeColor = "inicio";
        String color;
        int cont = 0;
        String json = "";
        String marca = "";
        if (Usuario.getInstance(getBaseContext()).isCadastrado()) {
            for (int a = 0; a < div.getChildCount(); a++) {
                LinearLayout linha = (LinearLayout) div.getChildAt(a);
                for (int b = 0; b < linha.getChildCount(); b++) {
                    LinearLayout palavra = (LinearLayout) linha.getChildAt(b);
                    for (int c = 0; c < palavra.getChildCount(); c++) {
                        TextView letra = (TextView) palavra.getChildAt(c);
                        if (letra.getTag() != null) {
                            color = (String) letra.getTag();
                        } else {
                            color = "W";
                        }
                        if (color.equalsIgnoreCase(beforeColor) || beforeColor.equalsIgnoreCase("inicio")) {
                            cont++;
                        } else {
                            if (!json.equalsIgnoreCase("")) {
                                json += ",";
                            }
                            json += "{\"color\":\"" + beforeColor + "\",\"num\":" + cont + "}";
                            cont = 1;
                        }
                        beforeColor = color;
                        marca += color;
                        Log.d("LETRAS", "" + letra.getText() + " " + color);
                    }
                }
            }
        } else {
            DialogoCadastro dialogoCadastro = new DialogoCadastro();
            dialogoCadastro.show(getSupportFragmentManager(), "dialogo");
        }
        adapterItemLei.list.get(position).setMarcacao(marca,tab_num,getBaseContext());
        adapterItemLei.list.get(position).setSelecionado(false);
        adapterItemLei.notifyItemChanged(position);
        clearSelectItem();
        full(400);
    }

    public void cancelMarcacao(View v) {
        full(400);
    }

    private class onItemMenuTop implements Toolbar.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.full_screen) {
                full(300);
            } else if (id == R.id.menus) {
                Intent intent = new Intent(getBaseContext(), ActivityMenusLeis.class);
                intent.putExtra("tab_num", tab_num);
                intent.putExtra("title", title);
                startActivityForResult(intent, KEY_RESULT_MENU);
                Log.d("ITEMMENU", "MENUS");
            } else if (id == R.id.fonte) {
                final DialogoFonte dialogoFonte = new DialogoFonte();
                dialogoFonte.setListerner(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dialogoFonte.getFonteNova() != 0) {
                            if (Config.getInstance(getBaseContext()).setFonte(dialogoFonte.getFonteNova())) {
                                adapterItemLei.fontSize = Config.getInstance(getBaseContext()).getFonte();
                                synchronized (adapterItemLei){
                                    adapterItemLei.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                });
                dialogoFonte.show(getSupportFragmentManager(), "dialogo");
                Log.d("ITEMMENU", "FONTE");
            } else if (id == R.id.pesquisa_lei) {
                Intent intent = new Intent(getBaseContext(), ActivityPesquisaNaLei.class);
                intent.putExtra("tab_num", tab_num);
                intent.putExtra("title", title);
                startActivityForResult(intent, KEY_RESULT_MENU);
                Log.d("ITEMMENU", "PLAYER");
            }else if(id == R.id.infolei){
                if(infolei != null){
                    DialogoInfoLei dialogoInfoLei = new DialogoInfoLei();
                    dialogoInfoLei.setTitulo(infolei.getTipoLeiHumano() + " " +infolei.getNumAnoFormat());
                    dialogoInfoLei.setDescricao(infolei.getDescricao());
                    dialogoInfoLei.show(getSupportFragmentManager(),"dialogo");
                }
            }
            return false;
        }
    }

    ListaLei listaLei;

    private void internet(final int keyTipo) {
        buscaNet = true;
        final ListaLei listaLei = new ListaLei(getBaseContext(),findViewById(R.id.root),  tab_num);
        if (keyTipo == KEY_NEW_NET || keyTipo == KEY_MENU_NET) {
            recyclerViewListaLei.setVisibility(View.GONE);
            if (keyTipo == KEY_NEW_NET) {
                listaLei.getLeiMaisInfo(limit, new ListaLei.OnItemInfoLei() {
                    @Override
                    public void resp(ArrayList<ItemLei> itemLeis, ItemInfoLei infoLei) {
                        if(itemLeis.size() > 0) {
                            totalItens = listaLei.getTotalItens();
                            title = infoLei.getNumAnoFormat();
                            ActivityLei.this.infolei = infoLei;
                            toolbarTop.setTitle(title);
                            ListaInfoLei listaMenu = new ListaInfoLei(getBaseContext());
                            listaMenu.addHistorico(infoLei, itemLeis.get(0));
                            recyclerViewListaLei.setVisibility(View.VISIBLE);
                            recyclerViewListaLei.setHasFixedSize(true);
                            adapterItemLei = new AdapterItemLei(getBaseContext(), itemLeis, AdapterItemLei.ORIGEM_ACITIVITY_LEI);
                            recyclerViewListaLei.setAdapter(adapterItemLei);
                 //           layoutManagerRecycler.scrollToPosition(50);
                            if(!Config.getInstance(getBaseContext()).getDicaInicial()){
                                Intent intent = new Intent(getBaseContext(),ActivityAjuda.class);
                                intent.putExtra(ActivityAjuda.KEY_TIPO_DICA, Ajuda.AJUDA_GERAL);
                                startActivity(intent);
                            }
                            full(1000);
                            limit = adapterItemLei.list.get((adapterItemLei.list.size() - 1)).getID();
                        }
                        buscaNet = false;
                        clearSelectItem();
                    }
                });
            }
            if(keyTipo == KEY_MENU_NET) {
                //impementar lei em back
                listaLei.getLei(limit, new ListaLei.OnItemLei() {
                    @Override
                    public void resp(ArrayList<ItemLei> itemLeis) {
                        if(itemLeis.size() > 0) {
                            ListaInfoLei listaMenu = new ListaInfoLei(getBaseContext());
                            listaMenu.addHistorico(ActivityLei.this.infolei, itemLeis.get(0));
                            recyclerViewListaLei.setVisibility(View.VISIBLE);
                            adapterItemLei = new AdapterItemLei(getBaseContext(), itemLeis, AdapterItemLei.ORIGEM_ACITIVITY_LEI);
                            recyclerViewListaLei.setAdapter(adapterItemLei);
                            full(1000);
                            limit = adapterItemLei.list.get((adapterItemLei.list.size() - 1)).getID();
                        }
                        clearSelectItem();
                        buscaNet = false;
                    }
                });
            }
        }
        if(keyTipo == KEY_ADD_NET) {
            //impementar lei em back
            listaLei.getLeiBack(limit, new ListaLei.OnItemLei() {
                @Override
                public void resp(ArrayList<ItemLei> itemLeis) {
                    if(itemLeis.size() > 0) {
                        adapterItemLei.list.addAll(itemLeis);
                        adapterItemLei.notifyDataSetChanged();
                        limit = adapterItemLei.list.get((adapterItemLei.list.size() - 1)).getID();
                    }
                    buscaNet = false;
                }
            });
        }
    }

    private void full(int delay) {
        clearSelectItem();
        viewWindow.postDelayed(new Runnable() {
            @Override
            public void run() {

                viewWindow.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                toolbarTop.setVisibility(View.GONE);
                painelEdicao.hide();
                full = true;
            }
        }, delay);
    }

    private void noFull() {
        viewWindow.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        toolbarTop.setVisibility(View.VISIBLE);
        painelEdicao.show();
        full = false;
    }

    public void btnEdicao(View v) {
        if (position != -1) {
            if (v.getId() == R.id.btn_player) {
                if (MediaPlayerService.playerWording && v.getId() == R.id.btn_player) {
                    painelEdicao.btnShows(v.getId());
                } else {
                    painelEdicao.btnShows(R.id.player_item);
                }
            } else {
                painelEdicao.btnShows(v.getId());
            }
        } else if (MediaPlayerService.playerWording && v.getId() == R.id.btn_player) {
            painelEdicao.btnShows(R.id.player_controller);
        } else {
            Snackbar.make(v, "Selecione um trecho da lei para fazer edicao", Snackbar.LENGTH_LONG).show();
        }
    }

    private void clearSelectItem() {
        if (position != -1) {
            if (adapterItemLei != null) {
                adapterItemLei.list.get(position).setSelecionado(false);
                adapterItemLei.notifyItemChanged(position);
                position = -1;
            }
        }
    }

    public void sendPlayerItem(View view) {

        Log.d("DB_JavaScript", "ouvirlei : tab_num :" + tab_num + ", limit : " + limit);
        MediaPlayerService.allTrecho = adapterItemLei.list;
        Intent intent = new Intent(getBaseContext(), MediaPlayerService.class);
        intent.putExtra(MediaPlayerService.NEW_LIST, MediaPlayerService.NEW_LIST);
        intent.putExtra(MediaPlayerService.TAB_NUM, tab_num);
        intent.putExtra(MediaPlayerService.LIMIT_TAB, position);
        startService(intent);

        startActivity(new Intent(getBaseContext(),ActivityPlayer.class));

    }

    public void sendPlayerController(View view) {
        startActivity(new Intent(getBaseContext(), ActivityPlayer.class));
    }

    //repassando parapainel
    public void selectColor(View view) {
        painelEdicao.selectColor(view);
    }

    public void btnTipoComentario(View view) {
        painelEdicao.btnTipoComentario(view);
    }

    public void espandirComent(View view){
        int position  = (int) view.getTag();
        if(adapterItemLei != null){
            adapterItemLei.setComentOpen();
            adapterItemLei.notifyItemChanged(position);
        }

    }
}