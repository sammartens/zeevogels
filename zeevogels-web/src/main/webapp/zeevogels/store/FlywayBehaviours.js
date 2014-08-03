Ext.define('Zeevogels.store.FlywayBehaviours', {
	extend : 'Ext.data.Store',
	model : 'Zeevogels.model.FlywayBehaviour',
	autoLoad:true,
	proxy : {
		type : 'rest',
		url : 'rest/references/flywayBehaviours',
		reader : {
			type : 'json',
			root : 'data',
			idProperty:'id',
			successProperty : 'success'
		}
	}
});