Ext.define('Zeevogels.view.TripGrid', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.tripGrid',
	title : 'Trips',
	requires:['Zeevogels.view.component.ShipCombo','Zeevogels.view.component.ClearableTextField','Zeevogels.view.component.ClearableMonth'],
	store : 'Trips',
	id:'tripGrid',
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
	                	var filterSurvey=Zeevogels.app.getController('Trips').getFilterSurveyField().getValue();
	                	if (filterSurvey !='' && colIdx==1){
	                		this.highlightRow(td,idx,currentIndex,indexes,filterSurvey);
	                	}
	                 	var filterTripId=Zeevogels.app.getController('Trips').getFilterTripIdField().getValue();
	                	if (filterTripId !='' && colIdx==3){
	                		this.highlightRow(td,idx,currentIndex,indexes,filterTripId);
	                	}
	                	var filterShip=Zeevogels.app.getController('Trips').getFilterShipField().getValue();
	                	if (filterShip !='' && colIdx==4){
	                		this.highlightRow(td,idx,currentIndex,indexes,filterShip);
	                	}
	                	var filterObserver1=Zeevogels.app.getController('Trips').getFilterObserver1Field().getValue();
	                	if (filterObserver1 !='' && colIdx==5){
	                		this.highlightRow(td,idx,currentIndex,indexes,filterObserver1);
	                	}
	                	var filterObserver2=Zeevogels.app.getController('Trips').getFilterObserver2Field().getValue();
	                	if (filterObserver2 !='' && colIdx==6){
	                		this.highlightRow(td,idx,currentIndex,indexes,filterObserver2);
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
	    {header : '', align: 'center', xtype: 'actioncolumn', width: 30,fixed:true, items: [
 			{icon   : 'resources/img/magnifier.png',tooltip:'Open trip',align: 'center',
 		    	handler:function (grid, rowIndex, colIndex) {
 		    		grid.getSelectionModel().select(rowIndex);
 		    		var rec = grid.getStore().getAt(rowIndex);
 		    		this.up('grid').fireEvent('viewTrip', rec);
 		        }
 		     }]
 		},
	    {text : 'Survey',dataIndex : 'survey',width: 227,fixed:true,editor: {xtype:'textfield'}},
	    {text : 'Date',dataIndex:'date',width: 90,fixed:true,renderer:Ext.util.Format.dateRenderer('d-m-Y'),sortable:false,editor: {xtype:'datefield',allowBlank:false}},
	    {text : 'Trip ID',dataIndex : 'id',width: 90,fixed:true}, 
	    {text : 'Ship',dataIndex : 'ship.id',width: 227,fixed:true,renderer:function(value,metaData,record,rowIndex,colIndex,store){
	    	return Zeevogels.getApplication().getController('Trips').getShipsStore().getById(value).get('name');
	    },editor: {xtype:'shipCombo'}},
	    {text : 'Observer 1',dataIndex : 'observer1',width: 227,fixed:true,editor: {xtype:'textfield'}},
	    {text : 'Observer 2',dataIndex : 'observer2',width: 227,fixed:true,editor: {xtype:'textfield'}}
	    ],
	dockedItems : [ 
       {xtype: 'toolbar',dock: 'top',items: [
           {xtype: 'fieldcontainer',layout: 'hbox',items:[
                {xtype: 'component',width:27},
                {xtype: 'cleartextfield',enableKeyEvents:true,filterProperty:'survey',width:227},
                {xtype:'clearmonth',filterProperty:'date',format:'m-Y',altFormats : 'n-y|m-y|n-Y|m-Y|n/y|m/y|n/Y|m/Y',cls: 'monthfield',width:90},
                {xtype: 'cleartextfield',enableKeyEvents:true,filterProperty:'id',width:90},
                {xtype: 'cleartextfield',enableKeyEvents:true,filterProperty:'ship',width:227},
                {xtype: 'cleartextfield',enableKeyEvents:true,filterProperty:'observer1',width:227},
                {xtype: 'cleartextfield',enableKeyEvents:true,filterProperty:'observer2',width:227}
            ]
        }]},
       {xtype : 'pagingtoolbar',store : 'Trips',dock : 'bottom',displayInfo : true} ],
	enableColumnHide : false,
	enableColumnMove : false,
	enableColumnResize : false,
	autoScroll : false,
	forceFit : true,
    flex: 1,
    initComponent: function(me) {
    	// register custom events
        this.addEvents('viewTrip');
        this.callParent(arguments);
    },
    plugins: [ new Ext.grid.plugin.CellEditing({
        clicksToEdit: 2       
    })],
    listeners : {
    	beforeedit : function(e){
    		return e.grid.editMode;
    	}
    }
    
});