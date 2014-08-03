INSERT INTO dbo.Trip(Id,Survey,Date,ShipId)
	VALUES(1,'Mijn eerste survey',SYSDATE,1);
	
INSERT INTO dbo.Observation(tripId,groupCode,TaxonId,AgeId,PlumageId,StratumId,FlywayBehaviourId,AssociationBehaviourId,TurbineHeightId) 
	VALUES(1,'dit is groep1',2,1,1,1,1,1,1);