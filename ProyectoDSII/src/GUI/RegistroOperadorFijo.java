package GUI;

import DAO.DAOOperador;
import DAO.DAOOperadorFijo;
import Logica.Operador;
import Logica.OperadorFijo;
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

public class RegistroOperadorFijo extends JFrame implements ActionListener
{
	private JButton btRegistrar, btCancelar, btLimpiar;
	private JTextField campoNombre, campoPais, campoCostoMin;
	private VentanaPrincipal ventanaP;
	
	public RegistroOperadorFijo(VentanaPrincipal ventanaP)
	{		
		super("Registro Operador");
		
		this.ventanaP = ventanaP;
		this.ventanaP.setVisible(true);

		JLabel lbNombre = new JLabel ("Nombre Operador");
		JLabel lbPais = new JLabel ("Pais");
		JLabel lbCostoMin = new JLabel ("Costo Minuto");

		campoNombre = new JTextField();
		campoPais = new JTextField();
		campoCostoMin = new JTextField();

		btRegistrar = new JButton("Registrar");
		btCancelar = new JButton("Cancelar");
		btLimpiar = new JButton("Limpiar");

		//AGREGAR EVENTOS DE LOS BOTONES
		btRegistrar.addActionListener(this);
		btCancelar.addActionListener(this);
		btLimpiar.addActionListener(this);

		JPanel panelCentral = new JPanel(new GridLayout(4,2,10,10));
		panelCentral.add(lbNombre); 
		panelCentral.add(campoNombre);
		panelCentral.add(lbPais); 
		panelCentral.add(campoPais);
		panelCentral.add(lbCostoMin); 
		panelCentral.add(campoCostoMin);

		JPanel panelB = new JPanel();
		panelB.setLayout(new GridLayout(1, 3 , 5 ,5));
		panelB.add(btRegistrar);
		panelB.add(btLimpiar);
		panelB.add(btCancelar);  

		ImageIcon imLogo  = new ImageIcon("Imagenes/smalllogo.png");		
		JLabel lbLogo = new JLabel(imLogo);		

		setLayout(new BorderLayout(5,5));
		add(lbLogo, BorderLayout.NORTH);
		add(panelCentral, BorderLayout.CENTER);
		add(panelB, BorderLayout.SOUTH);

		setSize(400,250);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ev) 
	{
		if (ev.getSource()== btRegistrar)
		{
			String nombre, Pais, numServCliente, estado;
			int costoMinuto;

			nombre = campoNombre.getText();
			Pais = campoPais.getText();
			costoMinuto = Integer.parseInt(campoCostoMin.getText());

			//Guardar en operador
			Operador op = new Operador();
			op.setNomOperador(nombre);
			op.setPais(Pais);

			DAOOperador operador = new DAOOperador();
			operador.GuardarOperador(op);

			// Guardar en operador fijo
			OperadorFijo opf = new OperadorFijo();


			opf.setCostoMinuto(costoMinuto);
			opf.setNomOperador(nombre);
			opf.setPais(Pais);


			DAOOperadorFijo operadorFijo = new DAOOperadorFijo();

			int resultado = operadorFijo.GuardarOperadorFijo(opf);
			
			if(ventanaP.isMostrandoTabla() == true && resultado > 0)
			{
				JOptionPane.showMessageDialog(null, "El operador ha sido agregado correctamente");
				ventanaP.removerContenidoVentana();
				ventanaP.mostrarTabla("ofijo");

			}
			if(ventanaP.isMostrandoTabla() == false && resultado > 0)
				JOptionPane.showMessageDialog(null, "El operador ha sido agregado correctamente");
			if(resultado <= 0)
				JOptionPane.showMessageDialog(null, "Error al registrar operador","ERROR", JOptionPane.ERROR_MESSAGE);	

			campoNombre.setText("");
			campoPais.setText("");
			campoCostoMin.setText("");                   
		}		

		if (ev.getSource()== btCancelar)
		{
			dispose();
		}

		if(ev.getSource() == btLimpiar)
		{

			campoNombre.setText("");
			campoPais.setText("");
			campoCostoMin.setText("");
		}


	}


}
