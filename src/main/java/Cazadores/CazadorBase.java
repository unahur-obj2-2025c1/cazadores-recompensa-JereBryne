package Cazadores;

import java.util.ArrayList;
//import java.util.Comparator;

import Profugos.IProfugo;
//import Profugos.ProfugoBase;

public abstract class CazadorBase  {
	private Integer experiencia;
	private ArrayList<IProfugo> capturados = new ArrayList<IProfugo>();
	
	public CazadorBase(Integer experiencia) {
		this.experiencia = experiencia;
	}
	
	public Integer getExperiencia() {
		return experiencia;
	}

	
	public void aumentarExperiencia(Integer experiencia) {
		this.experiencia += experiencia;
	}
	
	public Boolean puedeCapturar(IProfugo profugo) {
		return this.experiencia > profugo.getInocencia();
	}
	
	public void intimidar(IProfugo profugo) {
		profugo.disminuirInocencia();
	}
	
	public void agregarCapturado(IProfugo capturado) {
		this.capturados.add(capturado);
	}
	
	public ArrayList<IProfugo> getCapturados(){
		return capturados;
	}
	
/*	public IProfugo capturadoMasHabil() {
		return capturados.stream().max(Comparator.comparingInt(IProfugo::getHabilidad)).orElse(new ProfugoBase(0,0,false));
	}*/
	
	public Integer cantidadCapturados() {
		return capturados.size();
	}

}
