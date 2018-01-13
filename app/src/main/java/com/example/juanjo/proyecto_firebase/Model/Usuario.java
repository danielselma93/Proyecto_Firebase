package com.example.juanjo.proyecto_firebase.Model;

/**
 * Created by Juanjo on 15/12/17.
 */

public class Usuario {

    private String nombre;
    private String apellidos;
    private String correo;
    private String direccion;



    public Usuario(String nombre, String apellidos, String correo, String direccion) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.direccion = direccion;

    }

    public Usuario(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


}
