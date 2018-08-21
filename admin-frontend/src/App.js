import React, {Component} from 'react';
import './App.css';
import {Redirect, Route, Switch} from "react-router";
import {LoginForm} from "./components/login/LoginForm";
import {PrivateRoute} from "./util/PrivateRoute";
import {Main} from "./components/main/Main";

class App extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Switch>
                <Redirect exact from='/' to='/dashboard'/>
                <Route exact path='/login' component={LoginForm}/>
                <PrivateRoute path='/dashboard' component={Main}/>
                {/*<Route path='/' component={Page404}/>*/}
            </Switch>
        );
    }
}

export default App;
