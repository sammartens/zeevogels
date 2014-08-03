Ext.define('Zeevogels.view.component.StratumCombo', {
    extend: 'Ext.form.field.ComboBox',
    store : 'Stratums',
	alias : 'widget.stratumCombo',
	queryMode : 'local',
	displayField : 'value',
	valueField : 'id',
	typeAhead : true,
	minChars : 1,
	forceSelection : true,
	emptyText:'Selecteer...'
});