package ggtec.lei_concursospublicos.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ggtec.lei_concursospublicos.Abas.Aba;
import ggtec.lei_concursospublicos.Sistema.ListaInfoLei;

/**
 * Created by Vagner on 23/11/2015.
 */
public class AdapterAbas extends FragmentPagerAdapter {

    private int total = 6;
    private Context context;
    private ListaInfoLei listaMenu;
    public static final String KEY_TIPO = "tipo";
    public static final String KEY_ABA = "aba";
    public static final String KEY_GRUPO = "grupo";
    private Activity activity = null;
    private Aba aba = null;


    public AdapterAbas(FragmentManager fm,Context con) {
        super(fm);
        context = con;
        listaMenu = new ListaInfoLei(con);
        if(listaMenu.hasHistorico()){
            total = 7;
        }
    }


    @Override
    public Fragment getItem(int position) {
        aba = new Aba();
        Bundle args = new Bundle();
        args.putInt(KEY_TIPO, Aba.TIPO_LISTA_INFO_LEI);
        if(total == 7){
            int tipo = position - 1;
            if (position == 0) {
                args.putInt(KEY_ABA, Aba.TIPO_ABA_HISTORICO);
            } else if (position == 1) {
                args.putInt(KEY_ABA,Aba.TIPO_ABA_NET);
            } else {
                args.putInt(KEY_ABA, Aba.TIPO_ABA_GRUPO);
                args.putString(KEY_GRUPO, tipoBanco(tipo));
            }
        }else {
            if (position == 0) {
                args.putInt(KEY_ABA, Aba.TIPO_ABA_NET);
            } else {
                args.putInt(KEY_ABA,Aba.TIPO_ABA_GRUPO);
                args.putString(KEY_GRUPO,tipoBanco(position));
            }
        }
        aba.setArguments(args);
        return aba;
    }

    @Override
    public int getCount() {
        return total;
    }

    @Override
    public CharSequence getPageTitle(int position){
        String tittle = "";
        ListaInfoLei menu = new ListaInfoLei(context);
        if(total == 7){
            if(position == 0){
                tittle = "Historico";
            }else {
                int tipo = position - 1;
                tittle = tipo(tipo);
            }
        }else {
            tittle = tipo(position);
        }
        return tittle;
    }

    private String tipo(int position){
        String resp;
        switch (position){
            case 0:
                resp = "+ Acessadas";
                break;
            case 1:
                resp = "Código";
                break;
            case 2:
                resp = "Estatuto";
                break;
            case 3:
                resp = "Administração";
                break;
            case 4:
                resp = "Previdenciário";
                break;
            case 5:
                resp = "Penal";
                break;
            default:
                resp = "";
                break;
        }
        return resp;
    }

    private String tipoBanco(int position){
        String resp;
        switch (position){
            case 0:
                resp = "+ Acessadas";
                break;
            case 1:
                resp = ListaInfoLei.TIPO_CODIGO;
                break;
            case 2:
                resp = ListaInfoLei.TIPO_ESTATUTO;
                break;
            case 3:
                resp = ListaInfoLei.TIPO_ADM;
                break;
            case 4:
                resp = ListaInfoLei.TIPO_PREV;
                break;
            case 5:
                resp = ListaInfoLei.TIPO_PENAL;
                break;
            default:
                resp = "";
                break;
        }
        return resp;
    }
}