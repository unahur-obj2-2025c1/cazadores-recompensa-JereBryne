package Profugos;

public class ProfugoBase implements IProfugo {
	private Integer inocencia;
	private Integer habilidad;
	private Boolean esNervioso;
	
	public ProfugoBase(Integer inocencia, Integer habilidad, Boolean esNervioso) {
		this.inocencia = inocencia;
		this.habilidad = habilidad;
		this.esNervioso = esNervioso;
	}
	
	@Override
	public Integer getInocencia() {
		return this.inocencia;
	}

	@Override
	public Integer getHabilidad() {
		return this.habilidad;
	}

	@Override
	public Boolean esNervioso() {
		return this.esNervioso;
	}

	@Override
	public void volverseNervioso() {
		this.esNervioso = true;
	}

	@Override
	public void dejarDeEstarNervioso() {
		this.esNervioso = false;
	}

	@Override
	public void reducirHabilidad() {
		this.habilidad = Math.max(0, this.habilidad - 5);
	}

	@Override
	public void disminuirInocencia() {
		this.inocencia = Math.max(0, this.inocencia - 2);
	}
}
