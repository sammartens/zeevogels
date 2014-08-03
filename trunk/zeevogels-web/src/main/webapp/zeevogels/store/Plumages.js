Ext.define('Zeevogels.store.Plumages', {
	extend : 'Ext.data.Store',
	model : 'Zeevogels.model.Plumage',
	autoLoad:true,
	proxy : {
		type : 'rest',
		url : 'rest/references/plumages',
		reader : {
			type : 'json',
			root : 'data',
			idProperty:'id',
			successProperty : 'success'
		}
	}
});