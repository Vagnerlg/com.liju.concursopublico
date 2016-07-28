package ggtec.lei_concursospublicos.api_antiga;

import android.util.Log;

/**
 * Created by Vagner on 06/01/2016.
 */
public class Debug {

    public static void d(String mensagem) {
        Exception ex = new Exception();
        StackTraceElement[] trace = ex.getStackTrace();
        String callingClass = trace[1].getClassName();
        callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
        Log.d("DB_"+callingClass,trace[1].getMethodName()+": "+mensagem);

    }

}
