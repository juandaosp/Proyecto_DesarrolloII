package GUI;

import DAO.DAOPlanPostPago;
import DAO.DAOServPlanPostpago;
import DAO.DAOServicio;
import Logica.ServPlanPostpago;
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

public class RegistroServicioPPostpago extends JFrame implements ActionListener
{
	private JButton btRegistrar, btCancelar, btLimpiar;
	private JTextField campoNPlan, campoIDServicio, campoCServicio;
	
	public RegistroServicioPPostpago()
	{		
		super("Registro Servicio Plan Postpago");
		
		JLabel lbNPlan = new JLabel ("Numero del Plan");
		JLabel lbIDServicio = new JLabel ("ID del Servicio");
		JLabel lbCServicio = new JLabel ("Cantidad Servicio");
		
		campoNPlan = new JTextField();
		campoIDServicio = new JTextField();
		campoCServicio = new JTextField();
		
		btRegistrar = new JButton("Registrar");
		btCancelar = new JButton("Cancelar");
                btLimpiar = new JButton ("Limpiar");
		
		//AGREGAR EVENTOS DE LOS BOTONES
		btRegistrar.addActionListener(this);
		btCancelar.addActionListener(this);
                btLimpiar.addActionListener(this);
		
		JPanel panelCentral = new JPanel(new GridLayout(3,2,10,10));
		panelCentral.add(lbNPlan); 
		panelCentral.add(campoNPlan);
		panelCentral.add(lbIDServicio); 
		panelCentral.add(campoIDServicio);
		panelCentral.add(lbCServicio); 
		panelCentral.add(campoCServicio);
                
                JPanel b = new JPanel();
                b.setLayout(new GridLayout(1, 3 , 5 , 5));
		b.add(btRegistrar);
                b.add(btLimpiar);
		b.add(btCancelar);  

		ImageIcon imLogo  = new ImageIcon("Imagenes/smalllogo.png");		
		JLabel lbLogo = new JLabel(imLogo);		
		
		setLayout(new BorderLayout(5,5));
		add(lbLogo, BorderLayout.NORTH);
		add(panelCentral, BorderLayout.CENTER);
                add(b, BorderLayout.SOUTH);
				
		setSize(300,250);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent ev) 
	{
		if (ev.getSource()== btRegistrar)
		{
                    
                    String numPlan, idServicio, cantServicio;
                    
                    numPlan = campoNPlan.getText(); 
                    idServicio = campoIDServicio.getText(); 
                    cantServicio = campoCServicio.getText();
                    
                    ServPlanPostpago serv = new ServPlanPostpago();
                    
                    try
                    {
                    serv.setIdServicio(idServicio);
                    serv.setNumPlan(numPlan);
                    serv.setCantidad(Integer.parseInt(cantServicio));
                    
                    DAOPlanPostPago daop = new DAOPlanPostPago();
                    boolean bool = daop.ComprovarNumPlanPospago(numPlan);
                    
                    if(bool == false){
                    
                        JOptionPane.showMessageDialog(null, "Error el plan postpago: "+ numPlan +
                                " No existe, creelo o intente con otro");
                        campoNPlan.setText("");
                        
                    }
                    DAOServicio daos= new DAOServicio();
                    boolean bool2 = daos.ComprovarIdServicio(idServicio);
                    
                    if(bool2 == false){
                    
                        JOptionPane.showMessageDialog(null, "Error el Servicio: "+ idServicio +
                                " No existe, creelo o intente con otro");
                        campoIDServicio.setText("");
                    
                    }
                    
                    DAOServPlanPostpago daospp = new DAOServPlanPostpago();
                    
                    boolean bool3 = daospp.ComprovarIdServicioPP(idServicio, numPlan);
                    
                    if(bool3 == true){
                        
                        JOptionPane.showMessageDialog(null, "Error el Plan Postpago : "+ numPlan +
                                " ya tiene el servicio " + idServicio);
                        campoIDServicio.setText("");
                        campoNPlan.setText("");
                    }
                    
                    if(bool == true && bool2 == true && bool3 == false){
                    
                        daospp.GuardarServPlanPos(serv);
                        
                        campoIDServicio.setText("");
                        campoNPlan.setText("");
                        campoCServicio.setText("");
                    
                    
                    }
                    
                    
                    }catch(NumberFormatException e)
                    {
                        JOptionPane.showMessageDialog(null, "Error de formato, compruebe los campos: "+ 
                                e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    
                        campoCServicio.setText("");
                        System.out.println(e.getMessage());
                        
                    }

		}		
		
		if (ev.getSource()== btCancelar)
		{
			this.dispose();
		}
		
                if(ev.getSource() == btLimpiar) {
                
                        campoIDServicio.setText("");
                        campoNPlan.setText("");
                        campoCServicio.setText("");
                
                }
			
	}

	/**
	 * @param args
	 */
	public static void main (String[] args) 
	{
		// TODO Auto-generated method stub
		RegistroServicioPPostpago RSP = new RegistroServicioPPostpago();

	}


}
