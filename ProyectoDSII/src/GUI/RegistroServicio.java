package GUI;

import DAO.DAOServicio;
import Logica.Servicio;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class RegistroServicio extends JFrame implements ActionListener
{
	private JButton btRegistrar, btCancelar, btLimpiar;
	private JTextField campoNombre, campoIDServicio, campoTarifa;
	
	public RegistroServicio()
	{		
		super("Registro Servicio ");
		
		JLabel lbIDServicio = new JLabel ("Identificacion del servicio");
		JLabel lbNombre = new JLabel ("Nombre del servicio");
		JLabel lbTarifaExtra = new JLabel ("Tarifa Extra");
		
		campoNombre = new JTextField();
		campoIDServicio = new JTextField();
		campoTarifa = new JTextField();
		
		btRegistrar = new JButton("Registrar");
		btCancelar = new JButton("Cancelar");
                btLimpiar = new JButton ("Limpiar");
		
		//AGREGAR EVENTOS DE LOS BOTONES
		btRegistrar.addActionListener(this);
		btCancelar.addActionListener(this);
                btLimpiar.addActionListener(this);
		
		JPanel panelCentral = new JPanel(new GridLayout(3,2,10,10));
		panelCentral.add(lbIDServicio); 
		panelCentral.add(campoIDServicio);
		panelCentral.add(lbNombre); 
		panelCentral.add(campoNombre);
		panelCentral.add(lbTarifaExtra); 
		panelCentral.add(campoTarifa);
                
                JPanel b = new JPanel();
		b.add(btRegistrar);
                b.add(btLimpiar);
		b.add(btCancelar);  

		ImageIcon imLogo  = new ImageIcon("Imagenes/smalllogo.png");		
		JLabel lbLogo = new JLabel(imLogo);		
		
		setLayout(new BorderLayout(5,5));
		add(lbLogo, BorderLayout.NORTH);
		add(panelCentral, BorderLayout.CENTER);
                add(b, BorderLayout.SOUTH);
				
		setSize(300,250);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ev) 
	{
		if (ev.getSource()== btRegistrar)
		{
                    String nombre, identificacion, tarifa;
                    
                    	 nombre = campoNombre.getText();
                         identificacion = campoIDServicio.getText(); 
                         tarifa = campoTarifa.getText();
                         
                         try{
                         Servicio s = new Servicio();
                         
                         s.setIdServicio(identificacion);
                         s.setNombre(nombre);
                         s.setTarifaExtra(Integer.parseInt(tarifa));
                         
                         DAOServicio ds = new DAOServicio();
                         
                         boolean bool = ds.ComprovarIdServicio(identificacion);
                         
                         if (bool == true){
                         
                             JOptionPane.showMessageDialog(null, "Error el servicio con identificacion: "
                                     + identificacion+ " Ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                                                          
                             campoIDServicio.setText("");

                         
                         }else
                         {
                             ds.guardarServicio(s);
                             
                             campoTarifa.setText("");
                             campoNombre.setText("");
                             campoIDServicio.setText("");
                         }
                         }
                         catch(NumberFormatException e)
                         {
                             
                             JOptionPane.showMessageDialog(null, "Error de formato, compruebe los campos con "
                                     + "los datos: "+ e.getMessage());
                             
                             campoTarifa.setText("");
                        
                         }
                }		
		
		if (ev.getSource()== btCancelar)
		{
			this.dispose();
		}
                
                if(ev.getSource() == btLimpiar)
                {
                
                    campoTarifa.setText("");
                    campoNombre.setText("");
                    campoIDServicio.setText("");
                
                }
        }
}
