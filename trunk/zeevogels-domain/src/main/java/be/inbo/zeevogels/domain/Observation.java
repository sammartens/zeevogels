package be.inbo.zeevogels.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import be.inbo.zeevogels.json.JsonBooleanDeserializer;
import be.inbo.zeevogels.json.JsonBooleanSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(schema = "dbo")
public class Observation {

	private Long id;
	private Trip trip;
	private Boolean wp = Boolean.FALSE;
	private String startTime;
	private String endTime;
	private String groupCode;
	private Taxon taxon;
	private Age age;
	private Plumage plumage;
	private Stratum stratum;
	private FlywayBehaviour flywayBehaviour;
	private AssociationBehaviour associationBehaviour;
	private TurbineHeight turbineHeight;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonSerialize(using = JsonBooleanSerializer.class)
	public Boolean getWp() {
		return wp;
	}

	@JsonDeserialize(using = JsonBooleanDeserializer.class)
	public void setWp(Boolean wp) {
		this.wp = wp;
	}

	@ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "TripID")
	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@ManyToOne
	@JoinColumn(name = "TaxonID")
	public Taxon getTaxon() {
		return taxon;
	}

	public void setTaxon(Taxon taxon) {
		this.taxon = taxon;
	}

	@ManyToOne
	@JoinColumn(name = "AgeID")
	public Age getAge() {
		return age;
	}

	public void setAge(Age age) {
		this.age = age;
	}

	@ManyToOne
	@JoinColumn(name = "PlumageID")
	public Plumage getPlumage() {
		return plumage;
	}

	public void setPlumage(Plumage plumage) {
		this.plumage = plumage;
	}

	@ManyToOne
	@JoinColumn(name = "StratumID")
	public Stratum getStratum() {
		return stratum;
	}

	public void setStratum(Stratum stratum) {
		this.stratum = stratum;
	}

	@ManyToOne
	@JoinColumn(name = "FlywayBehaviourID")
	public FlywayBehaviour getFlywayBehaviour() {
		return flywayBehaviour;
	}

	public void setFlywayBehaviour(FlywayBehaviour flywayBehaviour) {
		this.flywayBehaviour = flywayBehaviour;
	}

	@ManyToOne
	@JoinColumn(name = "AssociationBehaviourID")
	public AssociationBehaviour getAssociationBehaviour() {
		return associationBehaviour;
	}

	public void setAssociationBehaviour(AssociationBehaviour associationBehaviour) {
		this.associationBehaviour = associationBehaviour;
	}

	@ManyToOne
	@JoinColumn(name = "TurbineHeightID")
	public TurbineHeight getTurbineHeight() {
		return turbineHeight;
	}

	public void setTurbineHeight(TurbineHeight turbineHeight) {
		this.turbineHeight = turbineHeight;
	}

}