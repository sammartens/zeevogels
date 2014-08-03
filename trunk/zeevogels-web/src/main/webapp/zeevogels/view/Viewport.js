Ext.define('Zeevogels.view.Viewport', {
	extend : 'Ext.container.Viewport',
	requires : [ 'Ext.ux.layout.Center','Zeevogels.view.TripPanel','Zeevogels.view.ObservationPanel' ],
	layout : 'ux.center',
	cls:'horizont',
	autoScroll : false,
	initComponent : function() {
		this.items = {
			xtype : 'panel',
			width : 1350,
			layout : 'card',
			tbar:[{xtype:'image',src:'resources/img/inbologo.png',style:'top:1px !important;height:22px'},,
			      {xtype:'label', text: 'Zeevogels '+version, cls:'x-panel-header-text-container-default title-label'},
        	      {xtype:'tbfill'},
        	      {xtype:'button',action: 'logout',icon: 'resources/img/door_out.png',text: 'Afmelden'}
			],
        	
			items : [ 
			      {xtype : 'tripPanel'},
			      {xtype : 'observationPanel'}
			]
		};
		this.callParent();
	}
});