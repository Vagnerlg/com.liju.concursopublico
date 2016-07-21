package ggtec.lei_concursospublicos.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Vagner on 20/07/2016.
 */
abstract class Resposta{

    String dataPai;
    String stringData;
    String codigo;
    String msg;
    Data data;

    abstract void resposta(Data data);

    void base(String data, String codigo, String msg){
        dataPai = data;
        this.msg = msg;
        this.codigo = codigo;
        if(this.codigo.equalsIgnoreCase(Requisicao.SUCESS)){
            try {
                JSONObject jsonRespose = new JSONObject(data);
                if(jsonRespose.has("response")){
                    JSONObject jsonData = jsonRespose.getJSONObject("response");
                    if(jsonData.has("data")){
                        this.stringData = jsonData.getString("data");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}