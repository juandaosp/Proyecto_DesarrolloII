package GUI;

import DAO.DAOOperador;
import DAO.DAOOperadorMovilInternacional;
import Logica.Operador;
import Logica.OperadorMovilInternacional;
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

public class RegistrarOpMovilInternacional extends JFrame implements ActionListener
{
	private JButton btRegistrar, btCancelar, bLimpiar;
	private JTextField campoNombre, campoPais, campoCMSNacional, campoCMENacional, campoCMSInter,campoNumServCli ,
	campoCMEInter,campoCSNacional, campoCSInter, campoCKB;
	private VentanaPrincipal ventanaP;

	public RegistrarOpMovilInternacional(VentanaPrincipal ventanaP)
	{		
		super("Registro Operador Movil Internacional");
		
		this.ventanaP = ventanaP;
		this.ventanaP.setVisible(true);

		JLabel lbNombre = new JLabel ("Nombre Operador");
		JLabel lbPais = new JLabel ("Pais");
		JLabel lbCMSNacional = new JLabel ("Costo Minuto Salida Nacional");
		JLabel lbCMENacional = new JLabel ("Costo Minuto Entrada Nacional");
		JLabel lbCMSInter = new JLabel ("Costo Minuto Salida Internacional");
		JLabel lbCMEInter = new JLabel ("Costo Minuto Entrada Internacional");
		JLabel lbCSNacional = new JLabel ("Costo SMS Nacional");
		JLabel lbCSInter = new JLabel ("Costo SMS Internacional");
		JLabel lbNumServCli = new JLabel("Numero de atencion al cliente");
		JLabel lbCKB = new JLabel("Costo KB");

		campoNumServCli = new JTextField();
		campoNombre = new JTextField();
		campoPais = new JTextField();
		campoCMSNacional = new JTextField();
		campoCMENacional = new JTextField();
		campoCMSInter = new JTextField();
		campoCMEInter = new JTextField();
		campoCSNacional = new JTextField();
		campoCSInter = new JTextField();
		campoCKB = new JTextField();

		btRegistrar = new JButton("Registrar");
		btCancelar = new JButton("Cancelar");
		bLimpiar = new JButton("Limpiar");

		//AGREGAR EVENTOS DE LOS BOTONES
		btRegistrar.addActionListener(this);
		btCancelar.addActionListener(this);
		bLimpiar.addActionListener(this);

		JPanel panelCentral = new JPanel(new GridLayout(11,2,10,10));
		panelCentral.add(lbNombre); 
		panelCentral.add(campoNombre);
		panelCentral.add(lbPais); 
		panelCentral.add(campoPais);
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
		panelCentral.add(lbNumServCli);
		panelCentral.add(campoNumServCli);
		panelCentral.add(lbCKB);
		panelCentral.add(campoCKB);

		JPanel b = new JPanel();
		b.setLayout(new GridLayout(1, 3, 5, 5));
		b.add(btRegistrar);
		b.add(bLimpiar);
		b.add(btCancelar); 

		ImageIcon imLogo  = new ImageIcon("Imagenes/smalllogo.png");		
		JLabel lbLogo = new JLabel(imLogo);		

		setLayout(new BorderLayout(5,5));
		add(lbLogo, BorderLayout.NORTH);
		add(panelCentral, BorderLayout.CENTER);
		add(b, BorderLayout.SOUTH);

		setSize(420,450);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ev) 
	{
		if (ev.getSource()== btRegistrar)
		{
			String nombre, pais, costoMinSalNac, costoMinEntNac, costoMinSalidaInt, costoMinEntradaInt, 
			costoSMSnac, costoSMSinter, numServClie, costoKB;


			nombre = campoNombre.getText();
			pais = campoPais.getText(); 
			costoMinSalNac = campoCMSNacional.getText();
			costoMinEntNac = campoCMENacional.getText();
			costoMinSalidaInt = campoCMSInter.getText(); 
			costoMinEntradaInt = campoCMEInter.getText();
			costoSMSnac = campoCSNacional.getText();
			costoSMSinter = campoCSInter.getText();
			numServClie = campoNumServCli.getText();
			costoKB = campoCKB.getText();

			Operador op = new Operador();
			op.setNomOperador(nombre);
			op.setNumServicioCliente(numServClie);
			op.setPais(pais);

			DAOOperador operador = new DAOOperador();
			operador.GuardarOperador(op);

			OperadorMovilInternacional operaMI = new OperadorMovilInternacional();
			operaMI.setPais(pais);
			operaMI.setNomOperador(nombre);
			operaMI.setCostoMinEntradaInter(Integer.parseInt(costoMinEntradaInt));
			operaMI.setCostoMinEntradaNacional(Integer.parseInt(costoMinEntNac));
			operaMI.setCostoMinSalidaNacional(Integer.parseInt(costoMinSalNac));
			operaMI.setCostoMinSalidaInter(Integer.parseInt(costoMinSalNac));
			operaMI.setCostoSmsNacional(Integer.parseInt(costoSMSnac));
			operaMI.setCostoSmsInter(Integer.parseInt(costoSMSinter));                    
			operaMI.setCostoKb(Integer.parseInt(costoKB));

			DAOOperadorMovilInternacional opmi = new DAOOperadorMovilInternacional();
			
			int resultado = opmi.guardarOMI(operaMI);
			
			if(ventanaP.isMostrandoTabla() == true && resultado > 0)
			{
				JOptionPane.showMessageDialog(null, "El operador ha sido agregado correctamente");
				ventanaP.removerContenidoVentana();
				ventanaP.mostrarTabla("ominter");

			}
			if(ventanaP.isMostrandoTabla() == false && resultado > 0)
				JOptionPane.showMessageDialog(null, "El operador ha sido agregado correctamente");
			if(resultado <= 0)
				JOptionPane.showMessageDialog(null, "Error al registrar operador","ERROR", JOptionPane.ERROR_MESSAGE);


			campoNombre.setText("");
			campoPais.setText(""); 
			campoCMSNacional.setText("");
			campoCMENacional.setText("");
			campoCMSInter.setText(""); 
			campoCMEInter.setText("");
			campoCSNacional.setText("");
			campoCSInter.setText("");
			campoNumServCli.setText("");
			campoCKB.setText("");


		}		

		if (ev.getSource()== btCancelar)
		{
			this.dispose();
		}

		if(ev.getSource() == bLimpiar)
		{
			campoNombre.setText("");
			campoPais.setText(""); 
			campoCMSNacional.setText("");
			campoCMENacional.setText("");
			campoCMSInter.setText(""); 
			campoCMEInter.setText("");
			campoCSNacional.setText("");
			campoCSInter.setText("");
			campoNumServCli.setText("");
			campoCKB.setText("");

		}


	}

}
