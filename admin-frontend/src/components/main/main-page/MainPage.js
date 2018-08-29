import React, {Component} from 'react';
import {Paper, Typography, Button, Divider, TextField, Dialog, DialogTitle, DialogActions} from '@material-ui/core';
import './MainPage.css';
import axios from 'axios';
import {Auth} from "../../../util/Auth";

export class MainPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            okDialogOpen: false,
            prevCardNumber: '',
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
            const cardNumber = result.data.cardNumber === null ? '' : result.data.cardNumber;
            this.setState({
                prevCardNumber: cardNumber,
                cardNumber: cardNumber
            })
        })
    }

    saveCard() {
        axios.get('/api/setInnInfo', {
            params: {
                inn: Auth.getUsername(),
                cardNumber: this.state.cardNumber
            }
        }).then(result => {
            this.setState({
                okDialogOpen: true,
                prevCardNumber: this.state.cardNumber
            });
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
                                Чтобы настроить работу с безналичными чаевыми через наш сервис, Вам нужно:
                                <br/>1. Добавить список своих ККТ (контрольно-кассовых техник) через вкладку “ККТ”
                                <br/>2. Добавить официантов через вкладку “Официанты”
                                <br/>По этим вкладкам Вы можете изменять список текущих ККТ, а также ознакомиться с оценками
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
                                {this.state.prevCardNumber
                                === '' &&
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
                <Dialog open={this.state.okDialogOpen}
                        onClose={() => this.setState({okDialogOpen: false})}
                        aria-labelledby='alert-dialog-title'
                        aria-describedby='alert-dialog-description'>
                    <DialogTitle id='alert-dialog-title'>
                        Номер карты сохранён
                    </DialogTitle>
                    <DialogActions>
                        <Button onClick={() => this.setState({okDialogOpen: false})} color='primary'>
                            Хорошо
                        </Button>
                    </DialogActions>
                </Dialog>
            </div>
        )
    }
}