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

	/*@Test
	public void testFindEmployeeById() {
		Employe e1 = employeRepository.getEmployeeById(1);
		assertThat(e1.getId()).isEqualTo(1);
	}
*/
	@Test
	public void testGetListEmployee() {
		ArrayList<Employe> employees = (ArrayList<Employe>) employeRepository.getAllEmployes();
		assertThat(employees.size()).isGreaterThan(0);
	}

	@Test
	public void tesUpdateEmployee() {
		Optional<Employe> employe = employeRepository.getEmployeeById(1);
		if (employe.isPresent())
		{ Employe emp = employe.get();
		emp.setNom("yassine");
		int employe1 = employeRepository.ajouterEmploye(emp);
		Optional<Employe> updatedEmploye = employeRepository.getEmployeeById(employe1);
		if (updatedEmploye.isPresent())
		{ Employe empUpdated = updatedEmploye.get();
		assertThat(empUpdated.getNom()).isEqualTo(emp.getNom());}
	}}

	/*@Test
	public void tesDeleteEmployee() {
		Optional<Employe> employe = employeRepository.getEmployeeById(1);
		if (employe.isPresent())
		{ Employe emp = employe.get();
		//Optional<Employe> optionalEmploye =  employeRepository.optionalGetEmployeById(emp.getId());
		
		//assertThat(optionalEmploye.isPresent()).isTrue();
		//employeRepository.deleteEmployeById(optionalEmploye.get().getId());}
		
	*/

	@Test
	public void testAffecterEmployeeDepartement(){
		Employe e = new Employe("Rana","Chaabane","rana@gmail.com",true, Role.CHEF_DEPARTEMENT);
		int employeIdAaffecter= employeRepository.ajouterEmploye(e);
		Departement d = new Departement("Informatique");
		int departmentId = enterpriseRepository.ajouterDepartement(d);
		employeRepository.affecterEmployeADepartement(employeIdAaffecter,departmentId);
		//Employe employeTocheck = employeRepository.getEmployeeById(employeIdAaffecter);
		//assertThat(employeTocheck.getDepartements().contains(d));
	}
}