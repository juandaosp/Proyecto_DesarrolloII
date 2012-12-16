package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;



public class VentanaPrincipal extends JFrame implements ActionListener
{
	private JButton btCerrar, btInformacion, btBorrar, btBuscar, btReporte;
	private JTextField campoBusqueda, campoSegBusqueda;
	private JMenuBar barraPrincipal;	
	private JMenu menuRegistros, menuConsultas, menuOpciones, menuEdiciones;
	private JMenuItem itemSalir, itemCliente, itemEmpleado, itemEquipo, itemOficina, itemPlanes, itemServicio;
	private JMenuItem itemOFijo, itemOMInter, itemMLocal, itemContrato, itemContratoPrepago;	
	private JMenuItem itemReposicion, itemSPPostpago, itemEdicionCliente, itemEdicionSPPostpago, itemEdicionPlanes;
	private JMenuItem itemEdicionEmpleado, itemEdicionOficina, itemEdicionEquipo,itemEdicionOFijo,itemEdicionOMInter, itemEdicionMLocal;
	private JMenuItem itemCClientesNuevos, itemFranjasUsoRed, itemPlanesEscogidos, itemUsuariosPrepago, itemUsuariosPostpago, itemDatosYConsumo;
	private JMenuItem itemOpRoaming, itemOpLocalMasUsado, itemCorporativos, itemConsumoPorGenero, itemRobos, itemConsumoPorAbonado, itemEquipos;
	private JPanel panelBotones;
	private ImageIcon imLogo;
	private JTable tablaConsultas;
	
	private JScrollPane scrollConsultas;
	private PanelCCAbonado panelCCAbonado;
	private PanelCCOperador panelCCOperador;
	private PanelEstadRobosPerdidasMes panelEstadisticas;
	private PanelClienteNuevoMes panelClienteNuevoMes;
	public String seleccion;
	private boolean boolTabla;

