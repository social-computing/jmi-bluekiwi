<?php
require_once 'Zend/Http/Client.php';

define('CLIENT_ID', '914d4ad0f30e01d3b48c');
define('CLIENT_SECRET', 'c62cee6c330a39f0a786');
define('SUPER_TOKEN', 'f523902728af04407ae2045975bfb0ff');
define('BK_URL', 'http://partners.sandboxbk.net');

// Sign the request
$time = time();
$strParams = "limit=1&oauth_timestamp=" . $time . "&oauth_token=" . SUPER_TOKEN;
$signature = sha1(CLIENT_ID . "&" . $strParams . "&" . CLIENT_SECRET);
		
$client = new Zend_Http_Client();
$client->resetParameters();
$client->setMethod("GET");
$client->setUri(BK_URL . '/api/v3/user/43/_spaces');
$client->setParameterGet('oauth_token', SUPER_TOKEN);
$client->setParameterGet('oauth_timestamp', $time);
$client->setParameterGet('oauth_signature', $signature);
$client->setParameterGet('limit', 1);
$response = $client->request();
$responseText = $response->getBody();
$access_token = $responseText;
var_dump($response);
var_dump($responseText);

?>
