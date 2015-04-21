Deployment-sphere
=======

Jenkins plugin to have a bird's eye view of your continuous deployment pipeline.

Status
------

[![Build Status](https://travis-ci.org/webdizz/deployment-sphere-plugin.png?branch=master)](https://travis-ci.org/webdizz/deployment-sphere-plugin)
[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/webdizz/deployment-sphere-plugin/trend.png)](https://bitdeli.com/free "Bitdeli Badge")

Dev Environment Requirements:
------

1. [Java 1.7](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
2. [Eclipse](https://eclipse.org/downloads/)
3. [Git](http://git-scm.com/downloads)
4. [Maven](https://maven.apache.org/download.cgi)
5. [Lombok](http://projectlombok.org/download.html)

Development cycle:
------

1. Сlone repository on your Computer, go there and run ```bash ./gradlew :pl:build```
2. Then run ```bash ./gradlew :pl:server```. By default Jetty will run on [http://localhost:8080/](http://localhost:8080/).

Add deployment-sphere-plugin to eclipse:
------

1. Install in IDE [lombok](http://projectlombok.org/download.html).
2. From Eclipse Marketplace install Gradle IDE.
3. Before adding project into IDE go to deployment-sphere-plugin and run ```bash ./gradlew :pl:build```.
4. In IDE go to Import -> Gradle-> Gradle Project select directory with project and click Build Model. After that just add it into IDE.

Install plugin on Jenkins:
------
1. Go to Jenkins dashboard. On the menu to the left, pick up ```Manage Jenkins``` option.
2. Select ```Manage plugins```.
3. Goto ```Advanced``` tab and use ```Upload plugin``` feature.

Jenkins configuration initial jobs:
------

1. On Jenkins dashboard create New Item "Build".
  * Tick "This build is parameterized" with any name. As an example use "**APP_PARAMETER**".
  * Add post-build Action "Collect Build Metadata" and "Application Name" put "**app-name**" in "Build version Pattern" put "**$APP_PARAMETER.{v}**" (part of pattern should match name, chosen on previous step)
2. Create New Item "Deploy".
  * Add post-build Action "Collect Deploy Metadata" in "Application name to deploy" put "**app-name**"

Building
--------
To build the plugin from source:

    ./gradlew :pl:build

To run Jenkins and test JPI:

    ./gradlew :pl:server

Build job-dsl.hpi to be installed in Jenkins:

    ./gradlew :pl:jpi


