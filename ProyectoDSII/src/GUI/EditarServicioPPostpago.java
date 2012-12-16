package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditarServicioPPostpago extends JFrame implements ActionListener
{
	private JButton btEditar, btCancelar;
	private JTextField campoNPlan, campoIDServicio, campoCServicio;
	
	public EditarServicioPPostpago()
		{		
			super("Registro Servicio Plan Postpago");
			
			JLabel lbNPlan = new JLabel ("Numero del Plan");
			JLabel lbIDServicio = new JLabel ("ID del Servicio");
			JLabel lbCServicio = new JLabel ("Cantidad Servicio");
			
			campoNPlan = new JTextField();
			campoIDServicio = new JTextField();
			campoCServicio = new JTextField();
			
			btEditar = new JButton("Editar");
			btCancelar = new JButton("Cancelar");
			
			//AGREGAR EVENTOS DE LOS BOTONES
			btEditar.addActionListener(this);
			btCancelar.addActionListener(this);
			
			campoNPlan.setEditable(false);
			
			JPanel panelCentral = new JPanel(new GridLayout(4,2,10,10));
			panelCentral.add(lbNPlan); 
			panelCentral.add(campoNPlan);
			panelCentral.add(lbIDServicio); 
			panelCentral.add(campoIDServicio);
			panelCentral.add(lbCServicio); 
			panelCentral.add(campoCServicio);
			panelCentral.add(btEditar);
			panelCentral.add(btCancelar);  
	
			ImageIcon imLogo  = new ImageIcon("Imagenes/smalllogo.png");		
			JLabel lbLogo = new JLabel(imLogo);		
			
			setLayout(new BorderLayout(5,5));
			add(lbLogo, BorderLayout.NORTH);
			add(panelCentral, BorderLayout.CENTER);
					
			setSize(300,250);
			setLocationRelativeTo(null);
			setVisible(true);
		}
	
	public void actionPerformed(ActionEvent ev) 
		{
			if (ev.getSource()== btEditar)
				{
				}		
			if (ev.getSource()== btCancelar)
				{
					dispose();
				}
		}
}
