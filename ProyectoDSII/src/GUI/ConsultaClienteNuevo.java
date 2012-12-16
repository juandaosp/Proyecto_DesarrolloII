package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;



public class ConsultaClienteNuevo extends JFrame implements ActionListener
{
	private JButton btConsultaTipo, btConsultaMes, btCancelar;
	private JComboBox comboTipo, comboMes;
	private TablaConsultas tbConsultas;
	
	public ConsultaClienteNuevo()
	{
		super("Consulta Nuevo Cliente");
		
		
		JLabel lbCTipo = new JLabel ("Consulta por tipo");
		JLabel lbCMes = new JLabel ("Consulta por mes");
		JLabel lbVacio = new JLabel ("        ");
		
		String [] listaTipo = {"Postpago", "Prepago"};	
		String [] listaMes = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
		
		comboTipo = new JComboBox(listaTipo);
		comboMes = new JComboBox(listaMes);	
		
		btConsultaTipo = new JButton("Consultar");
		btConsultaMes = new JButton("Consultar");
		btCancelar = new JButton("Cancelar");
		
		//AGREGAR EVENTOS DE LOS BOTONES
		btConsultaTipo.addActionListener(this);
		btConsultaMes.addActionListener(this);
		btCancelar.addActionListener(this);
		
		JPanel panelCentral = new JPanel(new GridLayout(3,3,10,10));
		panelCentral.add(lbCTipo); 
		panelCentral.add(comboTipo);
		panelCentral.add(btConsultaTipo); 
		panelCentral.add(lbCMes);
		panelCentral.add(comboMes); 
		panelCentral.add(btConsultaMes);
		panelCentral.add(lbVacio);
		panelCentral.add(btCancelar);
		
		ImageIcon imLogo  = new ImageIcon("Imagenes/smalllogo.png");		
		JLabel lbLogo = new JLabel(imLogo);
		
		setLayout(new BorderLayout(5,5));
		add(lbLogo, BorderLayout.NORTH);
		add(panelCentral, BorderLayout.CENTER);
				
		setSize(400,200);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
	}
	
	public void actionPerformed(ActionEvent ev) 
	{
		if (ev.getSource()== btConsultaTipo)
		{
			/*String tipo = comboTipo.getSelectedItem();
			 * DefaultTableModel mdConsulta = DaoCon.ClientesNuevosTipo(tipo);
			tbConsultas = new TablaConsultas(mdConsulta);*/
		}
		if (ev.getSource()== btConsultaMes)
		{
			generarConsultaMes();
		}
		if (ev.getSource()== btCancelar)
		{
			setVisible(false);
		}
			
			
	}
	
	private void generarConsultaMes()
	{
		String fechaI = "", fechaF = "";
		if(comboMes.getSelectedItem().equals("Enero"))
		{
			fechaI = "2012-01-01";
			fechaF = "2012-01-31";
		}
		
		if(comboMes.getSelectedItem().equals("Febrero"))
		{
			fechaI = "2012-02-01";
			fechaF = "2012-02-29";			
		}
		
		if(comboMes.getSelectedItem().equals("Marzo"))
		{
			fechaI = "2012-03-01";
			fechaF = "2012-03-31";			
		}
		
		if(comboMes.getSelectedItem().equals("Abril"))
		{
			fechaI = "2012-04-01";
			fechaF = "2012-04-30";			
		}
		
		if(comboMes.getSelectedItem().equals("Mayo"))
		{
			fechaI = "2012-05-01";
			fechaF = "2012-05-31";			
		}
		
		if(comboMes.getSelectedItem().equals("Junio"))
		{
			fechaI = "2012-06-01";
			fechaF = "2012-06-30";			
		}
		
		if(comboMes.getSelectedItem().equals("Julio"))
		{
			fechaI = "2012-07-01";
			fechaF = "2012-07-31";			
		}
		
		if(comboMes.getSelectedItem().equals("Agosto"))
		{
			fechaI = "2012-08-01";
			fechaF = "2012-08-31";			
		}
		
		if(comboMes.getSelectedItem().equals("Septiembre"))
		{
			fechaI = "2012-09-01";
			fechaF = "2012-09-30";			
		}
		
		if(comboMes.getSelectedItem().equals("Octubre"))
		{
			fechaI = "2012-10-01";
			fechaF = "2012-10-31";			
		}
		
		if(comboMes.getSelectedItem().equals("Noviembre"))
		{
			fechaI = "2012-11-01";
			fechaF = "2012-11-30";			
		}
		
		if(comboMes.getSelectedItem().equals("Diciembre"))
		{
			fechaI = "2012-12-01";
			fechaF = "2012-12-31";			
		}
		
		tbConsultas = new TablaConsultas(mdConsulta);
		
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		ConsultaClienteNuevo CN = new ConsultaClienteNuevo();

	}

}
