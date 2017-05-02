package com.yordy.ecoresi.api.modelos.user;

import com.yordy.ecoresi.loopback.ModelRepository;

/**
 * Created by yordy on 06/02/2017.
 */
public class JugadorBancaRepository extends ModelRepository<JugadorBanca> {
    public JugadorBancaRepository() {
        super("JugadorBanca", null, JugadorBanca.class);
    }
}
