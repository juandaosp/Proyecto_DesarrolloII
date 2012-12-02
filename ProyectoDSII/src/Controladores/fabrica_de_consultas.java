package Controladores;

import javax.persistence.EntityManager;


public class fabrica_de_consultas 
{
    private EntityManager manager;
    private fabrica_de_objetos mi_fabrica;
    
    public fabrica_de_consultas() 
    {   
        mi_fabrica = new fabrica_de_objetos();
        manager = mi_fabrica.crear().createEntityManager();
    }
    
    public EntityManager consultar()
    {
        return manager;
    }
        
}
