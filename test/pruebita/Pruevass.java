package pruebita;

import com.pe.farmacia.dao.VentaDao;
import com.pe.farmacia.daoimpl.VentaDaoImpl;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 51901
 */
public class Pruevass {

    public static void main(String[] args) {

        VentaDao v = new VentaDaoImpl();

        List<Map<String, Object>> lista = v.readAll3("Jean Cano");

        for (Map<String, Object> map : lista) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
            System.out.println(); // Imprimir una línea en blanco entre cada elemento
        }
        // Procesar los resultados

    }

}
