# Westjet - IBM Datapower Migration 

### Goal
The goal is to migrate services from IBM Datapower to multiple Micro services using Spring Boot. 

Each Datapower pipeline(service) has one to numerous nodes. 
As of now, we have discovered nodes that can perform one of the following tasks. (This list will grow as we migrate multiple microservices)
* XML Translation
* Pick a specific value from a XML

## Core Engine
The core engine is build with an aim to have all the features each node in IBM datapower is capable of.
1) The XML Transformations are done using XSLT translations.
2) The value selection is done using XPath

This core engine will later be extended by other Microservices to leverage the capabilities of Core engine.
The Node order, and passing Data to the next node is all controlled from the application yml of the service we are migrating.

## ProfileManagement
- ProfileManagement will be the first Microservice to be migrated.
- The API will first have to make a SOAP call to Sabre to get create a session and get a session token.
- With the session token Profile read call has to be performed.
