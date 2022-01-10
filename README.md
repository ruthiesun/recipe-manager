# Sous-Chef
## Recipe creation tool

**Application overview:**

*Sous-Chef* is a recipe manager that allows the user to tweak recipes and record changes. Modified recipes 
are saved under the original recipe, forming a tree-like network of recipes. One recipe in the tree can be designated
as the final version of the recipe.

Future goals:
- Allowing recipe projects to be tagged (breakfast, drink, etc.)
- Allowing the user to upload pictures for each recipe
- Allowing the user to record comments on each recipe
- Allowing recipe projects to be searched with an autocomplete search feature
- Allowing rescaling of recipe ingredient quantities without needing the user to modify each ingredient

**Target audience:**

*Sous-Chef* is catered toward anyone who enjoys creating new recipes.

**Personal interest:**

I oftentimes modify recipes that I find online. It is difficult to remember all the modifications that I liked and 
those that have failed. *Sous-Chef* fixes these problems by providing a platform to record recipe drafts.

## User Stories
- As a user, I want to be able to initiate a new project
- As a user, I want to be able to add ingredients to a list of ingredients in a recipe
- As a user, I want to be able to add instructions to a list of instructions in a recipe
- As a user, I want to be able to view a recipe
- As a user, I want to be able to edit a recipe and overwrite it
- As a user, I want to be able to duplicate a recipe as a separate recipe draft
- As a user, I want to be able to assign the final recipe status to a recipe draft
- As a user, I want to be able to save my projects
- As a user, I want to be able to reload all my previously-saved projects

## Phase 4: Task 2
Fri Nov 26 14:49:47 PST 2021\
hot water: Loaded project\
Fri Nov 26 14:49:47 PST 2021\
cold water: Loaded project\
Fri Nov 26 14:49:47 PST 2021\
spicy water: Loaded project\
Fri Nov 26 14:49:51 PST 2021\
spicy water: Assigned final recipe\
Fri Nov 26 14:49:53 PST 2021\
spicy water: Added new draft\
Fri Nov 26 14:50:21 PST 2021\
spicier water: Started new project\
Fri Nov 26 14:50:30 PST 2021\
hotter water: Started new project

## Phase 4: Task 3
- Apply the composite pattern to the WorkingRecipe/Recipe relationship
- Apply the observer pattern to reduce coupling in the ui classes
  - Example: ProjectCollectionPanel is an observer of ProjectNameFrame
- Construct an interface so that the InstructionList and IngredientList classes have a common supertype