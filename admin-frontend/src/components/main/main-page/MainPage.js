import React, {Component} from 'react';
import {Paper, Typography, Button, Divider, TextField} from '@material-ui/core';
import './MainPage.css';
import axios from 'axios';
import {Auth} from "../../../util/Auth";

export class MainPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            cardNumber: ''
        }
    }

    componentWillMount() {
        this.loadData()
    }

    loadData() {
        axios.get('/api/getInnInfo', {
            params: {
                inn: Auth.getUsername()
            }
        }).then(result => {
            this.setState({
                cardNumber: result.data.cardNumber === null ? '' : result.data.cardNumber
            })
        })
    }

    saveCard() {
        axios.get('/api/setInnInfo', {
            params: {
                inn: Auth.getUsername(),
                cardNumber: this.state.cardNumber
            }
        })
    }

    render() {
        return (
            <div className='main-page'>
                <div className='main-page-wrapper'>
                    <Paper>
                        <div className='main-page-paper'>
                            <Typography variant='headline'
                                        component='h3'
                                        style={{textAlign: 'center', fontSize: '25px'}}>
                                Добрый день!
                            </Typography>
                            <br/>
                            <Typography component='p' style={{fontSize: '18px'}}>
                                Чтобы настроить работу с безналичными чаевыми через наш сервис Вам нужно:
                                1. Добавить список своих ККТ (контрольно-кассовых техник) через вкладку “ККТ”
                                2. Добавить официантов через вкладку “Официанты”

                                По этим вкладкам Вы можете изменять список текущих ККТ, а также ознакомиться с оценками
                                и чаевыми, которые были оставлены для каждого из официантов.
                            </Typography>
                            <div className='main-page-paper-actions'>
                                <div className='main-page-button-wrapper'>
                                    {/*<Button variant='raised'*/}
                                            {/*color='primary'>*/}
                                        {/*Официанты*/}
                                    {/*</Button>*/}
                                    {/*<Button variant='raised'*/}
                                            {/*color='primary'>*/}
                                        {/*ККТ*/}
                                    {/*</Button>*/}
                                </div>
                            </div>
                            <Divider/>
                            <div className='main-page-card-block-wrapper'>
                                {this.state.cardNumber === '' &&
                                <Typography component='p' style={{fontSize: '18px'}}>
                                    Вы еще не ввели номер карты, безналичные чаевые пока недоступны!
                                </Typography>}
                                <div className='main-page-card-input-wrapper'>
                                    <TextField className='main-page-card-number'
                                               value={this.state.cardNumber}
                                               onChange={event => this.setState({cardNumber: event.target.value})}
                                               label='Номер карты'/>
                                    <div className='main-page-save-card-button-wrapper'>
                                        <Button variant='outlined'
                                                onClick={() => this.saveCard()}>
                                            Сохранить
                                        </Button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </Paper>
                </div>
            </div>
        )
    }
}