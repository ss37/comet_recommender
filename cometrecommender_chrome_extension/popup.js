var bookmarks = "";
var upcoming = "";
var query = "http://halley.exp.sis.pitt.edu/comet/utils/_rss.jsp";

function searchBookmarks(query) {
	function innerSearch(query){
	chrome.bookmarks.search(query, function(results) {
		if(results.length==2){
			for(var j=0; j<results.length; j++){
				if(results[j].title.indexOf("v=bookmark&user_id=")!=-1){
					bookmarks = results[j].title;
				}
				else{
					upcoming = results[j].title;
				}
			}
			getTalks(bookmarks, upcoming);
		}
		else{
			$("#results").html("Please bookmark 2 URLs from CoMeT as follows: <br />"
				+ "1. Bookmark the RSS feed for regular upcoming talks on CoMet. Click the RSS feeds box on the right section of <a href=\"http://halley.exp.sis.pitt.edu/comet/calendar.do\" target=\"_blank\">this webpage</a>.<br />"
				+ "2. Bookmark the RSS feed for the bookmarked talks on your profile page. Click the RSS feeds box on the right section of <a href=\"http://halley.exp.sis.pitt.edu/comet/myaccount.do?v=bookmark\" target=\"_blank\">this webpage</a>.<br />"
				+ "Once this is done, reload this extension.");
		}
	});
	}
	return innerSearch;
}

function getTalks(bookmarks, upcoming){
	this.json_data = "{ \"bookmarks\" : \""+bookmarks+"\", \"upcoming\" : \""+upcoming+"\"}";
	if(bookmarks.length!=0 && upcoming.length!=0) {
		$.ajax({
				url: "http://localhost:8080/cometrecommender/rest/talks/interesting",
				type: 'POST',
				data: json_data,
				dataType: 'json',
				contentType: "application/json",
				crossDomain: true,
				success: function(data) {
					$("#results").html("");
					for(var i=0; i<data.length; i++) {
						var a = document.createElement("a");
						a.href = data[i].url;
						a.target = "_blank";
						var text = document.createTextNode("["+(i+1)+"] "+data[i].title);
						a.appendChild(text);
						document.getElementById("results").appendChild(a);
						document.getElementById("results").appendChild(document.createElement("br"));
					}
				},
				error: function(data) {
					$("#results").html("Something went wrong while getting the recommended talks. Please try again later.");
				}
			}); 
	}
};

var s = searchBookmarks(query);
s(query);
