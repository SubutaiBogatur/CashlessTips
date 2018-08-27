#!/bin/bash
set -e

ROOT_DIR=$(pwd)
BACKEND_DIR=/var/www/cashlesstips/backend
FRONTEND_DIR=/var/www/cashlesstips/frontend
STATIC_DIR=/var/www/cashlesstips/static
rm -r $BACKEND_DIR 2> /dev/null
rm -r $FRONTEND_DIR 2> /dev/null
rm -r $STATIC_DIR 2> /dev/null
mkdir -p $BACKEND_DIR
mkdir -p $FRONTEND_DIR
mkdir -p $STATIC_DIR

echo copying files to $BACKEND_DIR and $FRONTEND_DIR...
cp -r $ROOT_DIR/cashless-tips-server/* $BACKEND_DIR
cp -r $ROOT_DIR/admin-frontend/* $FRONTEND_DIR
echo done

cd $FRONTEND_DIR
echo installing frontend dependencies...
npm install --save
echo building frontend...
npm run build

echo copying index.html to backend template dir...
cp $FRONTEND_DIR/build/index.html $BACKEND_DIR/src/main/resources/templates/
echo copying static files to $STATIC_DIR...
cp -r $FRONTEND_DIR/build/* $STATIC_DIR
rm $STATIC_DIR/index.html

echo restarting cashlesstips...
supervisorctl restart cashlesstips
