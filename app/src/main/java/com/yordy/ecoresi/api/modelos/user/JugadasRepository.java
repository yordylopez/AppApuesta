package com.yordy.ecoresi.api.modelos.user;

import com.yordy.ecoresi.loopback.ModelRepository;

/**
 * Created by Programmer on 6/3/2017.
 */
public class JugadasRepository extends ModelRepository<Jugadas> {
    public JugadasRepository() {
        super("Jugadas", null, Jugadas.class);
    }
}
