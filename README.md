deployment-sphere
=======

Jenkins plugin to have a bird's eye view of your continuous deployment pipeline.

Status
------

[![Build Status](https://travis-ci.org/webdizz/deployment-sphere-plugin.png?branch=master)](https://travis-ci.org/webdizz/deployment-sphere-plugin)
[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/webdizz/deployment-sphere-plugin/trend.png)](https://bitdeli.com/free "Bitdeli Badge")

Building
--------
To build the plugin from source:

    ./gradlew :pl:build

To run Jenkins and test JPI:

    ./gradlew :pl:server

Build job-dsl.hpi to be installed in Jenkins:

    ./gradlew :pl:jpi
