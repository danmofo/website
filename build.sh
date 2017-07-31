#!/usr/bin/env bash

# Website build script

set -x

cd src/main/resources/db
flyway info
flyway clean
flyway migrate
