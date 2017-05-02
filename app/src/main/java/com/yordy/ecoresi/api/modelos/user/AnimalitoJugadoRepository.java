package com.yordy.ecoresi.api.modelos.user;

import com.yordy.ecoresi.loopback.ModelRepository;

/**
 * Created by yordy on 05/02/2017.
 */
public class AnimalitoJugadoRepository extends ModelRepository<AnimalitoJugado> {
    public AnimalitoJugadoRepository() {
        super("JugadaAnimalito", "JugadaAnimalitos", AnimalitoJugado.class);
    }
}
