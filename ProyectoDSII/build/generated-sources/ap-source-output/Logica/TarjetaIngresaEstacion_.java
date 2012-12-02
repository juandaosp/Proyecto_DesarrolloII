package Logica;

import Logica.Estacion;
import Logica.Tarjeta;
import Logica.TarjetaIngresaEstacionPK;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2012-12-02T01:29:53")
@StaticMetamodel(TarjetaIngresaEstacion.class)
public class TarjetaIngresaEstacion_ { 

    public static volatile SingularAttribute<TarjetaIngresaEstacion, Date> fecha;
    public static volatile SingularAttribute<TarjetaIngresaEstacion, Estacion> estacion;
    public static volatile SingularAttribute<TarjetaIngresaEstacion, TarjetaIngresaEstacionPK> tarjetaIngresaEstacionPK;
    public static volatile SingularAttribute<TarjetaIngresaEstacion, Tarjeta> tarjeta;
    public static volatile SingularAttribute<TarjetaIngresaEstacion, Estacion> estacion1;

}