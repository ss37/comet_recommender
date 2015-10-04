---
layout: post
title: Developing CoMeT Recommender
---

######Content-based Recommendation
Content-based recommendation is one of the most basic kind of recommendation. It uses the actual content of the resource liked/viewed by the user, looks up for recurring keywords and ranks newer resources based on the similarity of the newer resource with the ranked keywords.  

For this project, I used TF*IDF technique (term-frequency and inversed document frequency). Based on the summary of the talks that the user previously bookmarked, it generates an ordered list of keywords. It then uses this list as a reference to find the recommendation factor for upcoming talks. Since these summaries are part of a web page, I used a number of filters to extract the meaningful keywords from the bookmarked talks webpages.  

######How It's Done
* I used an RSS feed consumer to retrieve all the URLs of the talks bookmarked by the user. For this I used **Rome library**.  
* Next, I retrieved each HTML document of the bookmarked talks and used an HTML parser to extract all the unwanted HTML tags and scripts from the document. This was when I realised the importance of Semantic Web. Web in its free-form nature needs to organized for its optimal use. I used **JSoup HTML parser** to extract the raw content of the bookmarked talks web pages.  
* I retrieved each word from the raw content, removed a set of stop-words, which are frequently occurring words (such as a, an, was, is, and so on), that add no meaning when used by itself. I used a set of stop-words from [this website](http://www.textfixer.com/resources/common-english-words.txt).  
* Then, I stemmed the remaining words to their root form. Words may seem different but they belong to the same contextual classification - for example, scientific and science would return sci- as their root form. I used **[Porter Stemming Algorithm](http://tartarus.org/martin/PorterStemmer/)** to extract the stem form of each keyword.  
* I generated a keyword model using these set of keywords using TF\*IDF. TF or Term Frequency is calculated by adding all the occurrences of the keyword in the document. IDF or Inverse Document Frequency is calculated by taking the log of inverse ratio of number of documents the word has occurred in a given set of documents. The resultant product TF\*IDF adds a weight to the occurrence of the word, whether it was spread out over the given set of documents or whether it occurs only in few documents thereby adding meaning only to those documents. This keyword model is used a baseline to compare with the keyword model generated for upcoming talks.  
* For each of those upcoming talks, cosine similarity is used to check how close the upcoming talk description is in the vector space of the previously bookmarked talks. Accordingly, the cosine values are used to arrange the list of upcoming talks, with most relevant talks on top.  
![Image of flow diagram](https://raw.githubusercontent.com/ss37/comet_recommender/gh-pages/public/images/flow_diagram.bmp) 


######Chrome extension and REST API
Building a Chrome extension was fun. I had never built a Chrome extension before, but the documentation was good enough to create one in a day.  

I am familiar with the Google API and their documentation, so I created an extension that searched for 2 bookmarks in the browser - one for upcoming talks RSS feed and the other for bookmarked talks RSS feed.  

I then created a RESTful web service that used these bookmark URLs to order the upcoming talks, and sends them back to the browser. I used RESTful web service because it is one of the easiest to create. It uses HTTP methods (GET, PUT, POST, DELETE) to perform CRUD-like operations. Since, I needed only a list of talks from the server, I chose to develop a RESTful web service.  

The browser then displays the ordered upcoming talk list in the popup as shown below. So simple!  

![Loading recommended list of upcoming talks](https://raw.githubusercontent.com/ss37/comet_recommender/gh-pages/public/images/screenshot_1.JPG)  

![Viewing recommended list of upcoming talks](https://raw.githubusercontent.com/ss37/comet_recommender/gh-pages/public/images/screenshot_2.JPG)  

![Recommended list of upcoming talks](https://raw.githubusercontent.com/ss37/comet_recommender/gh-pages/public/images/screenshot_3.JPG)  

Since most of the talks I bookmarked were related to Human-Centered Computing and Machine Learning, the topics related to Social Sciences and Management have moved to the bottom of the list of upcoming talks.
