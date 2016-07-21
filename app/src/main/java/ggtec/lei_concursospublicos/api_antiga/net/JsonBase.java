package ggtec.lei_concursospublicos.api_antiga.net;

import org.json.JSONException;
import org.json.JSONObject;

import ggtec.lei_concursospublicos.api.Request;

/**
 * Created by Vagner on 20/07/2016.
 */
abstract class JsonBase{


    String validaJsonResponse(String dataJson, String tipo, String mensagem) {
        String dataFinal = null;
        if(tipo.equalsIgnoreCase(Request.SUCESS)){
            try {
                JSONObject jsonRespose = new JSONObject(dataJson);
                if(jsonRespose.has("response")){
                    JSONObject jsonData = jsonRespose.getJSONObject("response");
                    if(jsonData.has("data")){
                        dataFinal = jsonData.getString("data");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return dataFinal;
    }
}
