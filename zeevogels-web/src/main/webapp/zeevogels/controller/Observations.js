Ext.define('Zeevogels.controller.Observations', {
    extend: 'Ext.app.Controller',
    stores: [ 'Observations','Ages','Plumages','Stratums','FlywayBehaviours','AssociationBehaviours','TurbineHeights','Taxons'],
    views: [ 'Viewport' ],
    refs: [
        {selector: 'viewport > panel',ref: 'mainPanel'},
        {selector: 'observationPanel > grid',ref: 'observationGrid'},
        {selector: 'observationPanel > toolbar button[action=saveObservations]',ref: 'saveButton'},
        {selector: 'observationPanel > grid toolbar fieldcontainer cleartextfield[filterProperty=groupCode]',ref: 'filterGroupCodeField'},
        {selector: 'observationPanel > grid toolbar fieldcontainer cleartextfield[filterProperty=taxon]',ref: 'filterTaxonField'},
        {selector: 'observationPanel > grid toolbar fieldcontainer cleartextfield[filterProperty=age]',ref: 'filterAgeField'},
        {selector: 'observationPanel > grid toolbar fieldcontainer cleartextfield[filterProperty=plumage]',ref: 'filterPlumageField'},
        {selector: 'observationPanel > grid toolbar fieldcontainer cleartextfield[filterProperty=stratum]',ref: 'filterStratumField'},
        {selector: 'observationPanel > grid toolbar fieldcontainer cleartextfield[filterProperty=flywayBehaviour]',ref: 'filterFlywayBehaviourField'},
        {selector: 'observationPanel > grid toolbar fieldcontainer cleartextfield[filterProperty=associationBehaviour]',ref: 'filterAssociationBehaviourField'},
        {selector: 'observationPanel > grid toolbar fieldcontainer cleartextfield[filterProperty=turbineHeight]',ref: 'filterTurbineHeightField'}
    ],
    init: function () {
    	this.control({
        	'observationPanel > grid > toolbar > fieldcontainer > cleartextfield': {
            	clear: this.onClearFilterClick,
        		keyup: {
        			fn:function(textfield){
        				this.getObservationsStore().removeFilter(textfield.filterProperty);
        		        this.getObservationsStore().addFilter({id:textfield.filterProperty,property:textfield.filterProperty, value:textfield.getValue()});
        			},
        			buffer:150
        		}
            },
            'observationPanel > grid > toolbar > fieldcontainer > monthfield': {
            	clear: this.onClearFilterClick,
            	select: {
        			fn:function(field){
        				this.getObservationsStore().removeFilter(field.filterProperty);
        		        this.getObservationsStore().addFilter({id:field.filterProperty,property:field.filterProperty, value:Ext.Date.format(field.getValue(), 'Y-m')});
        			},
        			buffer:150
        		},
        		change:function(field){
        			if (field.isValid()){
        				this.getObservationsStore().removeFilter(field.filterProperty);
        		        this.getObservationsStore().addFilter({id:field.filterProperty,property:field.filterProperty, value:Ext.Date.format(field.getValue(), 'Y-m')});
        			}
        			
        		}
            },
            'observationPanel > toolbar > button[action=addObservation]': {
                click: this.newObservation
            },
            'observationPanel > toolbar > button[action=saveObservations]': {
                click: this.saveObservations
            },
            'observationPanel > toolbar > button[action=cancel]': {
                click: this.closeObservations
            }
        });
    },
    onLaunch: function () {
    	this.getObservationsStore().on('update',function(store){
    		this.getSaveButton().enable();
    	},this);
		
    	/*
    	this.getObservationsStore().on('beforeload',function(store){
    		Ext.getCmp('tripGrid').setLoading('Even geduld...');
    	});
    	this.getObservationsStore().on('load',function(store){
    		Ext.getCmp('tripGrid').setLoading(false);
    	},this);
    	this.getShipsStore().on('load',function(store){
    		this.getObservationsStore().load({
            	scope:this,
            	callback: function(records, operation, success) {
            		this.getTripGrid().setLoading(false);
                }
            });	
    	},this);*/
    	
    },
    begin: function (tripId) {
		this.getMainPanel().getLayout().setActiveItem(1);
		this.getObservationsStore().removeFilter('tripId');
        this.getObservationsStore().addFilter({id:'tripId',property:'tripId', value:tripId});
        this.getObservationGrid().tripId=tripId;
        this.getObservationsStore().load({
        	/*scope:this,
        	callback: function(records, operation, success) {
        		this.getTripGrid().setLoading(false);
            }*/
        });
		console.log(tripId);
    },
    newObservation: function () {
    	this.getObservationGrid().plugins[0].cancelEdit();
    	var record = Ext.create('Zeevogels.model.Observation', {
            taxon: 'New taxon'           
        });
    	record.set('trip.id',this.getObservationGrid().tripId);
        this.getObservationsStore().insert(0, record);
        this.getObservationGrid().plugins[0].startEdit(0,1);
    },
    saveObservations: function () {
    	this.getObservationsStore().sync({
    		scope:this,
			success:function(){
				this.getSaveButton().disable();
			}
    	});
    },
    closeObservations: function () {
    	//TODO check dirty
    	this.getMainPanel().getLayout().setActiveItem(0);
    },
    onClearFilterClick: function(field){
		this.getObservationsStore().addFilter({id:field.filterProperty,property:field.filterProperty, value:null});
		this.getObservationsStore().removeFilter(field.filterProperty);
    }
});