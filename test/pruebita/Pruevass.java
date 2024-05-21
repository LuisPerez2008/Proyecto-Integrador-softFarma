package pruebita;

import com.pe.farmacia.dao.LoginDao;
import com.pe.farmacia.dao.VentaDao;
import com.pe.farmacia.daoimpl.LoginDAOImpl;
import com.pe.farmacia.daoimpl.VentaDaoImpl;
import com.pe.farmacia.modelo.Usuario;
import com.pe.farmacia.modelo.Venta;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author 51901
 */
public class Pruevass {

    public static void main(String[] args) {

        VentaDao v = new VentaDaoImpl();
        Venta venta = new Venta();
        LoginDao usr =new  LoginDAOImpl();
        Usuario user = new Usuario();
        
       
        
        venta = v.searchSaleById(1);
        System.out.println(venta.getEmpleado());
        int vendedor = venta.getEmpleado();
            user = usr.searchUserById(vendedor);
            System.out.println(user.getId());
            System.out.println( user.getNombre());
    }

}
