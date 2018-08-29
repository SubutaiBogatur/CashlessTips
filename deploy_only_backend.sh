set -e

ROOT_DIR=$(pwd)
BACKEND_DIR=/var/www/cashlesstips/backend
FRONTEND_DIR=/var/www/cashlesstips/frontend
STATIC_DIR=/var/www/cashlesstips/static
rm -r $BACKEND_DIR 2> /dev/null || true
mkdir -p $BACKEND_DIR

echo copying files to $BACKEND_DIR ...
cp -r $ROOT_DIR/cashless-tips-server/* $BACKEND_DIR
echo done

echo copying index.html to backend template dir...
cp $FRONTEND_DIR/build/index.html $BACKEND_DIR/src/main/resources/templates/
echo copying static files to $STATIC_DIR...
cp -r $FRONTEND_DIR/build/* $STATIC_DIR
rm $STATIC_DIR/index.html

echo restarting cashlesstips...
supervisorctl restart cashless-tips
