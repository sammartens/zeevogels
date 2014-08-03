Ext.define('Zeevogels.store.Taxons', {
	extend : 'Ext.data.Store',
	model : 'Zeevogels.model.Taxon',
	autoLoad:true,
	proxy : {
		type : 'rest',
		url : 'rest/references/taxons',
		reader : {
			type : 'json',
			root : 'data',
			idProperty:'id',
			successProperty : 'success'
		}
	}
});