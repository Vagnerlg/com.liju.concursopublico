package ggtec.lei_concursospublicos.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import ggtec.lei_concursospublicos.ActivityPlayer;
import ggtec.lei_concursospublicos.ActivityTssAjuda;
import ggtec.lei_concursospublicos.Dialog.DialogoTtsGoogle;
import ggtec.lei_concursospublicos.Eventos.OnBoolListener;
import ggtec.lei_concursospublicos.Eventos.OnIntListerner;
import ggtec.lei_concursospublicos.Eventos.RequesJsonObj;
import ggtec.lei_concursospublicos.Outros.BaseAnalytics;
import ggtec.lei_concursospublicos.R;
import ggtec.lei_concursospublicos.Sistema.AdapterFala;
import ggtec.lei_concursospublicos.Sistema.Config;
import ggtec.lei_concursospublicos.Sistema.Debug;
import ggtec.lei_concursospublicos.Sistema.Internet;
import ggtec.lei_concursospublicos.Sistema.ItemLei;
import ggtec.lei_concursospublicos.Sistema.Link;
import ggtec.lei_concursospublicos.Sistema.ListaLei;
import ggtec.lei_concursospublicos.Sistema.Request;
import ggtec.lei_concursospublicos.Sistema.Usuario;

/**
 * Created by paulruiz on 10/28/14.
 * modifeicado por vagner gonçalves
 * Atualizado por vagner 11/11/2015
 * <p/>
 * Versao 2 vagner 21/01/16
 * <p/>
 * P0 service
 * p1a notificação
 * p1b activityPlayer
 * p1c internet
 */
public class MediaPlayerService extends Service implements AudioManager.OnAudioFocusChangeListener {

    //versao 2
    public static Boolean playerWording = false;

    //chave de comandos publicos
    public static final String ACTION_INICIAR = "action_iniciar";
    public static final String ACTION_PLAY = "action_play";
    public static final String ACTION_PAUSE = "action_pause";
    public static final String ACTION_NEXT = "action_next";
    public static final String ACTION_PREVIOUS = "action_previous";
    public static final String ACTION_STOP = "action_stop";
    public static final String LATENCIA_MAIS_LENTO = "muito lento";
    public static final String LATENCIA_MAIS_RAPIDO = "muito rapido";

    //chaves de valores de busca de mais trechos de leis para a leitura
    public static final String NEW_LIST = "new_list";
    public static final String TAB_NUM = "tab_num";
    public static final String LIMIT_TAB = "limit_tab";
    public static final String USER_ID = "user_id";
    public static final String USER_PREMIUM = "user_premium";

    //variaveis para uso nas chaves acima
    private String tab_num;
    private String user_id;
    private String limit_tab;
    private Boolean isPremiun = false;

    //inicialização de variaveis usadas em toda a classe
    public static final int AC_PLAY = 1;
    public static final int AC_PAUSE = 2;
    public static ArrayList<ItemLei> allTrecho = null;
    private int pointer = 0;
    private TextToSpeech tts;
    private int resultSpeech;
    public static String notiTitulo = null;
    public static String notiTexto = null;
    private final String DB = "DB_MediaPlayerService";
    private NotificationManager mNotificationManager;

    private final IBinder mBinder = new LocalBinder();
    private OnIntListerner onIntListerner = null;

