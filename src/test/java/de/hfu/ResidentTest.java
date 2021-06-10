package de.hfu;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.hfu.residents.domain.Resident;
import de.hfu.residents.repository.ResidentRepository;
import de.hfu.residents.service.BaseResidentService;
import de.hfu.residents.service.ResidentServiceException;

public class ResidentTest {
	
	static ResidentRepositoryStub repositoryStub;
	static BaseResidentService baseResidentService;
	Resident filter;
		
	@BeforeClass
	public static void setUpBeforeClass() {
		repositoryStub = new ResidentRepositoryStub();
		baseResidentService = new BaseResidentService();
		baseResidentService.setResidentRepository(repositoryStub);
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
		
		assertEquals(foundResident.getGivenName(), filter.getGivenName());
	}
	
	@Test
	public void testGetFilteredResidents() {
	
		filter.setFamilyName("Winkler");
		
		List<Resident> foundResidents = baseResidentService.getFilteredResidentsList(filter);
		
		assertEquals(foundResidents.size(), 2);
		
	}
	
	@Test
	public void testGetFilteredResidents_Wildcard() {
		
		filter.setGivenName("Her*");
		
		List<Resident> foundResidents = baseResidentService.getFilteredResidentsList(filter);
		
		assertEquals(foundResidents.size(), 2);
	}
	
	@Test
	public void testGetFilteredResidents_Partial() {
		
		filter.setGivenName("He*");
		filter.setFamilyName("Winkler");
		
		List<Resident> foundResidents = baseResidentService.getFilteredResidentsList(filter);
	
		assertEquals(foundResidents.size(), 1);
	}

}
