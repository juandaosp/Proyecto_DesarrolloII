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
public class LoginPrincipal extends JFrame implements ActionListener{
    
    private JButton IniSesionPasajero, IniSesionEmpleado ;
    private JTextField campoLogin, campoPass, campoPin;
    private JLabel labLogin, labPass, labPin, labEsp;
    private JPanel panelPrincipal, panelSouth;
    private GridLayout GLPS;
    
    public LoginPrincipal(){
        
        super("SSTM - Login");
        IniSesionPasajero= new JButton("Iniciar Sesion Pasajero"); 
        IniSesionEmpleado= new JButton("Iniciar Sesion Empleado");
        

        IniSesionPasajero.addActionListener(this);
        IniSesionEmpleado.addActionListener(this);
        
        ImageIcon imLogo = new ImageIcon(getClass().getResource("/images/smalllogo.png"));
        
        JLabel lbLogo = new JLabel(imLogo);
        
        setLayout(new BorderLayout(5 , 5));
        add(lbLogo, BorderLayout.NORTH);
        
        panelSouth = new JPanel(new GridLayout(9 , 2  , 5, 5));
        
        JPanel pAux = new JPanel(new FlowLayout());
        
        pAux.add(panelSouth);
        
        panelSouth.add(IniSesionPasajero);
        panelSouth.add(IniSesionEmpleado);      
        add(pAux, BorderLayout.CENTER);
        add(new JPanel(), BorderLayout.SOUTH);
        
        
        setVisible(true);
        setSize(300,300);
        setResizable(false);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    
    }    public void actionPerformed(ActionEvent evento) 
	{
            if(evento.getSource() == IniSesionPasajero ){
            
                LoginPasajero lP = new LoginPasajero();
                
            }
            
            if(evento.getSource() == IniSesionEmpleado ){
            
                LoginEmpleado lE = new LoginEmpleado();
                
            }
            
}


}
