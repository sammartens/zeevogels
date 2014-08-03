create schema dbo;

create table dbo.Survey (
    Id int IDENTITY(1,1),
    Name nvarchar(20) NOT NULL,
	AdminName nvarchar(20) NULL,
	Description nvarchar(4000) NOT NULL,
    StartDate datetime NOT NULL,
    EndDate datetime NOT NULL,
    StatusId int NOT NULL,
    constraint PK_Survey primary key (ID)
);

CREATE TABLE dbo.SurveyStatus(
	ID int IDENTITY(1,1) NOT NULL,
	Name nvarchar(50) NOT NULL,
	constraint PK_SurveyStatus primary key (ID)
);

create table dbo.SurveyEvent (
    Id int IDENTITY(1,1),
    SurveyId int NOT NULL,
    Toponiem nvarchar(50) NOT NULL,
    constraint PK_SurveyEvent primary key (ID)
);


create table dbo.Trip (
    Id int IDENTITY(1,1),
    Survey nvarchar(20) NOT NULL,
	Date datetime NOT NULL,
    ShipId int NULL,
    Observer1 nvarchar(120) NULL,
    Observer2 nvarchar(120) NULL,
	constraint PK_Trip primary key (ID)
);

create table dbo.Ship (
    Id int IDENTITY(1,1),
    Name nvarchar(120) NOT NULL,
	constraint PK_Ship primary key (ID)
);


create table dbo.Taxon(
    Id int IDENTITY(1,1),
    Value nvarchar(50) NOT NULL,
    Code nvarchar(10) not null,
    constraint PK_Taxon primary key (ID)
);

create table dbo.Age (
    Id int IDENTITY(1,1),
    Value nvarchar(50) NOT NULL,
    Code nvarchar(10) not null,
    constraint PK_Age primary key (ID)
);

create table dbo.Plumage (
    Id int IDENTITY(1,1),
    Value nvarchar(50) NOT NULL,
    Code nvarchar(10) not null,
    constraint PK_Plumage primary key (ID)
);

create table dbo.Stratum (
    Id int IDENTITY(1,1),
    Value nvarchar(50) NOT NULL,
    Code nvarchar(10) not null,
    constraint PK_Stratum primary key (ID)
);

create table dbo.FlywayBehaviour (
    Id int IDENTITY(1,1),
    Value nvarchar(50) NOT NULL,
    Code nvarchar(10) not null,
    constraint PK_FlywayBehaviour primary key (ID)
);

create table dbo.AssociationBehaviour (
    Id int IDENTITY(1,1),
    Value nvarchar(50) NOT NULL,
    Code nvarchar(10) not null,
    constraint PK_AssociationBehaviour primary key (ID)
);

create table dbo.TurbineHeight(
    Id int IDENTITY(1,1),
    Value nvarchar(50) NOT NULL,
    Code nvarchar(10) not null,
    constraint PK_TurbineHeight primary key (ID)
);

create table dbo.Observation (
    Id int IDENTITY(1,1),
	TripId int NOT NULL,
    wp smallint NULL DEFAULT 0,
	startTime nvarchar(15) NULL,
	endTime nvarchar(15) NULL,
	GroupCode nvarchar(120) NOT NULL,
	TaxonId int NULL,
	AgeId int NULL,
	PlumageId int NULL,
	StratumId int NULL,
	FlywayBehaviourId int NULL,
	AssociationBehaviourId int NULL,
	TurbineHeightId int NULL,
	constraint PK_Observation primary key (ID)
);


alter table dbo.Trip add constraint FK_TripShip foreign key (ShipID) references dbo.Ship;
alter table dbo.Observation add constraint FK_Observation_Trip foreign key (TripID) references dbo.Trip;
alter table dbo.Observation add constraint FK_ObservationTaxon foreign key (TaxonID) references dbo.Taxon;
alter table dbo.Observation add constraint FK_ObservationAge foreign key (AgeID) references dbo.Age;
alter table dbo.Observation add constraint FK_ObservationPlumage foreign key (PlumageID) references dbo.Plumage;
alter table dbo.Observation add constraint FK_ObservationStratum foreign key (StratumID) references dbo.Stratum;
alter table dbo.Observation add constraint FK_ObservationFlywayBehaviour foreign key (FlywayBehaviourID) references dbo.FlywayBehaviour;
alter table dbo.Observation add constraint FK_ObservationAssociationBehaviour foreign key (AssociationBehaviourID) references dbo.AssociationBehaviour;
alter table dbo.Observation add constraint FK_ObservationTurbineHeight foreign key (TurbineHeightID) references dbo.TurbineHeight;

alter table dbo.Survey add constraint FK_Survey_SurveyStatus foreign key (StatusID) references dbo.SurveyStatus;
alter table dbo.SurveyEvent add constraint FK_SurveyEvent_Survey foreign key (SurveyID) references dbo.Survey;
