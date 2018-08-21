import React, {Component} from 'react';
import axios from 'axios'
import {Auth} from '../../util/Auth';
import {BrowserRouter, Redirect} from 'react-router-dom';
import {Paper, Button, FormControl, InputLabel, Input} from '@material-ui/core';
import {Route} from "react-router";
import {Header} from "./header/Header";

export class Main extends Component {
    constructor(props) {
        super(props);
        this.state = {

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
                    {/*<Route path='/stats/:type?/:value?/:date?'*/}
                           {/*render={({match, history}) => <Stats employees={this.state.employees}*/}
                                                                {/*projects={this.state.projects}*/}
                                                                {/*match={match}*/}
                                                                {/*history={history}/>}*/}
                    {/*/>*/}
                    {/*<Route path='/employees'*/}
                           {/*render={() => <Employees employees={this.state.employees}*/}
                                                    {/*projects={this.state.projects}/>}*/}
                    {/*/>*/}
                    {/*<Route path='/projects'*/}
                           {/*render={() => <Projects projects={this.state.projects}*/}
                                                   {/*reloadProjects={() => this.loadProjects()}/>}/>*/}
                </div>
            </BrowserRouter>
        )
    }
}