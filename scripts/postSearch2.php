<?php
require_once 'Zend/Http/Client.php';

define('CLIENT_ID', '0093584955edfe9e5312');
define('CLIENT_SECRET', '5ba5d71d6cbb3fe5a539');
define('SUPER_TOKEN', 'e169290f3065894c30e70d8aaa0033f2');
define('BK_URL', 'https://lecko.bluekiwi.net');

// $searchInfo = '{"text": "social", "destinationType": "space", "destinationIds": [2,26]}';
$searchInfo = '{"text": "social"}';

// Sign the request
$time = time();
$strParams = "access_token=" . SUPER_TOKEN . "&oauth_timestamp=" . $time . "&q=" . $searchInfo;
//$strParams = "access_token=" . SUPER_TOKEN . "&q=" . $searchInfo . "&oauth_timestamp=" . $time;
$signature = sha1(CLIENT_ID . "&" . $strParams . "&" . CLIENT_SECRET);

$client = new Zend_Http_Client();
$client->resetParameters();
$client->setMethod("POST");
$client->setUri(BK_URL . '/api/v3/post/_search');
$client->setParameterGet('access_token', SUPER_TOKEN);
$client->setParameterGet('oauth_timestamp', $time);
$client->setParameterGet('oauth_signature', $signature);
$client->setParameterPost('q', $searchInfo);

$response = $client->request();
$body = $response->getBody();

var_dump($response);
var_dump($body);

// Réponse type :
/*
object(Zend_Http_Response)#5 (5) {
  ["version":protected]=>
  string(3) "1.1"
  ["code":protected]=>
  int(201)
  ["message":protected]=>
  string(7) "Created"
  ["headers":protected]=>
  array(12) {
    ["Date"]=>
    string(29) "Tue, 20 Mar 2012 10:49:49 GMT"
    ["Server"]=>
    string(22) "Apache/2.2.16 (Debian)"
    ["X-powered-by"]=>
    string(32) "PHP/5.3.9-ZS5.6.0 ZendServer/5.0"
    ["Set-cookie"]=>
    string(44) "PHPSESSID=notcuictgpi1cluldouquuun85; path=/"
    ["Expires"]=>
    string(29) "Thu, 19 Nov 1981 08:52:00 GMT"
    ["Cache-control"]=>
    string(62) "no-store, no-cache, must-revalidate, post-check=0, pre-check=0"
    ["Pragma"]=>
    string(8) "no-cache"
    ["Vary"]=>
    string(15) "Accept-Encoding"
    ["Content-encoding"]=>
    string(4) "gzip"
    ["Content-length"]=>
    string(2) "24"
    ["Connection"]=>
    string(5) "close"
    ["Content-type"]=>
    string(9) "text/html"
  }
  ["body":protected]=>
  string(24) "S23W[��N"
}
string(4) ""67""
*/
?>
