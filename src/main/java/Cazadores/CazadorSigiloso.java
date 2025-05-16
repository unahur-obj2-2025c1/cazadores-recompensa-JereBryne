package Cazadores;

import Profugos.IProfugo;

public class CazadorSigiloso extends CazadorBase{

	public CazadorSigiloso(Integer experiencia) {
		super(experiencia);
	}
	
	@Override
	public Boolean puedeCapturar(IProfugo profugo) {
		return super.puedeCapturar(profugo) && profugo.getHabilidad() < 50;
	}

	@Override
	public void intimidar(IProfugo profugo) {
		super.intimidar(profugo);
		profugo.reducirHabilidad();
	}
}
