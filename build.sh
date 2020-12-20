#!/bin/sh

sbt dist
docker build -t scala-rest-api:1 .
