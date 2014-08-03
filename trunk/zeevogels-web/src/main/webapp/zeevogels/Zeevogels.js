Ext.application({
	name : 'Zeevogels',
	appFolder : 'zeevogels',
	paths : {
		'Ext.ux' : 'ext-4.2.1/examples/ux'
	},
	autoCreateViewport : true,
	controllers : [ 'Trips','Observations'],
	launch : function() {
		if (!window.console) { 
			console = {
				log: function(){}, 
				warn: function(){}, 
				error: function(){} 
			};
		};
	}
});