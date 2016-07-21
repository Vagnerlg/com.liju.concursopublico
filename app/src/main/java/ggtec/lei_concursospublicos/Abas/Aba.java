package ggtec.lei_concursospublicos.Abas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;

import ggtec.lei_concursospublicos.Adapter.AdapterAbas;
import ggtec.lei_concursospublicos.Adapter.AdapterItemInfoLei;
import ggtec.lei_concursospublicos.Adapter.AdapterItemLei;
import ggtec.lei_concursospublicos.Adapter.AdapterMenuArtigo;
import ggtec.lei_concursospublicos.Adapter.AdapterMenuTitulo;
import ggtec.lei_concursospublicos.R;
import ggtec.lei_concursospublicos.api_antiga.Debug;
import ggtec.lei_concursospublicos.api_antiga.Fast;
import ggtec.lei_concursospublicos.api_antiga.ItemInfoLei;
import ggtec.lei_concursospublicos.api_antiga.Trecho;
import ggtec.lei_concursospublicos.api_antiga.Lista;
import ggtec.lei_concursospublicos.api_antiga.ListaInfoLei;
import ggtec.lei_concursospublicos.api_antiga.Lei;
import ggtec.lei_concursospublicos.api_antiga.Usuario;

/**
 * Created by Vagner on 12/02/2016.
 */
public class Aba extends Fragment {


    private String tab_num = "";

    public static final int TIPO_LISTA_LEI = 11;
    public static final int TIPO_LISTA_INFO_LEI = 12;

    public static final int TIPO_ABA_HISTORICO = 21;
    public static final int TIPO_ABA_GRUPO = 22;
    public static final int TIPO_ABA_NET = 23;
    public static final int TIPO_ABA_TITULO = 31;
    public static final int TIPO_ABA_ARTIGO = 32;
    public static final int TIPO_ABA_COMENTARIO = 33;
    public static final int TIPO_ABA_MARCACAO = 34;

    private String tipoGrupo = "";
    private int tipoLista;
    private int tipoAba;
    private Lista lista = null;
    private OnItemLeiInfo onItemLeiInfo = null;
    private OnItemLei onItemLei = null;
    private RecyclerView mRecyclerView;
    private View root = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        tipoAba = args.getInt(AdapterAbas.KEY_ABA);
        tipoLista = args.getInt(AdapterAbas.KEY_TIPO);
        Debug.d("Aba Criada" + tipoAba);

        FrameLayout root = (FrameLayout) inflater.inflate(R.layout.aba, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.conteiner_lista);
        this.root = root;
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setVisibility(View.VISIBLE);

        if (tipoLista == TIPO_LISTA_INFO_LEI) {
            lista = new ListaInfoLei(getContext(), root);
            onItemLeiInfo = new OnItemLeiInfo();
            if (tipoAba == TIPO_ABA_HISTORICO) {
                ((ListaInfoLei) lista).getHistorico(onItemLeiInfo);
            }

            if (tipoAba == TIPO_ABA_NET) {
                ((ListaInfoLei) lista).getMaisAcessadas(onItemLeiInfo);
            }

            if (tipoAba == TIPO_ABA_GRUPO) {
                tipoGrupo = args.getString(AdapterAbas.KEY_GRUPO);
                ((ListaInfoLei) lista).getGrupo(tipoGrupo, onItemLeiInfo);
            }
        }

        if (tipoLista == TIPO_LISTA_LEI) {
            tab_num = args.getString(AdapterAbas.KEY_GRUPO);
            lista = new Lei(getContext(), root, tab_num);
            onItemLei = new OnItemLei();
            String origem = "";
            if(tipoAba == TIPO_ABA_TITULO){
                ((Lei)lista).getTitulos(onItemLei);
            }
            if(tipoAba == TIPO_ABA_ARTIGO){
                ((Lei)lista).getArtigos(onItemLei);
            }
            if(tipoAba == TIPO_ABA_COMENTARIO){
                ((Lei)lista).getComentarios(onItemLei);
            }
            if(tipoAba == TIPO_ABA_MARCACAO){
                ((Lei)lista).getMarcacoes(onItemLei);
            }
        }

        if (tipoAba == TIPO_ABA_ARTIGO)

        {
            GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 4);
            mRecyclerView.setLayoutManager(mLayoutManager);
        } else

        {
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
        }

        return root;
    }



    private class OnItemLeiInfo implements ListaInfoLei.OnItemInfoLei {

        @Override
        public void resp(ArrayList<ItemInfoLei> itemInfoLeis) {
            Debug.d("ItemInfoLei " + itemInfoLeis.size());
            AdapterItemInfoLei mAdapter = new AdapterItemInfoLei(getContext(), itemInfoLeis, tipoAba + "");
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    private class OnItemLei implements Lei.OnItemLei{

        @Override
        public void resp(ArrayList<Trecho> trechos) {
            if(tipoAba == TIPO_ABA_TITULO){
                if(trechos.size()>0){
                    AdapterMenuTitulo mAdapter = new AdapterMenuTitulo(trechos, getResources().getDisplayMetrics().density, getContext());
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    Fast.configInfo(root, "Esta lei não possui Títulos.");
                }
            }
            if(tipoAba == TIPO_ABA_ARTIGO){
                if(trechos.size()>0){
                    AdapterMenuArtigo mAdapter = new AdapterMenuArtigo(trechos);
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    Fast.configInfo(root, "Esta lei não possui Artigos.");
                }
            }
            if(tipoAba == TIPO_ABA_COMENTARIO){
                if(trechos.size()>0){
                    AdapterItemLei mAdapter = new AdapterItemLei(getContext(), trechos, AdapterItemLei.ORIGEM_MENU_COMENTARIO);
                    mRecyclerView.setAdapter(mAdapter);
                }else if(Usuario.getInstance(getContext()).isCadastrado()){
                    Fast.configInfo(root, "Você ainda não fez comentários nesta lei.");
                }else {
                    Fast.configInfoCadastroFragment(Aba.this, getContext(), root);
                }
            }
            if(tipoAba == TIPO_ABA_MARCACAO){
                if(trechos.size()>0){
                    AdapterItemLei mAdapter = new AdapterItemLei(getContext(), trechos, AdapterItemLei.ORIGEM_MENU_MARCACAO);
                    mRecyclerView.setAdapter(mAdapter);
                }else  if(Usuario.getInstance(getContext()).isCadastrado()){
                    Fast.configInfo(root, "Você ainda não fez marcações nesta lei.");
                }else {
                    Fast.configInfoCadastroFragment(Aba.this, getContext(), root);
                }
            }
        }
    }
}