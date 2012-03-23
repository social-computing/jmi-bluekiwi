<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.socialcomputing.wps.server.planDictionnary.connectors.utils.UrlHelper.Type"%>
<%@page import="org.codehaus.jackson.map.ObjectMapper"%>
<%@page import="org.codehaus.jackson.JsonNode"%>
<%@page import="com.socialcomputing.wps.server.planDictionnary.connectors.utils.UrlHelper"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.socialcomputing.bluekiwi.services.RestProvider"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<title>Just Map It! Lecko - super token test page</title>
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

</head>
<body>
<%
/*
UrlHelper urlHelper = new UrlHelper(RestProvider.BK_URL + "/api/v3/user/43/_spaces");
urlHelper.addParameter("limit", "1");
//urlHelper.addParameter("text", "test");
RestProvider.addSuperToken(urlHelper, RestProvider.SUPER_TOKEN);
urlHelper.openConnections();
ObjectMapper mapper = new ObjectMapper();
JsonNode jsonData = mapper.readTree(urlHelper.getStream());
*/

UrlHelper urlHelper = new UrlHelper(RestProvider.BK_URL + "/api/v3/post/_search");
urlHelper.setType(Type.POST);
urlHelper.addParameter("text", "just map it");
urlHelper.addParameter("destinationIds", "23");
RestProvider.addSuperToken(urlHelper, RestProvider.SUPER_TOKEN);
urlHelper.openConnections();
ObjectMapper mapper = new ObjectMapper();
JsonNode jsonData = mapper.readTree(urlHelper.getStream());
%>
<div>
<%=jsonData.toString() %>
</div>
</body>
</html>
