package tn.esprit.spring;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(AnnotationAwareAspectJAutoProxyCreator.class)
public class EnterpriseServiceImplTest {
	
	@Autowired
	IEntrepriseService enterpriseRepository;

	@Autowired
	IEmployeService iemployeservice;

	@Test
	public void testAjouterEntreprise() {
		Entreprise e = new Entreprise("Esprit","Educational");
		enterpriseRepository.ajouterEntreprise(e);
		assertThat(e.getId()).isPositive();
	}

	@Test
	public void testFindEntrepriseById() {
		Entreprise entreprise = enterpriseRepository.getEntrepriseById(25);
		assertThat(entreprise.getId()).isEqualTo(25);
	}

	@Test
	public void testGetListEntreprise() {
		ArrayList<Entreprise> entreprises = (ArrayList<Entreprise>) enterpriseRepository.getAllEntreprises();
		assertThat(entreprises.size()).isGreaterThan(0);
	}

	@Test
	public void tesUpdateEntreprise() {
		Entreprise entreprise = enterpriseRepository.getEntrepriseById(6);
		entreprise.setName("Esprit2");
		int entrepriseId = enterpriseRepository.ajouterEntreprise(entreprise);
		Entreprise updatedEntreprise = enterpriseRepository.getEntrepriseById(entrepriseId);
		assertThat(updatedEntreprise.getName()).isEqualTo(entreprise.getName());
	}

	@Test
	public void tesDeleteEntreprise() {
		Entreprise entreprise = enterpriseRepository.getEntrepriseById(6);
		Optional<Entreprise> optionalEntreprise =  enterpriseRepository.optionalGetEntrepriseById(entreprise.getId());
		assertThat(optionalEntreprise.isPresent()).isTrue();
		enterpriseRepository.deleteEntrepriseById(optionalEntreprise.get().getId());
	}

	@Test
	public void testAffecterEmployeeDepartement(){
		Employe e = new Employe("Rana","Chaabane","rana@gmail.com",true, Role.CHEF_DEPARTEMENT);
		int EmployeIdAaffecter= iemployeservice.ajouterEmploye(e);
		Departement d = new Departement("Informatique");
		int DepartmentId = enterpriseRepository.ajouterDepartement(d);
		iemployeservice.affecterEmployeADepartement(EmployeIdAaffecter,DepartmentId);
		Employe EmployeToCheck = iemployeservice.getEmployeeById(EmployeIdAaffecter);
		assertThat(EmployeToCheck.getDepartements().contains(d));
	}
}