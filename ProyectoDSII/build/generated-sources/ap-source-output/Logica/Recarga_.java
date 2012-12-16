package Logica;

import Logica.Tarjeta;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2012-12-02T01:29:53")
@StaticMetamodel(Recarga.class)
public class Recarga_ { 

    public static volatile SingularAttribute<Recarga, Integer> pinTarjeta;
    public static volatile SingularAttribute<Recarga, Integer> valorRecarga;
    public static volatile SingularAttribute<Recarga, Date> fechaRecarga;
    public static volatile SingularAttribute<Recarga, Tarjeta> tarjeta;

}