/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import model.Usuario;
import view.Login;
import view.Perfil;
import view.Registro;

/**
 * Clase Principal
 * @author Santiago
 */
public class Main {
    public static void main(String args[]) {
        Usuario u = null;
        Login fl = new Login();
        Perfil fp = new Perfil();
        Registro fr = new Registro();
        
        Controlador control = new Controlador(u, fl, fp,fr);
        
        control.inicioLogin();
        
        
    }
}
