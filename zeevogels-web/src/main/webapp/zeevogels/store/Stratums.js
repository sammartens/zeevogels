Ext.define('Zeevogels.store.Stratums', {
	extend : 'Ext.data.Store',
	model : 'Zeevogels.model.Stratum',
	autoLoad:true,
	proxy : {
		type : 'rest',
		url : 'rest/references/stratums',
		reader : {
			type : 'json',
			root : 'data',
			idProperty:'id',
			successProperty : 'success'
		}
	}
});