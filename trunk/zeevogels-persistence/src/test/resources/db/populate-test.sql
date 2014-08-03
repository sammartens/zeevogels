INSERT INTO dbo.SurveyStatus (Id,Name) VALUES (1,'Started');

INSERT INTO dbo.Survey(Id,Name,StartDate,EndDate,StatusId,Description)
	VALUES(1,'Mijn eerste survey',SYSDATE,SYSDATE,1,'1e');
INSERT INTO dbo.Survey(Id,Name,StartDate,EndDate,StatusId,Description)
	VALUES(2,'Mijn tweede survey',SYSDATE,SYSDATE,1,'2e');
INSERT INTO dbo.Survey(Id,Name,StartDate,EndDate,StatusId,Description)
	VALUES(3,'Mijn derde survey',SYSDATE,SYSDATE,1,'3e');
 
INSERT INTO dbo.SurveyEvent(SurveyID,Toponiem)
	VALUES(1,'Toponiem1');
