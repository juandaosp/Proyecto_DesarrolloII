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

import DAO.DAOEmpleado;
import Logica.Empleado;

public class EditarEmpleado extends JFrame implements ActionListener
{
	private JButton btEditar, btCancelar, btGuardar, btCerrar;
	private JComboBox comboGenero, comboEstadoCivil, comboTContrato, comboCargo;
	private JTextField campoNombre, campoCodOfi, campoCiudad; 	
	private JTextField campoFNDia, campoFNMes, campoFNAno,campoFIDia, campoFIMes, campoFIAno;
	private JPanel panelCentral;
	private String id_empleado;
	private DAOEmpleado daoempleado;
	private Empleado empleado;
	private VentanaPrincipal ventanaP;
	
	public EditarEmpleado(String id_empleado, VentanaPrincipal ventanaP)
	{		
		super("Edicion de  Empleado");
		
		this.id_empleado = id_empleado;
		this.ventanaP = ventanaP;
		this.ventanaP.setVisible(true);
		
		daoempleado = new DAOEmpleado();
		
		JLabel lbIdentificacion = new JLabel ("Identificacion");
		JLabel lbTipoId = new JLabel ("Tipo Identificacion");
		JLabel lbNombre = new JLabel ("Nombre");
		JLabel lbGenero = new JLabel ("Genero");
		JLabel lbEstadoCivil = new JLabel ("Estado Civil");
		JLabel lbFechaNacimiento = new JLabel ("Fecha de Nacimiento (DD-MM-AAAA)");
		JLabel lbFechaIngreso = new JLabel ("Fecha de Ingreso (DD-MM-AAAA)");
		JLabel lbTContrato = new JLabel ("Tipo de Contrato");		
		JLabel lbCargo = new JLabel ("Cargo");
		JLabel lbCodOfi = new JLabel("Codigo Oficina");
		JLabel lbCiudad = new JLabel("Ciudad");
		
		String [] listaGeneros = {"Masculino", "Femenino"};	
		String [] listaEstadoC = {"Soltero(a)", "Casado(a)", "Viudo(a)", "Divorciado(a)", "Union Libre"};
		String [] listaTContrato = {"Termino Indefinido", "Temporal", "Prestacion de Servicios"};
		String [] listaCargo = {"Vendedor", "Cajero", "Impulsador", "Atencion al Cliente", "Supervisor", "Gerente"};
		//para seleccionar cargos como un string: (String) cbCargo.getSelectedItem();
		
		comboGenero = new JComboBox(listaGeneros);
		comboGenero.setEnabled(false);
		comboEstadoCivil = new JComboBox(listaEstadoC);
		comboEstadoCivil.setEnabled(false);
		comboTContrato = new JComboBox(listaTContrato);
		comboTContrato.setEnabled(false);
		comboCargo = new JComboBox(listaCargo);
		comboCargo.setEnabled(false);

		campoNombre = new JTextField();
		campoNombre.setEditable(false);
		
		campoFNAno = new JTextField();
		campoFNAno.setEditable(false);
		campoFNMes = new JTextField();
		campoFNMes.setEditable(false);
		campoFNDia = new JTextField();
		campoFNDia.setEditable(false);
				
		campoFIAno = new JTextField();	
		campoFIAno.setEditable(false);
		campoFIMes = new JTextField();
		campoFIMes.setEditable(false);
		campoFIDia = new JTextField();
		campoFIDia.setEditable(false);
		
		campoCodOfi = new JTextField();
		campoCodOfi.setEditable(false);
		campoCiudad = new JTextField();
		campoCiudad.setEditable(false);
		
		//LLENAMOS LOS CAMPOS CON LOS DATOS DEL EMPLEADO A EDITAR
		empleado = daoempleado.consultarEmpleado(id_empleado);
		
		JLabel lbInfoID = new JLabel(empleado.getIdEmpleado());
		JLabel lbInfoTipoID = new JLabel(empleado.getTipoId());		
		comboGenero.setSelectedItem(empleado.getGenero());	
		comboEstadoCivil.setSelectedItem(empleado.getEstadoCivil());	
		comboTContrato.setSelectedItem(empleado.getTipoContrato());	
		comboCargo.setSelectedItem(empleado.getCargo());

		campoNombre.setText(empleado.getNombre());
		
		campoFNAno.setText(empleado.getFechaNac().substring(0, 4));
		campoFNMes.setText(empleado.getFechaNac().substring(5, 7));
		campoFNDia.setText(empleado.getFechaNac().substring(8, 10));
				
		campoFIAno.setText(empleado.getFechaIngreso().substring(0, 4));	
		campoFIMes.setText(empleado.getFechaIngreso().substring(5, 7));
		campoFIDia.setText(empleado.getFechaIngreso().substring(8, 10));
		
		campoCodOfi.setText(empleado.getCodigoOficina());		
		campoCiudad.setText(empleado.getCiudad());
		////////////////////////////////////////////////////////////
		
		btEditar = new JButton("Editar");
		btCerrar = new JButton("Cerrar");
		btGuardar = new JButton("Guardar");
		btCancelar = new JButton("Cancelar");
		
		
		//AGREGAR EVENTOS DE LOS BOTONES
		btEditar.addActionListener(this);
		btCerrar.addActionListener(this);
		btGuardar.addActionListener(this);
		btCancelar.addActionListener(this);
		
		JPanel panelFNacimiento = new JPanel(new GridLayout(1,3,5,5));
		panelFNacimiento.add(campoFNDia); 
		panelFNacimiento.add(campoFNMes); 
		panelFNacimiento.add(campoFNAno); 
		
		JPanel panelFIngreso = new JPanel(new GridLayout(1,3,5,5));
		panelFIngreso.add(campoFIDia); 
		panelFIngreso.add(campoFIMes); 
		panelFIngreso.add(campoFIAno); 
		
		panelCentral = new JPanel(new GridLayout(12,2,10,10));
		panelCentral.add(lbIdentificacion); 
		panelCentral.add(lbInfoID);
		panelCentral.add(lbTipoId); 
		panelCentral.add(lbInfoTipoID);
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
		panelCentral.add(lbCiudad);
		panelCentral.add(campoCiudad);
		panelCentral.add(btEditar);
		panelCentral.add(btCerrar);  

		ImageIcon imLogo  = new ImageIcon("Imagenes/smalllogo.png");		
		JLabel lbLogo = new JLabel(imLogo);		
		
		setLayout(new BorderLayout(5,5));
		add(lbLogo, BorderLayout.NORTH);
		add(panelCentral, BorderLayout.CENTER);
				
		setSize(430,500);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ev) 
	{if (ev.getSource()== btEditar)
	{
		comboGenero.setEnabled(true);
		comboEstadoCivil.setEnabled(true);
		comboTContrato.setEnabled(true);
		comboCargo.setEnabled(true);
		campoNombre.setEditable(true);
		campoFNAno.setEditable(true);
		campoFNMes.setEditable(true);
		campoFNDia.setEditable(true);
		campoFIAno.setEditable(true);
		campoFIMes.setEditable(true);
		campoFIDia.setEditable(true);
		campoCodOfi.setEditable(true);
		campoCiudad.setEditable(true);
				
		panelCentral.remove(btEditar);
		panelCentral.remove(btCerrar);
		
		panelCentral.add(btGuardar);
		panelCentral.add(btCancelar);

		this.pack();
		setSize(430,500);

	}		
	if (ev.getSource()== btCancelar)
	{
		comboGenero.setEnabled(false);
		comboEstadoCivil.setEnabled(false);
		comboTContrato.setEnabled(false);
		comboCargo.setEnabled(false);
		campoNombre.setEditable(false);
		campoFNAno.setEditable(false);
		campoFNMes.setEditable(false);
		campoFNDia.setEditable(false);
		campoFIAno.setEditable(false);
		campoFIMes.setEditable(false);
		campoFIDia.setEditable(false);
		campoCodOfi.setEditable(false);
		campoCiudad.setEditable(false);

		panelCentral.remove(btGuardar);
		panelCentral.remove(btCancelar);

		panelCentral.add(btEditar);
		panelCentral.add(btCerrar);

		this.pack();
		setSize(430,500);
	}
	if (ev.getSource()== btCerrar)
	{
		dispose();
	}
	if (ev.getSource()== btGuardar)
	{
		empleado.setGenero((String)comboGenero.getSelectedItem());	
		empleado.setEstadoCivil((String)comboEstadoCivil.getSelectedItem());	
		empleado.setTipoContrato((String)comboTContrato.getSelectedItem());	
		empleado.setCargo((String)comboCargo.getSelectedItem());

		empleado.setNombre(campoNombre.getText());
		
		String fechaN = campoFNAno.getText()+"-"+campoFNMes.getText()+"-"+campoFNDia.getText();
		empleado.setFechaNac(fechaN);
		
		String fechaI = campoFIAno.getText()+"-"+campoFIMes.getText()+"-"+campoFIDia.getText();
		empleado.setFechaIngreso(fechaI);
		
		empleado.setCodigoOficina(campoCodOfi.getText());		
		empleado.setCiudad(campoCiudad.getText());
		
		int resultado = daoempleado.modificarEmpleado(empleado, id_empleado);

		if(resultado > 0)
		{
			JOptionPane.showMessageDialog(null, "El empleado ha sido modificado satisfactoriamente");
			ventanaP.removerContenidoVentana();
			ventanaP.mostrarTabla("empleado");

		}	
		else
			JOptionPane.showMessageDialog(null, "Error al modificar empleado","ERROR", JOptionPane.ERROR_MESSAGE);
		
		dispose();
	}	
	}	
}
