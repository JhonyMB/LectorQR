package com.unfv.lector.qr.Utilidades;

public class Constantes {

    //Constantes de la tabla usuarioqr
    public static final String TABLA_USARIO_QR="USUARIO_QR";
    public static final String CAMPO_DATA="data";
    public static final String CAMPO_ID="id";

    public static String CREAR_TABLA_USUARIO_QR="CREATE TABLE "+TABLA_USARIO_QR+"("+CAMPO_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ CAMPO_DATA +" TEXT)";

}
