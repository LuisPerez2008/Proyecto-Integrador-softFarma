package com.pe.farmacia.dao;

import com.pe.farmacia.modelo.Farmacia;

/**
 *
 * @author Grupo 6
 */
public interface FarmaciaDao {

    Farmacia searchData();

    void update(Farmacia farmacia);
}
