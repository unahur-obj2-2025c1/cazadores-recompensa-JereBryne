package Cazadores;

import Profugos.IProfugo;
import Profugos.ProfugoArtesMarciales;
import Profugos.ProfugoBase;
import Profugos.ProfugoEntrenamientoElite;
import Profugos.ProfugoProteccionLegal;
import Zonas.Agencia;
import Zonas.Zona;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CazadoresTest {	
	@Test
    public void cazadorUrbanoCapturaEIntimidaYLaZonaSoloTieneNoCapturados() {
        // Crear un cazador urbano con experiencia suficiente
        CazadorUrbano cazador = new CazadorUrbano(65);

        // Crear algunos prófugos con diferentes características
        IProfugo profugoCapturable = new ProfugoBase(60, 70, false); // Inocencia < experiencia, No nervioso
        IProfugo profugoIntimidableNervioso = new ProfugoBase(70, 30, true); // Inocencia >= experiencia, Nervioso
        IProfugo profugoIntimidableNoNervioso = new ProfugoBase(90, 90, false); // Inocencia >= experiencia, No nervioso

        // Crear una zona con estos prófugos
        Zona zona = new Zona("Ciudad Central", List.of(profugoCapturable, profugoIntimidableNervioso));
        zona.agregarProfugo(profugoIntimidableNoNervioso);
        int cantidadProfugosInicial = zona.cantidadProfugos();

        // Ejecutar el proceso de cacería
        zona.realizarCaceria(cazador);
        
        assertEquals(zona.getNombre(),"Ciudad Central");

        // Verificar que el prófugo capturable ya no esté en la zona
        assertFalse(zona.getProfugos().contains(profugoCapturable));
        assertEquals(cantidadProfugosInicial - 1, zona.cantidadProfugos()); // Un prófugo debería haber sido capturado

        // Verificar que los prófugos intimidados sigan en la zona y hayan sido afectados
        assertTrue(zona.getProfugos().contains(profugoIntimidableNervioso));
        assertFalse(profugoIntimidableNervioso.esNervioso()); // Cazador urbano los deja no nerviosos
        assertEquals(68, profugoIntimidableNervioso.getInocencia()); // Inocencia disminuye en 2

        assertTrue(zona.getProfugos().contains(profugoIntimidableNoNervioso));
        assertFalse(profugoIntimidableNoNervioso.esNervioso()); // Ya era no nervioso, no debería cambiar
        assertEquals(88, profugoIntimidableNoNervioso.getInocencia()); // Inocencia disminuye en 2

        // Verificar la experiencia del cazador (el mínimo de habilidad entre los intimidados es 30)
        assertEquals(65 + 30 + (2 * 1), cazador.getExperiencia());
    }
	
	@Test
    public void cazadorRuralIntentaCapturarEIntimidaProfugo() {
        // Cazador rural con experiencia suficiente para la condición general
        CazadorRural cazador = new CazadorRural(60);

        // Prófugo que cumple la condición general pero no la específica (no nervioso inicialmente)
        IProfugo profugoNoCapturableInicialmente = new ProfugoBase(50, 70, false);
        boolean eraNerviosoInicial = profugoNoCapturableInicialmente.esNervioso();
        int inocenciaInicial = profugoNoCapturableInicialmente.getInocencia();

        Zona zona = new Zona("Zona Rural", List.of(profugoNoCapturableInicialmente));
        zona.realizarCaceria(cazador);

        // No debería ser capturado por no ser nervioso
        assertFalse(zona.getProfugos().isEmpty());
        assertTrue(zona.getProfugos().contains(profugoNoCapturableInicialmente));
        // Debería ser intimidado y volverse nervioso
        assertNotEquals(eraNerviosoInicial, profugoNoCapturableInicialmente.esNervioso());
        assertTrue(profugoNoCapturableInicialmente.esNervioso());
        // Su inocencia debería haber disminuido
        assertEquals(inocenciaInicial - 2, profugoNoCapturableInicialmente.getInocencia());

        // Ahora el prófugo es nervioso, intentamos capturarlo de nuevo en una nueva cacería
        Zona zona2 = new Zona("Zona Rural", List.of(profugoNoCapturableInicialmente));
        zona2.realizarCaceria(cazador);
        assertTrue(zona2.getProfugos().isEmpty()); // Debería ser capturado ahora
    }
	
	@Test
    public void cazadorSigilosoIntentaCapturarEIntimidaProfugo() {
        // Cazador sigiloso con experiencia suficiente para la condición general
        CazadorSigiloso cazador = new CazadorSigiloso(70);

        // Prófugo que cumple la condición general pero no la específica (habilidad > 50)
        IProfugo profugoNoCapturableInicialmente = new ProfugoBase(60, 54, false);
        int habilidadInicial = profugoNoCapturableInicialmente.getHabilidad();
        int inocenciaInicial = profugoNoCapturableInicialmente.getInocencia();

        Zona zona = new Zona("Zona Urbana", List.of(profugoNoCapturableInicialmente));
        zona.realizarCaceria(cazador);

        // No debería ser capturado por tener habilidad > 50
        assertFalse(zona.getProfugos().isEmpty());
        assertTrue(zona.getProfugos().contains(profugoNoCapturableInicialmente));
        // Debería ser intimidado y su habilidad debería reducirse
        assertNotEquals(habilidadInicial, profugoNoCapturableInicialmente.getHabilidad());
        assertEquals(Math.max(0, habilidadInicial - 5), profugoNoCapturableInicialmente.getHabilidad());
        // Su inocencia también debería haber disminuido
        assertEquals(inocenciaInicial - 2, profugoNoCapturableInicialmente.getInocencia());

        // Ahora la habilidad del prófugo es menor que 50 (asumiendo que no era 0 inicialmente),
        // intentamos capturarlo de nuevo en una nueva cacería
        Zona zona2 = new Zona("Zona Urbana", List.of(profugoNoCapturableInicialmente));
        zona2.realizarCaceria(cazador);
        assertTrue(zona2.getProfugos().isEmpty()); // Debería ser capturado ahora
    }
	
	@Test
    public void profugoAdquiereMultiplesEntrenamientos() {
        // Prófugo base con valores iniciales
        IProfugo profugoBase = new ProfugoBase(30, 20, true);

        // Aplicar los tres entrenamientos
        IProfugo profugoEntrenado = new ProfugoProteccionLegal(new ProfugoEntrenamientoElite(new ProfugoArtesMarciales(profugoBase)));

        // Verificar los efectos de los entrenamientos

        // Artes Marciales: Duplica la habilidad (20 * 2 = 40)
        assertEquals(40, profugoEntrenado.getHabilidad());

        // Entrenamiento de Elite: Nunca nervioso
        assertFalse(profugoEntrenado.esNervioso());

        // Protección Legal: Inocencia nunca por debajo de 40 (inicialmente 30, ahora debería ser 40)
        assertEquals(40, profugoEntrenado.getInocencia());

        // Intentar disminuir la inocencia por debajo de 40
        profugoEntrenado.disminuirInocencia();
        assertEquals(40, profugoEntrenado.getInocencia()); // Debería permanecer en 40

        // Intentar volverse nervioso (Entrenamiento de Elite debería prevenirlo)
        profugoEntrenado.volverseNervioso();
        assertFalse(profugoEntrenado.esNervioso()); // Debería seguir siendo no nervioso

        // Intentar dejar de estar nervioso (ya lo está por Entrenamiento de Elite)
        profugoEntrenado.dejarDeEstarNervioso();
        assertFalse(profugoEntrenado.esNervioso()); // Debería seguir siendo no nervioso

        // Intentar reducir la habilidad (Artes Marciales ya la modificó)
        int habilidadInicial = profugoEntrenado.getHabilidad(); // 40
        profugoEntrenado.reducirHabilidad();
        assertEquals(Math.max(0, habilidadInicial - 10), profugoEntrenado.getHabilidad()); // Debería ser 15*2
    }
	
	@Test
    public void probarMetodosDeReporteriaDeAgencia() {
        // Crear cazadores
        CazadorBase urbano1 = new CazadorUrbano(100);
        urbano1.agregarCapturado(new ProfugoBase(10, 70, false));
        urbano1.agregarCapturado(new ProfugoBase(20, 80, false));

        CazadorBase rural1 = new CazadorRural(90);
        IProfugo profugo = new ProfugoBase(15, 95, true);
        rural1.agregarCapturado(profugo);

        CazadorBase sigiloso1 = new CazadorSigiloso(80);
        sigiloso1.agregarCapturado(new ProfugoBase(20, 80, false));
        sigiloso1.agregarCapturado(new ProfugoBase(30, 60, true));
        sigiloso1.agregarCapturado(new ProfugoBase(40, 40, false));

        ArrayList<CazadorBase> cazadores = new ArrayList<>(List.of(urbano1, rural1, sigiloso1));
        Agencia agencia = new Agencia(cazadores);

        // Test para profugosCapturadosPorCazadores()
        ArrayList<IProfugo> todosCapturados = agencia.profugosCapturadosPorCazadores();
        assertEquals(6, todosCapturados.size());
        assertTrue(todosCapturados.stream().anyMatch(p -> p.getHabilidad() == 70));
        assertTrue(todosCapturados.stream().anyMatch(p -> p.getHabilidad() == 80));
        assertTrue(todosCapturados.stream().filter(p -> p.getHabilidad() == 80).count() == 2); // Dos prófugos con habilidad 80
        assertTrue(todosCapturados.stream().anyMatch(p -> p.getHabilidad() == 95));
        assertTrue(todosCapturados.stream().anyMatch(p -> p.getHabilidad() == 60));
        assertTrue(todosCapturados.stream().anyMatch(p -> p.getHabilidad() == 40));

        // Test para capturadoMasHabil()
        IProfugo masHabil = agencia.capturadoMasHabil();
        assertEquals(95, masHabil.getHabilidad());
        assertEquals(profugo,agencia.capturadoMasHabil());

        // Test para cazadorConMasCapturas()
        Optional<CazadorBase> cazadorLiderOptional = agencia.cazadorConMasCapturas();
        assertTrue(cazadorLiderOptional.isPresent());
        CazadorBase cazadorLider = cazadorLiderOptional.get();
        assertEquals(3, cazadorLider.cantidadCapturados());
        assertTrue(cazadorLider instanceof CazadorSigiloso);

        // Test para caso sin capturas
        Agencia agenciaSinCapturas = new Agencia(new ArrayList<>(List.of(new CazadorUrbano(50))));
        assertEquals(0, agenciaSinCapturas.capturadoMasHabil().getHabilidad());

        // Test para caso sin cazadores
        Agencia agenciaSinCazadores = new Agencia(new ArrayList<>());
        assertFalse(agenciaSinCazadores.cazadorConMasCapturas().isPresent());
        
        
    }
}

