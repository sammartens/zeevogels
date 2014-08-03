Ext.define('Zeevogels.store.Ages', {
	extend : 'Ext.data.Store',
	model : 'Zeevogels.model.Age',
	autoLoad:true,
	proxy : {
		type : 'rest',
		url : 'rest/references/ages',
		reader : {
			type : 'json',
			root : 'data',
			idProperty:'id',
			successProperty : 'success'
		}
	}
});