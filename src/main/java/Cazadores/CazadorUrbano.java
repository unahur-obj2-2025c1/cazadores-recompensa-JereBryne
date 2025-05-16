package Cazadores;

import Profugos.IProfugo;

public class CazadorUrbano extends CazadorBase{

	public CazadorUrbano(Integer experiencia) {
		super(experiencia);
	}
	
	@Override
	public Boolean puedeCapturar(IProfugo profugo) {
		return super.puedeCapturar(profugo) && ! profugo.esNervioso();
	}

	@Override
	public void intimidar(IProfugo profugo) {
		super.intimidar(profugo);
		profugo.dejarDeEstarNervioso();
	}
}
