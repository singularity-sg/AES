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

function openConfirmation(title, message, deletionLink) {
	$("#message-dialog div.message").text(message);
	$("#message-dialog").dialog({ closeOnEscape: true, title: title, modal: true, buttons: [ { text: "Ok", click: function() { document.location.href = deletionLink; $( this ).dialog( "close" );}} , { text: "Cancel", click: function() { $(this).dialog("close");}} ]});
	$("#message-dialog").dialog("open");
}
