// http://www.extjs.com/learn/Tutorials
// This be one ugly page, but it's just practice.

/*global Ext */
Ext.onReady(function () {

    var myDiv = Ext.get('myDiv');
    //myDiv.setWidth(400, true);
    myDiv.highlight();
    myDiv.addClass('red');  // CSS class defined in ExtStart.css.
    myDiv.center();
    myDiv.setOpacity(.25);

    Ext.select('p').highlight();

    // responding to events
    Ext.get('myButton').on('click', function() {
        Ext.MessageBox.show({
            title: 'The Button',
            msg: 'The button was clicked'
        });
    });

    // event handlers take up to 3 parameters
    // var paragraphClicked = function(e, t) {
    //     Ext.get(e.target).highlight();  // t same as e.target
    // }
    // Ext.select('p').on('click', paragraphClicked);

    // how to pass arguments to an event handler using createDelegate
    var someHandler = function (evt, t, o, myArg1, myArg2, myArg3) {
        // do stuff
    }
    Ext.select('.notice-type1').addListener('click', someHandler.createDelegate(this, [4, 'stuff1', 11], true));
    Ext.select('.notice-type2').addListener('click', someHandler.createDelegate(this, [7, 'stuff2', 12], true));

    // message box
    var paragraphClicked = function(e) {
        var paragraph = Ext.get(e.target);
        paragraph.highlight();

        // passing an object literal
        Ext.MessageBox.show({
            title: 'Paragraph Clicked',
            msg: paragraph.dom.innerHTML,
            width: 400,
            buttons: Ext.MessageBox.OK,
            animEl: paragraph
        });
    }
    Ext.select('p').on('click', paragraphClicked);

    // grid
    var myData = [
	['Apple',29.89,0.24,0.81,'9/1 12:00am'],
	['Ext',83.81,0.28,0.34,'9/12 12:00am'],
	['Google',71.72,0.02,0.03,'10/1 12:00am'],
	['Microsoft',52.55,0.01,0.02,'7/4 12:00am'],
	['Yahoo!',29.01,0.42,1.47,'5/22 12:00am']
    ];
 
    var ds = new Ext.data.Store({
	proxy: new Ext.data.MemoryProxy(myData),
	reader: new Ext.data.ArrayReader({id: 0}, [
	    {name: 'company'},
	    {name: 'price', type: 'float'},
	    {name: 'change', type: 'float'},
	    {name: 'pctChange', type: 'float'},
	    {name: 'lastChange', type: 'date', dateFormat: 'n/j h:ia'}
	])
    });
    ds.load();

    var colModel = new Ext.grid.ColumnModel([
	{header: "Company", width: 120, sortable: true, dataIndex: 'company'},
	{header: "Price", width: 90, sortable: true, dataIndex: 'price'},
	{header: "Change", width: 90, sortable: true, dataIndex: 'change'},
	{header: "% Change", width: 90, sortable: true, dataIndex: 'pctChange'},
	{header: "Last Updated", width: 120, sortable: true, 
	 renderer: Ext.util.Format.dateRenderer('m/d/Y'), 
         dataIndex: 'lastChange'}
    ]);

    var grid = new Ext.grid.GridPanel({el: 'grid-example', store: ds, colModel: colModel, viewConfig: {forceFit: true}, width: 600, height: 300, frame: true});
    grid.render();
    grid.getSelectionModel().selectFirstRow();
    Ext.get('grid-example').show();

    // ajax
    Ext.get('okButton').on('click', function() {
        var msg = Ext.get('msg');
        msg.load({
            url: 'http://localhost:8080/ajaxjsp/loadJSP.jsp',
            params: 'name=' + Ext.get('name').dom.value,
            text: 'Updating...'
        });
        msg.show();
    });

    
});



function test(n) {
    return n + 3;
}

function testtest() {
    var i = 0;
    for (i = 0; i <= 10; i++)
    {
        if (test(i) == (i + 3))
        {
            write("Test Passed");
        }
    }
}
