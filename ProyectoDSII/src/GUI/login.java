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
public class login extends JFrame implements ActionListener{
    
    private JButton IniSesionPasajero, IniSesionEmpleado ;
    private JTextField campoLogin, campoPass, campoPin;
    private JLabel labLogin, labPass, labPin, labEsp;
    private JPanel panelPrincipal, panelSouth;
    private GridLayout GLPS;
    
    public login(){
        
        super("SSTM - Login");
        IniSesionPasajero= new JButton("Iniciar Sesion Pasajero"); 
        IniSesionEmpleado= new JButton("Iniciar Sesion Empleado");
        

        
        ImageIcon imLogo = new ImageIcon(getClass().getResource("/images/smalllogo.png"));
        
        JLabel lbLogo = new JLabel(imLogo);
        
        setLayout(new BorderLayout(5 , 5));
        add(lbLogo, BorderLayout.NORTH);
        
        GLPS = new GridLayout(9 , 2  , 5, 5);
        panelSouth = new JPanel(GLPS);
        panelSouth.add(new JLabel());
        panelSouth.add(new JLabel());
        panelSouth.add(new JLabel());
        panelSouth.add(new JLabel());
        panelSouth.add(new JLabel());
        panelSouth.add(new JLabel());
        panelSouth.add(new JLabel());
        panelSouth.add(new JLabel());
        panelSouth.add(IniSesionPasajero);
        panelSouth.add(IniSesionEmpleado);
        panelSouth.add(new JLabel());
        panelSouth.add(new JLabel());
        panelSouth.add(new JLabel());
        panelSouth.add(new JLabel());
        panelSouth.add(new JLabel());
        panelSouth.add(new JLabel());
        panelSouth.add(new JLabel());
        panelSouth.add(new JLabel());        
        add(panelSouth, BorderLayout.CENTER);
        add(new JPanel(), BorderLayout.SOUTH);
        
        
        setVisible(true);
        setSize(400,500);
        setResizable(false);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    
    }    public void actionPerformed(ActionEvent evento) 
	{
            
            
}

    public static void main(String a[]){
    
        login l = new login();
    
    }
}
