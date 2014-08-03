Ext.define('Zeevogels.view.component.ShipCombo', {
    extend: 'Ext.form.field.ComboBox',
    store : 'Ships',
	alias : 'widget.shipCombo',
	queryMode : 'local',
	displayField : 'name',
	valueField : 'id',
	typeAhead : true,
	minChars : 1,
	forceSelection : true,
	emptyText:'Selecteer...'
});