	public VentanaPrincipal(String tipoUsuario)
	{
		super("COLMOVIL");

		

		panelEstadisticas = new PanelEstadRobosPerdidasMes();
		panelCCAbonado =  new PanelCCAbonado();
		panelCCOperador = new PanelCCOperador();
		panelClienteNuevoMes = new PanelClienteNuevoMes();
		panelEstadisticas.btConsultaTipo.addActionListener(this);
		panelEstadisticas.btConsultaMes.addActionListener(this);
		panelEstadisticas.btnCancelar_1.addActionListener(this);
		panelCCAbonado.btnAceptar.addActionListener(this);
		panelCCAbonado.btnCancelar.addActionListener(this);
		panelCCOperador.btnAceptar.addActionListener(this);
		panelCCOperador.btnCancelar.addActionListener(this);
		panelClienteNuevoMes.btConsultaTipo.addActionListener(this);
		panelClienteNuevoMes.btConsultaMes.addActionListener(this);
		panelClienteNuevoMes.btnCancelar_1.addActionListener(this);
		seleccion = "";
		boolTabla = false;

		imLogo  = new ImageIcon("Imagenes/smalllogo.png");		
		JLabel lbLogo = new JLabel(imLogo);		

		setLayout(new BorderLayout());
		add(lbLogo, BorderLayout.NORTH);	

		//instanciar botones
		btInformacion = new JButton("Informacion");
		btBorrar = new JButton("Borrar");
		btCerrar = new JButton("Cerrar");	
		btBuscar = new JButton("Buscar");	
		btReporte = new JButton("Generar Reporte");

		//instanciar campos de busqueda
		campoBusqueda = new JTextField();
		campoSegBusqueda = new JTextField();

		//Creacion de la Barra de Menu		
		barraPrincipal = new JMenuBar();
		menuRegistros = new JMenu ("Registros");
		menuEdiciones = new JMenu ("Ver");
		menuConsultas = new JMenu ("Consultas");
		menuOpciones = new JMenu ("Opciones");		

		itemCliente = new JMenuItem ("Cliente");
		itemEmpleado = new JMenuItem ("Empleado");
		itemEquipo = new JMenuItem ("Equipo");
		itemOficina = new JMenuItem ("Oficina");
		itemPlanes = new JMenuItem ("Plan Postpago");
		itemContrato = new JMenuItem ("Contrato Postpago");
		itemContratoPrepago = new JMenuItem ("Contrato Prepago");
		itemSPPostpago = new JMenuItem ("Servicio Plan Postpago");
		itemReposicion = new JMenuItem ("Reposicion");
		itemOFijo = new JMenuItem ("Operador Fijo");
		itemOMInter = new JMenuItem ("Operador Movil Internacional");
		itemMLocal = new JMenuItem ("Operador Movil Local");	
		itemSalir = new JMenuItem ("Salir");
		itemServicio = new JMenuItem("Servicio");

		itemEdicionCliente = new JMenuItem ("Clientes");
		itemEdicionEmpleado = new JMenuItem ("Empleados");
		itemEdicionOficina = new JMenuItem ("Oficinas");
		itemEdicionEquipo = new JMenuItem ("Equipos");
		itemEdicionPlanes = new JMenuItem ("Planes Postpago");
		itemEdicionSPPostpago = new JMenuItem ("Servicios Postpago");
		itemEdicionOFijo = new JMenuItem ("Operadores Fijos");
		itemEdicionOMInter = new JMenuItem ("Operadores Moviles Internacionales");
		itemEdicionMLocal = new JMenuItem ("Operadores Moviles Locales");

		itemCClientesNuevos = new JMenuItem ("Consultar Clientes Nuevos");
		itemFranjasUsoRed = new JMenuItem ("Franjas de Mayor Uso de la Red");
		itemPlanesEscogidos = new JMenuItem ("Planes mas escogidos");
		itemUsuariosPrepago = new JMenuItem ("Usuarios de Planes Prepago Registrados");
		itemUsuariosPostpago = new JMenuItem ("Usuarios de Planes Postpago Registrados");
		itemDatosYConsumo = new JMenuItem ("Usuarios con plan de datos y su consumo");
		itemOpRoaming = new JMenuItem ("Operadores extranjeros que ofrecen tarifas de roamming");
		itemOpLocalMasUsado = new JMenuItem ("Operadores nacionales m�s frecuentemente utilizados");
		itemCorporativos = new JMenuItem ("Planes preferidos por usuarios de convenios corporativos");
		itemConsumoPorGenero = new JMenuItem ("Consumo de servicios de mensajes, internet y correo electr�nico por regi�n, por g�nero");
		itemRobos = new JMenuItem ("Estad�sticas de robos y p�rdidas por mes y ciudad");
		itemConsumoPorAbonado = new JMenuItem ("Consumo por tipo de abonado de planes postpago");
		itemEquipos = new JMenuItem ("Equipos m�s demandados");

		btCerrar.addActionListener(this);
		btInformacion.addActionListener(this);
		btBorrar.addActionListener(this);
		btBuscar.addActionListener(this);

		itemCliente.addActionListener(this);
		itemOficina.addActionListener(this);
		itemEquipo.addActionListener(this);
		itemEmpleado.addActionListener(this);
		itemPlanes.addActionListener(this);
		itemContrato.addActionListener(this);
		itemContratoPrepago.addActionListener(this);
		itemSPPostpago.addActionListener(this);
		itemReposicion.addActionListener(this);
		itemOFijo.addActionListener(this);
		itemOMInter.addActionListener(this);
		itemSalir.addActionListener(this);
		itemMLocal.addActionListener(this);

		itemEdicionCliente.addActionListener(this);		
		itemEdicionEmpleado.addActionListener(this);
		itemEdicionOficina.addActionListener(this);
		itemEdicionEquipo.addActionListener(this);
		itemEdicionPlanes.addActionListener(this);
		itemEdicionSPPostpago.addActionListener(this);
		itemEdicionOFijo.addActionListener(this);
		itemEdicionOMInter.addActionListener(this);
		itemEdicionMLocal.addActionListener(this);

		itemCClientesNuevos.addActionListener(this);
		itemFranjasUsoRed.addActionListener(this);
		itemPlanesEscogidos.addActionListener(this);
		itemUsuariosPrepago.addActionListener(this);
		itemUsuariosPostpago.addActionListener(this);
		itemDatosYConsumo.addActionListener(this);
		itemOpRoaming.addActionListener(this);
		itemOpLocalMasUsado.addActionListener(this);
		itemCorporativos.addActionListener(this);
		itemConsumoPorGenero.addActionListener(this);
		itemRobos.addActionListener(this);
		itemConsumoPorAbonado.addActionListener(this);
		itemEquipos.addActionListener(this);
		itemServicio.addActionListener(this);

		menuRegistros.add(itemCliente);
		menuRegistros.add(itemEmpleado);			
		menuRegistros.add(itemEquipo);
		menuRegistros.add(itemOficina);
		menuRegistros.add(itemPlanes);
		menuRegistros.add(itemContrato);
		menuRegistros.add(itemContratoPrepago);
		menuRegistros.add(itemServicio);
		menuRegistros.add(itemSPPostpago);
		menuRegistros.add(itemReposicion);
		menuRegistros.add(itemOFijo);
		menuRegistros.add(itemOMInter);			
		menuRegistros.add(itemMLocal);


		menuEdiciones.add(itemEdicionCliente);
		menuEdiciones.add(itemEdicionEmpleado);
		menuEdiciones.add(itemEdicionOficina);
		menuEdiciones.add(itemEdicionEquipo);
		menuEdiciones.add(itemEdicionPlanes);
		menuEdiciones.add(itemEdicionSPPostpago);
		menuEdiciones.add(itemEdicionOFijo);
		menuEdiciones.add(itemEdicionOMInter);
		menuEdiciones.add(itemEdicionMLocal);

		menuConsultas.add(itemCClientesNuevos);
		menuConsultas.add(itemFranjasUsoRed);
		menuConsultas.add(itemPlanesEscogidos);
		menuConsultas.add(itemUsuariosPrepago);
		menuConsultas.add(itemUsuariosPostpago);
		menuConsultas.add(itemDatosYConsumo);
		menuConsultas.add(itemOpRoaming);
		menuConsultas.add(itemOpLocalMasUsado);
		menuConsultas.add(itemCorporativos);
		menuConsultas.add(itemConsumoPorGenero);
		menuConsultas.add(itemRobos);
		menuConsultas.add(itemConsumoPorAbonado);
		menuConsultas.add(itemEquipos);

		menuOpciones.add(itemSalir);	

		barraPrincipal.add(menuRegistros);
		barraPrincipal.add(menuEdiciones);
		barraPrincipal.add(menuConsultas);
		barraPrincipal.add(menuOpciones);


		setJMenuBar(barraPrincipal);
		setVisible(true);
		setSize(800,600);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

	}

