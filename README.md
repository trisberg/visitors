# Spring AMQP Stocks Demo for CloudFoundry

This app is a sample app featuring Spring Data and MongoDB. It is 
made to run on [CloudFoundry](http://cloudfoundry.com).

## Set Up

The app is a vanilla Spring MVC web application, so you can build with
Maven (or your tool of choice) and deploy with `vmc`, or use the
SpringSource ToolSuite CloudFoundry server tooling.

First create the services (the names are not significant).  Using the
command line:

    $ vmc create-service mongodb mongo-visitors

Then build and deploy:

    $ mvn package
    $ vmc push visitors --nostart --path target
    $ vmc bind-service mongo-visitors visitors
    $ vmc start visitors
    

## Basic Use Cases

### Capture visitor information

User opens the app in a browser, and the application captures
the IP address of the user as well as the Cloud Foundry router and
stores this information plus a timestamp in a Mongo database. The
application keeps track of the total number of visitors and the 10 
most recent visitors which are displayed on the main page.
