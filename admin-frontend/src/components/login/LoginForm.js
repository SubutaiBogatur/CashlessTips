import React, {Component} from 'react';
import axios from 'axios'
import {Auth} from '../../util/Auth';
import {Redirect} from 'react-router-dom';
import {Paper, Button, FormControl, InputLabel, Input} from '@material-ui/core';
import './LoginForm.css'

const defaultPath = '/dashboard';

export class LoginForm extends Component {

    constructor(props) {
        super(props);
        this.state = {
            inn: '',
            password: ''
        }
    }

    login() {
        // axios.post('/api/auth', {
        //     username: this.state.telegram_uid,
        //     password: this.state.password,
        // }, {
        //     headers: Auth.withCsrf({})
        // })
        //     .then(result => {
        //         if (result.status === 200) {
        //             Auth.saveCredentials(this.state.telegram_uid, result.data['token']);
        //             this.props.history.push(defaultPath)
        //         }
        //         console.log(result.data)
        //     });
        Auth.saveCredentials(this.state.inn, '1111');
        this.props.history.push(defaultPath)
    }

    render() {
        if (Auth.isLogged()) {
            return <Redirect to={defaultPath}/>
        }

        return (
            <div className='login-main'>
                <Paper className='login-wrapper'>
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
                            onClick={() => this.login()}>
                        Войти
                    </Button>
                </Paper>
            </div>
        );
    }
}
