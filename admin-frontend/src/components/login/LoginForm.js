import React, {Component} from 'react';
import axios from 'axios'
import {Auth} from '../../util/Auth';
import {Redirect} from 'react-router-dom';
import {
    Paper,
    Button,
    FormControl,
    InputLabel,
    Input,
    Tab,
    Tabs,
    AppBar,
    DialogTitle,
    DialogActions, Dialog
} from '@material-ui/core';
import './LoginForm.css'

const defaultPath = '/main';

export class LoginForm extends Component {

    constructor(props) {
        super(props);
        this.state = {
            errorDialogOpen: false,
            type: 0,
            inn: '',
            password: ''
        }
    }

    handleSubmin() {
        if (this.state.type === 0) {
            this.login();
        } else {
            this.register();
        }
    }

    login() {
        axios.get('/api/getInnInfo', {
            params :{
                inn: this.state.inn,
                // password: this.state.password,
            }
        })
            .then(result => {
                Auth.saveCredentials(this.state.inn, 'token');
                this.props.history.push(defaultPath)
            }, error => {
                this.setState({
                    errorDialogOpen: true
                })
            });
    }

    register() {
        axios.get('/api/setInnInfo', {
            params :{
                inn: this.state.inn,
                // password: this.state.password,
            }
        })
            .then(result => {
                Auth.saveCredentials(this.state.inn, 'token');
                this.props.history.push(defaultPath)
            }, error => {
                console.log(error)
            });
    }

    render() {
        if (Auth.isLogged()) {
            return <Redirect to={defaultPath}/>
        }

        return (
            <div className='login-main'>
                <Paper className='login-wrapper'>
                    <Tabs value={this.state.type} onChange={(event, value) => this.setState({type: value})}>
                        <Tab label="Логин" />
                        <Tab label="Регистрация" />
                    </Tabs>
                    <div className='form-control'>
                        <FormControl className='form-element'>
                            <InputLabel htmlFor='name-simple'>ИНН вашей компании</InputLabel>
                            <Input id='name-simple'
                                   value={this.state.inn}
                                   onChange={event => this.setState({inn: event.target.value})}/>
                        </FormControl>
                    </div>
                    {/*<div className='form-control'>*/}
                        {/*<FormControl className='form-element'*/}
                                     {/*aria-describedby='name-helper-text'>*/}
                            {/*<InputLabel htmlFor='name-helper'>Password</InputLabel>*/}
                            {/*<Input id='name-helper'*/}
                                   {/*className='input'*/}
                                   {/*fullWidth*/}
                                   {/*value={this.state.password}*/}
                                   {/*onChange={event => this.setState({password: event.target.value})}/>*/}
                            {/*/!*<FormHelperText id='name-helper-text'>Insert password</FormHelperText>*!/*/}
                        {/*</FormControl>*/}
                    {/*</div>*/}
                    <Button variant='contained'
                            color='primary'
                            onClick={() => this.handleSubmin()}>
                        {this.state.type === 0 ? 'Войти' : 'Зарегистрироваться'}
                    </Button>
                </Paper>
                <Dialog open={this.state.errorDialogOpen}
                        onClose={() => this.setState({errorDialogOpen: false})}
                        aria-labelledby='alert-dialog-title'
                        aria-describedby='alert-dialog-description'>
                    <DialogTitle id='alert-dialog-title'>
                        Такой ИНН ещё не зарегистрирован!
                    </DialogTitle>
                    <DialogActions>
                        <Button onClick={() => this.setState({errorDialogOpen: false})} color='primary'>
                            ОК
                        </Button>
                    </DialogActions>
                </Dialog>
            </div>
        );
    }
}
