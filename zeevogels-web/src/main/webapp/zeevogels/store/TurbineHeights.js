Ext.define('Zeevogels.store.TurbineHeights', {
	extend : 'Ext.data.Store',
	model : 'Zeevogels.model.TurbineHeight',
	autoLoad:true,
	proxy : {
		type : 'rest',
		url : 'rest/references/turbineHeights',
		reader : {
			type : 'json',
			root : 'data',
			idProperty:'id',
			successProperty : 'success'
		}
	}
});