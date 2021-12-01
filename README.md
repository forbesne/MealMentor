# Meal Mentor
---
## Introduction

Meal Mentor allows users to create a weekly meal plan. They can search through a variety of recipes and find the ingredients list and instructions.

Nutritional information is provided and allows users to track their daily calories.

Meal Mentor creates a shopping list for the user based on the items they will need for the week.
## Storyboard

[Storyboard in Invision](https://projects.invisionapp.com/freehand/document/4kpRuyrtp)
## Requirements

#### 1. As a person that cooks, I want to be able to plan my meals ahead of time, so that I know what I need to cook each day.

*Example*

**Given**: A feed of recipe data is available   
  **When**: The user chooses to add a meal for Monday dinner  
  **When**: The user selects Lasagna  
  **Then**: Lasagna is saved for Monday dinner  

*Example*

**Given**: A feed of recipe data is available   
  **When**: The user searches for “chicken”   
  **Then**: Meal Mentor will return recipes containing chicken

#### 2.	As a person that wants to be healthy, I want to know how many calories are in what I eat, so that I can track my daily calorie intake.

*Example*

**Given**: A feed of recipe data including calories is available  
  **When**: The user adds a recipe with 120 calories to their meal plan for Monday  
  **Then**: 120 calories will be added to their daily calorie total for Monday

*Example*

**Given**: A feed of recipe data including calories is available    
  **Given**: The user has 120 calories in their daily calorie total for Monday    
  **When**: The user adds a recipe with 250 calories to their meal plan for Monday    
  **Then**: The user has 370 calories in their daily calorie total for Monday

#### 3.	As a person that buys groceries, I want to know what I will need for the week, so that I can avoid purchasing items I don’t need.

*Example*

**Given**: The user has a valid account and meals saved in that account.    
  **When**: The user generates a shopping list.   
  **Then**: The user will see a shopping list of items with quantities needed.

*Example*

**Given**: The user has a valid account and no meals saved in that account.   
  **When**: The user generates a shopping list.   
  **Then**: The user will see an error, indicating no items in shopping list.

## Class Diagram
![class diagram](https://user-images.githubusercontent.com/64596547/144326577-db9eb4c1-ceb1-4131-b89c-0771e85be93b.JPG)

## Class Diagram Description

**com.mealmentor.enterprise**

- Functions as the centralized head of the entire application
- EnterpriseApplication strings up the framework for our application
- MealMentorController acts as the “main” method for our application that will call out to and use all other classes.

**com.mealmentor.enterprise.dto**

- The “nouns” of our application

**com.mealmentor.enterprise.service**

- A series of polymorphic methods/contracts that request and receive data from DAOs

**com.mealmentor.enterprise.dao**

- A series of basic polymorphic methods that allow for the sending/retrieval of data to user/FoodItem databases.

## JSON Schema

This is what we plan to export to another app.

> {"type": "object", "properties": {"calories":{"type": "integer"}, "name" : {"type": "string"}, "ingredients : {"type" : "array", "items": {"type”: "string"}, "uniqueItems" : true}}} 
## Team Members and Roles

DevOps/Product Owner/Scrum Master/GitHub Admin: Natalie Forbes

UI Specialists: Sri Lakshmi Sai Surapaneni, Archana Thapa

Business Logic/Persistence: Jeffrey Benton, John Adkerson
## Standup

Microsoft Teams

Monday at 4:00 PM     
Friday at 7:00 PM
