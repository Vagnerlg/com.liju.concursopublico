package ggtec.lei_concursospublicos.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ggtec.lei_concursospublicos.Abas.AbaAjuda;
import ggtec.lei_concursospublicos.Sistema.Ajuda;

/**
 * Created by Vagner on 25/02/2016.
 */
public class AdapterAjuda extends FragmentPagerAdapter {

    private String[] titulo;
    private String[] subtitulo;
    private int[] imagem;
    private String[] descricao;
    private int total;

    public AdapterAjuda(FragmentManager fm, Ajuda ajuda) {
        super(fm);
        titulo = ajuda.getTitulo();
        subtitulo = ajuda.getSubTitulo();
        imagem = ajuda.getImagem();
        descricao = ajuda.getDescricao();
        total = ajuda.getTotal();
    }

    @Override
    public Fragment getItem(int position) {
        AbaAjuda abaAjuda = new AbaAjuda();
        Bundle args = new Bundle();
        args.putString(AbaAjuda.KEY_TITULO,titulo[position]);
        args.putString(AbaAjuda.KEY_SUBTITULO,subtitulo[position]);
        args.putInt(AbaAjuda.KEY_IMAGEM,imagem[position]);
        args.putString(AbaAjuda.KEY_DESCRICAO,descricao[position]);
        abaAjuda.setArguments(args);
        return abaAjuda;
    }

    @Override
    public int getCount() {
        return total;
    }
}
