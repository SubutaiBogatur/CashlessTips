import React, {Component} from 'react';
import {Paper, Typography, Button} from '@material-ui/core';
import './MainPage.css';

export class MainPage extends Component {
    constructor(props) {
        super(props);
        this.state = {

        }
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
                                Здесь Вы можете получить или изменить текущий список контрольно-кассовых техник (ККТ),
                                а также ознакомиться с оценками и чаевыми, которые были
                                оставлены для каждого из официантов
                            </Typography>
                            <div className='main-page-paper-actions'>
                                <Button variant='raised'>
                                    Официанты
                                </Button>
                                <Button variant='raised'>
                                    ККТ
                                </Button>
                            </div>
                        </div>
                    </Paper>
                </div>
            </div>
        )
    }
}