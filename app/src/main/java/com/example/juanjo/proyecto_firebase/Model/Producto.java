package com.example.juanjo.proyecto_firebase.Model;

import java.util.ArrayList;

/**
 * Created by Juanjo on 13/1/18.
 */

public class Producto {

    private String nombre;
    private String descripcion;
    private String categoria;
    private String precio;

    public Producto(String nombre, String descripcion, String categoria, String precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
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

    public String  getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }


}


