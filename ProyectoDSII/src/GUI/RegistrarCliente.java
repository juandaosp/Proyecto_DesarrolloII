package GUI;

import DAO.DAOCliente;
import Logica.Cliente;
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



public class RegistrarCliente extends JFrame implements ActionListener
{
	private JButton btRegistrar, btCancelar, btLimpiar;
	private JComboBox comboGenero, comboTipoID, comboEstadoCivil,comboEstado;
	private JTextField campoIdentificacion, campoNombre, campoDireccion, campoBarrio, campoEstrato, campoCiudad, campoEmail1,campoEmail2,campoEmail3,campoComuna; 
	private JTextField campoFDia, campoFMes, campoFAno;
	private VentanaPrincipal ventanaP;

	public RegistrarCliente(VentanaPrincipal ventanaP)
	{		
		super("Registro Cliente");

		this.ventanaP = ventanaP;
		this.ventanaP.setVisible(true);

		JLabel lbIdentificacion = new JLabel ("Identificacion");
		JLabel lbTipoID = new JLabel ("Tipo Identificacion");
		JLabel lbNombre = new JLabel ("Nombre");
		JLabel lbGenero = new JLabel ("Genero");
		JLabel lbEstadoCivil = new JLabel ("Estado Civil");
		JLabel lbFechaNacimiento = new JLabel ("Fecha de Nacimiento (AAAA-MM-DD)");
		JLabel lbCiudad = new JLabel ("Ciudad");
		JLabel lbBarrio = new JLabel ("Barrio");
		JLabel lbEstrato = new JLabel ("Estrato");
		JLabel lbDireccion = new JLabel ("Direccion");		
		JLabel lbEmail1 = new JLabel ("Email 1");
		JLabel lbEmail2 = new JLabel ("Email 2");
		JLabel lbEmail3 = new JLabel ("Email 3");
		JLabel lbComuna = new JLabel("Comuna");
		JLabel lbEstado = new JLabel("Estado");

		String [] listaGeneros = {"Masculino", "Femenino"};	
		String [] listaTipoID = {"C.C.", "C.E."};	
		String [] listaEstadoC = {"Soltero(a)", "Casado(a)", "Viudo(a)", "Divorciado(a)", "Union Libre"};
		//para seleccionar cargos como un string: (String) cbCargo.getSelectedItem();

		comboTipoID = new JComboBox(listaTipoID);
		comboGenero = new JComboBox(listaGeneros);		
		comboEstadoCivil = new JComboBox(listaEstadoC);

		campoIdentificacion = new JTextField();
		campoNombre = new JTextField();
		campoCiudad = new JTextField();
		campoBarrio = new JTextField();
		campoEstrato = new JTextField();
		campoDireccion = new JTextField();			
		campoEmail1 = new JTextField();
		campoEmail2 = new JTextField();
		campoEmail3 = new JTextField();
		campoComuna = new JTextField();


		campoFDia = new JTextField();
		campoFMes = new JTextField();
		campoFAno = new JTextField();

		btRegistrar = new JButton("Registrar");
		btCancelar = new JButton("Cancelar");
		btLimpiar = new JButton("Limpiar");

		//AGREGAR EVENTOS DE LOS BOTONES
		btRegistrar.addActionListener(this);
		btCancelar.addActionListener(this);
		btLimpiar.addActionListener(this);

		JPanel panelFNacimiento = new JPanel(new GridLayout(1,3,5,5));
		panelFNacimiento.add(campoFDia); 
		panelFNacimiento.add(campoFMes); 
		panelFNacimiento.add(campoFAno); 

		JPanel panelCentral = new JPanel(new GridLayout(15,2,10,10));
		panelCentral.add(lbIdentificacion); 
		panelCentral.add(campoIdentificacion);
		panelCentral.add(lbTipoID); 
		panelCentral.add(comboTipoID);
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
		panelCentral.add(lbEmail1);
		panelCentral.add(campoEmail1);
		panelCentral.add(lbEmail2);
		panelCentral.add(campoEmail2);
		panelCentral.add(lbEmail3);
		panelCentral.add(campoEmail3);


		JPanel panelS = new JPanel();
		panelS.add(btRegistrar);
		panelS.add(btLimpiar);
		panelS.add(btCancelar);  

		ImageIcon imLogo  = new ImageIcon("Imagenes/smalllogo.png");		
		JLabel lbLogo = new JLabel(imLogo);		

		setLayout(new BorderLayout(5,5));
		add(lbLogo, BorderLayout.NORTH);
		add(panelCentral, BorderLayout.CENTER);
		add(panelS, BorderLayout.SOUTH);

		setSize(430,600);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void Limpiar()
	{
		campoIdentificacion.setText("");
		campoNombre.setText("");
		campoFDia.setText("");
		campoFMes.setText("");
		campoFAno.setText("");
		campoCiudad.setText("");
		campoBarrio.setText("");
		campoEstrato.setText("");
		campoDireccion.setText("");
		campoEmail1.setText("");
		campoEstrato.setText("");
		campoComuna.setText("");
	}

	public void actionPerformed(ActionEvent ev) 
	{
		if (ev.getSource()== btRegistrar)
		{

			String identificacion, TipoID, Nombre,estado, Genero, EstadoCivil, FechaNac, Ciudad, Barrio, Estrato, Direccion, comuna, Estado
			, Email1, Email2, Email3;

			Cliente cl = new Cliente();

			identificacion = campoIdentificacion.getText();
			TipoID = (String)comboTipoID.getSelectedItem() ;
			Nombre = campoNombre.getText();
			Genero = (String)comboGenero.getSelectedItem();
			EstadoCivil = (String) comboEstadoCivil.getSelectedItem();
			FechaNac= campoFDia.getText()+"-"+campoFMes.getText()+"-"+campoFAno.getText();
			Ciudad = campoCiudad.getText();
			Barrio = campoBarrio.getText();
			Estrato = campoEstrato.getText();
			Direccion = campoDireccion.getText();
			Email1 = campoEmail1.getText();
			comuna = campoComuna.getText();
			Estado = "Activo";

			Limpiar();

			cl.SetIdCliente(identificacion);
			cl.SetTipoId(TipoID);
			cl.SetNombre(Nombre);
			cl.SetGenero(Genero);
			cl.SetEstadoCivil(EstadoCivil);
			cl.setFechaNac(FechaNac);
			cl.SetCiudad(Ciudad);
			cl.SetBarrio(Barrio);
			cl.SetEstrato(Estrato);
			cl.SetDireccion(Direccion);
			cl.SetComuna(comuna);
			cl.SetEstado(Estado);

			DAOCliente daocliente = new DAOCliente();

			int resultado = daocliente.GuardarCliente(cl);

			if(ventanaP.isMostrandoTabla() == true && resultado > 0)
			{
				JOptionPane.showMessageDialog(null, "El cliente "+Nombre+" ha sido agregado correctamente");
				ventanaP.removerContenidoVentana();
				ventanaP.mostrarTabla("cliente");

			}
			if(ventanaP.isMostrandoTabla() == false && resultado > 0)
				JOptionPane.showMessageDialog(null, "El cliente "+Nombre+" ha sido agregado correctamente");
			if(resultado <= 0)
				JOptionPane.showMessageDialog(null, "Error al registrar cliente","ERROR", JOptionPane.ERROR_MESSAGE);	
		}

		if (ev.getSource()== btCancelar)
		{
			dispose();				
		}

		if(ev.getSource() == btLimpiar)
		{
			Limpiar();
		}
	}
}

