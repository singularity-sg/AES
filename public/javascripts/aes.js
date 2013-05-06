function openMessageIfAny(title) {
	var message = $("#message-dialog div.message").text();
	
	if(message) {
		openDialog(title, message);
	}
}

function openDialog(title, message, color) {
	$("#message-dialog div.message").text(message);
	$("#message-dialog").dialog({ 
			closeOnEscape: true, 
			title: title, 
			modal: true, 
			buttons: [ { text: "Ok", click: function() { $( this ).dialog( "close" ); }} ]
	});
	
	$("#message-dialog").dialog("open");
}

function openConfirmation(title, message, func) {
	$("#message-dialog div.message").text(message);
	$("#message-dialog").dialog({ 
			closeOnEscape: true, 
			title: title, 
			modal: true, 
			buttons: [ { text: "Ok", click: func } , 
			           { text: "Cancel", click: function() { $(this).dialog("close");}} ]
	});
	$("#message-dialog").dialog("open");
}


function createBarChart(data, targetId) {
	
	var storyPoints = [];
	var actualHours = [];
	
	for (var key in data) {
		if (data.hasOwnProperty(key)) {
			storyPoints.push(key);
		}
	}
	
	storyPoints.sort(function(a,b){return a-b});
	
	for (i=0;i<storyPoints.length;i++) {
		actualHours.push(data[storyPoints[i]]);
	}
	
	var w=800,
		h=600,
		p=20,
		lmargin=80;
	
	var bottom = h-2*p;
	var x = d3.scale.ordinal().domain(d3.range(storyPoints.length+1)).rangeBands([0, w], 0.2);
	var y = d3.scale.linear().domain([0, d3.max(actualHours)]).range([0, bottom]);
	
	var vis = d3.select(targetId)
	.append("svg:svg")
	.attr("width",w)
	.attr("height",h)
	.append("svg:g");
	
	vis.append("svg:g")
	.attr("class","label")
	.append("svg:text")
	.attr("x", 0)
    .attr("y", bottom)
    .attr("dy", "20")
    .attr("text-anchor", "start")
    .attr("font-family", "sans-serif")
    .attr("font-size", "12px")
    .attr("font-weight", "bold")
    .attr("fill","black")
    .text("Story Points");
	
	var bars = vis.selectAll("g.bar")
	.data(actualHours)
	.enter().append("svg:g")
	.attr("class","bar")
	.attr("transform", function(d, i) { return "translate(" + (i * (x.rangeBand() + 10) + lmargin) + ",0)"; })
	.on("mouseover", function(d, i) {d3.select(this).select(".actualHours").text(d3.round(d / 8.5, 2) + "MD")})
	.on("mouseout", function(d, i) {d3.select(this).select(".actualHours").text(d3.round(d, 2) + "MH")})
	
	bars.append("svg:rect")
    .attr("fill",  function(d) { return "rgb(0, 0, " + (d * 10) + ")";})
    .attr("y", function(d,i) { return bottom - y(d)})
    .attr("width", x.rangeBand())
    .attr("height", function(d) { return y(d) });
	
	bars.append("svg:text")
    .attr("x", x.rangeBand())
    .attr("y", function(d,i) { return bottom - y(d)})
    .attr("dx", -(x.rangeBand()/2))
    .attr("dy", "14")
    .attr("class", "actualHours")
    .attr("text-anchor", "middle")
    .attr("font-family", "sans-serif")
    .attr("font-size", "12px")
    .attr("font-weight", "bold")
    .attr("fill", "white")
    .text(function(d, i) { return d3.round(d,2) + "MH" });
	
	bars.append("svg:text")
    .attr("x", x.rangeBand())
    .attr("y", bottom)
    .attr("dx", -(x.rangeBand()/2))
    .attr("dy", "20")
    .attr("text-anchor", "middle")
    .attr("font-family", "sans-serif")
    .attr("font-size", "11px")
    .attr("font-weight", "bold")
    .text(function(d, i) { return storyPoints[i] });

	bars.transition()
	.duration(750)
	.delay(function(d, i) { return i * 10; })

}