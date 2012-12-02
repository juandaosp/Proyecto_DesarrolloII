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

import DAO.DAOOperadorMovilLocal;
import Logica.OperadorMovilLocal;

public class EditarMovilLocal extends JFrame implements ActionListener
{
	private JButton btEditar, btCancelar, btGuardar, btCerrar;
	private JTextField campoCostoSMS, campoCostoMin, campoCostoKB;
	private JPanel panelCentral;
	private String nom_operador, nom_pais;
	private DAOOperadorMovilLocal daoopmovillocal;
	private OperadorMovilLocal opmovillocal;
	private VentanaPrincipal ventanaP;
	
	public EditarMovilLocal(String nom_operador, String nom_pais, VentanaPrincipal ventanaP)
	{		
		super("Editar Movil Local");
		
		this.nom_operador = nom_operador;
		this.nom_pais = nom_pais;
		this.ventanaP = ventanaP;
		this.ventanaP.setVisible(true);
		
		daoopmovillocal = new DAOOperadorMovilLocal();
		
		JLabel lbNombre = new JLabel ("Nombre Operador");
		JLabel lbPais = new JLabel ("Pais");
		JLabel lbCostoSMS = new JLabel ("Costo SMS");
		JLabel lbCostoMin = new JLabel ("Costo Minuto");
		JLabel lbCostoKB = new JLabel ("Costo KB");

		campoCostoSMS = new JTextField();
		campoCostoSMS.setEditable(false);
		campoCostoMin = new JTextField();
		campoCostoMin.setEditable(false);
		campoCostoKB = new JTextField();
		campoCostoKB.setEditable(false);
		
		//LLENAR LOS CAMPOS CON LA INFORMACION DE OPERADOR MOVIL LOCAL
		opmovillocal = daoopmovillocal.consultarOML(nom_operador, nom_pais);
		
		JLabel lbInfoNombre = new JLabel(opmovillocal.getNomOperador());
		JLabel lbInfoPais = new JLabel(opmovillocal.getPais());
		campoCostoSMS.setText(opmovillocal.getCostoSms()+"");
		campoCostoMin.setText(opmovillocal.getCostoMinuto()+"");
		campoCostoKB.setText(opmovillocal.getCostoKb()+"");
		
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
		
		panelCentral = new JPanel(new GridLayout(6,2,10,10));
		panelCentral.add(lbNombre); 
		panelCentral.add(lbInfoNombre);
		panelCentral.add(lbPais); 
		panelCentral.add(lbInfoPais);
		panelCentral.add(lbCostoSMS); 
		panelCentral.add(campoCostoSMS);
		panelCentral.add(lbCostoMin); 
		panelCentral.add(campoCostoMin);
		panelCentral.add(lbCostoKB); 
		panelCentral.add(campoCostoKB);
		panelCentral.add(btEditar);
		panelCentral.add(btCerrar);  

		ImageIcon imLogo  = new ImageIcon("Imagenes/smalllogo.png");	
		JLabel lbLogo = new JLabel(imLogo);		
		
		setLayout(new BorderLayout(5,5));
		add(lbLogo, BorderLayout.NORTH);
		add(panelCentral, BorderLayout.CENTER);
				
		setSize(280,300);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ev) 
	{
		if (ev.getSource()== btEditar)
		{
			campoCostoSMS.setEditable(true);
			campoCostoMin.setEditable(true);
			campoCostoKB.setEditable(true);

			panelCentral.remove(btEditar);
			panelCentral.remove(btCerrar);

			panelCentral.add(btGuardar);
			panelCentral.add(btCancelar);

			this.pack();
			setSize(280,300);

		}		
		if (ev.getSource()== btCancelar)
		{
			campoCostoSMS.setEditable(false);
			campoCostoMin.setEditable(false);
			campoCostoKB.setEditable(false);

			panelCentral.remove(btGuardar);
			panelCentral.remove(btCancelar);

			panelCentral.add(btEditar);
			panelCentral.add(btCerrar);

			this.pack();
			setSize(280,300);
		}
		if (ev.getSource()== btCerrar)
		{
			dispose();
		}
		if (ev.getSource()== btGuardar)
		{
			opmovillocal.setCostoSms(Integer.parseInt(campoCostoSMS.getText()));
			opmovillocal.setCostoMinuto(Integer.parseInt(campoCostoMin.getText()));
			opmovillocal.setCostoKb(Integer.parseInt(campoCostoKB.getText()));
			
			int resultado = daoopmovillocal.modificarOML(opmovillocal, nom_operador, nom_pais);
			if(resultado > 0)
			{
				JOptionPane.showMessageDialog(null, "El operador ha sido modificado satisfactoriamente");
				ventanaP.removerContenidoVentana();
				ventanaP.mostrarTabla("mlocal");

			}	
			else
				JOptionPane.showMessageDialog(null, "Error al modificar operador","ERROR", JOptionPane.ERROR_MESSAGE);
			
			dispose();
		}
	}
}