#!/bin/sh

set -e

./test.sh

git pull
git push

