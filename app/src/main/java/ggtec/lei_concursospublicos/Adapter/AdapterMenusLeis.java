package ggtec.lei_concursospublicos.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import ggtec.lei_concursospublicos.Abas.Aba;
import ggtec.lei_concursospublicos.Sistema.ItemLei;

/**
 * Created by Vagner on 23/11/2015.
 */
public class AdapterMenusLeis extends FragmentPagerAdapter {

    private String tab_num;


    public AdapterMenusLeis(FragmentManager fm, String tab_num) {
        super(fm);
        this.tab_num = tab_num;
    }


    @Override
    public Fragment getItem(int position) {
        Aba aba = new Aba();
        Bundle args = new Bundle();
        args.putInt(AdapterAbas.KEY_TIPO,Aba.TIPO_LISTA_LEI);
        args.putString(AdapterAbas.KEY_GRUPO, tab_num);
        if(position == 0){
            args.putInt(AdapterAbas.KEY_ABA, Aba.TIPO_ABA_TITULO);
        }
        if(position == 1){
            args.putInt(AdapterAbas.KEY_ABA,Aba.TIPO_ABA_ARTIGO);
        }
        if(position == 2){
            args.putInt(AdapterAbas.KEY_ABA, Aba.TIPO_ABA_COMENTARIO);
        }
        if(position == 3){
            args.putInt(AdapterAbas.KEY_ABA, Aba.TIPO_ABA_MARCACAO);
        }
        aba.setArguments(args);
        return aba;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Títulos";
        }
        if (position == 1) {
            return "Artigos";
        }
        if (position == 2) {
            return "Comentários";
        }
        if (position == 3) {
            return "Marcaçôes";
        }
        return "";
    }
}