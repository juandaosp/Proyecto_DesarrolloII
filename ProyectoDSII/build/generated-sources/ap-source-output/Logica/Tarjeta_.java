package Logica;

import Logica.Estacion;
import Logica.Recarga;
import Logica.TarjetaIngresaEstacion;
import Logica.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2012-12-02T01:29:53")
@StaticMetamodel(Tarjeta.class)
public class Tarjeta_ { 

    public static volatile SingularAttribute<Tarjeta, Recarga> recarga;
    public static volatile SingularAttribute<Tarjeta, String> estado;
    public static volatile CollectionAttribute<Tarjeta, Usuario> usuarioCollection;
    public static volatile SingularAttribute<Tarjeta, Date> fecha;
    public static volatile SingularAttribute<Tarjeta, Integer> pin;
    public static volatile SingularAttribute<Tarjeta, Integer> pasajes;
    public static volatile SingularAttribute<Tarjeta, String> tipo;
    public static volatile CollectionAttribute<Tarjeta, TarjetaIngresaEstacion> tarjetaIngresaEstacionCollection;
    public static volatile SingularAttribute<Tarjeta, Integer> costo;
    public static volatile SingularAttribute<Tarjeta, Estacion> nombreEstacion;

}