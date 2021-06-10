package de.hfu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import de.hfu.residents.domain.Resident;
import de.hfu.residents.repository.ResidentRepository;

public class ResidentRepositoryStub implements ResidentRepository {

	@Override
	public List<Resident> getResidents() {
		
		List<Resident> repoList = Arrays.asList(
				new Resident("Udo", "Winkler", "Hohe Straße 25", "Großstadt", new Date(1963, 8, 21)),
				new Resident("Helga", "Winkler", "Niedrige Straße 55", "Großstadt", new Date(1973, 3, 15)),
				new Resident("Helga", "Lämmermühle", "Lange Straße 7", "Kleinstadt", new Date(1980, 1, 20)),
				new Resident("Herbert", "Schwind", "Breite Straße 8", "Riesenstadt", new Date(1982, 7, 9)),
				new Resident("Hermann", "Rauh", "Schmale Straße 9", "Kleinstadt", new Date(1968, 6, 27)));
		
		return repoList;
	}

	
}
