Ext.define('Zeevogels.view.component.ClearableMonth', {
    extend: 'Ext.ux.form.field.Month',
    alias: 'widget.clearmonth',
    trigger2Cls: 'x-form-clear-trigger',
    initComponent: function () {
        var me = this;

        me.addEvents(
           'reset',
        	/**
            * @event beforeclear
            *
            * @param {FilterCombo} FilterCombo The filtercombo that triggered the event
            */
            'beforeclear',
            /**
            * @event beforeclear
            *
            * @param {FilterCombo} FilterCombo The filtercombo that triggered the event
            */
            'clear'
        );

        me.callParent(arguments);

        me.on('specialkey', this.onSpecialKeyDown, me);
        /*me.on('select', function (me, rec) {
            me.onShowClearTrigger(true); 
        }, me);*/
        me.on('afterrender', function () { me.onShowClearTrigger(true); }, me);
    },

    /**
    * @private onSpecialKeyDown
    * eventhandler for special keys
    */
    onSpecialKeyDown: function (obj, e, opt) {
        if ( e.getKey() == e.ESC )
        {
            this.clear();
        }
    },

    onShowClearTrigger: function (show) {
        
    },

    /**
    * @override onTrigger2Click
    * eventhandler
    */
    onTrigger2Click: function (args) {
        this.clear();
    },

    /**
    * @private clear
    * clears the current search
    */
    clear: function () {
        var me = this;
        me.fireEvent('beforeclear', me);
        me.reset();
        me.onShowClearTrigger(false);
        me.fireEvent('clear', me);
    }
});