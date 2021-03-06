

function editEvent(event) {
	//alert('event.startDate.getDay(): '+ event.startDate.getDay());
	
	if(event.startDate.getDay()==5 || event.startDate.getDay()==6 || event.endDate.getDay()==5 || event.endDate.getDay()==6) {
		$('#divAlertMessage').html('Official Leaves cannot start or End on a weekend.');
		$('#alertModal').modal();
		return;
	}
	
    $('#event-modal input[name="event-index"]').val(event ? event.id : '');
    $('#event-modal input[name="event-name"]').val(event ? event.name : '');
    $('#event-modal input[name="event-location"]').val(event ? event.location : '');
    $('#event-modal input[name="dtFrom"]').datepicker({
        format: 'dd-mm-yyyy',
        autoclose: true
    }).datepicker("update", event ? event.startDate: '');
    $('#event-modal input[name="dtTo"]').datepicker({
        format: 'dd-mm-yyyy',
        autoclose: true
    }).datepicker("update", event ? event.endDate: '');
    $('#event-modal').modal();
}

function deleteEvent(event) {
    var dataSource = $('#calendar').data('calendar').getDataSource();

    for(var i in dataSource) {
        if(dataSource[i].id == event.id) {
            dataSource.splice(i, 1);
            break;
        }
    }
    
    $('#calendar').data('calendar').setDataSource(dataSource);
}

function saveEvent() {
    var event = {
        id: $('#event-modal input[name="event-index"]').val(),
        name: $('#event-modal input[name="event-name"]').val(),
        location: $('#event-modal input[name="event-location"]').val(),
        startDate: $('#event-modal input[name="dtFrom"]').datepicker('getDate'),
        endDate: $('#event-modal input[name="dtTo"]').datepicker('getDate')
    }
    
    var dataSource = $('#calendar').data('calendar').getDataSource();

    if(event.id) {
        for(var i in dataSource) {
            if(dataSource[i].id == event.id) {
                dataSource[i].name = event.name;
                dataSource[i].location = event.location;
                dataSource[i].startDate = event.startDate;
                dataSource[i].endDate = event.endDate;
            }
        }
    }
    else
    {
        var newId = 0;
        for(var i in dataSource) {
            if(dataSource[i].id > newId) {
                newId = dataSource[i].id;
            }
        }
        
        newId++;
        event.id = newId;
    
        dataSource.push(event);
    }
    
    $('#calendar').data('calendar').setDataSource(dataSource);
    $('#event-modal').modal('hide');
}

$(function() {
    var currentYear = 2000;

    $('#calendar').calendar({ 
        enableContextMenu: true,
        enableRangeSelection: true,
        contextMenuItems:[
            {
                text: 'Update',
                click: editEvent
            },
            {
                text: 'Delete',
                click: deleteEvent
            }
        ],
        selectRange: function(e) {
            editEvent({ startDate: e.startDate, endDate: e.endDate });
        },
        mouseOnDay: function(e) {
            if(e.events.length > 0) {
                var content = '';
                
                for(var i in e.events) {
                    content += '<div class="event-tooltip-content">'
                                    + '<div class="event-name" style="color:' + e.events[i].color + '">' + e.events[i].name + '</div>'
                                    + '<div class="event-location">' + e.events[i].location + '</div>'
                                + '</div>';
                }
            
                $(e.element).popover({ 
                    trigger: 'manual',
                    container: 'body',
                    html:true,
                    content: content
                });
                
                $(e.element).popover('show');
            }
        },
        mouseOutDay: function(e) {
            if(e.events.length > 0) {
                $(e.element).popover('hide');
            }
        },
        dayContextMenu: function(e) {
            $(e.element).popover('hide');
        },
        dataSource: [
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''},
            {id: 0,name: '',location: '',startDate: '',endDate: '',color: ''}
        ]
    });
    
    $('#save-event').click(function() {
        saveEvent();
    });
});