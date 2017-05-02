package com.yordy.ecoresi.api.modelos.user;

import com.yordy.ecoresi.loopback.ModelRepository;

/**
 * Created by yordy on 05/02/2017.
 */
public class BancaRepository extends ModelRepository<Banca> {
    public BancaRepository() {
        super("Banca", null, Banca.class);
    }
}
