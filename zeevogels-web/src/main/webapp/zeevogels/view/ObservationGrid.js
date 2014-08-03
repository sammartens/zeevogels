Ext.define('Zeevogels.view.ObservationGrid', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.observationGrid',
	title : 'Observations',
	requires:['Zeevogels.view.component.TaxonCombo','Zeevogels.view.component.AgeCombo','Zeevogels.view.component.PlumageCombo','Zeevogels.view.component.StratumCombo',
	          'Zeevogels.view.component.FlywayBehaviourCombo','Zeevogels.view.component.AssociationBehaviourCombo','Zeevogels.view.component.TurbineHeightCombo',
	          'Zeevogels.view.component.ClearableTextField','Zeevogels.view.component.ClearableMonth'],
	store : 'Observations',
	viewConfig: {
		loadMask:false,
		listeners:{
			refresh:function(view){
				var count=0;
				var currentIndex;
				this.store.each(function(record, idx) {
	        		var td = Ext.fly(view.getNode(idx)).down('td'),
	                    cell, matches, cellHTML;
	        		var colIdx=0;
	        		var indexes = [];
	                while(td) {
	                	var filterGroupCode=Zeevogels.app.getController('Observations').getFilterGroupCodeField().getValue();
	                	if (filterGroupCode !='' && colIdx==3){
	                		this.highlightRow(td,idx,currentIndex,indexes,filterGroupCode);
	                	}
	                	var filterTaxon=Zeevogels.app.getController('Observations').getFilterTaxonField().getValue();
	                	if (filterTaxon !='' && colIdx==4){
	                		this.highlightRow(td,idx,currentIndex,indexes,filterTaxon);
	                	}
	                	var filterAge=Zeevogels.app.getController('Observations').getFilterAgeField().getValue();
	                	if (filterAge !='' && colIdx==5){
	                		this.highlightRow(td,idx,currentIndex,indexes,filterAge);
	                	}
	                	var filterPlumage=Zeevogels.app.getController('Observations').getFilterPlumageField().getValue();
	                	if (filterPlumage !='' && colIdx==6){
	                		this.highlightRow(td,idx,currentIndex,indexes,filterPlumage);
	                	}
	                	var filterStratum=Zeevogels.app.getController('Observations').getFilterStratumField().getValue();
	                	if (filterStratum !='' && colIdx==7){
	                		this.highlightRow(td,idx,currentIndex,indexes,filterStratum);
	                	}
	                	var filterFlywayBehaviour=Zeevogels.app.getController('Observations').getFilterFlywayBehaviourField().getValue();
	                	if (filterFlywayBehaviour !='' && colIdx==8){
	                		this.highlightRow(td,idx,currentIndex,indexes,filterFlywayBehaviour);
	                	}
	                	var filterAssociationBehaviour=Zeevogels.app.getController('Observations').getFilterAssociationBehaviourField().getValue();
	                	if (filterAssociationBehaviour !='' && colIdx==9){
	                		this.highlightRow(td,idx,currentIndex,indexes,filterAssociationBehaviour);
	                	}
	                	var filterTurbineHeight=Zeevogels.app.getController('Observations').getFilterTurbineHeightField().getValue();
	                	if (filterTurbineHeight !='' && colIdx==10){
	                		this.highlightRow(td,idx,currentIndex,indexes,filterTurbineHeight);
	                	}
	                	td = td.next();
	                    colIdx++;
	                }
	            }, this);
			}
		},
		highlightRow:function(td,idx,currentIndex,indexes,filterValue){
			cell = td.down('.x-grid-cell-inner');
            matches = cell.dom.innerHTML.match(/<[^>]*>/gm);
            cellHTML = cell.dom.innerHTML.replace(/<[^>]*>/gm, '\x0f');
            var indexes = [];
            // populate indexes array, set currentIndex, and replace wrap matched string in a span
            var searchRegExp = new RegExp(filterValue, 'gi');
            cellHTML = cellHTML.replace(searchRegExp, function(m) {
               if (Ext.Array.indexOf(indexes, idx) === -1) {
                   indexes.push(idx);
               }
               if (currentIndex === null) {
                   currentIndex = idx;
               }
               return '<span class="x-livesearch-match">' + m + '</span>';
            });
            Ext.each(matches, function(match) {
                cellHTML = cellHTML.replace('\x0f', match); 
            });
            // update cell html
            cell.dom.innerHTML = cellHTML;
		}
	},
	columns : [
	    {text : 'Id',dataIndex : 'id',hidden:true}, 
	    {text : 'WP',dataIndex : 'wp',width:77,fixed:true,editor: {xtype:'checkbox'}},
	    {text : 'Start time',dataIndex : 'startTime',width:117,fixed:true,editor: {xtype:'textfield'}},
	    {text : 'End time',dataIndex : 'endTime',width:117,fixed:true,editor: {xtype:'textfield'}},
	    {text : 'Group',dataIndex : 'groupCode',width:117,fixed:true,editor: {xtype:'textfield'}},
		{text : 'Taxon',dataIndex : 'taxon.id',width: 117,fixed:true,renderer:function(value){
			if (value){
				return Zeevogels.getApplication().getController('Observations').getTaxonsStore().getById(value).get('value');
			}
	    },editor: {xtype:'taxonCombo'}},
		{text : 'Age',dataIndex : 'age.id',width: 117,fixed:true,renderer:function(value){
			if (value){
				return Zeevogels.getApplication().getController('Observations').getAgesStore().getById(value).get('value');
			}
	    },editor: {xtype:'ageCombo'}},
		{text : 'Plumage',dataIndex : 'plumage.id',width: 117,fixed:true,renderer:function(value){
			if (value){
				return Zeevogels.getApplication().getController('Observations').getPlumagesStore().getById(value).get('value');
			}
	    },editor: {xtype:'plumageCombo'}},
	    {text : 'Stratum',dataIndex : 'stratum.id',width: 117,fixed:true,renderer:function(value){
			if (value){
				return Zeevogels.getApplication().getController('Observations').getStratumsStore().getById(value).get('value');
			}
	    },editor: {xtype:'stratumCombo'}},
	    {text : 'Flyway behaviour',dataIndex : 'flywayBehaviour.id',width: 117,fixed:true,renderer:function(value){
			if (value){
				return Zeevogels.getApplication().getController('Observations').getFlywayBehavioursStore().getById(value).get('value');
			}
	    },editor: {xtype:'flywayBehaviourCombo'}},
	    {text : 'Association behaviour',dataIndex : 'associationBehaviour.id',width: 117,fixed:true,renderer:function(value){
			if (value){
				return Zeevogels.getApplication().getController('Observations').getAssociationBehavioursStore().getById(value).get('value');
			}
	    },editor: {xtype:'associationBehaviourCombo'}},
	    {text : 'Turbine height',dataIndex : 'turbineHeight.id',width: 117,fixed:true,renderer:function(value){
			if (value){
				return Zeevogels.getApplication().getController('Observations').getTurbineHeightsStore().getById(value).get('value');
			}
	    },editor: {xtype:'turbineHeightCombo'}}



	    ],
	dockedItems : [ 
       {xtype: 'toolbar',dock: 'top',items: [
           {xtype: 'fieldcontainer',layout: 'hbox',items:[
                {xtype: 'component',width:77},
                {xtype: 'component',width:117},
                {xtype: 'component',width:117},
                {xtype: 'cleartextfield',enableKeyEvents:true,filterProperty:'groupCode',width:117},
                {xtype: 'cleartextfield',enableKeyEvents:true,filterProperty:'taxon',width:117},
                {xtype: 'cleartextfield',enableKeyEvents:true,filterProperty:'age',width:117},
                {xtype: 'cleartextfield',enableKeyEvents:true,filterProperty:'plumage',width:117},
                {xtype: 'cleartextfield',enableKeyEvents:true,filterProperty:'stratum',width:117},
                {xtype: 'cleartextfield',enableKeyEvents:true,filterProperty:'flywayBehaviour',width:117},
                {xtype: 'cleartextfield',enableKeyEvents:true,filterProperty:'associationBehaviour',width:117},
                {xtype: 'cleartextfield',enableKeyEvents:true,filterProperty:'turbineHeight',width:117}
        ]
        }]},
       {xtype : 'pagingtoolbar',store : 'Observations',dock : 'bottom',displayInfo : false} 
    ],
	enableColumnHide : false,
	enableColumnMove : false,
	enableColumnResize : false,
	autoScroll : false,
	forceFit : true,
    flex: 1,
    plugins: [ new Ext.grid.plugin.CellEditing({
        clicksToEdit: 2       
    })],
    listeners : {
    	beforeedit : function(e){
    		return e.grid.editMode;
    	}
    }/*,
    setTripId:function(tripId){
    	this.tripId=tripId;
    }*/
    
});