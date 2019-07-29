/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import model.Usuario;
import view.Login;
import view.Perfil;
import view.Registro;

/**
 * Controlador General
 * @author Santiago
 */
public class Controlador implements ActionListener, MouseListener{

    // Atributos
    public Usuario usuario;
    public Login formLogin;
    public Perfil formPerfil;
    public Registro formRegistro;
    
    public ArrayList<Usuario> Usuarios = new ArrayList<Usuario>();
    
    // Constructor
    public Controlador(Usuario usuario, Login formLogin, Perfil formPerfil, Registro formRegistro) {
        this.usuario = usuario;
        this.formLogin = formLogin;
        this.formPerfil = formPerfil;
        this.formRegistro = formRegistro;
        
        formLogin.btnEntrar.addActionListener(this);
        formLogin.lblRegistro.addMouseListener(this);
        
        formRegistro.btnRegistrar.addActionListener(this);
        formRegistro.lblVolver.addMouseListener(this);
    }
    
    // INICIOS DE VENTANAS.
    
    /**
     * Metodo que configura la ventana de login
     */
    public void inicioLogin() {
        formLogin.setTitle("Login");
        formLogin.setLocationRelativeTo(null);
        formLogin.setResizable(false);
        
        formLogin.lblRegistro.setForeground(Color.black);
        
        formLogin.setVisible(true);
    }
    
    public void inicioPerfil(Usuario u) {
        if (u.getGenero().equals("Masculino")) {
            formPerfil.setTitle("Bienvenido " + u.getNombre());
        } else if (u.getGenero().equals("Femenino")) {
            formPerfil.setTitle("Bienvenida " + u.getNombre());
        } else if (u.getGenero().equals("Prefiero no decirlo")) {
            formPerfil.setTitle("Bienvenid@ " + u.getNombre());
        }
        formPerfil.setLocationRelativeTo(null);
        formPerfil.setResizable(false);
        
        formPerfil.setVisible(true);
    }
    
    /**
     * Metodo que configura la ventana de Registro
     */
    public void inicioRegistro() {
        formRegistro.setTitle("Registrate");
        formRegistro.setLocationRelativeTo(null);
        formRegistro.setResizable(false);
        formRegistro.setVisible(true);
        
        formRegistro.lblVolver.setForeground(Color.black);
        
        // Configurando el combo box de edad
        for (int i = 1; i <= 180; i++) {
            formRegistro.cbxEdad.addItem(i + " años");
        }
    }
    
    /**
     * Funcion que nos guarda los datos del usuario en un obj Usuario el cual
     * lo guardamos en nuestra lista Usuarios
     */
    public void registrar() {
        String nombre = formRegistro.txtNombre.getText().trim();
        String apellido = formRegistro.txtApellido.getText().trim();
        String username = formRegistro.txtUsername.getText().trim();
        String password = formRegistro.txtConfirmPass.getText().trim();
        String genero = String.valueOf(formRegistro.cbxGenero.getSelectedItem());
        int edad = Integer.parseInt(String.valueOf(formRegistro.cbxEdad.getSelectedItem()).substring(0, 0));
        
        Usuario u = new Usuario(nombre, apellido, username, password, genero, edad);
        Usuarios.add(u);
        
    }
    
    // Acciones de los botones.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == formRegistro.btnRegistrar) {
            if (comprobacionCampos()) {
                if (formRegistro.txtPassword.getText().trim().equals(formRegistro.txtConfirmPass.getText().trim())) {
                    registrar();
                    formRegistro.dispose();
                    JOptionPane.showMessageDialog(null, "Registro exitoso");
                    formLogin.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Llene todos los campos");
            }
        }
        
        if (e.getSource() == formLogin.btnEntrar) {
            if (!formLogin.txtUsuario.getText().trim().equals("") && !formLogin.txtContra.getText().trim().equals("")) {
                if (Usuarios.size() < 0) {
                    for(int i = 0; i <= Usuarios.size(); i++) {
                        if (formLogin.txtUsuario.getText().trim().equals(Usuarios.get(i).getUsername()) &&
                                formLogin.txtContra.getText().trim().equals(Usuarios.get(i).getPassword())) {
                            formLogin.dispose();
                            inicioPerfil(Usuarios.get(i));
                        } else {
                            JOptionPane.showMessageDialog(null, "Datos incorrectos.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No hay usuarios Registrados.");
                    limpiarLogin();
                }
               
            } else {
                JOptionPane.showMessageDialog(null, "Llene todos los campos.");
                limpiarLogin();
            }
        }
    }

    // Acciones que capturan el mouse
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == formLogin.lblRegistro) {
            formLogin.dispose();
            inicioRegistro();
        }
        if (e.getSource() == formRegistro.lblVolver) {
            formRegistro.dispose();
            inicioLogin();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
    
    // Hover: Cuando el mouse toca el elemento.
    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == formLogin.lblRegistro) {
            formLogin.lblRegistro.setForeground(Color.red);
        }
        if (e.getSource() == formRegistro.lblVolver) {
            formRegistro.lblVolver.setForeground(Color.red);
        }
    }

    // Hover: Cuando el mouse deja de tocar el elemento.
    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == formLogin.lblRegistro) {
            formLogin.lblRegistro.setForeground(Color.black);
        }
        if (e.getSource() == formRegistro.lblVolver) {
            formRegistro.lblVolver.setForeground(Color.black);
        }
    }
    
    /**
     * Metodo que comprueba que todos los campos se hayan llenado
     * y nos ahorramos de poner un if muy feo en el codigo
     * @return true si TODOS los campos fueron afectados
     * false si algun campo falta por rellenar.
     */
    public boolean comprobacionCampos() {
        if ( !formRegistro.txtNombre.getText().trim().equals("") && 
                !formRegistro.txtApellido.getText().trim().equals("") &&
                !formRegistro.txtUsername.getText().trim().equals("") &&
                !formRegistro.txtPassword.getText().trim().equals("") &&
                !formRegistro.txtConfirmPass.getText().trim().equals("") &&
                formRegistro.cbxEdad.getSelectedIndex() != 0 &&
                formRegistro.cbxGenero.getSelectedIndex() != 0) {
            return true;
        }else {
            return false;
        }
    }
    
    public void limpiarLogin() {
        formLogin.txtUsuario.setText(null);
        formLogin.txtContra.setText(null);
    }
}
