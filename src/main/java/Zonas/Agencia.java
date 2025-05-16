package Zonas;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

import Cazadores.CazadorBase;
//import Cazadores.CazadorBase;
import Profugos.IProfugo;
import Profugos.ProfugoBase;

public class Agencia {
	private ArrayList<CazadorBase> cazadores = new ArrayList<CazadorBase>();
	
	public Agencia(ArrayList<CazadorBase> cazadores) {
        this.cazadores = cazadores;
    }
	
	public ArrayList<IProfugo> profugosCapturadosPorCazadores(){
		return cazadores.stream()
				.flatMap(c -> c.getCapturados().stream())
				.collect(Collectors.toCollection(ArrayList::new));
	}
	
	public IProfugo capturadoMasHabil() {
		return profugosCapturadosPorCazadores().stream().max(Comparator.comparingInt(IProfugo::getHabilidad)).orElse(new ProfugoBase(0,0,false));
	}
	
	public Optional<CazadorBase> cazadorConMasCapturas() {
		return cazadores.stream().max(Comparator.comparingInt(CazadorBase::cantidadCapturados));
	}
}
