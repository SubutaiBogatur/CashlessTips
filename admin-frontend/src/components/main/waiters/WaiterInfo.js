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

export class WaiterInfo extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isEditingCard: true,
            cardNumber: this.props.waiter.cardNumber
        }
    }

    renderCardNumber() {
        if (this.state.isEditingCard) {
            return (
                <div className='waiter-info-bottom-wrapper'>
                    <TextField/>
                    <Button variant='raised'>
                        Сохранить
                    </Button>
                </div>
            )
        } else if (this.props.waiter.card_number !== null) {
            // TODO display card number
        } else {
            // TODO display 'add card' button
        }
    }

    render() {
        return (
            <ExpansionPanel className='waiter-info-expansion-panel' defaultExpanded>
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
                                Средний рейтинг: {this.props.waiter.rating}/5
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
                        Save
                    </Button>
                </ExpansionPanelActions>
            </ExpansionPanel>
        )
    }
}