	public void actionPerformed(ActionEvent evento) 
	{

		if (evento.getSource() == btInformacion)
		{
			int nFila =tablaConsultas.getSelectedRow();
			//hacemos una condicion de que si la varialbe i es igual a - es que no se ha seleccionado ninguna 
			//fila,
			if(nFila == -1)
			{
				JOptionPane.showMessageDialog(null,"Por favor seleccione una fila");
			}
			else//de lo contrario si se selecciono la fila
			{
				String identificacion = (String) tablaConsultas.getValueAt(nFila,0);

				if(seleccion.equals("cliente"))
				{
					EditarCliente EC = new EditarCliente(identificacion,this);					
				}				
				if(seleccion.equals("empleado"))
				{
					EditarEmpleado ED = new EditarEmpleado(identificacion,this);
				}				
				if(seleccion.equals("oficina"))
				{
					EditarOficina EO = new EditarOficina(identificacion,this);
				}				
				if(seleccion.equals("equipo"))
				{	
					EditarEquipoCelular EEC = new EditarEquipoCelular(identificacion,this);
				}				
				if(seleccion.equals("planpostpago"))
				{
					EditarPlanPostpago EPP = new EditarPlanPostpago(identificacion,this);
				}				
				if(seleccion.equals("sppostpago"))
				{		
				}				
				if(seleccion.equals("ofijo"))
				{
					String nom_pais = (String) tablaConsultas.getValueAt(nFila,1);
					EditarOperadorFijo EOF = new EditarOperadorFijo(identificacion,nom_pais,this);
				}				
				if(seleccion.equals("ominter"))
				{	
					String nom_pais = (String) tablaConsultas.getValueAt(nFila,1);
					EditarOpMovilInternacional EOMI = new EditarOpMovilInternacional(identificacion, nom_pais,this);
				}				
				if(seleccion.equals("mlocal"))
				{
					String nom_pais = (String) tablaConsultas.getValueAt(nFila,1);
					EditarMovilLocal EML = new EditarMovilLocal(identificacion, nom_pais,this);
				}				
			}
		}

		if (evento.getSource() == btBorrar)
		{
			int nFila =tablaConsultas.getSelectedRow();
			//hacemos una condicion de que si la varialbe i es igual a - es que no se ha seleccionado ninguna 
			//fila,
			if(nFila == -1)
			{
				JOptionPane.showMessageDialog(null,"Por favor seleccione una fila");
			}
			else//de lo contrario si se selecciono la fila
			{
				String identificacion = (String) tablaConsultas.getValueAt(nFila,0);

				if(seleccion.equals("cliente"))
				{
					removerContenidoVentana();
					mostrarTabla("cliente");
				}				
				if(seleccion.equals("empleado"))
				{
					removerContenidoVentana();
					mostrarTabla("empleado");
				}				
				if(seleccion.equals("oficina"))
				{
					removerContenidoVentana();
					mostrarTabla("oficina");
				}				
				if(seleccion.equals("equipo"))
				{
					removerContenidoVentana();
					mostrarTabla("equipo");

				}				
				if(seleccion.equals("planpostpago"))
				{
					removerContenidoVentana();
					mostrarTabla("planpostpago");
				}				
				if(seleccion.equals("sppostpago"))
				{		
					removerContenidoVentana();
					mostrarTabla("sppostpago");					
				}				
				if(seleccion.equals("ofijo"))
				{
					String pais = (String) tablaConsultas.getValueAt(nFila,1);
					removerContenidoVentana();
					mostrarTabla("ofijo");
				}				
				if(seleccion.equals("ominter"))
				{
					String pais = (String) tablaConsultas.getValueAt(nFila,1);
					removerContenidoVentana();
					mostrarTabla("ominter");
				}				
				if(seleccion.equals("mlocal"))
				{
					String pais = (String) tablaConsultas.getValueAt(nFila,1);
					removerContenidoVentana();
					mostrarTabla("mlocal");
				}				
			}
		}

		if (evento.getSource() == btCerrar)
		{
			boolTabla = false;
			this.remove(scrollConsultas);
			this.remove(panelBotones);
			repaint();
		}

		if (evento.getSource() == btBuscar)
		{
			String busqueda = campoBusqueda.getText();
			boolean encontrado = false;

			for (int i = 0; i < tablaConsultas.getRowCount(); i++)
			{
				if (tablaConsultas.getValueAt(i, 0).equals(busqueda)) 
				{
					if(seleccion.equals("ofijo") || seleccion.equals("ominter"))
					{
						String segBusqueda = campoSegBusqueda.getText();
						if(tablaConsultas.getValueAt(i, 1).equals(segBusqueda))
						{
							tablaConsultas.changeSelection(i, 0, false, false);
							encontrado = true;
							campoBusqueda.setText("");
							campoSegBusqueda.setText("");
							break;
						}
					}	
					else
					{
						tablaConsultas.changeSelection(i, 0, false, false);
						encontrado = true;
						campoBusqueda.setText("");
						break;
					}

				}
			}

			if(encontrado == false)
				JOptionPane.showMessageDialog(null, "La busqueda no obtuvo resultados","ERROR", JOptionPane.ERROR_MESSAGE);	
		}	

		if (evento.getSource() == panelClienteNuevoMes.btConsultaTipo)
		{
			String opcion = panelClienteNuevoMes.getOpcionTipo();

			this.remove(panelClienteNuevoMes);

			if(opcion.equals("Postpago"))
			{
				mostrarConsultas("Postpago");

			}
			if(opcion.equals("Prepago"))
			{
				mostrarConsultas("Prepago");
			}
		}	

		if (evento.getSource() == panelClienteNuevoMes.btConsultaMes)
		{
			panelClienteNuevoMes.generarConsultaMes();

			this.remove(panelClienteNuevoMes);
			mostrarConsultasConFechas(panelClienteNuevoMes.getFechaI(), panelClienteNuevoMes.getFechaF());
		}


		if (evento.getSource() == panelClienteNuevoMes.btnCancelar_1)
		{
			this.remove(panelClienteNuevoMes);
			this.repaint();

		}

		if (evento.getSource() == panelEstadisticas.btConsultaTipo)
		{
			String ciudad = panelEstadisticas.getOpcionCiudades();

			this.remove(panelEstadisticas);

			mostrarConsultasConCiudades(ciudad);

		}	

		if (evento.getSource() == panelEstadisticas.btConsultaMes)
		{
			panelEstadisticas.generarConsultaMes();

			this.remove(panelEstadisticas);

			mostrarConsultasConFechasRobo(panelEstadisticas.getFechaI(), panelEstadisticas.getFechaF());

			this.remove(panelEstadisticas);

		}


		if (evento.getSource() == panelEstadisticas.btnCancelar_1)
		{
			this.remove(panelEstadisticas);
			this.repaint();

		}

		if (evento.getSource() == panelCCOperador.btnAceptar)
		{
			String opcionOperador = panelCCOperador.getOpcion();

			this.remove(panelCCOperador);

			if(opcionOperador.equals("Operadores Locales Mas Usados por Llamadas"))
			{
				mostrarConsultas("Operadores Locales Mas Usados Llamadas");


			}
			if(opcionOperador.equals("Operadores Locales Mas Usados por Sms"))
			{
				mostrarConsultas("OperadoresLocalesMasUsadosSms");
			}
			if(opcionOperador.equals("Operadores Locales Mas Usados por Datos"))
			{
				mostrarConsultas("Operadores Locales Mas Usados Datos");
			}

		}

		if (evento.getSource() == panelCCOperador.btnCancelar)
		{
			this.remove(panelCCOperador);
			this.repaint();

		}

		if (evento.getSource() == panelCCAbonado.btnAceptar)
		{
			String opcion = panelCCAbonado.getOpcion();

			this.remove(panelCCAbonado);

			if(opcion.equals("por Llamadas"))
			{
				mostrarConsultas("por Llamadas");

			}

			if(opcion.equals("por SMS"))
			{
				mostrarConsultas("por SMS");				
			}

			if(opcion.equals("por Internet"))
			{
				mostrarConsultas("por Internet");	
			}


		}

		if (evento.getSource() == panelCCAbonado.btnCancelar)
		{
			this.remove(panelCCAbonado);
			this.repaint();

		}	
		
		//EVENTOS REGISTROS
		if (evento.getSource() == itemCliente)
		{
			RegistrarCliente RC = new RegistrarCliente(this);
		}
		if (evento.getSource() == itemOficina)
		{	
			RegistrarOficina ro = new RegistrarOficina(this);
		}
		if (evento.getSource() == itemEmpleado)
		{
			RegistrarEmpleado ce = new RegistrarEmpleado(this); 
		}	
		if (evento.getSource() == itemPlanes)
		{
			RegistroPlanPostpago pp = new RegistroPlanPostpago(this);
		}	
		if (evento.getSource() == itemEquipo)
		{
			RegistrarEquipoCelular regObj = new RegistrarEquipoCelular(this);
		}
		if (evento.getSource() == itemContrato)
		{
			RegistroContrato contratoObj = new RegistroContrato();
		}
		if (evento.getSource() == itemContratoPrepago)
		{
			removerContenidoVentana();
		}		
		if(evento.getSource() == itemServicio)
		{
			RegistroServicio s = new RegistroServicio();
		}		
		if (evento.getSource() == itemSPPostpago)
		{
			RegistroServicioPPostpago reg = new RegistroServicioPPostpago();
		}
		if (evento.getSource() == itemReposicion)
		{
			RegistrarReposicion repo = new RegistrarReposicion();
		}
		if (evento.getSource() == itemOFijo)
		{
			RegistroOperadorFijo opObj = new RegistroOperadorFijo(this);
		}
		if (evento.getSource() == itemOMInter)
		{
			RegistrarOpMovilInternacional movilInt = new RegistrarOpMovilInternacional(this);
		}
		if (evento.getSource() == itemMLocal)
		{
			RegistroMovilLocal movilLocal = new RegistroMovilLocal(this);
		}

		//EVENTOS INVENTARIO
		if (evento.getSource() == itemEdicionCliente)
		{
			seleccion = "cliente";
			mostrarTabla("cliente");
		}
		if (evento.getSource() == itemEdicionEmpleado)
		{
			seleccion = "empleado";
			mostrarTabla("empleado");
		}
		if (evento.getSource() == itemEdicionOficina)
		{
			seleccion = "oficina";
			mostrarTabla("oficina");			
		}
		if (evento.getSource() == itemEdicionEquipo)
		{
			seleccion = "equipo";
			mostrarTabla("equipo");
		}
		if (evento.getSource() == itemEdicionPlanes)
		{
			seleccion = "planpostpago";
			mostrarTabla("planpostpago");
		}
		if (evento.getSource() == itemEdicionSPPostpago)
		{
			seleccion = "sppostpago";
			mostrarTabla("sppostpago");
		}
		if (evento.getSource() == itemEdicionOFijo)
		{
			seleccion = "ofijo";
			mostrarTabla("ofijo");
		}
		if (evento.getSource() == itemEdicionOMInter)
		{
			seleccion = "ominter";
			mostrarTabla("ominter");
		}
		if (evento.getSource() == itemEdicionMLocal)
		{
			seleccion = "mlocal";
			mostrarTabla("mlocal");
		}

		//EVENTOS CONSULTAS
		if (evento.getSource() == itemCClientesNuevos)
		{
			try 
			{
				this.remove(scrollConsultas);
				this.remove(panelBotones);
			}
			catch(Exception e){}			

			this.add(panelClienteNuevoMes);
			pack();
			setSize(800,600);
		}
		if (evento.getSource() == itemFranjasUsoRed)
		{

		}
		if (evento.getSource() == itemUsuariosPrepago)
		{
			mostrarConsultas("usuarios prepago");
		}
		if (evento.getSource() == itemPlanesEscogidos)
		{
			mostrarConsultas("planes mas escojidos");
		}
		if (evento.getSource() == itemUsuariosPostpago)
		{
			mostrarConsultas("usuarios postpago");
		}
		if (evento.getSource() == itemDatosYConsumo)
		{
			mostrarConsultas("datos y consumo");
		}
		if (evento.getSource() == itemOpRoaming)
		{
			mostrarConsultas("Operadores Roaming");
		}
		if (evento.getSource() == itemOpLocalMasUsado)
		{
			try 
			{
				this.remove(scrollConsultas);
				this.remove(panelBotones);
			}
			catch(Exception e){}			

			this.add(panelCCOperador);
			pack();
			setSize(800,600);
		}
		if (evento.getSource() == itemCorporativos)
		{
			mostrarConsultas("Planes Preferidos Por Usuarios Corporativos");
		}
		if (evento.getSource() == itemConsumoPorGenero)
		{
			mostrarConsultas("consumo genero");			
		}
		if (evento.getSource() == itemRobos)
		{
			try 
			{
				this.remove(scrollConsultas);
				this.remove(panelBotones);
			}
			catch(Exception e){}			

			this.add(panelEstadisticas);
			pack();
			setSize(800,600);
		}
		if (evento.getSource() == itemConsumoPorAbonado)
		{

			try 
			{
				this.remove(scrollConsultas);
				this.remove(panelBotones);
			}
			catch(Exception e){}			

			this.add(panelCCAbonado);
			pack();
			setSize(800,600);

		}
		if (evento.getSource() == itemEquipos)
		{
			mostrarConsultas("Equipos Mas Demandados");
		}
		if (evento.getSource() == itemSalir)
		{
			System.exit(0);
		}
	}//FIN DEL MANEJADOR DE EVENTOS	

