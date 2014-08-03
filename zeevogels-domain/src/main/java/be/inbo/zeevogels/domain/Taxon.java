package be.inbo.zeevogels.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "dbo")
public class Taxon extends ReferenceEntityWithCode {

}
