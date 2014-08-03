Ext.define('Zeevogels.view.component.PlumageCombo', {
    extend: 'Ext.form.field.ComboBox',
    store : 'Plumages',
	alias : 'widget.plumageCombo',
	queryMode : 'local',
	displayField : 'value',
	valueField : 'id',
	typeAhead : true,
	minChars : 1,
	forceSelection : true,
	emptyText:'Selecteer...'
});