	////////////////////////////////////////////////////////////////////////////////////
	
	public void mostrarTabla(String opcion)
	{
		removerContenidoVentana();
		
		DefaultTableModel modeloConsultas = new DefaultTableModel();

		JLabel lbBusqueda = new JLabel();
		lbBusqueda.setHorizontalAlignment(4);

		JLabel lbSegBusqueda = new JLabel();
		lbSegBusqueda.setHorizontalAlignment(4);


		if(opcion == "cliente")
		{
			lbBusqueda.setText("Identificacion");
		}	

		if(opcion == "empleado")
		{		
			lbBusqueda.setText("Identificacion");
		}

		if(opcion == "oficina")
		{
			lbBusqueda.setText("Codigo Oficina");
		}

		if(opcion == "equipo")
		{	
			lbBusqueda.setText("IMEI");
		}

		if(opcion == "planpostpago")
		{	
			lbBusqueda.setText("Numero Plan");
		}

		if(opcion == "sppostpago")
		{		
			//modeloConsultas = daoservplanpostpago.g
		}

		if(opcion == "ofijo")
		{
			lbBusqueda.setText("Operador");
			lbSegBusqueda.setText("Pais");
		}

		if(opcion == "ominter")
		{
			lbBusqueda.setText("Operador");
			lbSegBusqueda.setText("Pais");
		}

		if(opcion == "mlocal")
		{		
			lbBusqueda.setText("Operador");
		}

		if(opcion.equals("ofijo") || opcion.equals("ominter"))
		{			
			JPanel panelA = new JPanel(new GridLayout(1,3,5,5));
			panelA.add(btInformacion);
			panelA.add(btBorrar);
			panelA.add(btCerrar);
			JPanel panelB = new JPanel(new GridLayout(1,5,5,5));
			panelB.add(lbBusqueda);
			panelB.add(campoBusqueda);
			panelB.add(lbSegBusqueda);
			panelB.add(campoSegBusqueda);
			panelB.add(btBuscar);

			panelBotones = new JPanel(new GridLayout(2,1,5,5));
			panelBotones.add(panelA);
			panelBotones.add(panelB);			
		}
		else
		{
			panelBotones = new JPanel(new GridLayout(2,3,5,5));
			panelBotones.add(btInformacion);
			panelBotones.add(btBorrar);
			panelBotones.add(btCerrar);
			panelBotones.add(lbBusqueda);
			panelBotones.add(campoBusqueda);
			panelBotones.add(btBuscar);
		}


		scrollConsultas = new JScrollPane();
		tablaConsultas = new JTable();
		tablaConsultas.setModel(modeloConsultas);
		scrollConsultas.setViewportView(tablaConsultas);
		//scrollConsultas.setPreferredSize(new Dimension(780, 420));
		
		boolTabla = true;
		this.add(scrollConsultas, BorderLayout.CENTER);
		this.add(panelBotones, BorderLayout.SOUTH);
		this.pack();
		this.setSize(800,600);


	}

