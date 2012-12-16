package Logica;

import Logica.Bus;
import Logica.Empleado;
import Logica.ProgramacionPK;
import Logica.Ruta;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2012-12-02T01:29:53")
@StaticMetamodel(Programacion.class)
public class Programacion_ { 

    public static volatile SingularAttribute<Programacion, ProgramacionPK> programacionPK;
    public static volatile SingularAttribute<Programacion, Bus> bus;
    public static volatile SingularAttribute<Programacion, Date> fecha;
    public static volatile SingularAttribute<Programacion, Empleado> empleado;
    public static volatile SingularAttribute<Programacion, String> descripcion;
    public static volatile SingularAttribute<Programacion, Ruta> ruta;

}