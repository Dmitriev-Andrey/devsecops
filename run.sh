#!/bin/bash

mkdir -p devsecops/output
chmod 777 devsecops/output
cd devsecops
./gradlew bootRun
