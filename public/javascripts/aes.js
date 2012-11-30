function openMessageIfAny(title) {
	var message = $("#message-dialog div.message").text();
	
	if(message) {
		$("#message-dialog").dialog({ closeOnEscape: true, title: title, modal: true, buttons: [ { text: "Ok", click: function() { $( this ).dialog( "close" ); }}]});
		$("#message-dialog").dialog("open");
	}
}
