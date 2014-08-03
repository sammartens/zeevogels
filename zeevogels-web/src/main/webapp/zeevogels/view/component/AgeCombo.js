Ext.define('Zeevogels.view.component.AgeCombo', {
    extend: 'Ext.form.field.ComboBox',
    store : 'Ages',
	alias : 'widget.ageCombo',
	queryMode : 'local',
	displayField : 'value',
	valueField : 'id',
	typeAhead : true,
	minChars : 1,
	forceSelection : true,
	emptyText:'Selecteer...'
});