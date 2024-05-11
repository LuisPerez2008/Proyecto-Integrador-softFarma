package com.pe.farmacia.modelo;

public class Usuario {

    private int id;
    private String nombre;
    private String correo;
    private String pass;
    private String rol;

    public Usuario() {
    }

    public Usuario(int id, String nombre, String correo, String pass, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.pass = pass;
        this.rol = rol;
    }
   
    public Usuario(Builder builder) {
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.correo = builder.correo;
        this.pass = builder.pass;
        this.rol = builder.rol;
    }
    //Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getPass() {
        return pass;
    }

    public String getRol() {
        return rol;
    }
    //Objeto Builder
    public static class Builder {

        private int id;
        private String nombre;
        private String correo;
        private String pass;
        private String rol;
        
        public Usuario.Builder id(int id){
            this.id = id;
            return this;
        }
        
        public Usuario.Builder nombre(String nombre){
            this.nombre = nombre;
            return this;
        }
        
        public Usuario.Builder correo(String correo){
            this.correo = correo;
            return this;
        }
        
        public Usuario.Builder pass(String pass){
            this.pass = pass;
            return this;
        }
        
        public Usuario.Builder rol(String rol){
            this.rol = rol;
            return this;
        }
        
        public Usuario build(){
            return new Usuario(this);
        }
    }

}