	public void mostrarConsultasConCiudades(String ciudad)
	{
		try 
		{
			this.remove(scrollConsultas);
			this.remove(panelBotones);
		}
		catch(Exception e){}

		DefaultTableModel modeloConsultas = new DefaultTableModel();


		panelBotones = new JPanel(new GridLayout(1,2,5,5));
		panelBotones.add(btReporte);
		panelBotones.add(btCerrar);

		scrollConsultas = new JScrollPane();
		tablaConsultas = new JTable();
		tablaConsultas.setModel(modeloConsultas);
		scrollConsultas.setViewportView(tablaConsultas);
		
		boolTabla = true;
		this.add(scrollConsultas, BorderLayout.CENTER);
		this.add(panelBotones, BorderLayout.SOUTH);
		this.pack();
		this.setSize(800,600);

	}

	public void mostrarConsultasConFechas(String fechaI, String fechaF)
	{
		try 
		{
			this.remove(scrollConsultas);
			this.remove(panelBotones);
		}
		catch(Exception e){}
		DefaultTableModel modeloConsultas = new DefaultTableModel();


		panelBotones = new JPanel(new GridLayout(1,2,5,5));
		panelBotones.add(btReporte);
		panelBotones.add(btCerrar);

		scrollConsultas = new JScrollPane();
		tablaConsultas = new JTable();
		tablaConsultas.setModel(modeloConsultas);
		scrollConsultas.setViewportView(tablaConsultas);
		
		boolTabla = true;
		this.add(scrollConsultas, BorderLayout.CENTER);
		this.add(panelBotones, BorderLayout.SOUTH);
		this.pack();
		this.setSize(800,600);

	}

