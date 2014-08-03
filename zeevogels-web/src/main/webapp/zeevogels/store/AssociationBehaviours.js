Ext.define('Zeevogels.store.AssociationBehaviours', {
	extend : 'Ext.data.Store',
	model : 'Zeevogels.model.AssociationBehaviour',
	autoLoad:true,
	proxy : {
		type : 'rest',
		url : 'rest/references/associationBehaviours',
		reader : {
			type : 'json',
			root : 'data',
			idProperty:'id',
			successProperty : 'success'
		}
	}
});