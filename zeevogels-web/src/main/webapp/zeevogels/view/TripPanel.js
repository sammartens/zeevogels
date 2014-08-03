Ext.define('Zeevogels.view.TripPanel', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.tripPanel',
	requires: ['Zeevogels.view.TripGrid'],
	bodyPadding : 5,
	defaults : {
		padding : 5
	},
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
	items : [
	         {xtype: 'toolbar',margin: 5,items: [
                 {xtype: 'button',icon:'resources/img/application_add.png',text: 'Nieuwe trip',action: 'addTrip'}]
	         },
	         {xtype:'tripGrid'},
	         {xtype: 'toolbar',margin: 5,items: [
                 {xtype: 'button',icon:'resources/img/disk.png',text: 'Save',disabled:true,action: 'saveTrips'}]
	         }
	]
});