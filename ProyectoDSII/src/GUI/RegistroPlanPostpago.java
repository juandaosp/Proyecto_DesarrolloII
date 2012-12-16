package GUI;

import DAO.DAOContrato;
import DAO.DAOOficina;
import DAO.DAOPlanPostPago;
import DAO.DAOSimcard;
import Logica.Contrato;
import Logica.PlanPostpago;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class RegistroPlanPostpago extends JFrame implements ActionListener
{
	private JButton btRegistrar, btCancelar, btLimpiar;
	private JTextField campoNumPlan, campoNombre, campoCostoBasico, campoCostoDiarioRoaming, campoPorcDescRepo;
	private VentanaPrincipal ventanaP;

	public RegistroPlanPostpago(VentanaPrincipal ventanaP)
	{		
		super("Registro Plan Postpago");

		this.ventanaP = ventanaP;
		this.ventanaP.setVisible(true);

		JLabel lbNPlan = new JLabel ("Numero del plan");
		JLabel lbNombre = new JLabel ("Nombre");
		JLabel lbCostoBasico = new JLabel ("Costo Basico");
		JLabel lbCostoDiarioRoaming= new JLabel ("Costo Diario Roamig");
		JLabel lbPorcDescRepo= new JLabel ("Porcentaje de descuento por repocision");

		campoNumPlan = new JTextField();
		campoNombre = new JTextField();
		campoCostoBasico = new JTextField();
		campoCostoDiarioRoaming = new JTextField();
		campoPorcDescRepo = new JTextField();



		btRegistrar = new JButton("Registrar");
		btCancelar = new JButton("Cancelar");
		btLimpiar = new JButton("Limpiar");

		//AGREGAR EVENTOS DE LOS BOTONES
		btRegistrar.addActionListener(this);
		btCancelar.addActionListener(this);
		btLimpiar.addActionListener(this);


		JPanel panelCampos = new JPanel(new GridLayout(5,2,10,10));
		panelCampos.add(lbNPlan); 
		panelCampos.add(campoNumPlan);
		panelCampos.add(lbNombre); 
		panelCampos.add(campoNombre);
		panelCampos.add(lbCostoBasico); 
		panelCampos.add(campoCostoBasico); 
		panelCampos.add(lbCostoDiarioRoaming);		
		panelCampos.add(campoCostoDiarioRoaming);
		panelCampos.add(lbPorcDescRepo);
		panelCampos.add(campoPorcDescRepo);



		ImageIcon imLogo  = new ImageIcon("Imagenes/smalllogo.png");		
		JLabel lbLogo = new JLabel(imLogo);	


		JPanel panelBotones = new JPanel(new GridLayout(1,3,5,5));
		panelBotones.add(btRegistrar);
		panelBotones.add(btCancelar);
		panelBotones.add(btLimpiar);

		setLayout(new BorderLayout(5,5));
		add(lbLogo, BorderLayout.NORTH);
		add(panelCampos, BorderLayout.CENTER);
		add(panelBotones, BorderLayout.SOUTH);

		setSize(500,300);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ev) 
	{
		if (ev.getSource()== btRegistrar)
		{
			String plan, nombre, costoBasico, costoRoaming, Descuento; 


			plan = campoNumPlan.getText();
			nombre = campoNombre.getText();
			costoBasico = campoCostoBasico.getText();
			costoRoaming = campoCostoDiarioRoaming.getText();
			Descuento = campoPorcDescRepo.getText();




			PlanPostpago planP = new PlanPostpago();

			try{

				planP.setNumPlan(plan);
				planP.setNombre(nombre);
				planP.setCostoBasico(Integer.parseInt(costoBasico));
				planP.setCostoDiarioRoaming(Integer.parseInt(costoRoaming));
				planP.setPorcentDescuentoRep(Integer.parseInt(Descuento));


				DAOPlanPostPago pp = new DAOPlanPostPago();

				boolean bool3 = pp.ComprovarNumPlanPospago(plan);

				if (bool3 == true){

					JOptionPane.showMessageDialog(null, "El plan postpago con numero: " +plan+" ya existe",
							"Error", JOptionPane.ERROR_MESSAGE);

					campoNumPlan.setText("");


				}
				else
				{
					int resultado = pp.GuardarPlanPostPago(planP);

					if(ventanaP.isMostrandoTabla() == true && resultado > 0)
					{
						JOptionPane.showMessageDialog(null, "El plan ha sido agregado correctamente");
						ventanaP.removerContenidoVentana();
						ventanaP.mostrarTabla("planpostpago");

					}
					if(ventanaP.isMostrandoTabla() == false && resultado > 0)
						JOptionPane.showMessageDialog(null, "El plan ha sido agregado correctamente");
					if(resultado <= 0)
						JOptionPane.showMessageDialog(null, "Error al registrar el plan","ERROR", JOptionPane.ERROR_MESSAGE);	

					campoNumPlan.setText("");
					campoNombre.setText("");
					campoCostoBasico.setText("");
					campoCostoDiarioRoaming.setText("");
					campoPorcDescRepo.setText("");  
				}

			}catch(NumberFormatException e ){
				JOptionPane.showMessageDialog(null, "Error de formato, compruebe los campos: "+ 
						e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

				campoCostoBasico.setText("");
				campoCostoDiarioRoaming.setText("");
				campoPorcDescRepo.setText("");
			}
		}

		if (ev.getSource()== btCancelar)

		{
			this.dispose();
		}


		if(ev.getSource() == btLimpiar){

			campoNumPlan.setText("");
			campoNombre.setText("");
			campoCostoBasico.setText("");
			campoCostoDiarioRoaming.setText("");
			campoPorcDescRepo.setText("");                  

		}
	}
}
