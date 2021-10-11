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
public class EmployeServiceImplTest {
	
	@Autowired
	IEmployeService employeRepository;
	
	@Autowired
	IEntrepriseService enterpriseRepository;



	@Test
	public void testAjouterEmployee() {
		Employe e = new Employe("rana","chaabne","@emal",true,Role.ADMINISTRATEUR);
		employeRepository.ajouterEmploye(e);
		assertThat(e.getId()).isGreaterThan(0);
	}

	@Test
	public void testFindEmployeeById() {
		Employe e1 = employeRepository.getEmployeeById(1);
		assertThat(e1.getId()).isEqualTo(1);
	}

	@Test
	public void testGetListEmployee() {
		ArrayList<Employe> employees = (ArrayList<Employe>) employeRepository.getAllEmployes();
		assertThat(employees.size()).isGreaterThan(0);
	}

	@Test
	public void tesUpdateEmployee() {
		Employe employe = employeRepository.getEmployeeById(1);
		employe.setNom("yassine");
		int employe1 = employeRepository.ajouterEmploye(employe);
		Employe updatedEmploye = employeRepository.getEmployeeById(employe1);
		assertThat(updatedEmploye.getNom()).isEqualTo(employe.getNom());
	}

	@Test
	public void tesDeleteEmployee() {
		Employe employe = employeRepository.getEmployeeById(1);
		Optional<Employe> optionalEmploye =  employeRepository.optionalGetEmployeById(employe.getId());
		assertThat(optionalEmploye.isPresent()).isTrue();
		employeRepository.deleteEmployeById(optionalEmploye.get().getId());
	}

	@Test
	public void testAffecterEmployeeDepartement(){
		Employe e = new Employe("Rana","Chaabane","rana@gmail.com",true, Role.CHEF_DEPARTEMENT);
		int EmployeIdAaffecter= employeRepository.ajouterEmploye(e);
		Departement d = new Departement("Informatique");
		int DepartmentId = enterpriseRepository.ajouterDepartement(d);
		employeRepository.affecterEmployeADepartement(EmployeIdAaffecter,DepartmentId);
		Employe EmployeToCheck = employeRepository.getEmployeeById(EmployeIdAaffecter);
		assertThat(EmployeToCheck.getDepartements().contains(d));
	}
}