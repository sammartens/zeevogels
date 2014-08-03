Ext.define('Zeevogels.controller.Trips', {
    extend: 'Ext.app.Controller',
    stores: [ 'Trips','Ships'],
    views: [ 'Viewport' ],
    refs: [
        {selector: 'tripPanel > grid',ref: 'tripGrid'},
        {selector: 'tripPanel > toolbar button[action=saveTrips]',ref: 'saveButton'},
        {selector: 'tripPanel > grid toolbar fieldcontainer cleartextfield[filterProperty=survey]',ref: 'filterSurveyField'},
        {selector: 'tripPanel > grid toolbar fieldcontainer cleartextfield[filterProperty=id]',ref: 'filterTripIdField'},
        {selector: 'tripPanel > grid toolbar fieldcontainer cleartextfield[filterProperty=ship]',ref: 'filterShipField'},
        {selector: 'tripPanel > grid toolbar fieldcontainer cleartextfield[filterProperty=observer1]',ref: 'filterObserver1Field'},
        {selector: 'tripPanel > grid toolbar fieldcontainer cleartextfield[filterProperty=observer2]',ref: 'filterObserver2Field'}

    ],
    init: function () {
    	this.control({
        	'tripPanel > tripGrid': {
                viewTrip: this.openExistingTrip
            },
            'tripPanel > grid toolbar fieldcontainer cleartextfield': {
            	clear: this.onClearFilterClick,
        		keyup: {
        			fn:function(textfield){
        				this.getTripGrid().getStore().removeFilter(textfield.filterProperty);
        		        this.getTripGrid().getStore().addFilter({id:textfield.filterProperty,property:textfield.filterProperty, value:textfield.getValue()});
        			},
        			buffer:150
        		}
            },
            'tripPanel > grid > toolbar > fieldcontainer > monthfield': {
            	clear: this.onClearFilterClick,
            	select: {
        			fn:function(field){
        				this.getTripGrid().getStore().removeFilter(field.filterProperty);
        		        this.getTripGrid().getStore().addFilter({id:field.filterProperty,property:field.filterProperty, value:Ext.Date.format(field.getValue(), 'Y-m')});
        			},
        			buffer:150
        		},
        		change:function(field){
        			if (field.isValid()){
        				this.getTripGrid().getStore().removeFilter(field.filterProperty);
        		        this.getTripGrid().getStore().addFilter({id:field.filterProperty,property:field.filterProperty, value:Ext.Date.format(field.getValue(), 'Y-m')});
        			}
        			
        		}
            },
            'tripPanel > toolbar > button[action=addTrip]': {
                click: this.newTrip
            },
            'tripPanel > toolbar > button[action=saveTrips]': {
                click: this.saveTrips
            }

        });
    },
    onLaunch: function () {
    	this.getTripsStore().on('update',function(store){
    		this.getSaveButton().enable();
    	},this);
		
    	this.getTripGrid().getStore().on('beforeload',function(store){
    		Ext.getCmp('tripGrid').setLoading('Even geduld...');
    	});
    	this.getTripGrid().getStore().on('load',function(store){
    		Ext.getCmp('tripGrid').setLoading(false);
    	},this);
    	this.getShipsStore().on('load',function(store){
    		this.getTripGrid().getStore().load({
            	scope:this,
            	callback: function(records, operation, success) {
            		this.getTripGrid().setLoading(false);
                }
            });	
    	},this);
    	
    },
	openExistingTrip: function (record) {
    	this.getController('Observations').begin(record.get('id'));
    },
    newTrip: function () {
    	this.getTripGrid().plugins[0].cancelEdit();
    	var record = Ext.create('Zeevogels.model.Trip', {
            survey: 'New Survey'
            
        });
        this.getTripGrid().getStore().insert(0, record);
        this.getTripGrid().plugins[0].startEdit(0,1);
        this.getSaveButton().enable();
    },
    saveTrips: function () {
    	this.getTripGrid().getStore().sync({
    		scope:this,
			success:function(){
				this.getSaveButton().disable();
			}
    	});
    },
    onClearFilterClick: function(field){
		this.getTripGrid().getStore().addFilter({id:field.filterProperty,property:field.filterProperty, value:null});
		this.getTripGrid().getStore().removeFilter(field.filterProperty);
    }
});