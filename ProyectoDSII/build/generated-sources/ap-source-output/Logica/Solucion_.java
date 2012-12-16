package Logica;

import Logica.Solicitud;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2012-12-02T01:29:53")
@StaticMetamodel(Solucion.class)
public class Solucion_ { 

    public static volatile SingularAttribute<Solucion, Date> fecha;
    public static volatile SingularAttribute<Solucion, String> ejecutada;
    public static volatile SingularAttribute<Solucion, String> descripcion;
    public static volatile SingularAttribute<Solucion, String> tiquetSolicitud;
    public static volatile SingularAttribute<Solucion, Solicitud> solicitud;

}