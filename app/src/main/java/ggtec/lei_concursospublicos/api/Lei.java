package ggtec.lei_concursospublicos.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vagner on 20/07/2016.
 */
public class Lei {

    private int total = 0;
    private int pagina = 0;
    private int inicio = 0;
    private InfoLei infoLei;
    private Map<Integer,Trecho> lista = new HashMap<>();

    Lei(InfoLei infoLei,int inicio,int pagina){
        this.infoLei = infoLei;
        this.inicio = inicio;
        this.pagina = pagina;
    }


}
