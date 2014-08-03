Ext.define('Zeevogels.model.Trip', {
	extend : 'Ext.data.Model',
	requires: ['Zeevogels.data.NestedJsonWriter'],
	fields : [ {name : 'id',type : 'integer',useNull : true}, 
	           'survey',
	           {name:'date',type:'date',dateFormat:'Y-m-d H:i:s'},
	           'ship.id',
		       'observer1',
	           'observer2'
	],
	proxy : {
		type : 'rest',
		url : 'rest/trip',
		reader : {type : 'json',root : 'data',successProperty : 'success'},
		writer : {type: 'nestedjson'}
	}
});