    //var para limitar tempo de uso de não premium
    private String dataBanco = null;
    private String hoje = null;
    private Long timeCont = null;


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        loadTTS();
        Debug.d("Iniciado");
        playerWording = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(DB, "flags: " + flags + " startID: " + startId);
        controller(intent);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        playerWording = false;
        Debug.d("Destruido");
    }

    @Override
    public void onAudioFocusChange(int i) {
        switch (i) {
            case AudioManager.AUDIOFOCUS_GAIN:
                Log.d(DB, "1 Audio Focus Gain");
                // resume playback
                //play();
                break;

            case AudioManager.AUDIOFOCUS_LOSS:
                Log.d(DB, "2 Audio Focus perda longa");
                // video do youtube (usuario iniciou o video)
                //quando pausa não retorna again
                //retorna quando o app e finalizado
                pause();
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                Log.d(DB, "3 Audio Focus perda curta");
                //ligação telefonica ao fim da ligação retorna no again
                pause();
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                Log.d(DB, "4 Audio Focus perda curta com volta");
                //notificação perde mas apos a toque de notificação volta no again
                break;
        }
    }

    private void loadTTS() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if (i == TextToSpeech.SUCCESS) {
                            resultSpeech = i;
                            Debug.d("TTS iniciado");
                            tts.setLanguage(Locale.getDefault());
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                                    @Override
                                    public void onDone(String s) {
                                        Debug.d("onDone " + pointer);
                                        play();
                                    }

                                    @Override
                                    public void onError(String s) {
                                    }

                                    @Override
                                    public void onStart(String s) {
                                    }
                                });
                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                tts.speak(" ", TextToSpeech.QUEUE_FLUSH, null, "tag_player");
                            } else {
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "tag_player");
                                tts.speak(" ", TextToSpeech.QUEUE_FLUSH, map);
                            }
                        }
                    }
                }, "com.google.android.tts");
                List<TextToSpeech.EngineInfo> listaTts =  tts.getEngines();
                boolean isTssGoogle = false;
                if(tts.getDefaultEngine() != null) {
                    if (tts.getDefaultEngine().equalsIgnoreCase("com.google.android.tts")) {
                        isTssGoogle = true;
                    }
                }else {
                    for (int i = 0; listaTts.size() > i; i++) {
                        if (listaTts.get(i).name.equalsIgnoreCase("com.google.android.tts")) {
                            isTssGoogle = true;
                        }
                    }
                }
                if(!isTssGoogle){
                    Intent intent = new Intent(getBaseContext(), ActivityTssAjuda.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        }).start();
    }

    //metodo responsavel pela busca de leis e direcionar comandos
    private void controller(Intent intent) {
        //verificar comandas enviados
        //1º pedido de novos trechos de lei
        if (intent != null) {

            if (intent.getStringExtra(NEW_LIST) != null && intent.getStringExtra(NEW_LIST).equalsIgnoreCase(NEW_LIST)) {
                Log.d(DB, "Intent com nova lista");
                pointer = intent.getIntExtra(LIMIT_TAB, 0);
                tab_num = intent.getStringExtra(TAB_NUM);
                user_id = Usuario.getInstance(getBaseContext()).getId()+"";
                Usuario.getInstance(getBaseContext()).isPremium(new Usuario.OnBoll() {
                    @Override
                    public void resp(Boolean boll) {
                        isPremiun = boll;
                    }
                });
                datas();
            }

            //2º direcionar para acao
            String action = intent.getAction();
            if (action != null) {
                if (action.equalsIgnoreCase(ACTION_PLAY)) {
                    play();
                } else if (action.equalsIgnoreCase(ACTION_PAUSE)) {
                    pause();
                } else if (action.equalsIgnoreCase(ACTION_PREVIOUS)) {
                    previous();
                } else if (action.equalsIgnoreCase(ACTION_NEXT)) {
                    next();
                } else if (action.equalsIgnoreCase(ACTION_STOP)) {
                    stop();
                }
            }
        }
    }

    private void play() {
        if (tts != null) {
            if (resultSpeech == TextToSpeech.SUCCESS) {
                if (allTrecho.size() > pointer) {
                    notificacao(AC_PLAY);
                    if (onIntListerner != null) {
                        onIntListerner.response(pointer);
                    }
                    loadPlusTrechoLei();
                    if (timePlayer()) {
                        AdapterFala mAF = new AdapterFala(allTrecho.get(pointer));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            tts.speak(mAF.fala(), TextToSpeech.QUEUE_FLUSH, null, "tag_player");
                        } else {
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "tag_player");
                            tts.speak(mAF.fala(), TextToSpeech.QUEUE_FLUSH, map);
                        }
                        if (pointer == allTrecho.size() && fimLoad) {
                            stop();
                        } else {
                            pointer++;
                        }
                    } else {
                        Log.d(DB, "Texto de expirado");
                        String fala = "O tempo promocional acabou. Seja Premium e por a partir de um real e 19 centavos ao mês ouça as leis de forma ilimitada";
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            tts.speak(fala, TextToSpeech.QUEUE_FLUSH, null, null);
                        } else {
                            tts.speak(fala, TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                }
            }
        } else {
            resultSpeech = -1;
            loadTTS();
        }
    }

    private void next() {
        int ponter2 = pointer + 10;
        if (allTrecho.size() > ponter2) {
            while (allTrecho.get(pointer).getTipo() > 5 && pointer < ponter2) {
                Log.d(DB, "Busca Next " + allTrecho.get(pointer).getTexto());
                pointer++;
            }
            Log.d(DB, "Next escolhido " + allTrecho.get(pointer).getTexto());
            pauseNN();
            play();
        }
    }

    private void previous() {
        pointer--;
        pointer--;
        int ponter2 = pointer - 10;
        if (ponter2 >= 0) {
            while (allTrecho.get(pointer).getTipo() > 5 && pointer > ponter2) {
                Log.d(DB, "Busca previous " + allTrecho.get(pointer).getTexto());
                pointer--;
            }
            Log.d(DB, "Previous escolhido " + allTrecho.get(pointer).getTexto());
            pauseNN();
            play();
        } else {
            pointer = 0;
        }
    }

    private void pause() {
        if (tts != null) {
            tts.shutdown();
            tts = null;
            notificacao(AC_PAUSE);
        }
    }

    //pause sem notificação
    private void pauseNN() {
        if (tts != null) {
            tts.shutdown();
            tts = null;
        }
    }

    private void stop() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            tts = null;
        }
        mNotificationManager.cancel(1);
        //mNotificationManager.cancel(2);
        mNotificationManager = null;
        stopForeground(true);
        stopSelf();
    }

    private float velocidade = 1.0f;
    public void maisLento(){
        if (tts != null) {
            if(velocidade == 0.5f){
                Toast.makeText(getBaseContext(), "A voz está o mais lenta possível.", Toast.LENGTH_LONG).show();
            }else if(velocidade == 0.7f){
                tts.setSpeechRate(0.5f);
                Toast.makeText(getBaseContext(), "Voz muito lenta.\n" +
                        "Aguarde o próximo trecho para alterar a voz.", Toast.LENGTH_LONG).show();
                velocidade = 0.5f;
            }else if(velocidade == 1.0f){
                tts.setSpeechRate(0.7f);
                Toast.makeText(getBaseContext(), "Voz lenta.\n" +
                        "Aguarde o próximo trecho para alterar a voz.", Toast.LENGTH_LONG).show();
                velocidade = 0.7f;
            }else if(velocidade == 1.5f){
                tts.setSpeechRate(1.0f);
                Toast.makeText(getBaseContext(), "Voz normal.\n" +
                        "Aguarde o próximo trecho para alterar a voz.", Toast.LENGTH_LONG).show();
                velocidade = 1.0f;
            }else  if(velocidade == 2.0f){
                tts.setSpeechRate(1.5f);
                Toast.makeText(getBaseContext(), "Voz rápida.\n" +
                        "Aguarde o próximo trecho para alterar a voz.", Toast.LENGTH_LONG).show();
                velocidade = 1.5f;
            }
        }
    }

    public void maisRapido(){
        if(velocidade == 0.5f){
            tts.setSpeechRate(0.7f);
            Toast.makeText(getBaseContext(), "Voz lenta.\nAguarde o próximo trecho para alterar a voz.", Toast.LENGTH_LONG).show();
            velocidade = 0.7f;
        }else if(velocidade == 0.7f){
            tts.setSpeechRate(1.0f);
            Toast.makeText(getBaseContext(), "Voz normal.\n" +
                    "Aguarde o próximo trecho para alterar a voz.", Toast.LENGTH_LONG).show();
            velocidade = 1.0f;
        }else if(velocidade == 1.0f){
            tts.setSpeechRate(1.5f);
            Toast.makeText(getBaseContext(), "Voz rápida.\n" +
                    "Aguarde o próximo trecho para alterar a voz.", Toast.LENGTH_LONG).show();
            velocidade = 1.5f;
        }else if(velocidade == 1.5f){
            tts.setSpeechRate(2.0f);
            Toast.makeText(getBaseContext(), "Voz muito rápida.\n" +
                    "Aguarde o próximo trecho para alterar a voz.", Toast.LENGTH_LONG).show();
            velocidade = 2.0f;
        }else if(velocidade == 2.0f){
            Toast.makeText(getBaseContext(), "A voz mais rápida possível.", Toast.LENGTH_LONG).show();
        }
    }

    private Voice factary(Voice voice,int latencia){
        Voice newVoice = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            newVoice = new Voice(voice.getName(),voice.getLocale(),voice.getQuality(),latencia,voice.isNetworkConnectionRequired(),voice.getFeatures());
        }
        return newVoice;
    }

    //----------------------------------------------------------------------------------------------
    //------------------------------------NOTIFICAÇÃO-----------------------------------------------
    //----------------------------------------------------------------------------------------------
    private void notificacao(int tipo) {
        Notification mNotification = null;
        String mTitulo = null;
        String mTexto = null;

        if (tipo == AC_PAUSE) {
            mTitulo = "Player de Lei";
            mTexto = "Pause";
        } else {
            if (allTrecho.get(pointer).getClasse().equalsIgnoreCase("titulo")) {
                notiTitulo = allTrecho.get(pointer).getTexto();
            }
            mTitulo = notiTitulo;
            mTexto = allTrecho.get(pointer).getTexto();
            notiTexto = allTrecho.get(pointer).getTexto();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Notification.Action mAction = null;
            mAction = new Notification.Action.Builder(android.R.drawable.ic_media_pause, null,
                    PendingIntent.getService(getApplicationContext(), 1,
                            new Intent(getApplicationContext(), MediaPlayerService.class).setAction(ACTION_PAUSE), PendingIntent.FLAG_CANCEL_CURRENT)).build();

            if (tipo == AC_PAUSE) {
                mAction = new Notification.Action.Builder(android.R.drawable.ic_media_play, null,
                        PendingIntent.getService(getApplicationContext(), 1,
                                new Intent(getApplicationContext(), MediaPlayerService.class).setAction(ACTION_PLAY), PendingIntent.FLAG_CANCEL_CURRENT)).build();
            }

            mNotification = new Notification.Builder(getApplicationContext())
                    // Show controls on lock screen even when user hides sensitive content.
                    .setVisibility(Notification.VISIBILITY_PUBLIC)
                    .setSmallIcon(R.drawable.ic_notifiction)
                    .setContentTitle(mTitulo)
                    .setContentText(mTexto)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_media_logo))
                            // Add media control buttons that invoke intents in your media service
                    .addAction(
                            new Notification.Action.Builder(android.R.drawable.ic_media_previous, null,
                                    PendingIntent.getService(getApplicationContext(), 1,
                                            new Intent(getApplicationContext(), MediaPlayerService.class).setAction(ACTION_PREVIOUS), PendingIntent.FLAG_CANCEL_CURRENT)).build()
                    ).addAction(
                            mAction
                    ).addAction(
                            new Notification.Action.Builder(android.R.drawable.ic_media_next, null,
                                    PendingIntent.getService(getApplicationContext(), 1,
                                            new Intent(getApplicationContext(), MediaPlayerService.class).setAction(ACTION_NEXT), PendingIntent.FLAG_CANCEL_CURRENT)).build()
                    ).addAction(
                            new Notification.Action.Builder(android.R.drawable.ic_menu_close_clear_cancel, null,
                                    PendingIntent.getService(getApplicationContext(), 1,
                                            new Intent(getApplicationContext(), MediaPlayerService.class).setAction(ACTION_STOP), PendingIntent.FLAG_CANCEL_CURRENT)).build()
                    )
                    .setContentIntent(
                            PendingIntent.getActivity(getApplicationContext(), 0,
                                    new Intent(getApplicationContext(), ActivityPlayer.class), PendingIntent.FLAG_UPDATE_CURRENT)
                    )// Apply the media style template
                    .setStyle(new Notification.MediaStyle()
                            .setShowActionsInCompactView(0, 1, 2))
                    .build();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_notifiction)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_media_logo))
                    .setContentIntent(
                            PendingIntent.getActivity(getApplicationContext(), 0,
                                    new Intent(getApplicationContext(), ActivityPlayer.class), PendingIntent.FLAG_UPDATE_CURRENT)
                    )
                    .setContentTitle(mTitulo)
                    .setContentText(mTexto);

            if (tipo == AC_PAUSE) {
                builder.addAction(
                        new NotificationCompat.Action.Builder(android.R.drawable.ic_media_play, null,
                                PendingIntent.getService(getApplicationContext(), 1,
                                        new Intent(getApplicationContext(), MediaPlayerService.class).setAction(ACTION_PLAY), PendingIntent.FLAG_CANCEL_CURRENT)).build()
                ).addAction(
                        new NotificationCompat.Action.Builder(android.R.drawable.ic_menu_close_clear_cancel, null,
                                PendingIntent.getService(getApplicationContext(), 1,
                                        new Intent(getApplicationContext(), MediaPlayerService.class).setAction(ACTION_STOP), PendingIntent.FLAG_CANCEL_CURRENT)).build()
                );
            } else {
                builder.addAction(
                        new NotificationCompat.Action.Builder(android.R.drawable.ic_media_previous, null,
                                PendingIntent.getService(getApplicationContext(), 1,
                                        new Intent(getApplicationContext(), MediaPlayerService.class).setAction(ACTION_PREVIOUS), PendingIntent.FLAG_CANCEL_CURRENT)).build()
                ).addAction(
                        new NotificationCompat.Action.Builder(android.R.drawable.ic_media_pause, null,
                                PendingIntent.getService(getApplicationContext(), 1,
                                        new Intent(getApplicationContext(), MediaPlayerService.class).setAction(ACTION_PAUSE), PendingIntent.FLAG_CANCEL_CURRENT)).build()
                ).addAction(
                        new NotificationCompat.Action.Builder(android.R.drawable.ic_media_next, null,
                                PendingIntent.getService(getApplicationContext(), 1,
                                        new Intent(getApplicationContext(), MediaPlayerService.class).setAction(ACTION_NEXT), PendingIntent.FLAG_CANCEL_CURRENT)).build()
                );
            }
            mNotification = builder.build();
        } else {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_notifiction)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_media_logo))
                            .setContentTitle(mTitulo)
                            .setContentText(mTexto)
                            .setContentIntent(
                                    PendingIntent.getActivity(getApplicationContext(), 0,
                                            new Intent(getApplicationContext(), ActivityPlayer.class), PendingIntent.FLAG_UPDATE_CURRENT)
                            ).setDeleteIntent(
                            PendingIntent.getService(getApplicationContext(), 0,
                                    new Intent(getApplicationContext(), MediaPlayerService.class).setAction(ACTION_STOP), PendingIntent.FLAG_CANCEL_CURRENT)
                    );
            mNotification = mBuilder.build();
        }
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mNotification);
        startForeground(1, mNotification);
    }

    private Boolean isLoad = false;
    private Boolean fimLoad = false;

    private void loadPlusTrechoLei() {
        int ponter2 = 0;
        if (pointer > 30) {
            ponter2 = allTrecho.size() - 30;
            if (pointer > ponter2) {
                if (!isLoad && !fimLoad) {
                    //busca mais leis
                    isLoad = true;
                    ListaLei listaLei = new ListaLei(getBaseContext(),null,tab_num);
                    listaLei.getLeiBack(allTrecho.get(allTrecho.size() - 1).getID(),
                            new ListaLei.OnItemLei() {
                                @Override
                                public void resp(ArrayList<ItemLei> itemLeis) {
                                    allTrecho.addAll(itemLeis);
                                    int limit = itemLeis.get(itemLeis.size() - 1).getID();
                                    limit_tab = "" + limit;
                                    isLoad = false;
                                }
                            });
                }
            }
        }
    }

    private Boolean timePlayer() {
        Boolean resp = true;
        //verificar se não é premium
        if (!isPremiun) {
            if (hoje == null || timeCont == null) {
                datas();
            } else if (hoje.equalsIgnoreCase(dataBanco)) {
                Log.d(DB, "Tempo Expirado " + hoje);
                resp = false;
            } else {
                Long soma;
                GregorianCalendar agora = new GregorianCalendar();
                soma = agora.getTimeInMillis() - timeCont;
                Log.d(DB, "Tempo de Player " + soma);
                if (soma > 420000) {//7 minutos //420000
                    Log.d(DB, "Tempo Expirado " + soma);
                    Config.getInstance(getBaseContext()).setTimePlayer(hoje);
                    BaseAnalytics.getInstance(getBaseContext()).evento(BaseAnalytics.CAT_SISTEMA,"tempo Limite de player gratis",null);
                    resp = false;
                }
            }
        }
        return resp;
    }

    private void datas() {
        //tempo limitado
        if (!isPremiun) {
            dataBanco = Config.getInstance(getBaseContext()).getTimePlayer();
            Log.d(DB, "Banco Data " + dataBanco);
            GregorianCalendar dataG = new GregorianCalendar();
            Date data = dataG.getTime();
            DateFormat formata = DateFormat.getDateInstance();
            hoje = formata.format(data);
            Log.d(DB, "Hoje " + hoje);
            timeCont = dataG.getTimeInMillis();
            Log.d(DB, "timeCont" + timeCont);
        }
    }

    public class LocalBinder extends Binder {
        public MediaPlayerService getService() {
            // Return this instance of LocalService so clients can call public methods
            return MediaPlayerService.this;
        }
    }

    public void setListener(OnIntListerner listerner) {
        onIntListerner = listerner;
    }

}