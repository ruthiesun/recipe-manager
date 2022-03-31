# Sous-Chef
## Recipe creation tool

**Application overview:**

*Sous-Chef* is a recipe manager that allows the user to tweak recipes and record changes. Modified recipes 
are saved under the original recipe, forming a tree-like network of recipes. One recipe in the tree can be designated
as the final version of the recipe. This allows the user to save new versions of recipes without modifying the original recipe.

Future goals:
- Allowing recipe projects to be tagged (breakfast, drink, etc.)
- Allowing the user to upload pictures for each recipe
- Allowing the user to record comments on each recipe
- Allowing recipe projects to be searched with an autocomplete search feature
- Allowing rescaling of recipe ingredient quantities without needing the user to modify each ingredient

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

## TODO
- Apply the composite pattern to the WorkingRecipe/Recipe relationship
- Apply the observer pattern to reduce coupling in the ui classes
  - Example: ProjectCollectionPanel is an observer of ProjectNameFrame
- Construct an interface so that the InstructionList and IngredientList classes have a common supertype