package Logica;

import Logica.Estacion;
import Logica.Programacion;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2012-12-02T01:29:53")
@StaticMetamodel(Ruta.class)
public class Ruta_ { 

    public static volatile CollectionAttribute<Ruta, Estacion> estacionCollection;
    public static volatile SingularAttribute<Ruta, String> estado;
    public static volatile SingularAttribute<Ruta, String> nombreRuta;
    public static volatile SingularAttribute<Ruta, String> descripcion;
    public static volatile CollectionAttribute<Ruta, Programacion> programacionCollection;

}