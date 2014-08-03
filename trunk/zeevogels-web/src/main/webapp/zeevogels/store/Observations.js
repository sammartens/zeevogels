Ext.define('Zeevogels.store.Observations', {
	extend : 'Ext.data.Store',
	model : 'Zeevogels.model.Observation',
	pageSize : 50,
	remoteFilter:true,
	remoteSort:true,
	autoSync:false
});