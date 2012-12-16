package GUI;

import DAO.DAOContrato;
import DAO.DAOOficina;
import DAO.DAOPlanPostPago;
import DAO.DAOSimcard;
import Logica.Contrato;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class RegistroContrato extends JFrame implements ActionListener
{
	private JButton btRegistrar, btCancelar, btLimpiar;
	private JTextField campoNSim, campoCOficina, campoNPlan, campoFechaDD, campoFechaAAAA, campoFechaMM;
	private JEditorPane areaDescripcion;
        private JComboBox comboTipo;
	
	public RegistroContrato()
	{
            super("Registro Contrato Postpago");
		
		JLabel lbNSim = new JLabel ("Numero Simcard");
		JLabel lbCOficina = new JLabel ("Codigo Oficina");
		JLabel lbNPlan = new JLabel ("Numero Plan");
		JLabel lbFecha= new JLabel ("Fecha (AAAA-MM-DD)");
		JLabel lbDescripcion= new JLabel ("Descripcion");
                JLabel lbTipo = new JLabel("Tipo de contrato");
		
		campoNSim = new JTextField();
		campoCOficina = new JTextField();
		campoNPlan = new JTextField();
		
                campoFechaAAAA = new JTextField();
                campoFechaMM = new JTextField();
                campoFechaDD   = new JTextField();
                
                String[] listaTipo = {"Sencillo", "Corporativo"};
		
		areaDescripcion = new JEditorPane();
                
                comboTipo = new JComboBox(listaTipo);
		
		btRegistrar = new JButton("Registrar");
		btCancelar = new JButton("Cancelar");
                btLimpiar = new JButton("Limpiar");
		
		//AGREGAR EVENTOS DE LOS BOTONES
		btRegistrar.addActionListener(this);
		btCancelar.addActionListener(this);
                btLimpiar.addActionListener(this);
                
                JPanel panelFecha = new JPanel();
                panelFecha.setLayout(new GridLayout(1, 3 , 5, 5));
                panelFecha.add(campoFechaAAAA);
                panelFecha.add(campoFechaMM);
                panelFecha.add(campoFechaDD);
		
		JPanel panelCampos = new JPanel(new GridLayout(6,2,10,10));
		panelCampos.add(lbNSim); 
		panelCampos.add(campoNSim);
		panelCampos.add(lbCOficina); 
		panelCampos.add(campoCOficina);
		panelCampos.add(lbNPlan); 
		panelCampos.add(campoNPlan); 
		panelCampos.add(lbFecha);		
		panelCampos.add(panelFecha);
		panelCampos.add(lbTipo);
                panelCampos.add(comboTipo);
                panelCampos.add(lbDescripcion);
                panelCampos.add(areaDescripcion);
                
		

		ImageIcon imLogo  = new ImageIcon("Imagenes/smalllogo.png");		
		JLabel lbLogo = new JLabel(imLogo);	
		
		JPanel panelSuperior = new JPanel(new BorderLayout(5,5));
		panelSuperior.add(lbLogo, BorderLayout.NORTH);
		panelSuperior.add(panelCampos, BorderLayout.CENTER);
		
		JPanel panelBotones = new JPanel(new GridLayout(1,3,5,5));
		panelBotones.add(btRegistrar);
		panelBotones.add(btCancelar);
                panelBotones.add(btLimpiar);
		
		setLayout(new BorderLayout(5,5));
		add(panelSuperior, BorderLayout.NORTH);
		add(areaDescripcion, BorderLayout.CENTER);
		add(panelBotones, BorderLayout.SOUTH);
				
		setSize(300,400);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ev) 
	{
		if (ev.getSource()== btRegistrar)
		{
                    String simcard, oficina, numeroPlan, fecha, descripcion, tipo; 
                    
                    simcard = campoNSim.getText();
                    oficina = campoCOficina.getText();
                    numeroPlan = campoNPlan.getText();
                    fecha = campoFechaAAAA.getText()+"-"+campoFechaMM.getText()+"-"+campoFechaDD.getText();
	            descripcion = areaDescripcion.getText();
                    tipo = (String)comboTipo.getSelectedItem();
                    
                    
                    Contrato c = new Contrato();
                    
                    c.setCodOficina(oficina);
                    c.setNumSimcard(simcard);
                    c.setNumPlan(numeroPlan);
                    c.setDescripcion(descripcion);
                    c.setFecha(fecha);
                    c.setTipo(tipo);
                    
                    
                    DAOSimcard sim = new DAOSimcard();
                    
                    boolean bool1 = sim.ComprovarNumSimcard(simcard);
                    
                    //Comprobar si existe la simcard
                    if(bool1 == false){
                    
                        JOptionPane.showMessageDialog(null, "La simcard con numero: "+simcard+" no existe, por favor agreguela o "
                                + "intente con otra.", "Error de llave foranea", JOptionPane.ERROR_MESSAGE);
                                            
                        campoNSim.setText("");

                        
                    
                    }
                    
                    // Comprobar si existe la oficina
                    
                    DAOOficina ofi = new DAOOficina();
                    boolean bool2 = ofi.ComprovarCodigoOficina(oficina);
                    
                    if(bool2 == false){
                    
                        JOptionPane.showMessageDialog(null, "La oficina de codigo: "+oficina+ "no existe por favor agreguela o intente con otra"
                                , "Error de llave foranea", JOptionPane.ERROR_MESSAGE);
                                            
                        campoCOficina.setText("");

                        
                        
                    }
                    
                    // comprobar si existe el plan
                    
                    DAOPlanPostPago pp = new DAOPlanPostPago();
                    
                    boolean bool3 = pp.ComprovarNumPlanPospago(numeroPlan);
                    
                    if (bool3 == false){
                    
                        JOptionPane.showMessageDialog(null, "El plan postpago con numero: " +numeroPlan+" no existe, agregue el plan o intente con otro",
                                "Error", JOptionPane.ERROR_MESSAGE);
                                            
                        campoNPlan.setText("");

                    
                    }
                    
                    // comprobar si la el contrato ya existe
                    DAOContrato contrato = new DAOContrato();
                    boolean bool4  = contrato.ComprovarContrato(simcard, numeroPlan, oficina);
                    
                    if(bool4 == true){
                        
                        JOptionPane.showMessageDialog(null, "Este contrato ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                    
                        campoNSim.setText("");
                        campoCOficina.setText("");
                        campoNPlan.setText("");
                    
                    
                    }
                    
                    if ((bool1 == true) && (bool2 == true)  && (bool3 == true) && (bool4 == false)){
                    
                    contrato.GuardarContrato(c);
                    
                    campoNSim.setText("");
                    campoCOficina.setText("");
                    campoNPlan.setText("");
                    campoFechaAAAA.setText("");
                    campoFechaMM.setText("");
                    campoFechaDD.setText("");
	            areaDescripcion.setText("");
                    }
                }		
		
		if (ev.getSource()== btCancelar)
		{
			this.dispose();
		
                }
                
                
                if(ev.getSource() == btLimpiar){
                    
                    campoNSim.setText("");
                    campoCOficina.setText("");
                    campoNPlan.setText("");
                    campoFechaAAAA.setText("");
                    campoFechaMM.setText("");
                    campoFechaDD.setText("");
	            areaDescripcion.setText("");
                }
        }
}


