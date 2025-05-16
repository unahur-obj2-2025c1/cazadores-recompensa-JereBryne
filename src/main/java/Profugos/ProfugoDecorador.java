package Profugos;

public abstract class ProfugoDecorador implements IProfugo { //creo una abstracta para los decoradores
	protected IProfugo profugoDecorado; //a la cual le agrego como atributo un objeto a decorar
	
	public ProfugoDecorador(IProfugo profugoDecorado) {
		this.profugoDecorado = profugoDecorado;
	}

	@Override //los metodos sirven como puente solamente
	public Integer getInocencia() {
		return profugoDecorado.getInocencia();
	}

	@Override
	public Integer getHabilidad() {
		return profugoDecorado.getHabilidad();
	}

	@Override
	public Boolean esNervioso() {
		return profugoDecorado.esNervioso();
	}

	@Override
	public void volverseNervioso() {
		profugoDecorado.volverseNervioso();
	}

	@Override
	public void dejarDeEstarNervioso() {
		profugoDecorado.dejarDeEstarNervioso();
	}

	@Override
	public void reducirHabilidad() {
		profugoDecorado.reducirHabilidad();
	}

	@Override
	public void disminuirInocencia() {
		profugoDecorado.disminuirInocencia();
	}
}
