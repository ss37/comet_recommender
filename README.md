# comet_recommender
###### Chrome extension for content-based recommendation of upcoming talks on CoMeT


[CoMeT - Collaborative Management of Talks](http://halley.exp.sis.pitt.edu/comet/) is a website that describes and notifies about all talks conducted in University of Pittsburgh and Carnegie Mellon University.  


I developed a personalised content-based recommendation engine that displays all the upcoming talks based on the talks bookmarked by the user in the past. The [cometrecommender_server](https://github.com/ss37/comet_recommender/tree/master/cometrecommender_server) uses a RESTful web service to receive the user-specific RSS feeds for bookmarked talks and upcoming talks. I then used TF\*IDF (term-frequency and inverse document frequency) and cosine similarity to rank each upcoming talks. TF\*IDF and cosine similarity are the simplest word ranking algorithms used for information retrieval and content-based recommendation.


I also developed a custom Chrome extension in [cometrecommender_chrome_extension](https://github.com/ss37/comet_recommender/tree/master/cometrecommender_chrome_extension) that searches for CoMeT-related bookmarks in the user's web browser and sends the URL to the backend server for generating personalised list of recommended talks.


**Tools and Technologies used:** Java, Jersey RESTful Web services, Chrome Extension API, Porter Stemmer (to reduce words to their root form), JSoup HTML Parser, Rome library (for reading RSS feeds)

**Concepts:** content-based recommendation, adaptive presentation, TF\*IDF with cosine similarity

You can read more about it [here](http://ss37.github.io/comet_recommender/).
