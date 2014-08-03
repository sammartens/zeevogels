Ext.define('Zeevogels.view.component.AssociationBehaviourCombo', {
    extend: 'Ext.form.field.ComboBox',
    store : 'AssociationBehaviours',
	alias : 'widget.associationBehaviourCombo',
	queryMode : 'local',
	displayField : 'value',
	valueField : 'id',
	typeAhead : true,
	minChars : 1,
	forceSelection : true,
	emptyText:'Selecteer...'
});