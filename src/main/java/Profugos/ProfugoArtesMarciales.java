package Profugos;

public class ProfugoArtesMarciales extends ProfugoDecorador {

	public ProfugoArtesMarciales(IProfugo profugoDecorado) {
		super(profugoDecorado);
	}
	
	@Override
	public Integer getHabilidad() {
		return Math.min(100, profugoDecorado.getHabilidad()*2);
	}
	

}
