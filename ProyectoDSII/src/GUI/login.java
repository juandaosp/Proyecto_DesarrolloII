/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author RICURA FOODS
 */
public class login extends JFrame implements ActionListener{
    
    private JButton IniSesionPasajero, IniSesionEmpleado ;
    private JTextField campoLogin, campoPass, campoPin;
    private JLabel labLogin, labPass, labPin, labEsp;
    private JPanel panelPrincipal, panelPrincipal2, panelB1, panelB2;
    private GridLayout GLpp, GLpp2, Glpb1, GLpb2;
    
    public login(){
        
        super("SSTM - Login");
        IniSesionPasajero= new JButton("Iniciar Sesion Pasajero"); 
        IniSesionEmpleado= new JButton("Iniciar Sesion Empleado");
        
        ImageIcon imLogo  = new ImageIcon("Imagenes/smalllogo.png");
        JLabel lbLogo = new JLabel(imLogo);
        
        Container contenedor = getContentPane();
        setLayout(new BorderLayout());
        add(lbLogo, BorderLayout.NORTH);
        
        
        setVisible(true);
        setSize(800,600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    
    }    public void actionPerformed(ActionEvent evento) 
	{
}

    public static void main(String a[]){
    
        login l = new login();
    
    }
}
