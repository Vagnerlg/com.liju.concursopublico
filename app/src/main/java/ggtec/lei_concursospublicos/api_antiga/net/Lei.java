package ggtec.lei_concursospublicos.api_antiga.net;

import ggtec.lei_concursospublicos.api_antiga.ItemInfoLei;
import ggtec.lei_concursospublicos.api_antiga.Usuario;

/**
 * Created by Vagner on 19/07/2016.
 */
class Lei extends Body{

    private int numero;
    private String tipo;
    private Usuario user;

    Lei(ItemInfoLei itemInfoLei,Usuario user){
        numero = itemInfoLei.getNumero();
        tipo = itemInfoLei.getTipo();
        this.user = user;
        url = "lei";
        post.put("tipo",tipo);
        post.put("numero", numero + "");
        //post.put("user",""+Usuario.getId());
    }

    protected Lei lei(int inicio){
        post.put("limit", inicio + "");
        get = "lei";
        return this;
    }

}