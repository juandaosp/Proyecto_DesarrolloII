package GUI;

import DAO.DAOEquipo;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import DAO.DaoConsultas;
import Logica.Equipo;
import javax.swing.JOptionPane;

public class RegistrarEquipoCelular extends JFrame implements ActionListener
{
	private JButton btRegistrar, btCancelar, btLimpiar;
	private JComboBox comboGama;
	private JTextField campoImei, campoFrecuencia, campoMarca, campoModelo, campoPrecio;
	private VentanaPrincipal ventanaP;

	public RegistrarEquipoCelular(VentanaPrincipal ventanaP)
	{		
		super("Nuevo Equipo Celular");

		this.ventanaP = ventanaP;
		this.ventanaP.setVisible(true);

		JLabel lbImei = new JLabel ("IMEI");
		JLabel lbGama = new JLabel ("Gama");
		JLabel lbFrecuencia = new JLabel ("Frecuencia");
		JLabel lbMarca= new JLabel ("Marca");
		JLabel lbModelo = new JLabel ("Modelo");
		JLabel lbPrecio = new JLabel ("Precio");

		String [] listaGama = {"Alta", "Media", "Baja"};
		//para seleccionar cargos como un string: (String) cbCargo.getSelectedItem();

		comboGama = new JComboBox(listaGama);

		campoImei = new JTextField();
		campoFrecuencia = new JTextField();
		campoMarca = new JTextField();
		campoModelo = new JTextField();
		campoPrecio = new JTextField();

		btRegistrar = new JButton("Registrar");
		btCancelar = new JButton("Cancelar");
		btLimpiar = new JButton("Limpiar");

		//AGREGAR EVENTOS DE LOS BOTONES
		btRegistrar.addActionListener(this);
		btCancelar.addActionListener(this);
		btLimpiar.addActionListener(this);

		JPanel panelCentral = new JPanel(new GridLayout(7,2,10,10));
		panelCentral.add(lbImei); 
		panelCentral.add(campoImei);
		panelCentral.add(lbGama); 
		panelCentral.add(comboGama);
		panelCentral.add(lbFrecuencia); 
		panelCentral.add(campoFrecuencia); 
		panelCentral.add(lbMarca);		
		panelCentral.add(campoMarca);		
		panelCentral.add(lbModelo);
		panelCentral.add(campoModelo);
		panelCentral.add(lbPrecio);
		panelCentral.add(campoPrecio);

		JPanel panelb = new JPanel();
		panelb.setLayout(new GridLayout(1, 3, 5, 5));

		panelb.add(btRegistrar);
		panelb.add(btLimpiar);
		panelb.add(btCancelar);

		ImageIcon imLogo  = new ImageIcon("Imagenes/smalllogo.png");		
		JLabel lbLogo = new JLabel(imLogo);		

		setLayout(new BorderLayout(5,5));
		add(lbLogo, BorderLayout.NORTH);
		add(panelCentral, BorderLayout.CENTER);
		add(panelb, BorderLayout.SOUTH);

		setSize(300,300);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent ev) 
	{
		if (ev.getSource()== btRegistrar)
		{
			String IMEI, gama, frecuencia, marca, modelo;
			int precio;

			IMEI = campoImei.getText();
			gama = (String) comboGama.getSelectedItem();
			frecuencia = campoFrecuencia.getText();
			marca = campoMarca.getText();
			modelo = campoModelo.getText();

			precio = Integer.parseInt(campoPrecio.getText());

			Equipo lEquipo = new Equipo();

			lEquipo.setImei(IMEI);
			lEquipo.setGama(gama);
			lEquipo.setFrecuencia(frecuencia);
			lEquipo.setMarca(marca);
			lEquipo.setModelo(modelo);
			lEquipo.setPrecio(precio);


			DAOEquipo equipo = new DAOEquipo();
			boolean bool1 = equipo.ComprovarImei(IMEI);

			if (bool1 == true)
			{

				JOptionPane.showMessageDialog(null, "El equipo celular con IMEI: "+IMEI+" ya existe");
				campoImei.setText("");


			}
			else
			{
				int resultado = equipo.GuardarEquipo(lEquipo);
				
				if(ventanaP.isMostrandoTabla() == true && resultado > 0)
				{
					JOptionPane.showMessageDialog(null, "El equipo ha sido agregado correctamente");
					ventanaP.removerContenidoVentana();
					ventanaP.mostrarTabla("equipo");

				}
				if(ventanaP.isMostrandoTabla() == false && resultado > 0)
					JOptionPane.showMessageDialog(null, "El equipo ha sido agregado correctamente");
				if(resultado <= 0)
					JOptionPane.showMessageDialog(null, "Error al registrar equipo","ERROR", JOptionPane.ERROR_MESSAGE);	

				campoImei.setText("");
				campoFrecuencia.setText("");
				campoMarca.setText("");
				campoModelo.setText("");
				campoPrecio.setText("");

			}
		}		

		if (ev.getSource()== btCancelar)
		{
			this.dispose();
		}

		if(ev.getSource() == btLimpiar)
		{

			campoImei.setText("");
			campoFrecuencia.setText("");
			campoMarca.setText("");
			campoModelo.setText("");
			campoPrecio.setText("");



		}

	}



}
