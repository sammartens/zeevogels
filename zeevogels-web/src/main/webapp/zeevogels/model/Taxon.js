Ext.define('Zeevogels.model.Taxon', {
	extend : 'Ext.data.Model',
	fields : [ {name : 'id',type : 'integer',useNull : true}, 
	           'code',
	           'value'
	]
});