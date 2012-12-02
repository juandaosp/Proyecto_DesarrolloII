package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Logica.Cliente;
import Logica.Oficina;
import DAO.DAOCliente;
import DAO.DAOOficina;
import javax.swing.*;

public class RegistrarOficina extends JFrame {

	JLabel labelCod, labelDireccion, labelCiudad, labelTelefono, labelEstado;
	JTextField campoCod, campoDireccion, campoCiudad, campoTelefono;
	JComboBox comboEstado;
	JButton bCrear, btCancelar;
	private VentanaPrincipal ventanaP;

	public RegistrarOficina(VentanaPrincipal ventanaP)
	{
		super("Registro Oficina");
		
		this.ventanaP = ventanaP;
		this.ventanaP.setVisible(true);
		
		labelCod = new JLabel("Codigo de oficina");
		labelDireccion = new JLabel("Direccion");
		labelCiudad = new JLabel("Ciudad");
		labelTelefono = new JLabel("Telefono");
		labelEstado = new JLabel("Estado de la oficina");

		campoCod = new JTextField();
		campoDireccion = new JTextField();
		campoCiudad = new JTextField();
		campoTelefono = new JTextField();

		String [] listaEstado = {"Activo", "Inactivo"};

		comboEstado = new JComboBox(listaEstado);


		bCrear = new JButton("Crear oficina");
		btCancelar = new JButton("Cancalar");

		ManejadorBoton manejador = new ManejadorBoton();

		bCrear.addActionListener(manejador);
		btCancelar.addActionListener(manejador);

		JPanel panelCentral = new JPanel(new GridLayout(6,2,10,10));
		panelCentral.add(labelCod);
		panelCentral.add(campoCod);
		panelCentral.add(labelDireccion);
		panelCentral.add(campoDireccion);
		panelCentral.add(labelCiudad);
		panelCentral.add(campoCiudad);
		panelCentral.add(labelTelefono);
		panelCentral.add(campoTelefono);
		panelCentral.add(labelEstado);
		panelCentral.add(comboEstado);
		panelCentral.add(bCrear);
		panelCentral.add(btCancelar);

		ImageIcon imLogo  = new ImageIcon("Imagenes/smalllogo.png");		
		JLabel lbLogo = new JLabel(imLogo);	

		setLayout(new BorderLayout(5,5));
		add(lbLogo, BorderLayout.NORTH);
		add(panelCentral, BorderLayout.CENTER);

		setLocation(300,100);
		this.setSize(350,300);
		this.setVisible(true);
	}	

	private class ManejadorBoton implements ActionListener
	{

		public void actionPerformed(ActionEvent evento)
		{
			if (evento.getSource() == bCrear)
			{

				String codigo, ciudad, direccion, telefono, estado ;

				codigo = campoCod.getText();
				ciudad = campoCiudad.getText();
				direccion = campoDireccion.getText();
				telefono = campoTelefono.getText();
				estado = (String)comboEstado.getSelectedItem();


				Oficina of = new Oficina();
				of.setCodigoOficina(codigo);
				of.setCiudad(ciudad);
				of.setDireccion(direccion);
				of.setTelefono(telefono);
				of.setEstado(estado);

				DAOOficina mo = new DAOOficina();
				int resultado = mo.GuardarOficina(of);

				if(ventanaP.isMostrandoTabla() == true && resultado > 0)
				{
					JOptionPane.showMessageDialog(null, "La oficina se ha registrado correctamente");
					ventanaP.removerContenidoVentana();
					ventanaP.mostrarTabla("oficina");
				}
				
				if(ventanaP.isMostrandoTabla() == false && resultado > 0)
					JOptionPane.showMessageDialog(null, "La oficina se ha registrado correctamente");

				if(resultado <= 0)
					JOptionPane.showMessageDialog(null, "Error al registrar oficina","ERROR", JOptionPane.ERROR_MESSAGE);	
			}

			if (evento.getSource() == btCancelar)
			{
				dispose();
			}
		}

	}	
}
