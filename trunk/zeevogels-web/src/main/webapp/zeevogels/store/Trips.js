Ext.define('Zeevogels.store.Trips', {
	extend : 'Ext.data.Store',
	model : 'Zeevogels.model.Trip',
	pageSize : 50,
	remoteFilter:true,
	remoteSort:true,
	autoSync:false
});