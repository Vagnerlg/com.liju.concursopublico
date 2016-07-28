package ggtec.lei_concursospublicos.api;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vagner on 20/07/2016.
 */
public class ListaInfolei extends Data {

    private List<InfoLei> lista = new ArrayList<>();


    void adciona(InfoLei itemlei){
        lista.add(itemlei);
    }

    InfoLei pega(int posicao){
        return lista.get(posicao);
    }

    void adcionar(List<InfoLei> listaall){
        this.lista.addAll(listaall);
    }

}
