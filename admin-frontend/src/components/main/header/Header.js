import React, {Component} from 'react';
import '../../../App.css'
import './Header.css'
import {Button, createMuiTheme, MuiThemeProvider} from '@material-ui/core';
import {Link} from 'react-router-dom';
import {blueGrey, purple, red} from "@material-ui/core/colors";

const theme = createMuiTheme({
    palette: {
        primary: { main: blueGrey[50] },
        secondary: { main: '#11cb5f' },
    },
});

export class Header extends Component {
    constructor(props) {
        super(props);
        this.state = {

        }
    }

    render() {
        return (
            <MuiThemeProvider theme={theme}>
                <div className='header-main'>
                    <div className='header-wrapper'>
                        <div className='header-nav'>
                            <div className='nav-item'>
                                <Link to='/main'>Главная</Link>
                            </div>
                            <div className='nav-item'>
                                <Link to='/waiters'>Официанты</Link>
                            </div>
                            <div className='nav-item'>
                                <Link to='/kkts'>ККТ</Link>
                            </div>
                        </div>
                        <div className='header-control'>
                            <Button variant='raised'
                                    color='primary'
                                    className='logout-button'
                                    onClick={this.props.logout}>
                                Выйти
                            </Button>
                        </div>
                    </div>
                </div>
            </MuiThemeProvider>
        )
    }
}