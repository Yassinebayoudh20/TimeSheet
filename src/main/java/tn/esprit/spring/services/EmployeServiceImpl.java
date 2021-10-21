package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class EmployeServiceImpl implements IEmployeService {

	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;

	public int ajouterEmploye(Employe employe) {
		employeRepository.save(employe);
		return employe.getId();
	}

	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		Optional<Employe> employe = employeRepository.findById(employeId);
		if(employe.isPresent())
		{ 
		Employe emp=employe.get();
		emp.setEmail(email);
		employeRepository.save(emp);
		}

	}

	@Transactional	
	public void affecterEmployeADepartement(int employeId, int depId) {
		Optional<Departement> depManagedOptional = deptRepoistory.findById(depId);
		Optional<Employe> employeManagedOptional = employeRepository.findById(employeId);
		if(depManagedOptional.isPresent()&&employeManagedOptional.isPresent())
		{ 
			Employe emp=employeManagedOptional.get();
			Departement dep = depManagedOptional.get();
			if(dep.getEmployes() == null){

				List<Employe> employes = new ArrayList<>();
				employes.add(emp);
				dep.setEmployes(employes);
			}else{

				dep.getEmployes().add(emp);

			}
		}
	

	}
	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId)
	{
		Optional<Departement> depManagedOptional = deptRepoistory.findById(depId);
		if(depManagedOptional.isPresent())
		{ 
			Departement dep = depManagedOptional.get();
		int employeNb = dep.getEmployes().size();
		for(int index = 0; index < employeNb; index++){
			if(dep.getEmployes().get(index).getId() == employeId){
				dep.getEmployes().remove(index);
				break;//a revoir
			}
		}}
	}

	public int ajouterContrat(Contrat contrat) {
		contratRepoistory.save(contrat);
		return contrat.getReference();
	}

	public void affecterContratAEmploye(int contratId, int employeId) {
		Optional<Contrat> contratManagedOptional = contratRepoistory.findById(contratId);
		Optional<Employe> employeManagedOptional = employeRepository.findById(employeId);
		if(employeManagedOptional.isPresent()&&contratManagedOptional.isPresent())
		{ 
			Employe emp=employeManagedOptional.get();
			Contrat cont =contratManagedOptional.get();
			cont.setEmploye(emp);
			contratRepoistory.save(cont);
		}

		
		
	}

	public Optional<Employe> getEmployeeById(int id){
		return employeRepository.findById(id);
	}

	public String getEmployePrenomById(int employeId) {
		Optional<Employe> employeManagedOptional = employeRepository.findById(employeId);
		if(employeManagedOptional.isPresent())
		{ 			
		Employe emp=employeManagedOptional.get();
		return emp.getPrenom();}
		return null;
		
	}
	public void deleteEmployeById(int employeId)
	{
		Optional<Employe> employeManagedOptional = employeRepository.findById(employeId);
		if(employeManagedOptional.isPresent())
		{ 			
		Employe emp=employeManagedOptional.get();

		//Desaffecter l'employe de tous les departements
		//c'est le bout master qui permet de mettre a jour
		//la table d'association
		for(Departement dep : emp.getDepartements()){
			dep.getEmployes().remove(emp);
		}

		employeRepository.delete(emp);}
	}

     public void deleteContratById(int contratId) {
    	 Optional<Contrat> contratManagedOptional = contratRepoistory.findById(contratId);
    		if(contratManagedOptional.isPresent())
    		{ 			
    		Contrat cont=contratManagedOptional.get();
		contratRepoistory.delete(cont);}

	}

	public int getNombreEmployeJPQL() {
		return employeRepository.countemp();
	}
	
	public List<String> getAllEmployeNamesJPQL() {
		return employeRepository.employeNames();

	}
	
	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		return employeRepository.getAllEmployeByEntreprisec(entreprise);
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);

	}
	public void deleteAllContratJPQL() {
         employeRepository.deleteAllContratJPQL();
	}
	
	public float getSalaireByEmployeIdJPQL(int employeId) {
		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}

	public Double getSalaireMoyenByDepartementId(int departementId) {
		return employeRepository.getSalaireMoyenByDepartementId(departementId);
	}
	
	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}

	public List<Employe> getAllEmployes() {
				return (List<Employe>) employeRepository.findAll();
	}
	public Optional<Employe> optionalGetEmployeById(int employee) {return employeRepository.findById(employee);}
}
