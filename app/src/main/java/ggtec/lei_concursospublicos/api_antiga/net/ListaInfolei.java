package ggtec.lei_concursospublicos.api_antiga.net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ggtec.lei_concursospublicos.api_antiga.ItemInfoLei;

/**
 * Created by Vagner on 20/07/2016.
 */
class ListaInfolei extends JsonBase implements Resposta{


    @Override
    public void response(String dataJson, String tipo, String mensagem) {
        String data = validaJsonResponse(dataJson,tipo,mensagem);
        if(data != null){
            List<ItemInfoLei> listaInfolei = listaInfolei(data);
        }
    }

    private List<ItemInfoLei> listaInfolei(String data){
        List<ItemInfoLei> lista = new ArrayList<>();
        try {
            JSONArray listaInfoLei = new JSONArray(data);
            int tamanho  = listaInfoLei.length();
            for (int interacao = 0 ;interacao < tamanho; interacao++){
                JSONObject jsonInfoLei = listaInfoLei.getJSONObject(interacao);
                boolean temCampos = jsonInfoLei.has("tipo")&&jsonInfoLei.has("numero")&&jsonInfoLei.has("ano")&&jsonInfoLei.has("nome")
                        &&jsonInfoLei.has("descricao")&&jsonInfoLei.has("tags");
                if (temCampos) {
                    String tipo = jsonInfoLei.getString("tipo");
                    int numero = Integer.parseInt(jsonInfoLei.getString("numero"));
                    int ano = Integer.parseInt(jsonInfoLei.getString("ano"));
                    String descricao = jsonInfoLei.getString("descricao");
                    String nome = jsonInfoLei.getString("nome");
                    String tags = jsonInfoLei.getString("tags");
                    lista.add(new ItemInfoLei(tipo,numero,ano,nome,descricao,tags));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
