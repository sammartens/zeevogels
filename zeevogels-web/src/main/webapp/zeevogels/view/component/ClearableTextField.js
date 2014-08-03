Ext.define('Zeevogels.view.component.ClearableTextField', {
    extend: 'Ext.form.field.Trigger',
    alias: 'widget.cleartextfield',
    triggerCls: 'x-form-clear-trigger',

    initComponent: function () {
        var me = this;
        me.addEvents('clear');

        me.callParent(arguments);

        me.on('change', function (me, rec) {
            me.onShowClearTrigger(true); 
        }, me);
        me.on('afterrender', function () { me.onShowClearTrigger(true); }, me);
    },
    onShowClearTrigger: function (show) {
    },
    onTriggerClick: function (args) {
        this.clear();
    },
    clear: function () {
        var me = this;
        me.setValue(null);
        me.onShowClearTrigger(false);
        me.fireEvent('clear', me);
    }
});