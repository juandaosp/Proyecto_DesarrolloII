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

import Logica.Oficina;
import DAO.DAOOficina;
import javax.swing.*;

public class EditarOficina extends JFrame implements ActionListener
{

	private JLabel labelCod, labelDireccion, labelCiudad, labelTelefono, labelEstado;
	private JTextField campoDireccion, campoCiudad, campoTelefono;
	private JPanel panelCentral;
	private JComboBox comboEstado;
	private JButton btEditar, btCancelar, btGuardar, btCerrar;
	private String cod_oficina;
	private DAOOficina daooficina;
	private Oficina oficina;
	private VentanaPrincipal ventanaP;
	
	public EditarOficina(String cod_oficina, VentanaPrincipal ventanaP)
	{
		this.cod_oficina = cod_oficina;
		this.ventanaP = ventanaP;
		this.ventanaP.setVisible(true);
		
		daooficina = new DAOOficina();
		
		labelCod = new JLabel("Codigo de oficina");
		labelDireccion = new JLabel("Direccion");
		labelCiudad = new JLabel("Ciudad");
		labelTelefono = new JLabel("Telefono");
		labelEstado = new JLabel("Estado de la oficina");

		campoDireccion = new JTextField();
		campoDireccion.setEditable(false);
		campoCiudad = new JTextField();
		campoCiudad.setEditable(false);
		campoTelefono = new JTextField();
		campoTelefono.setEditable(false);
                    
		String [] listaEstado = {"Activo", "Inactivo"};
                    
		comboEstado = new JComboBox(listaEstado);
		comboEstado.setEnabled(false);
		
		//LLENAMOS LOS DATOS DE LA OFICINA
		oficina = daooficina.consultarOficina(cod_oficina);
		
		campoCiudad.setText(oficina.getCiudad());
		JLabel InfoCodOficina = new JLabel(oficina.getCodigoOficina());
		campoDireccion.setText(oficina.getDireccion());
		campoTelefono.setText(oficina.getTelefono());
		
		comboEstado.setSelectedItem(oficina.getEstado());
/////////////////////////////////////////////////////////////////
		btEditar = new JButton("Editar");
		btCerrar = new JButton("Cerrar");
		btGuardar = new JButton("Guardar");
		btCancelar = new JButton("Cancelar");
		
		
		//AGREGAR EVENTOS DE LOS BOTONES
		btEditar.addActionListener(this);
		btCerrar.addActionListener(this);
		btGuardar.addActionListener(this);
		btCancelar.addActionListener(this);
		
		panelCentral = new JPanel(new GridLayout(6,2,10,10));
		panelCentral.add(labelCod);
		panelCentral.add(InfoCodOficina);
		panelCentral.add(labelDireccion);
		panelCentral.add(campoDireccion);
		panelCentral.add(labelCiudad);
		panelCentral.add(campoCiudad);
		panelCentral.add(labelTelefono);
		panelCentral.add(campoTelefono);
		panelCentral.add(labelEstado);
		panelCentral.add(comboEstado);
		panelCentral.add(btEditar);
		panelCentral.add(btCerrar);
		
		ImageIcon imLogo  = new ImageIcon("Imagenes/smalllogo.png");		
		JLabel lbLogo = new JLabel(imLogo);	
		
		setLayout(new BorderLayout(5,5));
		add(lbLogo, BorderLayout.NORTH);
		add(panelCentral, BorderLayout.CENTER);
		
		setLocation(300,100);
		setSize(350,300);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ev) 
	{
		if (ev.getSource()== btEditar)
		{
			campoDireccion.setEditable(true);
			campoCiudad.setEditable(true);
			campoTelefono.setEditable(true);
			comboEstado.setEnabled(true);

			panelCentral.remove(btEditar);
			panelCentral.remove(btCerrar);

			panelCentral.add(btGuardar);
			panelCentral.add(btCancelar);

			this.pack();
			setSize(350,300);

		}		
		if (ev.getSource()== btCancelar)
		{
			campoDireccion.setEditable(false);
			campoCiudad.setEditable(false);
			campoTelefono.setEditable(false);
			comboEstado.setEnabled(false);

			panelCentral.remove(btGuardar);
			panelCentral.remove(btCancelar);

			panelCentral.add(btEditar);
			panelCentral.add(btCerrar);

			this.pack();
			setSize(350,300);
		}
		if (ev.getSource()== btCerrar)
		{
			dispose();
		}
		if (ev.getSource()== btGuardar)
		{
			oficina.setCiudad(campoCiudad.getText());
			oficina.setDireccion(campoDireccion.getText());
			oficina.setTelefono(campoTelefono.getText());			
			oficina.setEstado((String)comboEstado.getSelectedItem());
			
			int resultado = daooficina.modificarOficina(oficina, cod_oficina);

			if(resultado > 0)
			{
				JOptionPane.showMessageDialog(null, "La oficina ha sido modificada satisfactoriamente");
				ventanaP.removerContenidoVentana();
				ventanaP.mostrarTabla("oficina");

			}	
			else
				JOptionPane.showMessageDialog(null, "Error al modificar oficina","ERROR", JOptionPane.ERROR_MESSAGE);
			
			dispose();
		}
	}
}
