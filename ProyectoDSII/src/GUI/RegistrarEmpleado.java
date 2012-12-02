package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import DAO.DAOEmpleado;
import DAO.DAOOficina;
import Logica.Empleado;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RegistrarEmpleado extends JFrame implements ActionListener
{
	private JButton btRegistrar, btCancelar, btLimpiar;
	private JComboBox comboGenero, comboEstadoCivil, comboTContrato, comboCargo, comboTipoID;
	private JTextField campoIdentificacion, campoNombre; 
	private VentanaPrincipal ventanaP;

	private JTextField campoFNDia, campoFNMes, campoFNAno,campoFIDia, campoFIMes, campoFIAno, campoCodOfi, campoCiudad;

	public RegistrarEmpleado(VentanaPrincipal ventanaP)
	{		
		super("Registro Empleado");

		this.ventanaP = ventanaP;
		this.ventanaP.setVisible(true);

		JLabel lbIdentificacion = new JLabel ("Identificacion");
		JLabel lbNombre = new JLabel ("Nombre");
		JLabel lbGenero = new JLabel ("Genero");
		JLabel lbEstadoCivil = new JLabel ("Estado Civil");
		JLabel lbFechaNacimiento = new JLabel ("Fecha de Nacimiento (DD-MM-AAAA)");
		JLabel lbFechaIngreso = new JLabel ("Fecha de Ingreso (DD-MM-AAAA)");
		JLabel lbTContrato = new JLabel ("Tipo de Contrato");		
		JLabel lbCargo = new JLabel ("Cargo");
		JLabel lbCodOfi = new JLabel("Codigo Oficina");
		JLabel lbTipoID = new JLabel("Tipo de Identificacion");
		JLabel lbCiudad = new JLabel("Ciudad");

		String [] listaGeneros = {"Masculino", "Femenino"};	
		String [] listaEstadoC = {"Soltero(a)", "Casado(a)", "Viudo(a)", "Divorciado(a)", "Union Libre"};
		String [] listaTContrato = {"Termino Indefinido", "Temporal", "Prestacion de Servicios"};
		String [] listaCargo = {"Vendedor", "Cajero", "Impulsador", "Atencion al Cliente", "Supervisor", "Gerente"};
		String [] listaTipoID = {"Cedula Extranjeria", "Cedula Ciudadania", "Pasaporte", "Tarjeta de identidad"};
		//para seleccionar cargos como un string: (String) cbCargo.getSelectedItem();

		comboGenero = new JComboBox(listaGeneros);		
		comboEstadoCivil = new JComboBox(listaEstadoC);
		comboTContrato = new JComboBox(listaTContrato);
		comboCargo = new JComboBox(listaCargo);
		comboTipoID = new JComboBox(listaTipoID);

		campoIdentificacion = new JTextField();
		campoNombre = new JTextField();
		campoCodOfi = new JTextField();
		campoCiudad = new JTextField();

		campoFNDia = new JTextField();
		campoFNMes = new JTextField();
		campoFNAno = new JTextField();

		campoFIDia = new JTextField();
		campoFIMes = new JTextField();
		campoFIAno = new JTextField();

		btRegistrar = new JButton("Registrar");
		btCancelar = new JButton("Cancelar");
		btLimpiar = new JButton("Limpiar");

		//AGREGAR EVENTOS DE LOS BOTONES
		btRegistrar.addActionListener(this);
		btCancelar.addActionListener(this);
		btLimpiar.addActionListener(this);

		JPanel panelFNacimiento = new JPanel(new GridLayout(1,3,5,5));
		panelFNacimiento.add(campoFNAno); 
		panelFNacimiento.add(campoFNMes); 
		panelFNacimiento.add(campoFNDia); 

		JPanel panelFIngreso = new JPanel(new GridLayout(1,3,5,5));
		panelFIngreso.add(campoFIAno); 
		panelFIngreso.add(campoFIMes); 
		panelFIngreso.add(campoFIDia); 

		JPanel panelCentral = new JPanel(new GridLayout(11,2,10,10));
		panelCentral.add(lbIdentificacion); 
		panelCentral.add(campoIdentificacion);
		panelCentral.add(lbNombre); 
		panelCentral.add(campoNombre); 
		panelCentral.add(lbGenero);		
		panelCentral.add(comboGenero);		
		panelCentral.add(lbEstadoCivil);
		panelCentral.add(comboEstadoCivil);
		panelCentral.add(lbFechaNacimiento);
		panelCentral.add(panelFNacimiento);
		panelCentral.add(lbFechaIngreso);
		panelCentral.add(panelFIngreso);
		panelCentral.add(lbTContrato);		
		panelCentral.add(comboTContrato);		
		panelCentral.add(lbCargo);
		panelCentral.add(comboCargo);
		panelCentral.add(lbCodOfi);
		panelCentral.add(campoCodOfi);
		panelCentral.add(lbTipoID);
		panelCentral.add(comboTipoID);
		panelCentral.add(lbCiudad);
		panelCentral.add(campoCiudad);

		JPanel b = new JPanel();
		b.setLayout(new GridLayout(1,3,5,5));
		b.add(btRegistrar);
		b.add(btLimpiar); 
		b.add(btCancelar);

		ImageIcon imLogo  = new ImageIcon("Imagenes/smalllogo.png");		
		JLabel lbLogo = new JLabel(imLogo);		

		setLayout(new BorderLayout(5,5));
		add(lbLogo, BorderLayout.NORTH);
		add(panelCentral, BorderLayout.CENTER);
		add(b, BorderLayout.SOUTH);

		setSize(430,500);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent ev) 
	{
		if (ev.getSource()== btRegistrar)
		{	



			String id, nombre, genero, tipoID ,ciudad, estadoCivil, 
			codOficina, FechaNac, FechaIng, tipoContrato, cargo ;
			boolean bool1, bool2;

			id = campoIdentificacion.getText();
			nombre = campoNombre.getText();
			tipoID = (String)comboTipoID.getSelectedItem();
			genero = (String)comboGenero.getSelectedItem();
			ciudad = campoCiudad.getText();
			estadoCivil = (String)comboEstadoCivil.getSelectedItem();
			codOficina = campoCodOfi.getText();
			FechaIng = campoFIAno.getText()+"-"+campoFIMes.getText()+"-"+campoFIDia.getText();
			FechaNac = campoFNAno.getText()+"-"+campoFNMes.getText()+"-"+campoFNDia.getText();
			tipoContrato = (String)comboTContrato.getSelectedItem();
			cargo = (String)comboCargo.getSelectedItem();

			Empleado em = new Empleado();

			em.setIdEmpleado(id);
			em.setNombre(nombre);
			em.setTipoId(tipoID);
			em.setGenero(genero);
			em.setCiudad(ciudad);
			em.setEstadoCivil(estadoCivil);
			em.setCodigoOficina(codOficina);
			em.setFechaIngreso(FechaIng);
			em.setFechaNac(FechaNac);
			em.setTipoContrato(tipoContrato);
			em.setCargo(cargo);


			DAOEmpleado me = new DAOEmpleado();
			// Comprobar que el empleado no existe

			DAOOficina daofi = new DAOOficina();
			bool2 = daofi.ComprovarCodigoOficina(codOficina);
			bool1 = me.ComprovarIDEmpleado(id);

			if(bool1 == true){
				JOptionPane.showMessageDialog(null, "El empleado con idetificacion: "+id+" ya existe", "Error", JOptionPane.ERROR_MESSAGE);

				campoIdentificacion.setText("");

			}
			if (bool2 == false){

				JOptionPane.showMessageDialog(null, "La oficina no existe", "Error", JOptionPane.ERROR_MESSAGE);
				campoCodOfi.setText("");

			}
			if (bool1 == false && bool2 == true){

				int resultado = me.GuardarEmpleado(em);
				
				if(ventanaP.isMostrandoTabla() == true && resultado > 0)
				{
					JOptionPane.showMessageDialog(null, "El empleado "+nombre+" ha sido agregado correctamente");
					ventanaP.removerContenidoVentana();
					ventanaP.mostrarTabla("empleado");

				}
				if(ventanaP.isMostrandoTabla() == false && resultado > 0)
					JOptionPane.showMessageDialog(null, "El empleado "+nombre+" ha sido agregado correctamente");
				if(resultado <= 0)
					JOptionPane.showMessageDialog(null, "Error al registrar empleado","ERROR", JOptionPane.ERROR_MESSAGE);	

				campoIdentificacion.setText("");
				campoNombre.setText("");
				campoCiudad.setText("");
				campoCodOfi.setText("");
				campoFIAno.setText("");
				campoFIMes.setText("");
				campoFIDia.setText("");
				campoFNAno.setText("");
				campoFNMes.setText("");
				campoFNDia.setText("");				
				

			}

		} 

		if (ev.getSource()== btCancelar)
		{

			dispose();
		}

		if (ev.getSource() == btLimpiar){

			campoIdentificacion.setText("");
			campoNombre.setText("");
			campoCiudad.setText("");
			campoCodOfi.setText("");
			campoFIAno.setText("");
			campoFIMes.setText("");
			campoFIDia.setText("");
			campoFNAno.setText("");
			campoFNMes.setText("");
			campoFNDia.setText("");                



		}
	}	


}


