package GUI;

import DAO.DAOEquipo;
import DAO.DAORepEquipos;
import DAO.DAOSimcard;
import Logica.RepEquipos;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class RegistrarReposicion  extends JFrame implements ActionListener
{
	private JButton btEnviar, btCancelar, btLimpiar;
	private JComboBox comboTReposicion;
	private JTextField campoImei, campoNumSimcard; 
	
	private JTextField campoFDia, campoFMes, campoFAno;
	
	public RegistrarReposicion()
	{		
		super("Reposicion");
		
		JLabel lbImei = new JLabel ("IMEI");
		JLabel lbNumSimcard = new JLabel ("Numero de la simcard");
		JLabel lbTReposicion = new JLabel ("Tipo Reposicion");
                JLabel lbFecha = new JLabel ("Fecha");
		
		String [] listaTReposicion = {"Renovacion", "Perdida", "Robo", "Daño", "Otro"};
		//para seleccionar cargos como un string: (String) cbCargo.getSelectedItem();
		
		comboTReposicion = new JComboBox(listaTReposicion);		
		
		campoImei = new JTextField();
                campoNumSimcard = new JTextField();
                campoFDia = new JTextField();
                campoFMes = new JTextField();
                campoFAno = new JTextField();
		
		btEnviar = new JButton("Enviar");
		btCancelar = new JButton("Cancelar");
                btLimpiar = new JButton("Limpiar");
		
		//AGREGAR EVENTOS DE LOS BOTONES
		btEnviar.addActionListener(this);
		btCancelar.addActionListener(this);
                btLimpiar.addActionListener(this);
                
                JPanel panelFecha = new JPanel();
                panelFecha.setLayout(new GridLayout(1, 3, 5, 5));
                panelFecha.add(campoFAno);
                panelFecha.add(campoFMes);
                panelFecha.add(campoFDia);
		
		JPanel panelCentral = new JPanel(new GridLayout(4,2,10,10));
		panelCentral.add(lbImei); 
		panelCentral.add(campoImei);
		panelCentral.add(lbNumSimcard); 
		panelCentral.add(campoNumSimcard); 
		panelCentral.add(lbTReposicion);		
		panelCentral.add(comboTReposicion);
                panelCentral.add(lbFecha);
                panelCentral.add(panelFecha);
                
                JPanel b = new JPanel();
                
                b.setLayout(new GridLayout(1, 3, 5 ,5));
		b.add(btEnviar);
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
	}
	
	public void actionPerformed(ActionEvent ev) 
	{
		if (ev.getSource()== btEnviar)
		{	
                    String tipoRep, imei, numSim, Fecha;
                    tipoRep =  (String) comboTReposicion.getSelectedItem();
                    imei = campoImei.getText();
                    numSim = campoNumSimcard.getText();
                    Fecha = campoFAno.getText()+"-"+campoFMes.getText()+"-"+campoFDia.getText();
                    
                    RepEquipos r = new RepEquipos();
                    
                    r.setFecha(Fecha);
                    r.setImei(imei);
                    r.setNumSimcard(numSim);
                    r.setTipoRep(tipoRep);
                    
                    DAOEquipo daoe = new DAOEquipo();
                    
                    boolean bool1 = daoe.ComprovarImei(imei);
                    
                    if(bool1 == false){
                    
                        JOptionPane.showMessageDialog(null, "Error el Equipo con IMEI : "+ imei +
                                " No existe, agreguelo o intente con otro");
                        campoImei.setText("");
                        
                    }
                    DAOSimcard daosim = new DAOSimcard();
                    
                    boolean bool2 = daosim.ComprovarNumSimcard(numSim);
                    
                    if(bool2 == false){
                    
                        JOptionPane.showMessageDialog(null, "Error la simcard con numero : "+ numSim +
                                " No existe, agreguela o intente con otra");
                        
                        campoNumSimcard.setText("");
                    }
                    
                    DAORepEquipos daore= new DAORepEquipos();
                    
                    boolean bool3 = daore.ComprovarRepEquipo(numSim, imei);
                    
                    if(bool3 == true){
                    
                        JOptionPane.showMessageDialog(null, "Error la repocision del equipo con IMEI: "+ imei +
                                "para la simcard: "+numSim+ " ya existe en el sistema ");
                       
                    }
                    
                    if(bool1 == true && bool2 == true && bool3 == false){
                    
                        daore.GuardarPlanPrepago(r);
                        
                        campoImei.setText("");
                        campoNumSimcard.setText("");
                        campoFAno.setText("");
                        campoFMes.setText("");
                        campoFDia.setText("");
                    
                    
                    }
                    
		}
		
                if(ev.getSource() == btLimpiar){
                
                    campoImei.setText("");
                    campoNumSimcard.setText("");
                    campoFAno.setText("");
                    campoFMes.setText("");
                    campoFDia.setText("");
                    
                
                
                }
                
		if (ev.getSource()== btCancelar)
		{
                    this.dispose();
			

                }		
	}	

        public static void main(String args[]){
        
            RegistrarReposicion repp = new RegistrarReposicion();
        
        }
}
