
package pruebita;

import com.pe.farmacia.dao.VentaDao;
import com.pe.farmacia.daoimpl.VentaDaoImpl;
import java.util.Map;

/**
 *
 * @author 51901
 */
public class Pruevass {

    public static void main(String[] args) {
        
        VentaDao v = new VentaDaoImpl();
        Map<String, Integer> listaMpita = v.reportForCantSale();
            
        for (String vendedor : listaMpita.keySet()) {
            int cantidadVentas = listaMpita.get(vendedor);
            System.out.println("Vendedor: " + vendedor + ", Cantidad de Ventas: " + cantidadVentas);
        }
       
        
        // Procesar los resultados
                
        
    }
    
}
