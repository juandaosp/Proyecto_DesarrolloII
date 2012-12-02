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

import DAO.DAOOperadorFijo;
import Logica.OperadorFijo;

public class EditarOperadorFijo extends JFrame implements ActionListener
{
	private JButton btEditar, btCancelar, btGuardar, btCerrar;
	private JTextField campoCostoMin;
	private JPanel panelCentral;
	private String nom_operador, nom_pais;
	private DAOOperadorFijo daoopfijo;
	private OperadorFijo opfijo;
	private VentanaPrincipal ventanaP;
	
	public EditarOperadorFijo(String nom_operador, String nom_pais, VentanaPrincipal ventanaP)
	{		
		super("Edicion de operador fijo");
		
		this.nom_operador = nom_operador;
		this.nom_pais = nom_pais;
		this.ventanaP = ventanaP;
		this.ventanaP.setVisible(true);
		
		daoopfijo = new DAOOperadorFijo();
		
		JLabel lbNombre = new JLabel ("Nombre Operador");
		JLabel lbPais = new JLabel ("Pais");
		JLabel lbCostoMin = new JLabel ("Costo Minuto");

		campoCostoMin = new JTextField();
		campoCostoMin.setEditable(false);	
		
		//LLENAR LOS CAMPOS CON LA INFORMACION DE OPERADOR FIJO
		opfijo = daoopfijo.consultarOperadorFijo(nom_operador, nom_pais);
		
		JLabel lnInfoNombre = new JLabel(opfijo.getNomOperador());
		JLabel lnInfoPais = new JLabel(opfijo.getPais());
		campoCostoMin.setText(opfijo.getCostoMinuto()+"");
		
		btEditar = new JButton("Editar");
		btCerrar = new JButton("Cerrar");
		btGuardar = new JButton("Guardar");
		btCancelar = new JButton("Cancelar");
		
		
		//AGREGAR EVENTOS DE LOS BOTONES
		btEditar.addActionListener(this);
		btCerrar.addActionListener(this);
		btGuardar.addActionListener(this);
		btCancelar.addActionListener(this);
		
		panelCentral = new JPanel(new GridLayout(4,2,10,10));
		panelCentral.add(lbNombre); 
		panelCentral.add(lnInfoNombre);
		panelCentral.add(lbPais); 
		panelCentral.add(lnInfoPais);
		panelCentral.add(lbCostoMin); 
		panelCentral.add(campoCostoMin);
		panelCentral.add(btEditar);
		panelCentral.add(btCerrar);  

		ImageIcon imLogo  = new ImageIcon("Imagenes/smalllogo.png");		
		JLabel lbLogo = new JLabel(imLogo);		
		
		setLayout(new BorderLayout(5,5));
		add(lbLogo, BorderLayout.NORTH);
		add(panelCentral, BorderLayout.CENTER);
				
		setSize(300,250);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ev) 
	{
		if (ev.getSource()== btEditar)
		{
			campoCostoMin.setEditable(true);

			panelCentral.remove(btEditar);
			panelCentral.remove(btCerrar);

			panelCentral.add(btGuardar);
			panelCentral.add(btCancelar);

			this.pack();
			setSize(300,250);

		}		
		if (ev.getSource()== btCancelar)
		{
			campoCostoMin.setEditable(false);

			panelCentral.remove(btGuardar);
			panelCentral.remove(btCancelar);

			panelCentral.add(btEditar);
			panelCentral.add(btCerrar);

			this.pack();
			setSize(300,250);
		}
		if (ev.getSource()== btCerrar)
		{
			dispose();
		}
		if (ev.getSource()== btGuardar)
		{
			opfijo.setCostoMinuto(Integer.parseInt(campoCostoMin.getText()));
			
			int resultado = daoopfijo.modificarOperadorFijo(opfijo, nom_operador, nom_pais);
			
			if(resultado > 0)
			{
				JOptionPane.showMessageDialog(null, "El operador ha sido modificado satisfactoriamente");
				ventanaP.removerContenidoVentana();
				ventanaP.mostrarTabla("ofijo");

			}	
			else
				JOptionPane.showMessageDialog(null, "Error al modificar operador","ERROR", JOptionPane.ERROR_MESSAGE);
			
			dispose();
		}		
	}
}