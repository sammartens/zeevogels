Ext.define('Zeevogels.view.component.FlywayBehaviourCombo', {
    extend: 'Ext.form.field.ComboBox',
    store : 'FlywayBehaviours',
	alias : 'widget.flywayBehaviourCombo',
	queryMode : 'local',
	displayField : 'value',
	valueField : 'id',
	typeAhead : true,
	minChars : 1,
	forceSelection : true,
	emptyText:'Selecteer...'
});