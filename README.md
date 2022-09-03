Qawafel Careers (Senior Android Engineer)

[Goal]
This assignment aims to have you work with some of the tools that we use every
day in Qawafel. It is designed to check your coding and problem-solving skills.

[Problem description]
We ask you to implement an Android app that presents an overview of
documents retrieved from [Open Library] (https://openlibrary.org).

● [DONE] The first screen should present a list of documents (the document list
screen). This list is populated with query results from Open Library and the
user should be able to specify a search query. 
● [DONE] Each list item on the document list screen displays a document's title and
author.
● [DONE] By clicking on any of the items, a document details screen should be
presented. This should include:
○ title, author, list of ISBNs (up to 5 entries) and cover image.
○ If the user clicks on the author or title from the document details
screen search results for that author or title should be presented in
the document list screen.   

Main Requirements
● Kotlin.
● Coroutines with flow
● High code quality and reusability.
● Clean architecture (MVVM or MVI).
● Unit Tests
● Proper unit, ideally measured.
● Care about the internet connection and the status for each API request.
● Some type of caching is necessary like if there's no internet connection so
you'll list the previous cached list.
● The UI is fitting in different screen resolutions.
● a private GitHub/Bitbucket repo.
    
Bonus Points
● Jetpack Compose for the UI.
● Modularity as design pattern (Each feature in a separate module).
● Room database for caching last list and details user search for it for the
caching feature.
● Navigation Component.
● Caring about memory leaks.
● Some UI tests.


Hints
● We prefer clean UX but we will not evaluate you on your Photoshop skills,
feel free to use icons from any design page.
● Third-party libraries are allowed.
● Please provide a readme with your thoughts.