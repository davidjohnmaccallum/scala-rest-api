#!/bin/bash

sbt dist
docker build -t scala-rest-api:1.0 .