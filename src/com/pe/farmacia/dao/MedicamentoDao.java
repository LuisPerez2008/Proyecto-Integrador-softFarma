package com.pe.farmacia.dao;

import com.pe.farmacia.modelo.Medicamento;
import java.util.List;
import java.util.Map;

public interface MedicamentoDao {

    int register(Medicamento med);
    
    List<Map<String, Object>> readAll2();

    void delete(int id);

    void update(Medicamento med);

    Medicamento searchMedicineByCode(String cod);

    Map<String, Object> searchMedicineById(int id);
}


//List<Medicamento> readAll();