package Controladores;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

public class fabrica_de_objetos 
{
    @PersistenceUnit
     //crea la fabrica de objetos, utiliza el patron de diseño factoria
    private EntityManagerFactory factory;
    
    //CONSTRUCTOR DE LA CLASE
    public fabrica_de_objetos()
    {       
        factory = Persistence.createEntityManagerFactory("taller_jpa_unidad_de_persistencia");
    }  
    
    //METODO QUE RETORNA LA FABRICA DE OBJETOS
    public EntityManagerFactory crear()
    {
        return factory;
    }
}
