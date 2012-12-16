package Logica;

import Logica.Programacion;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2012-12-02T01:29:53")
@StaticMetamodel(Bus.class)
public class Bus_ { 

    public static volatile SingularAttribute<Bus, String> tipoBus;
    public static volatile SingularAttribute<Bus, String> estado;
    public static volatile SingularAttribute<Bus, String> aO;
    public static volatile SingularAttribute<Bus, String> chasis;
    public static volatile CollectionAttribute<Bus, Programacion> programacionCollection;
    public static volatile SingularAttribute<Bus, String> cilindrinaje;
    public static volatile SingularAttribute<Bus, String> fabricante;
    public static volatile SingularAttribute<Bus, String> capacidad;
    public static volatile SingularAttribute<Bus, String> matricula;

}