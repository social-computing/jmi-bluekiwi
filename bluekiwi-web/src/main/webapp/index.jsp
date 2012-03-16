<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<title>Just Map It! Lecko</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="target- densitydpi=device-dpi, width=device-width, user-scalable=no"/>
<style type="text/css" media="screen">
html, body {
	height: 100%;
}
#map {
	width: 100%;
	height: 80%;
	background-color: #FFFFFF;
}
img {
	border: 0;
}
</style>
<%Boolean inverse = request.getParameter("Inverse") != null;
String query = request.getParameter("query");
if( query == null) {
    query = "";
 }%>
<link rel="stylesheet" type="text/css" href="../jmi-client/jmi-client.css" />
<script type="text/javascript" src="../jmi-client/jmi-client.js"></script>
<script type="text/javascript">
function getParams() {
	var p = {
		map: 'BlueKiwi',
		jsessionid: '<%=session.getId()%>',
		query: '<%=query%>'
    };
    return p;
};
function GoMap() {
	var parameters = getParams();
	parameters.analysisProfile='GlobalProfile';
	if( parameters.query.length > 0) {
		var map = JMI.Map({
					parent: 'map', 
					swf: './jmi-client/jmi-flex-1.0-SNAPSHOT.swf', 
					//server: 'http://server.just-map-it.com', 
					server: 'http://localhost:8080/jmi-server/', 
					//client: JMI.Map.SWF,
					parameters: parameters
				});
		map.addEventListener(JMI.Map.event.READY, function(event) {
		} );
		map.addEventListener(JMI.Map.event.ACTION, function(event) {
			window[event.fn](event.map, event.args);
		} );
		map.addEventListener(JMI.Map.event.EMPTY, function(event) {
			document.getElementById("status").innerHTML = 'Map is empty.';
		} );
		map.addEventListener(JMI.Map.event.ERROR, function(event) {
			document.getElementById("status").innerHTML = 'An error occured: ' + event.message;
		} );
	};
};
function JMIF_Navigate(map, url) {
	window.open( url, "_blank");
}
function JMIF_Focus(map, args) {
	var parameters = getParams();
	parameters.entityId = args[0];
	map.compute( parameters);
	document.getElementById("status").innerHTML = "<i>Focus on:</i> " + args[1];
}
function JMIF_Center(map, args) {
	var parameters = getParams();
	parameters.attributeId = args[0];
	parameters.analysisProfile = "DiscoveryProfile";
	map.compute( parameters);
	document.getElementById("status").innerHTML = "<i>Centered on:</i> " + args[1];
}
</script>
</head>
<body onload="GoMap()">
<form id="main" method="get">
<table width="100%" border="0">
	<tr>
		<td><a title="Just Map It! Lecko" href="./"><img alt="Just Map It! Lecko" src="./images/logo_lecko.gif" /></a></td>
		<td>
			<input type="text" name="query" title="Query" size="80" value="<%=query%>" />
			<input type="submit" value="Just Map It!" />
		</td>
		<td align="right"><a title="Just Map It!" href="http://www.social-computing.com/offre/cartographie-just-map-it/" target="_blank"><img alt="Just Map It!" src="./images/justmapit.png" /></a></td>
	</tr>
</table>
</form>
<div id="status">&nbsp;</div>
<div id="map"></div>
</body>
</html>
