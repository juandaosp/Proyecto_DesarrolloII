package GUI;


import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import javax.swing.JButton;

public class PanelEstadRobosPerdidasMes extends JPanel {

	public JPanel contentPane;
	String opcMes;
	String opcTipo;
	String opcion;
	JButton btConsultaTipo;
	JButton btConsultaMes;
	JButton btnCancelar_1;
	private JComboBox comboCiudades;
	private JComboBox comboMes;
	private JLabel lblSeleccioneElTipo;
	private JLabel lblPorTipo;
	private JLabel lblPorMes;
	String fechaI = "", fechaF = "";
	
	public String getFechaI() {
		return fechaI;
	}

	public String getFechaF() {
		return fechaF;
	}

	String [] listaCiudades = {"Medellin", "Bogota", "Bucaramanga", "Pereira", "Cali", "Pasto", "Cartagena", "Santander de quilichao", "Palmira", "Cucuta", "Buenaventura" };	
	String [] listaMes = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

	public PanelEstadRobosPerdidasMes() {
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			
		opcMes = "Por mes";
		opcTipo = "Por tipo";
				
		lblSeleccioneElTipo = new JLabel("");
		GridBagConstraints gbc_lblSeleccioneElTipo = new GridBagConstraints();
		gbc_lblSeleccioneElTipo.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeleccioneElTipo.gridx = 6;
		gbc_lblSeleccioneElTipo.gridy = 1;
		add(lblSeleccioneElTipo);
		
		lblPorTipo = new JLabel("Por ciudad");
		GridBagConstraints gbc_lblPorTipo = new GridBagConstraints();
		gbc_lblPorTipo.insets = new Insets(0, 0, 5, 5);
		gbc_lblPorTipo.gridx = 1;
		gbc_lblPorTipo.gridy = 3;
		add(lblPorTipo, gbc_lblPorTipo);
		
		comboCiudades = new JComboBox(listaCiudades);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 3;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 4;
		gbc_comboBox.gridy = 3;
		add(comboCiudades, gbc_comboBox);
		
		btConsultaTipo = new JButton("Consultar");
		GridBagConstraints gbc_btnConsultar = new GridBagConstraints();
		gbc_btnConsultar.insets = new Insets(0, 0, 5, 5);
		gbc_btnConsultar.gridx = 8;
		gbc_btnConsultar.gridy = 3;
		add(btConsultaTipo, gbc_btnConsultar);
		
		lblPorMes = new JLabel("Por mes");
		GridBagConstraints gbc_lblPorMes = new GridBagConstraints();
		gbc_lblPorMes.insets = new Insets(0, 0, 5, 5);
		gbc_lblPorMes.gridx = 1;
		gbc_lblPorMes.gridy = 4;
		add(lblPorMes, gbc_lblPorMes);
		
		comboMes = new JComboBox(listaMes);
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.gridwidth = 3;
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 4;
		gbc_comboBox_1.gridy = 4;
		add(comboMes, gbc_comboBox_1);
		
		btConsultaMes = new JButton("Consultar");
		GridBagConstraints gbc_btnConsultar_1 = new GridBagConstraints();
		gbc_btnConsultar_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnConsultar_1.gridx = 8;
		gbc_btnConsultar_1.gridy = 4;
		add(btConsultaMes, gbc_btnConsultar_1);
		
		btnCancelar_1 = new JButton("Cancelar");
		
		GridBagConstraints gbc_btnCancelar_1 = new GridBagConstraints();
		gbc_btnCancelar_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelar_1.gridx = 6;
		gbc_btnCancelar_1.gridy = 6;
		add(btnCancelar_1, gbc_btnCancelar_1);
	}
	
	public String getOpcionCiudades()
	{
		opcion = (String) comboCiudades.getSelectedItem();
		return opcion;
	}
	
	
	public void generarConsultaMes()
	{
		
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

	}
}
