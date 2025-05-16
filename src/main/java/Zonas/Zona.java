package Zonas;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//import Cazadores.Cazador;
import Cazadores.CazadorBase;
import Profugos.IProfugo;

public class Zona {
	private String nombre;
	private List<IProfugo> profugos = new ArrayList<IProfugo>();
	
	public Zona(String nombre,List<IProfugo> profugos) {
		this.nombre = nombre;
		this.profugos = new ArrayList<>(profugos);
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public List<IProfugo> getProfugos(){
		return this.profugos;
	}
	
	public Integer cantidadProfugos() {
		return profugos.size();
	}
	
	public void agregarProfugo(IProfugo profugo) {
		profugos.add(profugo);
	}
	
	public void realizarCaceria(CazadorBase cazador) {
		List<IProfugo> capturados = new ArrayList<IProfugo>();
		List<IProfugo> intimidados = new ArrayList<IProfugo>();
		
		profugos.stream().filter(p -> cazador.puedeCapturar(p)).forEach(p -> capturados.add(p));
		if (! capturados.isEmpty()) {
			this.profugos.removeAll(capturados);
		}
		
		
		profugos.stream().filter(p -> ! cazador.puedeCapturar(p)).forEach(p -> intimidados.add(p));
		
		intimidados.stream().forEach(c -> cazador.intimidar(c));
		Integer minHabilidadIntimidados = 
				intimidados.stream()
				.map(i -> i.getHabilidad())
				.filter(Objects::nonNull)
				.min(Integer::compareTo)
				.orElse(0);
		cazador.aumentarExperiencia(minHabilidadIntimidados + 2 * capturados.size());
	}
}
