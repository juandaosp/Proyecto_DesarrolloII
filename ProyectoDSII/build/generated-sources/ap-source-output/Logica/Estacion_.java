package Logica;

import Logica.Empleado;
import Logica.Ruta;
import Logica.Solicitud;
import Logica.Tarjeta;
import Logica.TarjetaIngresaEstacion;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2012-12-02T01:29:53")
@StaticMetamodel(Estacion.class)
public class Estacion_ { 

    public static volatile SingularAttribute<Estacion, String> estado;
    public static volatile CollectionAttribute<Estacion, Tarjeta> tarjetaCollection;
    public static volatile SingularAttribute<Estacion, Empleado> idEmpleado;
    public static volatile CollectionAttribute<Estacion, TarjetaIngresaEstacion> tarjetaIngresaEstacionCollection;
    public static volatile SingularAttribute<Estacion, String> nombreEstacion;
    public static volatile CollectionAttribute<Estacion, TarjetaIngresaEstacion> tarjetaIngresaEstacionCollection1;
    public static volatile CollectionAttribute<Estacion, Solicitud> solicitudCollection;
    public static volatile SingularAttribute<Estacion, String> ubicacion;
    public static volatile CollectionAttribute<Estacion, Ruta> rutaCollection;

}