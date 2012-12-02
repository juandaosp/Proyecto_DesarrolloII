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

import DAO.DAOOperadorMovilInternacional;
import Logica.OperadorMovilInternacional;

public class EditarOpMovilInternacional extends JFrame implements ActionListener
{
	private JButton btEditar, btCancelar, btGuardar, btCerrar;
	private JTextField campoCMSNacional, campoCMENacional, campoCMSInter, campoCMEInter,campoCSNacional, campoCSInter;
	private JPanel panelCentral;
	private String nom_operador, pais;
	private DAOOperadorMovilInternacional daoopmovilinter;
	private OperadorMovilInternacional opmovilinter;
	private VentanaPrincipal ventanaP;
	
	public EditarOpMovilInternacional(String nom_operador, String pais, VentanaPrincipal ventanaP)
		{		
			super("Editar Operador Movil Internacional");
			
			
			this.nom_operador = nom_operador;
			this.pais = pais;
			this.ventanaP = ventanaP;
			this.ventanaP.setVisible(true);
			
			daoopmovilinter = new DAOOperadorMovilInternacional();
			
			JLabel lbNombre = new JLabel ("Nombre Operador");
			JLabel lbPais = new JLabel ("Pais");
			JLabel lbCMSNacional = new JLabel ("Costo Minuto Salida Nacional");
			JLabel lbCMENacional = new JLabel ("Costo Minuto Entrada Nacional");
			JLabel lbCMSInter = new JLabel ("Costo Minuto Salida Internacional");
			JLabel lbCMEInter = new JLabel ("Costo Minuto Entrada Internacional");
			JLabel lbCSNacional = new JLabel ("Costo SMS Nacional");
			JLabel lbCSInter = new JLabel ("Costo SMS Internacional");

			campoCMSNacional = new JTextField();
			campoCMSNacional.setEditable(false);
			campoCMENacional = new JTextField();
			campoCMENacional.setEditable(false);
			campoCMSInter = new JTextField();
			campoCMSInter.setEditable(false);
			campoCMEInter = new JTextField();
			campoCMEInter.setEditable(false);
			campoCSNacional = new JTextField();
			campoCSNacional.setEditable(false);
			campoCSInter = new JTextField();
			campoCSInter.setEditable(false);
			
			//LLENAR LOS CAMPOS CON LA INFO DE OPERADOR MOVIL INTERNACIONAL
			opmovilinter = daoopmovilinter.consultarOMI(nom_operador, pais);
			JLabel lbInfoNombre = new JLabel(opmovilinter.getNomOperador());
			JLabel lbInfoPais = new JLabel(opmovilinter.getPais());
			campoCMSNacional.setText(opmovilinter.getCostoMinSalidaNacional()+"");
			campoCMENacional.setText(opmovilinter.getCostoMinEntradaNacional()+"");
			campoCMSInter.setText(opmovilinter.getCostoMinSalidaInter()+"");
			campoCMEInter.setText(opmovilinter.getCostoMinEntradaInter()+"");
			campoCSNacional.setText(opmovilinter.getCostoSmsNacional()+"");
			campoCSInter.setText(opmovilinter.getCostoSmsInter()+"");
			
			btEditar = new JButton("Editar");
			btCerrar = new JButton("Cerrar");
			btGuardar = new JButton("Guardar");
			btCancelar = new JButton("Cancelar");
			
			
			//AGREGAR EVENTOS DE LOS BOTONES
			btEditar.addActionListener(this);
			btCerrar.addActionListener(this);
			btGuardar.addActionListener(this);
			btCancelar.addActionListener(this);
			
			panelCentral = new JPanel(new GridLayout(9,2,10,10));
			panelCentral.add(lbNombre); 
			panelCentral.add(lbInfoNombre);
			panelCentral.add(lbPais); 
			panelCentral.add(lbInfoPais);
			panelCentral.add(lbCMSNacional); 
			panelCentral.add(campoCMSNacional);
			panelCentral.add(lbCMENacional);
			panelCentral.add(campoCMENacional);  
			panelCentral.add(lbCMSInter); 
			panelCentral.add(campoCMSInter);
			panelCentral.add(lbCMEInter); 
			panelCentral.add(campoCMEInter);
			panelCentral.add(lbCSNacional); 
			panelCentral.add(campoCSNacional);
			panelCentral.add(lbCSInter);
			panelCentral.add(campoCSInter); 
			panelCentral.add(btEditar);
			panelCentral.add(btCerrar); 
	
			ImageIcon imLogo  = new ImageIcon("Imagenes/smalllogo.png");		
			JLabel lbLogo = new JLabel(imLogo);		
			
			setLayout(new BorderLayout(5,5));
			add(lbLogo, BorderLayout.NORTH);
			add(panelCentral, BorderLayout.CENTER);
					
			setSize(420,450);
			setLocationRelativeTo(null);
			setVisible(true);
		}
		
	public void actionPerformed(ActionEvent ev) 
		{
		if (ev.getSource()== btEditar)
		{
			campoCMSNacional.setEditable(true);
			campoCMENacional.setEditable(true);
			campoCMSInter.setEditable(true);
			campoCMEInter.setEditable(true);
			campoCSNacional.setEditable(true);
			campoCSInter.setEditable(true);


			panelCentral.remove(btEditar);
			panelCentral.remove(btCerrar);

			panelCentral.add(btGuardar);
			panelCentral.add(btCancelar);

			this.pack();
			setSize(420,450);

		}		
		if (ev.getSource()== btCancelar)
		{
			campoCMSNacional.setEditable(false);
			campoCMENacional.setEditable(false);
			campoCMSInter.setEditable(false);
			campoCMEInter.setEditable(false);
			campoCSNacional.setEditable(false);
			campoCSInter.setEditable(false);

			panelCentral.remove(btGuardar);
			panelCentral.remove(btCancelar);

			panelCentral.add(btEditar);
			panelCentral.add(btCerrar);

			this.pack();
			setSize(420,450);
		}
		if (ev.getSource()== btCerrar)
		{
			dispose();
		}
		if (ev.getSource()== btGuardar)
		{
			opmovilinter.setCostoMinSalidaNacional(Integer.parseInt(campoCMSNacional.getText()));
			opmovilinter.setCostoMinEntradaNacional(Integer.parseInt(campoCMENacional.getText()));
			opmovilinter.setCostoMinSalidaInter(Integer.parseInt(campoCMSInter.getText()));
			opmovilinter.setCostoMinEntradaInter(Integer.parseInt(campoCMEInter.getText()));
			opmovilinter.setCostoSmsNacional(Integer.parseInt(campoCSNacional.getText()));
			opmovilinter.setCostoSmsInter(Integer.parseInt(campoCSInter.getText()));
			
			int resultado = daoopmovilinter.modificarOMI(opmovilinter, nom_operador, pais);
			if(resultado > 0)
			{
				JOptionPane.showMessageDialog(null, "El operador ha sido modificado satisfactoriamente");
				ventanaP.removerContenidoVentana();
				ventanaP.mostrarTabla("ominter");

			}	
			else
				JOptionPane.showMessageDialog(null, "Error al modificar operador","ERROR", JOptionPane.ERROR_MESSAGE);
			
			dispose();
			
		}		
		}
}
