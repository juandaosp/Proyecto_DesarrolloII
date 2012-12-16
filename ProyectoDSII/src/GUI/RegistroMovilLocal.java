package GUI;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RegistroMovilLocal extends JFrame implements ActionListener
{
	private JButton btRegistrar, btCancelar, btLimpiar;
	private JTextField campoNombre, campoPais, campoCostoSMS, campoCostoMin, campoCostoKB;
	private VentanaPrincipal ventanaP;
	
	public RegistroMovilLocal(VentanaPrincipal ventanaP)
	{		
		super("Registro Movil Local");
		
		this.ventanaP = ventanaP;
		this.ventanaP.setVisible(true);

		JLabel lbNombre = new JLabel ("Nombre Operador");
		JLabel lbPais = new JLabel ("Pais");
		JLabel lbCostoSMS = new JLabel ("Costo SMS");
		JLabel lbCostoMin = new JLabel ("Costo Minuto");
		JLabel lbCostoKB = new JLabel ("Costo KB");

		campoNombre = new JTextField();
		campoPais = new JTextField();
		campoCostoSMS = new JTextField();
		campoCostoMin = new JTextField();
		campoCostoKB = new JTextField();

		campoPais.setText("Colombia");
		campoPais.setEditable(false);

		btRegistrar = new JButton("Registrar");
		btCancelar = new JButton("Cancelar");
		btLimpiar = new JButton("Limpiar");

		//AGREGAR EVENTOS DE LOS BOTONES
		btRegistrar.addActionListener(this);
		btCancelar.addActionListener(this);
		btLimpiar.addActionListener(this);

		JPanel panelCentral = new JPanel(new GridLayout(5,2,10,10));
		panelCentral.add(lbNombre); 
		panelCentral.add(campoNombre);
		panelCentral.add(lbPais); 
		panelCentral.add(campoPais);
		panelCentral.add(lbCostoSMS); 
		panelCentral.add(campoCostoSMS);
		panelCentral.add(lbCostoMin); 
		panelCentral.add(campoCostoMin);
		panelCentral.add(lbCostoKB); 
		panelCentral.add(campoCostoKB);

		JPanel panelB = new JPanel();
		panelB.setLayout(new GridLayout(1, 3 , 5 ,5));

		panelB.add(btRegistrar);
		panelB.add(btLimpiar);
		panelB.add(btCancelar);  

		ImageIcon imLogo  = new ImageIcon("smalllogo.png");		
		JLabel lbLogo = new JLabel(imLogo);		

		setLayout(new BorderLayout(5,5));
		add(lbLogo, BorderLayout.NORTH);
		add(panelCentral, BorderLayout.CENTER);
		add(panelB, BorderLayout.SOUTH);

		setSize(300,300);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ev) 
	{
		if (ev.getSource()== btRegistrar)
		{

			String nombre, pais, costoSMS, costoMin, CostoKB, numServCli;

			nombre = campoNombre.getText(); 
			pais = campoPais.getText(); 
			costoSMS = campoCostoSMS.getText();
			costoMin = campoCostoMin.getText(); 
			CostoKB = campoCostoKB.getText(); 

		
			

			
			
			
			campoNombre.setText(""); 
			campoCostoSMS.setText("");
			campoCostoMin.setText(""); 
			campoCostoKB.setText(""); 

		}		

		if (ev.getSource()== btCancelar)
		{
			this.dispose();
		}

		if(ev.getSource() == btLimpiar){


			campoNombre.setText(""); 
			campoCostoSMS.setText("");
			campoCostoMin.setText(""); 
			campoCostoKB.setText(""); 

		}


	}

	

}
