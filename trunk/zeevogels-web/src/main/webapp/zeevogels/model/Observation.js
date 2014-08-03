Ext.define('Zeevogels.model.Observation', {
	extend : 'Ext.data.Model',
	requires: ['Zeevogels.data.NestedJsonWriter'],
	fields : [ {name : 'id',type : 'integer',useNull : true}, 
	           'wp',
	           'startTime',
	           'endTime',
	           'taxon.id',
	           'groupCode',
	           'age.id',
	           'plumage.id',
	           'stratum.id',
	           'flywayBehaviour.id',
	           'associationBehaviour.id',
	           'turbineHeight.id',
	           'trip.id'
	],
	proxy : {
		type : 'rest',
		url : 'rest/observation',
		reader : {type : 'json',root : 'data',successProperty : 'success'},
		writer : {type: 'nestedjson'}
	}
});