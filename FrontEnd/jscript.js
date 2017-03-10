$(document).ready(function(){

//Use of jQuery timeago function 
jQuery("time.timeago").timeago();

//start index for iterating in API
var startIndex = 0;
//number of articles/videos to pull every time
var count = 10;
//total number of videos/articles pulled so far
var videoCount = 0;
var articleCount = 0;

//begin by loading videos by default
loadVideos();

//check if navigation is clicked on either articles or videos buttons and swap active class
$('.nav span').on('click',function(){

	if ( $('.articles').hasClass('active') )
	{
		$('.articles').removeClass('active');
		$('.videos').addClass('active');
	}
	else{
		if ( $('.videos').hasClass('active') )
		{
			$('.videos').removeClass('active');
			$('.articles').addClass('active');
		}
	}
});

//if articles button is clicked, reset all values, empty playlist and load articles
$('.articles').click(function(){
	$('.panel-group').empty();
	$('.morevideos').empty();
	$('.morearticles').empty();
	startIndex = 0;
	articleCount = 0;
	loadArticles();
});

//if videos button is clicked, reset all values, empty playlist and load articles
$('.videos').click(function(){
	$('.panel-group').empty();
	$('.morevideos').empty();	
	$('.morearticles').empty();
	startIndex = 0;
	videoCount = 0;
	loadVideos();
});

//if more articles button is clicked, increment start index by article count, remove preivous more button and load articles
$('.morearticles').click(function(){
	startIndex = startIndex + articleCount;
	$('.morearticles').empty();
	loadArticles();
});

//if more videos button is clicked, increment start index by video count, remove preivous more button and load videos
$('.morevideos').click(function(){
	startIndex = startIndex + videoCount;
	$('.morevideos').empty();
	loadVideos();
});

//take time stamp and translate it to mm/dd/yyyy format
function translateTimestamp(timestamp){
	var date = timestamp.split('T');
	var time = date[1].split('+');
	var dashDate = date[0].split('-');
	var year = dashDate[0];
	var month = dashDate[1].replace(/^0+/, '');;
	var day = dashDate[2].replace(/^0+/, '');;
	var newTimestamp = month + '/' + day + '/' + year;
	return newTimestamp;
}

//take duration value and translate it to mm : ss format
function translateDuration(duration){
	var minutes = Math.floor(duration / 60);
	var seconds = duration - minutes * 60;
	var time = minutes + ":" + seconds;

	return time;
}

//function that handles leading zeros in the numbering of videos and articles
function zeroInCount(count){
	var digit = "";
	if(count<10){
		digit = "0";
	}
	return digit;
}

//main function for loading videos
function loadVideos(){

	var videosUrl = 'https://ign-apis.herokuapp.com/videos?startIndex=' + startIndex + '&count=' + count + '&callback=?';

	//iterate through API to get JSON data
	$.getJSON(videosUrl, function(data){

		for (var i = 0, j = startIndex; j < (startIndex+count); i++, j++)
		{
			//save important JSON data to be used later
			var name = data.data[i].metadata.name;
			var description = data.data[i].metadata.description;
			var publishDate = data.data[i].metadata.publishDate;
			var longTitle = data.data[i].metadata.longTitle;
			var duration = data.data[i].metadata.duration;
			var url = data.data[i].metadata.url;
			var slug = data.data[i].metadata.slug;
			var networks = data.data[i].metadata.networks;
			var state = data.data[i].metadata.state;
			var thumbnails = data.data[i].thumbnails;
			var thumbnail_url = data.data[i].thumbnails[2].url;

			//formatted translations of times, date and description
			var formattedTime = translateDuration(duration);
			var formattedDate = translateTimestamp(publishDate);
			var timeSince = jQuery.timeago(publishDate);

			//Replace all single quotes with &#39; to be recognized in browser and not break the code which displays this
			var formattedDescription = description.replace("'", "&#39;");


			//check for and handle any null long titles to avoid blank lines 
			if(longTitle==null){
				longTitle = name;
			}

			//handle live videos by having an alternate duration value
			if(duration==null){
				duration = 'LIVE!';
				formattedTime = duration;
			}

			//increment total video count
			videoCount++;

			//append the data in correct format to collapsible panel
			$('.panel-group').append(
				//panel title which can be clicked to collapse. This is an accordion style group of panels. The data-parent value allows only one panel to be opened at a time.				
				"<div class='panel panel-default'> <span class = 'redbox'></span>  <span class = 'numbering'>" + zeroInCount(videoCount) + videoCount + "<span class = 'duration'>" + formattedTime + "</span>" +
				"</span><a class='panelheading' data-toggle='collapse' data-parent='#accordion' href='#collapse" + videoCount +"'>"
				+ "<span class = 'title'>" + name + "</span><br>" 
				+ "<span class = 'description'>" + longTitle +  "<br><br></span></a>" +
				"<div id='collapse" + videoCount +"' class='panel-collapse collapse'><div class='panel-body" + videoCount + "'>")

			//append thumbnail, link and formatted description to the panel's body. Formatted description appears when user hovers over image for added visibility of what the article is about. 
			$('.panel-body' + videoCount).append("<a class='thumbnail' title='"+ formattedDescription +"' href='" + url + "'>" + "<img src=" + thumbnail_url +"></a>") + "</div></div></div>";

		}

		//more videos button apended to bottom of panels
		$('.morevideos').append("</div><div class = 'morevideos'> SEE MORE VIDEOS...</div>");
	});
}

//main function for loading articles
function loadArticles(){

	var articlesUrl = 'https://ign-apis.herokuapp.com/articles?startIndex=' + startIndex + '&count=' + count + '&callback=?';

	//iterate through API to get JSON data
	$.getJSON(articlesUrl, function(data){

		for (var i = 0, j = startIndex; j < (startIndex+count); i++, j++)
		{
			//save important JSON data to be used later
			var thumbnails = data.data[i].thumbnails;
			var thumbnail_url = data.data[i].thumbnails[2].url;
			var headline = data.data[i].metadata.headline;
			var networks = data.data[i].metadata.networks;
			var state = data.data[i].metadata.state;
			var slug = data.data[i].metadata.slug;
			var subHeadline = data.data[i].metadata.subHeadline;
			var publishDate = data.data[i].metadata.publishDate;
			var articleType = data.data[i].metadata.articleType;

			//formatted translations of time, date and description
			var formattedDate = translateTimestamp(publishDate);
			var timeSince = jQuery.timeago(publishDate);
			var formattedSubheadline = subHeadline.replace("'", "&#39;");

			//divide publish date and save values to be used in URL
			var date = publishDate.split('-');
			var year = date[0];
			var month = date[1];
			var dateDay = date[2].split('T');
			var day = dateDay[0];

			//URL format for reaching articles on IGN.com
			var url = 'http://www.ign.com/articles/' + year + '/' + month + '/' + day + '/' + slug;

			//increment total article count
			articleCount++;

			//append the data in correct format to collapsible panel
			$('.panel-group').append(
				//panel title which can be clicked to collapse. This is an accordion style group of panels. The data-parent value allows only one panel to be opened at a time.				
				"<div class='panel panel-default'> <span class = 'redbox'></span><span class = 'numbering'>" + zeroInCount(articleCount) + articleCount + "<span class = 'date'>" + formattedDate + "</span>" +
				"</span><a class='panelheading' data-toggle='collapse' data-parent='#accordion' href='#collapse" + articleCount +"'>"
				+ "<span class = 'title'>" + headline + "</span><br>" 
				+ "<span class = 'timesince'>" + timeSince + " - </span><span class = 'subheadline'>" + subHeadline +  "</span>" 
				+ "<br><br></a><div id='collapse" + articleCount +"' class='panel-collapse collapse'><div class='panel-body" + articleCount + "'>")

			//append thumbnail, link and formatted description to the panel's body. Formatted description appears when user hovers over image for added visibility of what the article is about.
			$('.panel-body' + articleCount).append("<a class='thumbnail' title='"+ formattedSubheadline +"' href='" + url + "'>" + "<img src=" + thumbnail_url +"></a>") + "</div></div></div>";

		}
		
		//more videos button apended to bottom of panels
		$('.morearticles').append("</div><div class = 'morearticles'> SEE MORE ARTICLES...</div>");
	});
}


});