/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
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
public class LoginPasajero extends JFrame implements ActionListener{
    
    private JButton IniSesionPasajero, IniCancelar ;
    private JTextField campoLogin, campoPass, campoPin;
    private JLabel labLogin, labPass, labPin, labEsp;
    private JPanel panelPrincipal, panelBotones;
    private GridLayout GLPS;
    
    public LoginPasajero(){
        
        super("SSTM - Login");
        
        IniSesionPasajero= new JButton("Iniciar Sesion"); 
        IniCancelar= new JButton("Cancelar");
        
        ImageIcon imLogo = new ImageIcon(getClass().getResource("/images/smalllogo.png"));
        
        JLabel lbLogo = new JLabel(imLogo);
        
        setLayout(new BorderLayout(5 , 5));
        
         
        labPin = new JLabel ("PIN (tarjeta personalizada)");
        campoPin = new JTextField();
        
        campoPin.setSize(10, 10);
        
        JPanel pAux = new JPanel(new FlowLayout());
        panelPrincipal = new JPanel(new GridLayout(1,2,10,10));
        
        panelPrincipal.add(labPin);
        panelPrincipal.add(campoPin);
       
        pAux.add(panelPrincipal);
        panelBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        
      
       IniSesionPasajero= new JButton("Iniciar Sesion"); 
       IniCancelar= new JButton("Cancelar");
       
       panelBotones.add(IniSesionPasajero);
       panelBotones.add(IniCancelar);
       
       add(lbLogo, BorderLayout.NORTH);
       add(pAux, BorderLayout.CENTER);
       add(panelBotones, BorderLayout.SOUTH);
       
       
       setVisible(true);
       setSize(400,300);
       setResizable(false);
    
    }    public void actionPerformed(ActionEvent evento) 
	{
            
            
}

    public static void main(String a[]){
    
        LoginPasajero l = new LoginPasajero();
    
    }
}
