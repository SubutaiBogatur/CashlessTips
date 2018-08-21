import React from 'react';
import {Route, Redirect} from 'react-router-dom';
import {Auth} from './Auth';

export const PrivateRoute = ({component: Component, ...rest}) => (
    <Route {...rest} render={props => (
        Auth.isLogged()
            ? <Component {...props} />
            : <Redirect to={{pathname: '/login', state: {from: props.location}}}/>
    )}/>
);