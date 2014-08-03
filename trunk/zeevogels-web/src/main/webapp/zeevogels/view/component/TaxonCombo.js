Ext.define('Zeevogels.view.component.TaxonCombo', {
    extend: 'Ext.form.field.ComboBox',
    store : 'Taxons',
	alias : 'widget.taxonCombo',
	queryMode : 'local',
	displayField : 'value',
	valueField : 'id',
	typeAhead : true,
	minChars : 1,
	forceSelection : true,
	emptyText:'Selecteer...'
});