package Profugos;

public class ProfugoEntrenamientoElite extends ProfugoDecorador{

	public ProfugoEntrenamientoElite(IProfugo profugoDecorado) {
		super(profugoDecorado);
	}
	
	@Override
	public Boolean esNervioso() {
		return false;
	}

	@Override
	public void volverseNervioso() {;
	}

	@Override
	public void dejarDeEstarNervioso() {;
	}
}
