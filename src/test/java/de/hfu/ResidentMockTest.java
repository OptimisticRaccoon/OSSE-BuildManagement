package de.hfu;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.hfu.residents.domain.Resident;
import de.hfu.residents.repository.ResidentRepository;
import de.hfu.residents.service.BaseResidentService;
import de.hfu.residents.service.ResidentServiceException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*; 

public class ResidentMockTest {
	
	static ResidentRepository repositoryMock;
	static BaseResidentService baseResidentService;
	Resident filter;
		
	@BeforeClass
	public static void setUpBeforeClass() {
		repositoryMock = EasyMock.createMock(ResidentRepository.class);
		baseResidentService = new BaseResidentService();
		baseResidentService.setResidentRepository(repositoryMock);
		
		List<Resident> repoList = Arrays.asList(
				new Resident("Udo", "Winkler", "Hohe Straße 25", "Großstadt", new Date(1963, 8, 21)),
				new Resident("Helga", "Winkler", "Niedrige Straße 55", "Großstadt", new Date(1973, 3, 15)),
				new Resident("Helga", "Lämmermühle", "Lange Straße 7", "Kleinstadt", new Date(1980, 1, 20)),
				new Resident("Herbert", "Schwind", "Breite Straße 8", "Riesenstadt", new Date(1982, 7, 9)),
				new Resident("Hermann", "Rauh", "Schmale Straße 9", "Kleinstadt", new Date(1968, 6, 27)));
		
		EasyMock.expect(repositoryMock.getResidents()).andStubReturn(repoList);
		
		EasyMock.replay(repositoryMock);
		
		EasyMock.verify(repositoryMock);
	}
	
	@Before
	public void setUpBefore() {
		filter = new Resident();
	}
	
	@Rule 
	public ExpectedException exceptionRule = ExpectedException.none();
	
	@Test
	public void testGetUniqueResident_WildcardException() throws ResidentServiceException {
		
		filter.setGivenName("H*");
		
		exceptionRule.expect(ResidentServiceException.class);
		exceptionRule.expectMessage("Wildcards (*) sind nicht erlaubt!");
		
		baseResidentService.getUniqueResident(filter);
	}
	
	@Test
	public void testGetUniqueResident_NotUniqueException() throws ResidentServiceException {

		filter.setFamilyName("Winkler");
		
		exceptionRule.expect(ResidentServiceException.class);
		exceptionRule.expectMessage("Suchanfrage lieferte kein eindeutiges Ergebnis!");
		
		baseResidentService.getUniqueResident(filter);
	}
	
	@Test
	public void testGetUniqueResident() throws ResidentServiceException {
		
		Resident filter = new Resident("Udo", "Winkler", "Hohe Straße 25", "Großstadt", new Date(1963, 8, 21));
		
		Resident foundResident = baseResidentService.getUniqueResident(filter);
		
		assertThat(foundResident.getGivenName(), equalTo(filter.getGivenName()));
	}
	
	@Test
	public void testGetFilteredResidents() {
	
		filter.setFamilyName("Winkler");
		
		List<Resident> foundResidents = baseResidentService.getFilteredResidentsList(filter);
		
		assertThat(foundResidents.size(), equalTo(2));
		
	}
	
	@Test
	public void testGetFilteredResidents_Wildcard() {
		
		filter.setGivenName("Her*");
		
		List<Resident> foundResidents = baseResidentService.getFilteredResidentsList(filter);
		
		assertThat(foundResidents.size(), equalTo(2));
	}
	
	@Test
	public void testGetFilteredResidents_Partial() {
		
		filter.setGivenName("He*");
		filter.setFamilyName("Winkler");
		
		List<Resident> foundResidents = baseResidentService.getFilteredResidentsList(filter);
	
		assertThat(foundResidents.size(), equalTo(1));
	}

}
