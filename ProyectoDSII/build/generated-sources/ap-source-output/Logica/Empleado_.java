package Logica;

import Logica.Estacion;
import Logica.Programacion;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2012-12-02T01:29:53")
@StaticMetamodel(Empleado.class)
public class Empleado_ { 

    public static volatile SingularAttribute<Empleado, String> licencia;
    public static volatile SingularAttribute<Empleado, String> estadoCivil;
    public static volatile SingularAttribute<Empleado, Date> fechaNac;
    public static volatile SingularAttribute<Empleado, String> contrasena;
    public static volatile SingularAttribute<Empleado, String> cargo;
    public static volatile SingularAttribute<Empleado, String> id;
    public static volatile SingularAttribute<Empleado, String> nombre;
    public static volatile SingularAttribute<Empleado, String> eps;
    public static volatile CollectionAttribute<Empleado, Estacion> estacionCollection;
    public static volatile SingularAttribute<Empleado, Character> genero;
    public static volatile SingularAttribute<Empleado, String> estado;
    public static volatile SingularAttribute<Empleado, String> salario;
    public static volatile CollectionAttribute<Empleado, Programacion> programacionCollection;
    public static volatile SingularAttribute<Empleado, String> login;

}