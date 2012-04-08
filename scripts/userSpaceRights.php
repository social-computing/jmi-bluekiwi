<?php
require_once 'Zend/Http/Client.php';

define('CLIENT_ID', '914d4ad0f30e01d3b48c');
define('CLIENT_SECRET', 'c62cee6c330a39f0a786');
define('SUPER_TOKEN', 'f523902728af04407ae2045975bfb0ff');
define('BK_URL', 'http://partners.sandboxbk.net');

$userRights = '{"userId": 71, "acl": "writer"}';

// Sign the request
$time = time();
$strParams = "oauth_timestamp=" . $time . "&oauth_token=" . SUPER_TOKEN . "&q=" . $userRights;
$signature = sha1(CLIENT_ID . "&" . $strParams . "&" . CLIENT_SECRET);

$client = new Zend_Http_Client();
$client->resetParameters();
$client->setMethod("POST");
$client->setUri(BK_URL . '/api/v3/space/23/_members');
$client->setParameterGet('oauth_token', SUPER_TOKEN);
$client->setParameterGet('oauth_timestamp', $time);
$client->setParameterGet('oauth_signature', $signature);
$client->setParameterPost('q', $userRights);

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
    string(29) "Tue, 20 Mar 2012 11:22:32 GMT"
    ["Server"]=>
    string(22) "Apache/2.2.16 (Debian)"
    ["X-powered-by"]=>
    string(32) "PHP/5.3.9-ZS5.6.0 ZendServer/5.0"
    ["Set-cookie"]=>
    string(44) "PHPSESSID=rpgotpmoaivbvjtu20hploio92; path=/"
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
    string(2) "44"
    ["Connection"]=>
    string(5) "close"
    ["Content-type"]=>
    string(9) "text/html"
  }
  ["body":protected]=>
  string(44) "S*-N-R/VHL�Q(O,V(-HI,IMQ+D�"
}
string(24) ""user's acl was updated""
*/
?>

