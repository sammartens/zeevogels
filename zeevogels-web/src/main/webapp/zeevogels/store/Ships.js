Ext.define('Zeevogels.store.Ships', {
	extend : 'Ext.data.Store',
	model : 'Zeevogels.model.Ship',
	autoLoad:true,
	proxy : {
		type : 'rest',
		url : 'rest/references/ships',
		reader : {
			type : 'json',
			root : 'data',
			idProperty:'id',
			successProperty : 'success'
		}
	}
});