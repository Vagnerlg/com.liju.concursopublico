package ggtec.lei_concursospublicos.Sistema;

/**
 * Created by Vagner on 05/11/2015.
 */
public class AdapterFala {
    private String fala;
    public AdapterFala(ItemLei trecho){

        this.fala = trecho.getTexto().replaceAll("(?i)art\\.", "Artigo")
                    .replaceAll("§§", "parágrafos")
                    .replaceAll("§", "parágrafo ")
                    .replaceAll("\\(Redação.*dada.*pela.*\\)|\\(Incluíd.*pela.*\\)|\\(Revogad.*pela.*\\)|\\(Vide.*\\)|\\&#\\d\\d\\d|\\(Regulamento.*\\)|\"|\\(Inciso.*\\)|\\&.{3,6};", " ")
                    .replaceAll("(?i)\\sI\\W", "1º ")
                    .replaceAll("(?i)\\sII\\W", "2º ")
                    .replaceAll("(?i)\\sIII\\W", "3º ")
                    .replaceAll("(?i)\\sIV\\W", "4º ")
                    .replaceAll("(?i)\\sV\\W", "5º ")
                    .replaceAll("(?i)\\sVI\\W", "6º ")
                    .replaceAll("(?i)\\sVII\\W", "7º ")
                    .replaceAll("(?i)\\sVIII\\W", "8º ")
                    .replaceAll("(?i)\\sIX\\W", "9º ")
                    .replaceAll("(?i)\\sX\\W","10 ")
                    .replaceAll("(?i)\\sXI\\W", "11 ")
                    .replaceAll("(?i)\\sXII\\W", "12 ")
                    .replaceAll("(?i)\\sXIII\\W", "13 ")
                    .replaceAll("(?i)\\sXIV\\W", "14 ")
                    .replaceAll("(?i)\\sXV\\W", "15 ")
                    .replaceAll("(?i)\\sXVI\\W", "16 ")
                    .replaceAll("(?i)\\sXVII\\W", "17 ")
                    .replaceAll("(?i)\\sXVIII\\W", "18 ")
                    .replaceAll("(?i)\\sXIX\\W", "19 ")
                    .replaceAll("(?i)\\sXX\\W","20 ")
                    .replaceAll("(?i)\\sXXI\\W", "21 ")
                    .replaceAll("(?i)\\sXXII\\W", "22 ")
                    .replaceAll("(?i)\\sXXIII\\W", "23 ")
                    .replaceAll("(?i)\\sXXIV\\W", "24 ")
                    .replaceAll("(?i)\\sXXV\\W", "25 ")
                    .replaceAll("(?i)\\sXXVI\\W", "26 ")
                    .replaceAll("(?i)\\sXXVII\\W", "27 ")
                    .replaceAll("(?i)\\sXXVIII\\W", "28 ")
                    .replaceAll("(?i)\\sXXIX\\W", "29 ")
                    .replaceAll("(?i)\\sXXX\\W","30 ")
                    .replaceAll("(?i)\\sXXXI\\W", "31 ")
                    .replaceAll("(?i)\\sXXXII\\W", "32 ")
                    .replaceAll("(?i)\\sXXXIII\\W", "33 ")
                    .replaceAll("(?i)\\sXXXIV\\W", "34 ")
                    .replaceAll("(?i)\\sXXXV\\W", "35 ")
                    .replaceAll("(?i)\\sXXXVI\\W", "36 ")
                    .replaceAll("(?i)\\sXXXVII\\W", "37 ")
                    .replaceAll("(?i)\\sXXXVIII\\W", "38 ")
                    .replaceAll("(?i)\\sXXXIX\\W", "39 ")
                    .replaceAll("(?i)\\sXLX\\W","40 ")
                    .replaceAll("(?i)\\sXLI\\W", "41 ")
                    .replaceAll("(?i)\\sXLII\\W", "42 ")
                    .replaceAll("(?i)\\sXLIII\\W", "43 ")
                    .replaceAll("(?i)\\sXLIV\\W", "44 ")
                    .replaceAll("(?i)\\sXLV\\W", "45 ")
                    .replaceAll("(?i)\\sXLVI\\W", "46 ")
                    .replaceAll("(?i)\\sXLVII\\W", "47 ")
                    .replaceAll("(?i)\\sXLVIII\\W", "48 ")
                    .replaceAll("(?i)\\sXLIX\\W", "49 ")
                    .replaceAll("(?i)\\sL\\W","50 ")
                    .replaceAll("(?i)\\sLI\\W", "51 ")
                    .replaceAll("(?i)\\sLII\\W", "52 ")
                    .replaceAll("(?i)\\sLIII\\W", "53 ")
                    .replaceAll("(?i)\\sLIV\\W", "54 ")
                    .replaceAll("(?i)\\sLV\\W", "55 ")
                    .replaceAll("(?i)\\sLVI\\W", "56 ")
                    .replaceAll("(?i)\\sLVII\\W", "57 ")
                    .replaceAll("(?i)\\sLVIII\\W", "58 ")
                    .replaceAll("(?i)\\sLIX\\W", "59 ")
                    .replaceAll("(?i)\\sLX\\W","60 ")
                    .replaceAll("(?i)\\sLXI\\W", "61 ")
                    .replaceAll("(?i)\\sLXII\\W", "62 ")
                    .replaceAll("(?i)\\sLXIII\\W", "63 ")
                    .replaceAll("(?i)\\sLXIV\\W", "64 ")
                    .replaceAll("(?i)\\sLXV\\W", "65 ")
                    .replaceAll("(?i)\\sLXVI\\W", "66 ")
                    .replaceAll("(?i)\\sLXVII\\W", "67 ")
                    .replaceAll("(?i)\\sLXVIII\\W", "68 ")
                    .replaceAll("(?i)\\sLXIX\\W", "69 ")
                    .replaceAll("(?i)\\sLXX\\W","70 ")
                    .replaceAll("(?i)\\sLXXI\\W", "71 ")
                    .replaceAll("(?i)\\sLXXII\\W", "72 ")
                    .replaceAll("(?i)\\sLXXIII\\W", "73 ")
                    .replaceAll("(?i)\\sLXXIV\\W", "74 ")
                    .replaceAll("(?i)\\sLXXV\\W", "75 ")
                    .replaceAll("(?i)\\sLXXVI\\W", "76 ")
                    .replaceAll("(?i)\\sLXXVII\\W", "77 ")
                    .replaceAll("(?i)\\sLXXVIII\\W", "78 ")
                    .replaceAll("(?i)\\sLXXIX\\W", "79 ")
                    .replaceAll("(?i)\\sLXXX\\W","80 ")
                    .replaceAll("(?i)\\sLXXXI\\W", "81 ")
                    .replaceAll("(?i)\\sLXXXII\\W", "82 ")
                    .replaceAll("(?i)\\sLXXXIII\\W", "83 ")
                    .replaceAll("(?i)\\sLXXXIV\\W", "84 ")
                    .replaceAll("(?i)\\sLXXXV\\W", "85 ")
                    .replaceAll("(?i)\\sLXXXVI\\W", "86 ")
                    .replaceAll("(?i)\\sLXXXVII\\W", "87 ")
                    .replaceAll("(?i)\\sLXXXVIII\\W", "88 ")
                    .replaceAll("(?i)\\sLXXXIX\\W", "89 ");

        if(trecho.getClasse().equalsIgnoreCase("inciso")){
            this.fala = this.fala.replaceAll("^I\\s|^I-", "1º ")
                    .replaceAll("^II\\s|^II-", "2º ")
                    .replaceAll("^III\\s|^III-", "3º ")
                    .replaceAll("^IV\\s|^IV-", "4º ")
                    .replaceAll("^V\\s|^V-", "5º ")
                    .replaceAll("^VI\\s|^VI-", "6º ")
                    .replaceAll("^VII\\s|^VII-", "7º ")
                    .replaceAll("^VIII\\s|^VIII-", "8º ")
                    .replaceAll("^IX\\s|^IX-", "9º ")
                    .replaceAll("^X\\s|^X-","10 ")
                    .replaceAll("^XI\\s|^XI-", "11 ")
                    .replaceAll("^XII\\s|^XII-", "12 ")
                    .replaceAll("^XIII\\s|^XIII-", "13 ")
                    .replaceAll("^XIV\\s|^XIV-", "14 ")
                    .replaceAll("^XV\\s|^XV-", "15 ")
                    .replaceAll("^XVI\\s|^XVI-", "16 ")
                    .replaceAll("^XVII\\s|^XVII-", "17 ")
                    .replaceAll("^XVIII\\s|^XVIII-", "18 ")
                    .replaceAll("^XIX\\s|^XIX-", "19 ")
                    .replaceAll("^XX\\s|^XX-","20 ")
                    .replaceAll("^XXI\\s|^XXI-", "21 ")
                    .replaceAll("^XXII\\s|^XXII-", "22 ")
                    .replaceAll("^XXIII\\s|^XXIII-", "23 ")
                    .replaceAll("^XXIV\\s|^XXIV-", "24 ")
                    .replaceAll("^XXV\\s|^XXV-", "25 ")
                    .replaceAll("^XXVI\\s|^XXVI-", "26 ")
                    .replaceAll("^XXVII\\s|^XXVII-", "27 ")
                    .replaceAll("^XXVIII\\s|^XXVIII-", "28 ")
                    .replaceAll("^XXIX\\s|^XXIX-", "29 ")
                    .replaceAll("^XXX\\s|^XXX-","30 ")
                    .replaceAll("^XXXI\\s|^XXXI-", "31 ")
                    .replaceAll("^XXXII\\s|^XXXII-", "32 ")
                    .replaceAll("^XXXIII\\s|^XXXIII-", "33 ")
                    .replaceAll("^XXXIV\\s|^XXXIV-", "34 ")
                    .replaceAll("^XXXV\\s|^XXXV-", "35 ")
                    .replaceAll("^XXXVI\\s|^XXXVI-", "36 ")
                    .replaceAll("^XXXVII\\s|^XXXVII-", "37 ")
                    .replaceAll("^XXXVIII\\s|^XXXVIII-", "38 ")
                    .replaceAll("^XXXIX\\s|^XXXIX-", "39 ")
                    .replaceAll("^XLX\\s|^XLX-","40 ")
                    .replaceAll("^XLI\\s|^XLI-", "41 ")
                    .replaceAll("^XLII\\s|^XLII-", "42 ")
                    .replaceAll("^XLIII\\s|^XLIII-", "43 ")
                    .replaceAll("^XLIV\\s|^XLIV-", "44 ")
                    .replaceAll("^XLV\\s|^XLV-", "45 ")
                    .replaceAll("^XLVI\\s|^XLVI-", "46 ")
                    .replaceAll("^XLVII\\s|^XLVII-", "47 ")
                    .replaceAll("^XLVIII\\s|^XLVIII-", "48 ")
                    .replaceAll("^XLIX\\s|^XLIX-", "49 ")
                    .replaceAll("^L\\s|^L-","50 ")
                    .replaceAll("^LI\\s|^LI-", "51 ")
                    .replaceAll("^LII\\s|^LII-", "52 ")
                    .replaceAll("^LIII\\s|^LIII-", "53 ")
                    .replaceAll("^LIV\\s|^LIV-", "54 ")
                    .replaceAll("^LV\\s|^LV-", "55 ")
                    .replaceAll("^LVI\\s|^LVI-", "56 ")
                    .replaceAll("^LVII\\s|^LVII-", "57 ")
                    .replaceAll("^LVIII\\s|^LVIII-", "58 ")
                    .replaceAll("^LIX\\s|^LIX-", "59 ")
                    .replaceAll("^LX\\s|^LX-","60 ")
                    .replaceAll("^LXI\\s|^LXI-", "61 ")
                    .replaceAll("^LXII\\s|^LXII-", "62 ")
                    .replaceAll("^LXIII\\s|^LXIII-", "63 ")
                    .replaceAll("^LXIV\\s|^LXIV-", "64 ")
                    .replaceAll("^LXV\\s|^LXV-", "65 ")
                    .replaceAll("^LXVI\\s|^LXVI-", "66 ")
                    .replaceAll("^LXVII\\s|^LXVII-", "67 ")
                    .replaceAll("^LXVIII\\s|^LXVIII-", "68 ")
                    .replaceAll("^LXIX\\s|^LXIX-", "69 ")
                    .replaceAll("^LXX\\s|^LXX-","70 ")
                    .replaceAll("^LXXI\\s|^LXXI-", "71 ")
                    .replaceAll("^LXXII\\s|^LXXII-", "72 ")
                    .replaceAll("^LXXIII\\s|^LXXIII-", "73 ")
                    .replaceAll("^LXXIV\\s|^LXXIV-", "74 ")
                    .replaceAll("^LXXV\\s|^LXXV-", "75 ")
                    .replaceAll("^LXXVI\\s|^LXXVI-", "76 ")
                    .replaceAll("^LXXVII\\s|^LXXVII-", "77 ")
                    .replaceAll("^LXXVIII\\s|^LXXVIII-", "78 ")
                    .replaceAll("^LXXIX\\s|^LXXIX-", "79 ")
                    .replaceAll("^LXXX\\s|^LXXX-","80 ")
                    .replaceAll("^LXXXI\\s|^LXXXI-", "81 ")
                    .replaceAll("^LXXXII\\s|^LXXXII-", "82 ")
                    .replaceAll("^LXXXIII\\s|^LXXXIII-", "83 ")
                    .replaceAll("^LXXXIV\\s|^LXXXIV-", "84 ")
                    .replaceAll("^LXXXV\\s|^LXXXV-", "85 ")
                    .replaceAll("^LXXXVI\\s|^LXXXVI-", "86 ")
                    .replaceAll("^LXXXVII\\s|^LXXXVII-", "87 ")
                    .replaceAll("^LXXXVIII\\s|^LXXXVIII-", "88 ")
                    .replaceAll("^LXXXIX\\s|^LXXXIX-", "89 ");
            this.fala = "Inciso "+this.fala;
        }

        if(trecho.getClasse().equalsIgnoreCase("revogado")){
            this.fala = " ";
        }

        if(trecho.getClasse().equalsIgnoreCase("alinea")){
            this.fala = "Alinea "+this.fala;
        }

    }

    public String fala(){
        return this.fala;
    }
}
