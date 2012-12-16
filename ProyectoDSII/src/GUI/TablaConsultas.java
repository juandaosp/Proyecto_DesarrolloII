package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TablaConsultas extends JFrame implements ActionListener
{
	private JButton btCerrar;

	public TablaConsultas(DefaultTableModel mdConsultas)
	{		
		JTable tablaConsultas = new JTable();
		DefaultTableModel modeloConsultas = mdConsultas;
		JScrollPane scrollConsultas = new JScrollPane();
		
		tablaConsultas.setModel(modeloConsultas);
		scrollConsultas.setViewportView(tablaConsultas);
		//scrollConsultas.setPreferredSize(new Dimension(580, 305));
		
		btCerrar = new JButton("Cerrar");
		JPanel panelBtCerrar = new JPanel(new FlowLayout());
		panelBtCerrar.add(btCerrar);
		
		setLayout(new BorderLayout());
		add(scrollConsultas, BorderLayout.CENTER);
		add(panelBtCerrar, BorderLayout.SOUTH);
		
		btCerrar.addActionListener(this);
		
		setSize(500,500);
		setResizable(false);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ev) 
	{
		if (ev.getSource()== btCerrar)
		{
			dispose();
		}
	}
	
	/*public static void main(String args [])
	{
		TablaConsultas TC = new TablaConsultas(new DefaultTableModel());
	}*/

}
