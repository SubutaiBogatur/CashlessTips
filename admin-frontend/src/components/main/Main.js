import React, {Component} from 'react';
import axios from 'axios'
import {Auth} from '../../util/Auth';
import {BrowserRouter, Redirect} from 'react-router-dom';
import {Paper, Button, FormControl, InputLabel, Input} from '@material-ui/core';
import {Route} from "react-router";
import {Header} from "./header/Header";
import {MainPage} from "./main-page/MainPage";
import {Waiters} from "./waiters/Waiters";
import {Kkts} from "./kkts/Kkts";

export class Main extends Component {
    constructor(props) {
        super(props);
        this.state = {
            inn: Auth.getUsername()
        }
    }

    logout() {
        Auth.logout(this.props.history);
    }

    render() {
        return (
            <BrowserRouter>
                <div className='main-wrapper'>
                    <div className='header'>
                        <Header logout={() => this.logout()}/>
                    </div>
                    <Route path='/main'
                           render={() => <MainPage/>}/>
                    <Route path='/waiters'
                           render={() => <Waiters/>}/>
                    <Route path='/kkts'
                           render={() => <Kkts inn={this.state.inn}/>}/>
                </div>
            </BrowserRouter>
        )
    }
}
