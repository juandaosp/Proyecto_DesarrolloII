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

import DAO.DAOPlanPostPago;
import Logica.PlanPostpago;

public class EditarPlanPostpago extends JFrame implements ActionListener
{
	private JButton btEditar, btCancelar, btGuardar, btCerrar;
	private JTextField campoNombre, campoCostoBasico, campoCostoDiarioRoaming, campoPorcDescRepo;
	private JPanel panelCentral;
	private String num_plan;
	private DAOPlanPostPago daoplanpostpago;
	PlanPostpago planpostpago;
	
	private VentanaPrincipal ventanaP;

	public EditarPlanPostpago(String num_plan, VentanaPrincipal ventanaP)
	{		
		super("Informacion Plan Postpago");

		this.num_plan = num_plan;
		this.ventanaP = ventanaP;
		this.ventanaP.setVisible(true);
		
		daoplanpostpago = new DAOPlanPostPago();

		JLabel lbNPlan = new JLabel ("Numero del plan");
		JLabel lbNombre = new JLabel ("Nombre");
		JLabel lbCostoBasico = new JLabel ("Costo Basico");
		JLabel lbCostoDiarioRoaming= new JLabel ("Costo Diario Roamig");
		JLabel lbPorcDescRepo= new JLabel ("Porcentaje de descuento por repocision");

		campoNombre = new JTextField();
		campoNombre.setEditable(false);
		campoCostoBasico = new JTextField();
		campoCostoBasico.setEditable(false);
		campoCostoDiarioRoaming = new JTextField();
		campoCostoDiarioRoaming.setEditable(false);
		campoPorcDescRepo = new JTextField();
		campoPorcDescRepo.setEditable(false);
		
		//LLENAMOS LOS CAMPOS CON LOS DATOS DEL CLIENTE A EDITAR
		planpostpago = daoplanpostpago.consultarPlanPostPago(num_plan);
		JLabel lbInfoNPlan = new JLabel(planpostpago.getNumPlan());
		campoNombre.setText(planpostpago.getNombre());
		campoCostoBasico.setText(planpostpago.getCostoBasico()+"");
		campoCostoDiarioRoaming.setText(planpostpago.getCostoDiarioRoaming()+"");
		campoPorcDescRepo.setText(planpostpago.getPorcentDescuentoRep()+"");
		

		btGuardar = new JButton("Guardar");
		btCancelar = new JButton("Cancelar");
		btEditar = new JButton("Editar");
		btCerrar = new JButton("Cerrar");
		
		//AGREGAR EVENTOS DE LOS BOTONES
		btEditar.addActionListener(this);
		btCerrar.addActionListener(this);
		btGuardar.addActionListener(this);
		btCancelar.addActionListener(this);


		panelCentral = new JPanel(new GridLayout(6,2,10,10));
		panelCentral.add(lbNPlan); 
		panelCentral.add(lbInfoNPlan);
		panelCentral.add(lbNombre); 
		panelCentral.add(campoNombre);
		panelCentral.add(lbCostoBasico); 
		panelCentral.add(campoCostoBasico); 
		panelCentral.add(lbCostoDiarioRoaming);		
		panelCentral.add(campoCostoDiarioRoaming);
		panelCentral.add(lbPorcDescRepo);
		panelCentral.add(campoPorcDescRepo);
		panelCentral.add(btEditar);
		panelCentral.add(btCerrar);
		
		ImageIcon imLogo  = new ImageIcon("Imagenes/smalllogo.png");		
		JLabel lbLogo = new JLabel(imLogo);	

		setLayout(new BorderLayout(5,5));
		add(lbLogo, BorderLayout.NORTH);
		add(panelCentral, BorderLayout.CENTER);

		setSize(500,300);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ev) 
	{
		if (ev.getSource()== btEditar)
		{	
			campoNombre.setEditable(true);
			campoCostoBasico.setEditable(true);
			campoCostoDiarioRoaming.setEditable(true);
			campoPorcDescRepo.setEditable(true);
			
			panelCentral.remove(btEditar);
			panelCentral.remove(btCerrar);
			
			panelCentral.add(btGuardar);
			panelCentral.add(btCancelar);
			
			this.pack();
			setSize(500,300);
				
		}		
		if (ev.getSource()== btCancelar)
		{
			campoNombre.setEditable(false);
			campoCostoBasico.setEditable(false);
			campoCostoDiarioRoaming.setEditable(false);
			campoPorcDescRepo.setEditable(false);
			
			panelCentral.remove(btGuardar);
			panelCentral.remove(btCancelar);
			
			panelCentral.add(btEditar);
			panelCentral.add(btCerrar);
			
			this.pack();
			setSize(500,300);
		}
		if (ev.getSource()== btCerrar)
		{
			dispose();
		}
		if (ev.getSource()== btGuardar)
		{
			planpostpago.setNombre(campoNombre.getText());
			planpostpago.setCostoBasico(Integer.parseInt(campoCostoBasico.getText()));
			planpostpago.setCostoDiarioRoaming(Integer.parseInt(campoCostoDiarioRoaming.getText()));
			planpostpago.setPorcentDescuentoRep(Integer.parseInt(campoPorcDescRepo.getText()));
			
			int resultado = daoplanpostpago.modificarPlanPospago(planpostpago, this.num_plan);

			if(resultado > 0)
			{
				JOptionPane.showMessageDialog(null, "El plan ha sido modificado satisfactoriamente");
				ventanaP.removerContenidoVentana();
				ventanaP.mostrarTabla("planpostpago");

			}	
			else
				JOptionPane.showMessageDialog(null, "Error al modificar plan","ERROR", JOptionPane.ERROR_MESSAGE);
			
			dispose();
		}
	}

}
