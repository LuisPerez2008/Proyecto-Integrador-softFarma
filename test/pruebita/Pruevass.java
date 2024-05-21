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
        
       
        
        Map<Integer, Integer> listamap = v.reportCantVentasXmes(2023, 12);
        
        for (Map.Entry<Integer, Integer> entry : listamap.entrySet()) {
        System.out.println(entry.getKey() + ": " + entry.getValue());
    }
    }

}
