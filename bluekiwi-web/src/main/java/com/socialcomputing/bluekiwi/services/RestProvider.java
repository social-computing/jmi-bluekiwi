package com.socialcomputing.bluekiwi.services;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;

import com.socialcomputing.bluekiwi.utils.HashUtil;
import com.socialcomputing.wps.server.planDictionnary.connectors.WPSConnectorException;
import com.socialcomputing.wps.server.planDictionnary.connectors.datastore.Attribute;
import com.socialcomputing.wps.server.planDictionnary.connectors.datastore.Entity;
import com.socialcomputing.wps.server.planDictionnary.connectors.datastore.StoreHelper;
import com.socialcomputing.wps.server.planDictionnary.connectors.utils.NameValuePair;
import com.socialcomputing.wps.server.planDictionnary.connectors.utils.UrlHelper;

@Path("/")
public class RestProvider {

    public static final String CLIENT_ID = "914d4ad0f30e01d3b48c";
    public static final String CLIENT_SECRET = "c62cee6c330a39f0a786";
    public static final String SUPER_TOKEN = "f523902728af04407ae2045975bfb0ff";
    public static final String BK_URL = "http://partners.sandboxbk.net";
    
    public static final String CALLBACK_URL = "http://bluekiwi.wps.cloudbees.net/";
    public static final String AUTHORIZE_ENDPOINT = BK_URL + "/oauth2/authorize";
    public static final String TOKEN_ENDPOINT = BK_URL + "/oauth2/token";
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    @GET
    @Path("maps/map.json")
    @Produces(MediaType.APPLICATION_JSON)
    public String kind(@Context HttpServletRequest request, @DefaultValue("") @QueryParam("query") String query, @QueryParam("token") String token) {
        HttpSession session = request.getSession(true);
        String key = query;
        String result = null;//( String)session.getAttribute( key);
        if (result == null || result.length() == 0) {
           result = build(query, token);
           session.setAttribute( key, result);
        }
        return result;
    }
    
    String build(String query, String token) {
        StoreHelper storeHelper = new StoreHelper();
        try {
            UrlHelper urlIdeas = new UrlHelper(RestProvider.BK_URL + "/api/v3/post/_search");
            urlIdeas.addParameter("oauth_token", token);
            urlIdeas.addParameter("limit", "50");
            urlIdeas.addParameter("text", query);
            urlIdeas.openConnections();
            JsonNode ideas = mapper.readTree(urlIdeas.getStream());
            /*
            for (JsonNode idea : (ArrayNode) ideas) {
                Attribute att = storeHelper.addAttribute(String.valueOf(idea.get("id").getIntValue()));
                att.addProperty("name", idea.get("title").getTextValue());
                att.addProperty("url", idea.get("permalink").getTextValue());
                
                // Author
                Entity ent = storeHelper.addEntity(String.valueOf(idea.get("user_id").getIntValue()));
                ent.addAttribute( att, 1);
                // Comments
                UrlHelper urlComments = new UrlHelper( DimeloRestProvider.IDEA_API_URL + "/1.0/feedbacks/" + att.getId() + "/comments");
                urlComments.addParameter( "access_token", DimeloRestProvider.ACCESS_TOKEN);
                urlComments.openConnections();
                JsonNode commments = mapper.readTree(urlComments.getStream());
                for (JsonNode comment : (ArrayNode) commments) {
                    if( comment.has("user_id")) {
                        ent = storeHelper.addEntity(String.valueOf(comment.get("user_id").getIntValue()));
                        ent.addAttribute( att, 1);
                    }
                }
                urlComments.closeConnections();
                // Votes
                UrlHelper urlVotes = new UrlHelper( DimeloRestProvider.IDEA_API_URL + "/1.0/feedbacks/" + att.getId() + "/comments");
                urlVotes.addParameter( "access_token", DimeloRestProvider.ACCESS_TOKEN);
                urlVotes.openConnections();
                JsonNode votes = mapper.readTree(urlVotes.getStream());
                for (JsonNode vote : (ArrayNode) votes) {
                    if( vote.has("user_id")) {
                        ent = storeHelper.addEntity(String.valueOf(vote.get("user_id").getIntValue()));
                        ent.addAttribute( att, 1);
                    }
                }
                urlVotes.closeConnections();
            }
            urlIdeas.closeConnections();*/
        }
        catch (Exception e) {
            return StoreHelper.ErrorToJson(e);
        }
        return storeHelper.toJson();
    }
    
    public static String getAccessToken(String code, HttpSession session) throws JsonProcessingException, IOException, WPSConnectorException {
    	UrlHelper urlHelper = new UrlHelper(UrlHelper.Type.POST, TOKEN_ENDPOINT);
    	urlHelper.addParameter("client_id", CLIENT_ID);
    	urlHelper.addParameter("client_secret", CLIENT_SECRET);
    	urlHelper.addParameter("code", code);
    	urlHelper.addParameter("redirect_uri", CALLBACK_URL);
    	urlHelper.addParameter("grant_type", "authorization_code");
    	
    	urlHelper.openConnections();
    	JsonNode tokenData = mapper.readTree(urlHelper.getStream());
    	String token = tokenData.get("access_token").getTextValue();
    	session.setAttribute("oauth_token", token);
    	return token;
    }
    
    
    public static void addSuperToken(UrlHelper urlHelper, String superToken) {
    	// Signing the request
    	long now = new Date().getTime()  / 1000;
    	urlHelper.addParameter("oauth_timestamp", String.valueOf(now));
    	urlHelper.addParameter("oauth_token", superToken);
    	
    	String signParameters = "";
    	for(NameValuePair parameter : urlHelper.getParameters()) {
    		signParameters += "&" + parameter.getName() + "=" + parameter.getValue();  
    	}
    	signParameters = signParameters.substring(1);
    	String signature = HashUtil.getPHPSha1(RestProvider.CLIENT_ID + "&" + signParameters + "&" + RestProvider.CLIENT_SECRET);    	
    	urlHelper.addParameter("oauth_signature", signature);
    }
     
}