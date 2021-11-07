package tn.esprit.spring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.services.IEntrepriseService;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(AnnotationAwareAspectJAutoProxyCreator.class)
@Slf4j
public class EnterpriseServiceImplTest {
	
	@MockBean
	IEntrepriseService enterpriseRepository;

	@Autowired
	IEntrepriseService iEntrepriseService;
	
	@Test
	public void getAllEntreprisesListTest(){
		when(enterpriseRepository.getAllEntreprises()).thenReturn(Stream
				.of(new Entreprise("Esprit","RaisonSocial1"),
					new Entreprise("DECADE","RaisonSocial2")).collect(Collectors.toList()));
		assertThat(iEntrepriseService.getAllEntreprises()).isNot(null);
		assertThat(iEntrepriseService.getAllEntreprises().size()).isEqualTo(2);
	}

	@Test
	public void saveEntrepriseTest(){
		Entreprise e = new Entreprise("Entrepiser1","ReasonSocial1");
		when(enterpriseRepository.ajouterEntreprise(e)).thenReturn(e.getId());
		assertThat(e.getId()).isEqualTo(enterpriseRepository.ajouterEntreprise(e));
	}

	@Test
	public void getEntrepriseByIsTest(){
		Entreprise entrepriseMock = new Entreprise("Entrepiser1","ReasonSocial1");
		entrepriseMock.setId(1);
		when(enterpriseRepository.getEntrepriseById(1)).thenReturn(entrepriseMock);
		Entreprise e =enterpriseRepository.getEntrepriseById(1);
		assertThat(1).isEqualTo(e.getId());
	}

	@Test
	public void deleteEntrepriseTest(){
		Entreprise entrepriseMock = new Entreprise("Entrepiser1","ReasonSocial1");
		entrepriseMock.setId(1);
		iEntrepriseService.deleteEntrepriseById(1);
		verify(enterpriseRepository,times(1)).deleteEntrepriseById(1);
	}

	@Test
	public void updateEntrepriseTest(){
		Entreprise entrepriseMock = new Entreprise("Entrepiser1","ReasonSocial1");
		entrepriseMock.setId(1);
		when(enterpriseRepository.getEntrepriseById(1)).thenReturn(entrepriseMock);
		when(enterpriseRepository.ajouterEntreprise(entrepriseMock)).thenReturn(entrepriseMock.getId());
		//if already exists it will update it
		entrepriseMock.setName("Entreprise2");
		iEntrepriseService.ajouterEntreprise(entrepriseMock);
		verify(enterpriseRepository,times(1)).ajouterEntreprise(entrepriseMock);
		assertThat(enterpriseRepository.getEntrepriseById(1).getName()).matches("Entreprise2");
	}

/*
	@Test
	public void testAffecterEmployeeDepartement(){
		Employe e = new Employe("Rana","Chaabane","rana@gmail.com",true, Role.CHEF_DEPARTEMENT);
		int EmployeIdAaffecter= iemployeservice.ajouterEmploye(e);
		Departement d = new Departement("Informatique");
		int DepartmentId = enterpriseRepository.ajouterDepartement(d);
		iemployeservice.affecterEmployeADepartement(EmployeIdAaffecter,DepartmentId);
		Employe EmployeToCheck = iemployeservice.getEmployeeById(EmployeIdAaffecter);
		assertThat(EmployeToCheck.getDepartements().contains(d));
	}*/
}