	public void mostrarConsultasConFechasRobo(String fechaI, String fechaF)
	{
		try 
		{
			this.remove(scrollConsultas);
			this.remove(panelBotones);
		}
		catch(Exception e){}

		DefaultTableModel modeloConsultas = new DefaultTableModel();

		panelBotones = new JPanel(new GridLayout(1,2,5,5));
		panelBotones.add(btReporte);
		panelBotones.add(btCerrar);

		scrollConsultas = new JScrollPane();
		tablaConsultas = new JTable();
		tablaConsultas.setModel(modeloConsultas);
		scrollConsultas.setViewportView(tablaConsultas);
		
		boolTabla = true;
		this.add(scrollConsultas, BorderLayout.CENTER);
		this.add(panelBotones, BorderLayout.SOUTH);
		this.pack();
		this.setSize(800,600);

	}



	public void mostrarConsultas(String opcion)
	{
		try 
		{
			this.remove(scrollConsultas);
			this.remove(panelBotones);
		}
		catch(Exception e){}

		DefaultTableModel modeloConsultas = new DefaultTableModel();

		if(opcion == "Postpago")
		{
		}
		if(opcion == "Prepago")
		{
		}
		if(opcion == "consumo genero")
		{
		}

		if(opcion == "por Llamadas")
		{
		}

		if(opcion == "por SMS")
		{
		}

		if(opcion == "por Internet")
		{
		}
		if(opcion == "Operadores Locales Mas Usados Llamadas")
		{
		}
		if(opcion == "Operadores Locales Mas Usados Sms")
		{
		}
		if(opcion == "Operadores Locales Mas Usados Datos")
		{
			System.out.println("aca todo bn");
		}

		if(opcion == "planes mas escojidos")
		{
		}
		if(opcion == "usuarios prepago")
		{
		}
		if(opcion == "usuarios postpago")
		{
		}
		if(opcion == "datos y consumo")
		{
			//modeloConsultas = daoconsultas.Usu
		}
		if(opcion == "Operadores Roaming")
		{
		}
		if(opcion == "Planes Preferidos Por Usuarios Corporativos")
		{
		}
		if(opcion == "Equipos Mas Demandados")
		{
		}

		panelBotones = new JPanel(new GridLayout(1,2,5,5));
		panelBotones.add(btReporte);
		panelBotones.add(btCerrar);

		scrollConsultas = new JScrollPane();
		tablaConsultas = new JTable();
		tablaConsultas.setModel(modeloConsultas);
		scrollConsultas.setViewportView(tablaConsultas);
		//scrollConsultas.setPreferredSize(new Dimension(780, 450));

		boolTabla = true;
		this.add(scrollConsultas, BorderLayout.CENTER);
		this.add(panelBotones, BorderLayout.SOUTH);
		this.pack();
		this.setSize(800,600);


	}

	public void removerContenidoVentana()
	{
		try 
		{
			this.remove(scrollConsultas);
			this.remove(panelBotones);
			repaint();
		}
		catch(Exception e){}
		boolTabla = false;
	}

	public boolean isMostrandoTabla()
	{
		return boolTabla;
	}

	public static void main(String[] args) 
	{
		//VentanaPrincipal VP = new VentanaPrincipal();
	}

}
