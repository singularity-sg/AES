function openMessageIfAny(title) {
	var message = $("#message-dialog div.message").text();
	
	if(message) {
		openDialog(title, message);
	}
}

function openDialog(title, message, color) {
	$("#message-dialog div.message").text(message);
	$("#message-dialog").dialog({ closeOnEscape: true, title: title, modal: true, buttons: [ { text: "Ok", click: function() { $( this ).dialog( "close" ); }}]});
	$("#message-dialog").dialog("open");
}
