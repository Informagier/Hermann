#!/bin/sh

set -e

./test.sh

git commit
git pull
