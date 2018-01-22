package com.example.juanjo.proyecto_firebase.Model;

import java.util.ArrayList;

/**
 * Created by Juanjo on 13/1/18.
 */

public class Producto {

    private String nombre;
    private String descripcion;
    private String categoria;
    private String codigo_usuario;
    private String precio;

    public Producto(String nombre, String descripcion, String categoria, String codigo_usuario, String precio) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.codigo_usuario = codigo_usuario;
        this.precio = precio;
    }


    public Producto(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCodigo_usuario() {
        return codigo_usuario;
    }

    public void setCodigo_usuario(String codigo_usuario) {
        this.codigo_usuario = codigo_usuario;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }


}


