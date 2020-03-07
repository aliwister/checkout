#!/bin/bash

set -e

rm -rf package/ | true
mkdir package
cp -r server/* package/
rm -rf package/node_modules | true

zip -r deploy.zip package Dockerfile Dockerrun.aws.json .ebextensions