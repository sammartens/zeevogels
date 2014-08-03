Ext.define('Zeevogels.view.component.TurbineHeightCombo', {
    extend: 'Ext.form.field.ComboBox',
    store : 'TurbineHeights',
	alias : 'widget.turbineHeightCombo',
	queryMode : 'local',
	displayField : 'value',
	valueField : 'id',
	typeAhead : true,
	minChars : 1,
	forceSelection : true,
	emptyText:'Selecteer...'
});