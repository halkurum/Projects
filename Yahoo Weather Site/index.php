<?php
function GetGeoXML($loc, $type, $unit)
{
	$url = "http://where.yahooapis.com/v1/";
	if($type === "city")
	{
		$url = $url."places\$and(.q('".$loc."'),.type(7));start=0;count=5";
	}	
	else
	{
		$url = $url."concordance/usps/".$loc;
	}
	$appid = "?appid=K_zdrbXV34GUL97d6_YA0pQnr.f9timIbDXlc8tK98jFReWyvRR_Z6ayKYI47tg-";
	$url = $url.$appid;

	$xml = @simplexml_load_file(urlencode($url));
	return $xml;
}

$location_text = trim($_GET["location"]);
$location_type = $_GET["type"];
$unit = $_GET["unit"];

$xml123 = GetGeoXML($location_text, $location_type, $unit);
ParseGeoAndGetWeather($xml123, $unit, $location_text, $location_type);

function ParseGeoAndGetWeather($xml, $unit, $loc, $type)
{
	Header('Content-type: text/xml');

	$weather = new SimpleXMLElement('<weather/>');
	if($xml == NULL)
	{
		$weather->addAttribute('hasValue', 'false');
		echo $returnXML->asXML();
		return;
	}
	
	$woeid = null;
	$pos = false;
	if($type == "zip")
	{
		$woeid = $xml->woeid;
		$woeid = (int)$woeid;
		if($woeid === 0)
		{
			$weather->addAttribute('hasValue', 'false');
			echo $returnXML->asXML();
			return;
		}
	}
	else
	{	
		$place = $xml->children();		
		$count = $place->count();
		if($count == 0)
		{
			$weather->addAttribute('hasValue', 'false');
			echo $weather->asXML();
			return;
		}
		$woeid = $place[0]->woeid;
	}	
	
	$weatherURL = 'http://weather.yahooapis.com/forecastrss?w='.$woeid.'&u='.$unit;
	$weatherXML = simplexml_load_file($weatherURL, 'SimpleXMLElement', LIBXML_NOCDATA);

	$returnXML = new SimpleXMLElement('<xml/>');
	$weather = $returnXML->addChild('weather');

	$channel = $weatherXML->channel;
	$title = $channel->title;
	$pos=strpos($title,"Error");
	
	if($pos !== false )
	{
		$weather->addAttribute('hasValue', 'false');
		echo $weather->asXML();
		return;
	}

	$weather->addAttribute('hasValue', 'true');
	$namespaces = $weatherXML->getNameSpaces(true);
	$yweather = $channel->item->children($namespaces['yweather']);
	$location = $channel->children($namespaces['yweather'])->location;
	$unit1 = $channel->children($namespaces['yweather'])->units->attributes()->temperature;
	$city=$location->attributes()->city;
	if($city=="")
		$city="N/A";
	$region=$location->attributes()->region;
	if($region=="")
		$region="N/A";
	$country=$location->attributes()->country;
	if($country=="")
		$country="N/A";
		
	$condition = $yweather->condition->attributes();

	$link=$weatherXML->channel->link;
	if($link=="")
		$link="N/A";
	$desc = $weatherXML->channel->item->description;
	if($desc=="")
		$desc="N/A";
	$res = '';
	$res1 = '';
	if (preg_match('/<img[^>]+>/i',$desc, $result))
	{ 
		$img = $result[0];
		preg_match('/"[^"]*"/',$img, $res);
		$img1 = str_replace("\"", "",$res[0]);
	}
	
	
	$feed = $weather->addChild('feed');
	$feed->value = $weatherURL;
	$weather->addChild('link', $link);
	$loc = $weather->addChild('location');
	$loc->addAttribute('city', $city);
	$loc->addAttribute('region', $region);
	$loc->addAttribute('country', $country);
	$units = $weather->addChild('units');
	$units->addAttribute('temperature', $unit1);
	$cond = $weather->addChild('condition');
	$cond->addAttribute('text', $condition->text);
	$cond->addAttribute('temp', $condition->temp);
	$weather->addChild('img', $img1);

	for($i = 0;$i<5 ; $i++)
	{
		$forecast = $yweather->forecast[$i]->attributes();
		$f = $weather->addChild('forecast');
		$f->addAttribute('day', $forecast->day);
		$f->addAttribute('low', $forecast->low);
		$f->addAttribute('high', $forecast->high);
		$f->addAttribute('text', $forecast->text);
	}

	echo $weather->asXML();
	return;
}
?>