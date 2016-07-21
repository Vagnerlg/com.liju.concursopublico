package ggtec.lei_concursospublicos.api;

import android.provider.ContactsContract;

/**
 * Created by Vagner on 20/07/2016.
 */
class Data {

    private String data;
    private String msg;
    private String codigo;

    void setData(String data,String msg,String codigo){
        this.data = data;
        this.msg = msg;
        this.codigo = codigo;
    }


    String getData() {
        return data;
    }

    String getMsg() {
        return msg;
    }

    String getCodigo() {
        return codigo;
    }
}
