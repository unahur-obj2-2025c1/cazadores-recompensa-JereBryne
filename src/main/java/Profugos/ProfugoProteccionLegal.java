package Profugos;

public class ProfugoProteccionLegal extends ProfugoDecorador{

	public ProfugoProteccionLegal(IProfugo profugoDecorado) {
		super(profugoDecorado);
	}
	
	@Override
	public Integer getInocencia() {
		return Math.max(40, profugoDecorado.getInocencia());
	}

}
