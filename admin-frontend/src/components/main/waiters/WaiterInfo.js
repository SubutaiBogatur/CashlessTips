import React, {Component} from 'react';
import './WaiterInfo.css'
import {
    ExpansionPanel,
    ExpansionPanelSummary,
    Typography,
    ExpansionPanelDetails,
    Paper,
    TextField,
    FormControlLabel,
    Divider,
    ExpansionPanelActions,
    Button
} from "@material-ui/core";
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import axios from "axios";
import {Auth} from "../../../util/Auth";

export class WaiterInfo extends Component {
    constructor(props) {
        super(props);
        this.state = {
            cardNumber: this.props.waiter.cardNumber,
        }
    }

    validateCardNumber() {
        return true; // TODO
    }

    save() {
        axios.get('/api/setWaiter', {
            params: {
                inn: Auth.getUsername(),
                name: this.props.waiter.name,
                cardNumber: this.state.cardNumber
            }
        }).then(result => {
            console.log(result.data)
        })
    }

    renderCardNumber() {
        return (
            <div className='waiter-info-bottom-wrapper'>
                <TextField className='waiter-info-card-number'
                           value={this.state.cardNumber}
                           margin='dense'
                           onChange={event => this.setState({ cardNumber: event.target.value })}
                           label='Номер карты'/>
            </div>
            // TODO add hint
        )
    }

    render() {
        return (
            <ExpansionPanel className='waiter-info-expansion-panel'>
                <ExpansionPanelSummary expandIcon={<ExpandMoreIcon/>}>
                    <div>
                        <Typography variant='subheading' style={{fontSize: '20px'}}>
                            {this.props.waiter.name}
                        </Typography>
                    </div>
                </ExpansionPanelSummary>

                <ExpansionPanelDetails className='waiter-info-details'>
                    <div className='waiter-info-top-wrapper'>
                        <div className='waiter-info-rating-wrapper'>
                            <Typography component='p' style={{fontSize: '18px'}}>
                                Средний рейтинг: {this.props.waiterInfo === undefined ? 'ещё неизвестен' : this.props.waiterInfo.avRate + ' / 5'}
                            </Typography>
                        </div>
                        <div className='waiter-info-tips-wrapper'>
                            <Typography component='p' style={{fontSize: '18px'}}>
                                Полученные чаевые: {this.props.waiter.tips_total} рублей
                            </Typography>
                        </div>
                    </div>
                    {this.renderCardNumber()}
                </ExpansionPanelDetails>
                <Divider/>
                <ExpansionPanelActions>
                    <Button size='small'
                            color='primary'
                            // disabled={!this.somethingChanged()}
                            onClick={() => this.save()}>
                        Сохранить
                    </Button>
                </ExpansionPanelActions>
            </ExpansionPanel>
        )
    }
}