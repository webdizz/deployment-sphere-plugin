Deployment Sphere
=======

[Jenkins plugin to have a bird's eye view of your continuous deployment pipeline](https://wiki.jenkins-ci.org/display/JENKINS/Deployment+Sphere+Plugin).

Status
------

[![Build Status](https://travis-ci.org/webdizz/deployment-sphere-plugin.png?branch=master)](https://travis-ci.org/webdizz/deployment-sphere-plugin)
[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/webdizz/deployment-sphere-plugin/trend.png)](https://bitdeli.com/free "Bitdeli Badge")

Development
=======

Dev Environment Requirements:
------

1. [Java 1.7](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
2. [Eclipse](https://eclipse.org/downloads/)
3. [Git](http://git-scm.com/downloads)
4. [Gradle](https://gradle.org/downloads/)
5. [Lombok](http://projectlombok.org/download.html)

Development cycle:
------

1. Сlone repository on your Computer, go there and run ```bash ./gradlew :pl:build```
2. Then run ```bash ./gradlew :pl:server```. By default [Jetty](http://www.eclipse.org/jetty/) will run on [http://localhost:8080/](http://localhost:8080/).

Add deployment-sphere-plugin to eclipse:
------

1. Install in IDE [lombok](http://projectlombok.org/download.html).
2. From Eclipse Marketplace install Gradle IDE.
3. Before adding project into IDE go to _deployment-sphere-plugin_ and run ```bash ./gradlew :pl:build```.
4. In IDE go to _Import -> Gradle-> Gradle Project_, select directory with project and click _Build Model_. After that just add it into IDE.

Install plugin on Jenkins local mode:
------
1. Go to Jenkins dashboard. On the menu to the left, pick up ```Manage Jenkins``` option.
2. Select ```Manage plugins```.
3. Install dependency plugins _H2 Database Plugin_.
4. Goto ```Advanced``` tab and use ```Upload plugin``` feature.

Building
--------
To build the plugin from source:

    ./gradlew :pl:build

To run Jenkins and test JPI:

    ./gradlew :pl:server

Build job-dsl.hpi to be installed in Jenkins:

    ./gradlew :pl:jpi


Usage
=======

Install plugin on Jenkins from [Jenkins Plugins](https://wiki.jenkins-ci.org/display/JENKINS/Plugins):
------
1. Go to Jenkins dashboard. On the menu to the left, pick up ```Manage Jenkins``` option.
2. Select ```Manage plugins```.
3. Install dependency plugins _H2 Database Plugin_.
4. Install _Deployment Sphere Plugin_.

Jenkins configuration initial jobs:
------

0. Go to ```Manage plugins```->```Configure System```.
  * Navigate to _Deployment Sphere configuration_ section;
  * Add environments you're working with;
  * Add applications names you're working with.
1. Create build project.
  * After configuration of build navigate to _Add post-build action_ and select _Collect Build metadata_;
  * Select _"Application Name"_ this project is responsible to build;
  * Modify _"Build version Pattern"_ if your versioning strategy is different by specifying your pattern;
  * Save project.
2. Create deploy project.
  * After configuration of deployment navigate to _Add post-build action_ and select _Collect Deploy metadata_;
  * Select application this project is responsible to deploy;
  * Save project.