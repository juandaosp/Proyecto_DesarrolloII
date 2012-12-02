package GUI;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class PanelConsulta extends JPanel implements ActionListener{

	private JComboBox comboBox;
	private String opLlamada, opSMS, opInternet, opcion;
	private JButton btnAceptar, btnCancelar;
	
	public PanelConsulta() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{133, 71, 75, 28, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{23, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		opLlamada = "por Llamadas";
		opSMS = "por SMS";
		opInternet = "por Internet";
		
		JLabel lblSeleccioneElTipo = new JLabel("Seleccione el tipo de consumo que desea ver");
		GridBagConstraints gbc_lblSeleccioneElTipo = new GridBagConstraints();
		gbc_lblSeleccioneElTipo.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeleccioneElTipo.gridwidth = 4;
		gbc_lblSeleccioneElTipo.gridx = 3;
		gbc_lblSeleccioneElTipo.gridy = 6;
		add(lblSeleccioneElTipo, gbc_lblSeleccioneElTipo);
		
		comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridwidth = 3;
		gbc_comboBox.anchor = GridBagConstraints.NORTH;
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 8;
		
		comboBox.addItem(opLlamada);
		comboBox.addItem(opSMS);
		comboBox.addItem(opInternet);
		
		add(comboBox, gbc_comboBox);
		
		btnAceptar = new JButton("Aceptar");
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAceptar.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnAceptar.gridx = 3;
		gbc_btnAceptar.gridy = 10;
		add(btnAceptar, gbc_btnAceptar);
		
		JButton btnCancelar_1 = new JButton("Cancelar");
		GridBagConstraints gbc_btnCancelar_1 = new GridBagConstraints();
		gbc_btnCancelar_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancelar_1.gridx = 5;
		gbc_btnCancelar_1.gridy = 10;
		add(btnCancelar_1, gbc_btnCancelar_1);

	}
	
	public String getOpcion()
		{
			return opcion;
		}

	@Override
	public void actionPerformed(ActionEvent evento) {
		
		if (evento.getSource() == btnAceptar)
			{
				opcion = (String)comboBox.getSelectedItem();
				
			}
		if (evento.getSource() == btnCancelar)
			{
				this.removeAll();
			}
	}

	
}
