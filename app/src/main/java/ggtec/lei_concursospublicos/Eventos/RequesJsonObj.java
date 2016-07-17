package ggtec.lei_concursospublicos.Eventos;

import org.json.JSONObject;

import java.util.ArrayList;

import ggtec.lei_concursospublicos.Sistema.ItemLei;

/**
 * Created by Vagner on 18/01/2016.
 */
public interface RequesJsonObj {
    void response(JSONObject jsonObject,String erro);
}
