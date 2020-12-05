package com.unfv.lector.qr.Entidad;

public class UsuarioQR {

    private Integer id;
    private String dato;

    public UsuarioQR(Integer id, String dato) {
        this.id = id;
        this.dato = dato;
    }

    public UsuarioQR() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }
}
