package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import DAO.DAOEquipo;
import DAO.DaoConsultas;
import Logica.Equipo;

public class EditarEquipoCelular extends JFrame implements ActionListener
{
	private JButton btEditar, btCancelar, btGuardar, btCerrar;
	private JComboBox comboGama;
	private JTextField campoFrecuencia, campoMarca, campoModelo, campoPrecio;
	private JPanel panelCentral;
	private String imei;
	private DAOEquipo daoequipo;
	private Equipo equipo;
	private VentanaPrincipal ventanaP;
	
	public EditarEquipoCelular(String imei,VentanaPrincipal ventanaP)
	{		
		super("Editar Equipo Celular");
		
		this.imei = imei;
		this.ventanaP = ventanaP;
		this.ventanaP.setVisible(true);
		
		daoequipo = new DAOEquipo();
		
		JLabel lbImei = new JLabel ("IMEI");
		JLabel lbMarca= new JLabel ("Marca");
		JLabel lbModelo = new JLabel ("Modelo");
		JLabel lbGama = new JLabel ("Gama");
		JLabel lbFrecuencia = new JLabel ("Frecuencia");		
		JLabel lbPrecio = new JLabel ("Precio");
		
		String [] listaGama = {"Alta", "Media", "Baja"};
		//para seleccionar cargos como un string: (String) cbCargo.getSelectedItem();
		
		comboGama = new JComboBox(listaGama);
		comboGama.setEnabled(false);
		
		campoMarca = new JTextField();
		campoMarca.setEditable(false);
		campoModelo = new JTextField();
		campoModelo.setEditable(false);
		campoFrecuencia = new JTextField();		
		campoFrecuencia.setEditable(false);
		campoPrecio = new JTextField();
		campoPrecio.setEditable(false);
		
		//LLENAMOS LOS CAMPOS CON LA INFORMACION DE EQUIPOS
		equipo = daoequipo.consultarEquipo(imei);
		
		comboGama.setSelectedItem(equipo.getGama());
		
		JLabel lbInfoImei = new JLabel(equipo.getImei());
		campoMarca.setText(equipo.getMarca());
		campoModelo.setText(equipo.getModelo());
		campoFrecuencia.setText(equipo.getFrecuencia());
		campoPrecio.setText(equipo.getPrecio()+"");
		
		//INSTANCIAR BOTONES
		btEditar = new JButton("Editar");
		btCerrar = new JButton("Cerrar");
		btGuardar = new JButton("Guardar");
		btCancelar = new JButton("Cancelar");
		
		
		//AGREGAR EVENTOS DE LOS BOTONES
		btEditar.addActionListener(this);
		btCerrar.addActionListener(this);
		btGuardar.addActionListener(this);
		btCancelar.addActionListener(this);
		
		panelCentral = new JPanel(new GridLayout(7,2,10,10));
		panelCentral.add(lbImei); 
		panelCentral.add(lbInfoImei);
		panelCentral.add(lbMarca);		
		panelCentral.add(campoMarca);		
		panelCentral.add(lbModelo);
		panelCentral.add(campoModelo);
		panelCentral.add(lbGama); 
		panelCentral.add(comboGama);
		panelCentral.add(lbFrecuencia); 
		panelCentral.add(campoFrecuencia); 		
		panelCentral.add(lbPrecio);
		panelCentral.add(campoPrecio);
		panelCentral.add(btEditar);
		panelCentral.add(btCerrar);  

		ImageIcon imLogo  = new ImageIcon("Imagenes/smalllogo.png");		
		JLabel lbLogo = new JLabel(imLogo);		
		
		setLayout(new BorderLayout(5,5));
		add(lbLogo, BorderLayout.NORTH);
		add(panelCentral, BorderLayout.CENTER);
				
		setSize(300,300);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ev) 
	{
		if (ev.getSource()== btEditar)
		{
			comboGama.setEnabled(true);				
			campoMarca.setEditable(true);
			campoModelo.setEditable(true);	
			campoFrecuencia.setEditable(true);
			campoPrecio.setEditable(true);

			panelCentral.remove(btEditar);
			panelCentral.remove(btCerrar);

			panelCentral.add(btGuardar);
			panelCentral.add(btCancelar);

			this.pack();
			setSize(300,300);

		}		
		if (ev.getSource()== btCancelar)
		{
			comboGama.setEnabled(false);				
			campoMarca.setEditable(false);
			campoModelo.setEditable(false);	
			campoFrecuencia.setEditable(false);
			campoPrecio.setEditable(false);

			panelCentral.remove(btGuardar);
			panelCentral.remove(btCancelar);

			panelCentral.add(btEditar);
			panelCentral.add(btCerrar);

			this.pack();
			setSize(300,300);
		}
		if (ev.getSource()== btCerrar)
		{
			dispose();
		}
		if (ev.getSource()== btGuardar)
		{
			equipo.setGama((String)comboGama.getSelectedItem());
			equipo.setMarca(campoMarca.getText());
			equipo.setModelo(campoModelo.getText());
			equipo.setFrecuencia(campoFrecuencia.getText());
			equipo.setPrecio(Integer.parseInt(campoPrecio.getText()));
			
			int resultado = daoequipo.modificarEquipo(equipo, imei);
			
			if(resultado > 0)
			{
				JOptionPane.showMessageDialog(null, "El equipo ha sido modificado satisfactoriamente");
				ventanaP.removerContenidoVentana();
				ventanaP.mostrarTabla("equipo");

			}	
			else
				JOptionPane.showMessageDialog(null, "Error al modificar equipo","ERROR", JOptionPane.ERROR_MESSAGE);
			
			dispose();
			
		}
	}
}
