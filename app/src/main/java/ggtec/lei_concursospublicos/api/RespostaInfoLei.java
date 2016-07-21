package ggtec.lei_concursospublicos.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vagner on 20/07/2016.
 */
public class RespostaInfoLei extends Resposta{

    @Override
    void resposta(Data data) {
        super.base(data.getData(), data.getCodigo(),data.getMsg());
        if(codigo.equalsIgnoreCase(Requisicao.SUCESS)){
            List<InfoLei> lista = new ArrayList<>();
            try {
                JSONArray listaInfoLei = new JSONArray(stringData);
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
                        lista.add(new InfoLei(tipo,numero,ano,nome,descricao,tags));
                        this.data = data;
                        ((ListaInfolei) this.data).adcionar(lista);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
