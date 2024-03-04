# My Personal Project

## Project Proposal

This application is an **item-lookup tool** which will allow users to 
look up specific items in a clothing store. The details of the particular
item lookup include its availability, name, colour, size and location of availability.
With this design, the intention of this application is for stores to implement, as it will
assist and help maximize sales for their clients. As such,
the intended users are for store employees (internal use). Furthermore, this project is of interest to me 
because having used a similar tool, I think it would be interesting and very cool
to create my own version of it.




## User Stories
- As a user, I want to be able to _view the list of items_ in the store inventory
- As a user, I want to be able to _add items_ to the store inventory and specify the item code, name, colour, size
- As a user, I want to be able to _update the stock count_ of a specific item
- As a user, I want to be able to _select a specific item_ and view all the details
- As a user, I want to be able to _remove an item_ from the store inventory
- As a user, I want to be given the option to _save the inventory_ to file
- As a user, I want to be given the option to _reload the inventory_ from file

## Instructions for Grader
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by clicking onto the
add items tab and filling in the form to then clicking enter
- You can generate the second required action related to the user story "adding multiple Xs to Y" by clicking on a 
specific row and clicking the red X button to the right to remove that item from the list
- You can locate my visual component on the main menu tab, as well as in the View All tab (the red X button to remove 
item)
- You can save the state of my application by clicking the save button in the View All tab
- You can reload the state of my application by clicking the load button in the View All tab

## Phase 4: Task 2
**Sample of events:**
Shows 4 new actions (add new item, remove item, sort by status and sort by name)

Tue Nov 28 10:22:34 PST 2023
Successfully added new item

Tue Nov 28 10:22:38 PST 2023
Successfully removed item

Tue Nov 28 10:22:39 PST 2023
Table sorted by status

Tue Nov 28 10:22:40 PST 2023
Table sorted by name


## Phase 4: Task 3

One way to refactor my code to improve my design would be to improve readability of the code.
For instance, the names of some variables and methods could be more descriptive. An example would be in 
the MainMenu class, the method image() could be renamed to placeImageOnScreen(), so that whoever is reading the code
and trying to understand it will know exactly what the method does. Some of my methods throughout also have quite long 
methods. This could be refactored to improve readability by creating more helper classes with descriptive names. This 
would make it quick and easy to understand what the method is intended to do. By creating shorter methods, this helps 
reinforce the single responsibility principle, such that if any changes are needed, this will lead to fewer errors, and
it will make it easier to maintain. Also, I could have introduced exception handling. After looking over my 
code, there are times when I could have introduced an "Out of stock exception", in both the item and store classes. More 
specifically, the exception could be used in the statusUpdate() method or stockCountTotal(). Again, this improves 
readability and understanding the code. Finally, I have noticed some duplication of code. For example, sortStatus() and 
sortName() have near identical code but similar functionality. Refactoring this by taking the duplicate code and 
making a new method to eliminate the duplication would be a good idea. This is especially important if any changes are 
made in the future which could affect both methods, and as well the new method could be reused in the future as well.

I could also possibly implement the Composite design pattern. This is so that in the future instead of categorizing to
just item types, there could be different classes for different item types. This would be an example of utilizing the 
leaf structure and the store itself would be the composite - which could lead to more stores as well. The composite 
pattern makes it easier to add new types of data which is particularly useful for this store inventory project, if it
continues to expand and grow.


