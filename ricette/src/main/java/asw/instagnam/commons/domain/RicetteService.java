package asw.instagnam.commons.domain;

import asw.instagnam.common.event.RicettaCreatedEvent;
import asw.instagnam.commons.producer.RicetteEventProducer;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class RicetteService {

	private final RicetteRepository ricetteRepository;
	private final RicetteEventProducer producer;

	public RicetteService(RicetteRepository repository, RicetteEventProducer producer){
		this.ricetteRepository = repository;
		this.producer = producer;
	}

 	public RicettaCompleta createRicetta(String autore, String titolo, String preparazione) {
		RicettaCompleta ricetta = new RicettaCompleta(autore, titolo, preparazione); 
		ricetta = ricetteRepository.save(ricetta);
		producer.produce(new RicettaCreatedEvent(ricetta.getId(), autore, titolo));
		return ricetta;
	}

 	public RicettaCompleta getRicetta(Long id) {
		return ricetteRepository.findById(id).orElse(null);
	}

	public Collection<RicettaCompleta> getRicette() {
		return ricetteRepository.findAll();
	}

	public Collection<RicettaCompleta> getRicetteByAutore(String autore) {
		return ricetteRepository.findAllByAutore(autore);
	}

}
