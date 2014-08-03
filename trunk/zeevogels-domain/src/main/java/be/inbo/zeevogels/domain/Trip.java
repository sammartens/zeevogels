package be.inbo.zeevogels.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(schema = "dbo")
public class Trip {

	private Long id;
	private String survey;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CET")
	private Date date;
	private Ship ship;
	private String observer1;
	private String observer2;
	private Set<Observation> observations = new HashSet<Observation>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSurvey() {
		return survey;
	}

	public void setSurvey(String survey) {
		this.survey = survey;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@ManyToOne
	@JoinColumn(name = "ShipID")
	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public String getObserver1() {
		return observer1;
	}

	public void setObserver1(String observer1) {
		this.observer1 = observer1;
	}

	public String getObserver2() {
		return observer2;
	}

	public void setObserver2(String observer2) {
		this.observer2 = observer2;
	}

	@OneToMany(mappedBy = "trip", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JsonIgnore
	public Set<Observation> getObservations() {
		return observations;
	}

	public void setObservations(Set<Observation> observations) {
		this.observations = observations;
	}

}