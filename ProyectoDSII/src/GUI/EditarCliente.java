package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import DAO.DAOCliente;
import Logica.Cliente;

public class EditarCliente extends JFrame implements ActionListener
{

	private JButton btEditar, btCancelar, btGuardar, btCerrar;
	private JComboBox comboGenero, comboEstadoCivil;
	private JTextField campoNombre, campoDireccion, campoBarrio, campoEstrato, campoCiudad, campoComuna; 
	private JTextField campoFDia, campoFMes, campoFAno;
	private JPanel panelCentral;
	private String id_cliente;
	private DAOCliente daocliente;
	private Cliente cliente;
	private VentanaPrincipal ventanaP;
	
	public EditarCliente(String id_cliente,VentanaPrincipal ventanaP)
	{		
		super("Edicion de Cliente");
		
		this.id_cliente = id_cliente;
		this.ventanaP = ventanaP;
		this.ventanaP.setVisible(true);
		
		daocliente = new DAOCliente();
		
		JLabel lbIdentificacion = new JLabel ("Identificacion");
		JLabel lbTipoID = new JLabel ("Tipo Identificacion");
		JLabel lbNombre = new JLabel ("Nombre");
		JLabel lbGenero = new JLabel ("Genero");
		JLabel lbEstadoCivil = new JLabel ("Estado Civil");
		JLabel lbFechaNacimiento = new JLabel ("Fecha de Nacimiento (DD-MM-AAAA)");
		JLabel lbCiudad = new JLabel ("Ciudad");
		JLabel lbBarrio = new JLabel ("Barrio");
		JLabel lbEstrato = new JLabel ("Estrato");
		JLabel lbDireccion = new JLabel ("Direccion");	
		JLabel lbComuna = new JLabel("Comuna");
		
		String [] listaGeneros = {"Masculino", "Femenino"};	
		String [] listaTipoID = {"C.C.", "C.E."};	
		String [] listaEstadoC = {"Soltero(a)", "Casado(a)", "Viudo(a)", "Divorciado(a)", "Union Libre"};
                //para seleccionar cargos como un string: (String) cbCargo.getSelectedItem();

		comboGenero = new JComboBox(listaGeneros);	
		comboGenero.setEnabled(false);
		comboEstadoCivil = new JComboBox(listaEstadoC);
		comboEstadoCivil.setEnabled(false);

		campoNombre = new JTextField();
		campoNombre.setEditable(false);
		campoCiudad = new JTextField();
		campoCiudad.setEditable(false);
		campoBarrio = new JTextField();
		campoBarrio.setEditable(false);
		campoEstrato = new JTextField();
		campoEstrato.setEditable(false);
		campoDireccion = new JTextField();		
		campoDireccion.setEditable(false);
		campoComuna = new JTextField();	
		campoComuna.setEditable(false);
		
		campoFDia = new JTextField();
		campoFDia.setEditable(false);
		campoFMes = new JTextField();
		campoFMes.setEditable(false);
		campoFAno = new JTextField();
		campoFAno.setEditable(false);
		
		//LLENAMOS LOS CAMPOS CON LOS DATOS DEL CLIENTE A EDITAR
		cliente = daocliente.consultarCliente(id_cliente);
		
		comboGenero.setSelectedItem(cliente.GetGenero());
		comboEstadoCivil.setSelectedItem(cliente.GetEstadoCivil());
		JLabel lbInfoID = new JLabel(cliente.GetIdCliente());
		JLabel lbInfoTipoID = new JLabel(cliente.GetTipoId());
		campoNombre.setText(cliente.GetNombre());
		campoCiudad.setText(cliente.GetCiudad());
		campoBarrio.setText(cliente.GetBarrio());
		campoEstrato.setText(cliente.GetEstrato());
		campoDireccion.setText(cliente.GetDireccionCorrespondencia());
		campoComuna.setText(cliente.GetComuna());
		campoFAno.setText(cliente.getFechaNac().substring(0, 4));
		campoFMes.setText(cliente.getFechaNac().substring(5, 7));	
		campoFDia.setText(cliente.getFechaNac().substring(8, 10));
		
		btGuardar = new JButton("Guardar");
		btCancelar = new JButton("Cancelar");
		btEditar = new JButton("Editar");
		btCerrar = new JButton("Cerrar");
		
		//AGREGAR EVENTOS DE LOS BOTONES
		btEditar.addActionListener(this);
		btCerrar.addActionListener(this);
		btGuardar.addActionListener(this);
		btCancelar.addActionListener(this);
		
		JPanel panelFNacimiento = new JPanel(new GridLayout(1,3,5,5));
		panelFNacimiento.add(campoFDia); 
		panelFNacimiento.add(campoFMes); 
		panelFNacimiento.add(campoFAno); 
		
		panelCentral = new JPanel(new GridLayout(12,2,10,10));
		panelCentral.add(lbIdentificacion); 
		panelCentral.add(lbInfoID);
		panelCentral.add(lbTipoID); 
		panelCentral.add(lbInfoTipoID);
		panelCentral.add(lbNombre); 
		panelCentral.add(campoNombre); 
		panelCentral.add(lbGenero);		
		panelCentral.add(comboGenero);		
		panelCentral.add(lbEstadoCivil);
		panelCentral.add(comboEstadoCivil);
		panelCentral.add(lbFechaNacimiento);
		panelCentral.add(panelFNacimiento);
		panelCentral.add(lbCiudad);
		panelCentral.add(campoCiudad);
		panelCentral.add(lbBarrio);
		panelCentral.add(campoBarrio);
		panelCentral.add(lbEstrato);
		panelCentral.add(campoEstrato);
		panelCentral.add(lbComuna);
		panelCentral.add(campoComuna);
		panelCentral.add(lbDireccion);
		panelCentral.add(campoDireccion);
		panelCentral.add(lbComuna);
		panelCentral.add(campoComuna);
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
	{
		if (ev.getSource()== btEditar)
		{	
			comboGenero.setEnabled(true);
			comboEstadoCivil.setEnabled(true);
			campoNombre.setEditable(true);
			campoCiudad.setEditable(true);
			campoBarrio.setEditable(true);
			campoEstrato.setEditable(true);	
			campoDireccion.setEditable(true);
			campoComuna.setEditable(true);
			campoFDia.setEditable(true);
			campoFMes.setEditable(true);
			campoFAno.setEditable(true);
			
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
			campoNombre.setEditable(false);
			campoCiudad.setEditable(false);
			campoBarrio.setEditable(false);
			campoEstrato.setEditable(false);	
			campoDireccion.setEditable(false);
			campoComuna.setEditable(false);
			campoFDia.setEditable(false);
			campoFMes.setEditable(false);
			campoFAno.setEditable(false);
			
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
			String fecha_nac = campoFAno.getText()+"-"+campoFMes.getText()+"-"+campoFDia.getText();
			cliente.SetGenero((String)comboGenero.getSelectedItem());
			cliente.SetEstadoCivil((String)comboEstadoCivil.getSelectedItem());
			cliente.SetNombre(campoNombre.getText());
			cliente.SetCiudad(campoCiudad.getText());
			cliente.SetBarrio(campoBarrio.getText());
			cliente.SetEstrato(campoEstrato.getText());
			cliente.SetDireccion(campoDireccion.getText());
			cliente.SetComuna(campoComuna.getText());
			cliente.setFechaNac(fecha_nac);
			
			int resultado = daocliente.modificarCliente(cliente, this.id_cliente);

			if(resultado > 0)
			{
				JOptionPane.showMessageDialog(null, "El cliente ha sido modificado satisfactoriamente");
				ventanaP.removerContenidoVentana();
				ventanaP.mostrarTabla("cliente");

			}	
			else
				JOptionPane.showMessageDialog(null, "Error al modificar cliente","ERROR", JOptionPane.ERROR_MESSAGE);
			
			dispose();
		}
	}
}
