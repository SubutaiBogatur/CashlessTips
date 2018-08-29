import React, {Component} from 'react';
import './Waiters.css'
import {WaiterInfo} from "./WaiterInfo";
import {Button} from "@material-ui/core";
import {AddWaiterDialog} from "./AddWaiterDialog";
import {Auth} from "../../../util/Auth";
import axios from 'axios';

export class Waiters extends Component {
    constructor(props) {
        super(props);
        this.state = {
            addDialogOpen: false,
            waiters: []
        }
    }

    componentWillMount() {
        this.loadWaiters()
    }

    loadWaiters() {
        axios.get('/api/listWaitersByInn', {
            params: {
                inn: Auth.getUsername()
            }
        }).then(result => {
            this.setState({
                waiters: result.data.map(entry => {
                    return {
                        id: entry.id,
                        name: entry.name,
                        rating: 'NA',
                        tips_total: 'NA',
                        cardNumber: entry.cardNumber
                    }
                })
            });
        });
    }

    render() {
        return (
            <div className='waiters-main'>
                <div className='waiters-wrapper'>
                    <div className='waiters-add-button'>
                        <Button variant='raised'
                                color='primary'
                                onClick={() => this.setState({addDialogOpen: true})}>
                            Добавить официанта
                        </Button>
                    </div>
                    <div className='waiters-list'>
                        {this.state.waiters.map(waiter =>
                            <WaiterInfo key={waiter.id}
                                        waiter={waiter}/>
                        )}
                    </div>
                </div>
                <AddWaiterDialog open={this.state.addDialogOpen}
                                 updateWaiters={() => this.loadWaiters()}
                                 handleClose={() => this.setState({addDialogOpen: false})}/>
            </div>
        )
    }
}