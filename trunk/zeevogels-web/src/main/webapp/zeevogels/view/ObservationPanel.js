Ext.define('Zeevogels.view.ObservationPanel', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.observationPanel',
	requires: ['Zeevogels.view.ObservationGrid'],
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
                 {xtype: 'button',icon:'resources/img/application_add.png',text: 'Nieuwe observatie',action: 'addObservation'}]
	         },
	         {xtype:'observationGrid'},
	         {xtype: 'toolbar',margin: 5,items: [
                 {xtype: 'button',icon:'resources/img/disk.png',text: 'Save',disabled:true,action: 'saveObservations'},
                 '->',
                 {xtype: 'button',icon:'resources/img/cross.png',text: 'Close',action: 'cancel'}
                 ]
	         }
	]
});