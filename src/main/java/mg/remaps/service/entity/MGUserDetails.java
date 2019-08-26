package mg.remaps.service.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;



@Entity
public class MGUserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String username;

	@Column
	private String chasisNumber;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private String carBrand;
	
	@Column
	private String carModel;
	
	@Column
	private String carYear;
	
	@Column
	private String carEngine;
	
	@Column
	private String carStage;
	
	@Column
	private String carPlateNumber;

	@Column
	private String phoneNumber;

	@Column
	private String password;


	public MGUserDetails() {
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getChasisNumber() {
		return chasisNumber;
	}

	public void setChasisNumber(String chasisNumber) {
		this.chasisNumber = chasisNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getCarPlateNumber() {
		return carPlateNumber;
	}

	public void setCarPlateNumber(String carPlateNumber) {
		this.carPlateNumber = carPlateNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getCarBrand() {
		return carBrand;
	}

	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public String getCarYear() {
		return carYear;
	}

	public void setCarYear(String carYear) {
		this.carYear = carYear;
	}

	public String getCarEngine() {
		return carEngine;
	}

	public void setCarEngine(String carEngine) {
		this.carEngine = carEngine;
	}

	public String getCarStage() {
		return carStage;
	}

	public void setCarStage(String carStage) {
		this.carStage = carStage;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public MGUserDetails(String username, String chasisNumber, String firstName, String lastName, String carBrand,
			String carModel, String carYear, String carEngine, String carStage, String carPlateNumber,
			String phoneNumber, String password) {
		this.username = username;
		this.chasisNumber = chasisNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.carBrand = carBrand;
		this.carModel = carModel;
		this.carYear = carYear;
		this.carEngine = carEngine;
		this.carStage = carStage;
		this.carPlateNumber = carPlateNumber;
		this.phoneNumber = phoneNumber;
		this.password = password;
	}
	
	
	
}